package com.sky.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;


/**
 * 照相拍照、图库选择功能
 * 
 * @author sky
 * 
 */
public class PhotoHelper {

    private static final String TAG = "PhotoHelper";

    private static final String BITMAP_PROP_NAME = "data";

    /**
     * 拍照请求码
     */
    public final static int FROM_CAMERA = 1;

    /**
     * 图库请求码
     */
    public final static int FROM_MEDIA = 2;

    /**
     * 截图请求码
     */
    public final static int FROM_CROP = 3;

    /**
     * 来源系统分享
     */
    public final static int FROM_SYSTEM = 4;

    /**
     * 请求成功的回调
     */
    private PhotoResultCallback callback = null;

    private Activity context = null;

    /**
     * 截图参数
     */
    private CropParam cropParam = null;

    /**
     * 标记是否需要截图
     */
    private boolean needCrop = false;

    /**
     * 当前的请求码
     */
    private int source;

    /**
     * 保存拍照后的图片文件
     */
    private File savedTakePhotoFile;

    public PhotoHelper(Activity context) {
	this.context = context;
    }

    public interface PhotoResultCallback {
	/**
	 * @param savedCropPhotoFile
	 *            返回保存截图后的的图片文件
	 */
	void onResult(File savedCropPhotoFile);
    }

    /**
     * 该构造函数只在拍照时有的机子会把本进程杀死，然后拍照返回到界面的时候 去做的复原被回收的参数的构造函数
     * 
     * @param context
     * @param cropParam
     * @param savedTakePhotoFile
     * @param source
     */
    public PhotoHelper(Activity context, PhotoResultCallback callback,
	    CropParam cropParam, File savedTakePhotoFile, int source) {
	this.context = context;
	this.callback = callback;
	this.cropParam = cropParam;
	this.savedTakePhotoFile = savedTakePhotoFile;
	this.source = source;
    }

    /**
     * 裁剪图片 Continue to crop bitmap.
     * 
     * @param data
     */
    public void doCrop(Intent data) {
	needCrop = false;
	Intent intent = new Intent();
	switch (source) {
	case FROM_CAMERA:
	    Uri cropedUri = null;
	    if (!android.os.Build.MANUFACTURER.equals("HUAWEI")) {
		Uri imgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		ContentResolver cr = context.getContentResolver();
		Uri fileUri = Uri.fromFile(savedTakePhotoFile);
		context.sendBroadcast(new Intent(
			Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, fileUri));
		try {
		    Thread.sleep(2000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		Cursor cursor = cr.query(imgUri, null,
			MediaStore.Images.Media.DISPLAY_NAME + "='"
				+ savedTakePhotoFile.getName() + "'", null,
			null);
		if (cursor != null && cursor.getCount() > 0) {
		    cursor.moveToLast();
		    long id = cursor.getLong(0);
		    cropedUri = ContentUris.withAppendedId(imgUri, id);
		}
	    }
	    if (cropedUri == null) {
		cropedUri = Uri.fromFile(savedTakePhotoFile);
	    }
	    intent.setDataAndType(cropedUri, "image/*");
	    cropParam.toIntent(intent);
	    break;
	case FROM_MEDIA:
	    intent.setDataAndType(data.getData(), "image/*");
	    cropParam.toIntent(intent);
	    break;
	case FROM_SYSTEM:
	    intent.setDataAndType(data.getData(), "image/*");
	    cropParam.toIntent(intent);
	    break;
	}
	context.startActivityForResult(intent, FROM_CROP);
    }

    public static class CropParam {

	public int aspectX = 16;
	public int aspectY = 15;
	public int outputX = 96;
	public int outputY = 96;
	public boolean scale = true;

	/**
	 * 保存裁剪后的图片文件
	 */
	private File savedCropPhotoFile;

	public CropParam(File savedCropPhotoFile) {
	    this.savedCropPhotoFile = savedCropPhotoFile;
	}

	public void toIntent(Intent intent) {
	    intent.setAction("com.android.camera.action.CROP");
	    // outputX outputY 是裁剪图片宽高
	    // intent.putExtra("outputX", outputX);
	    // intent.putExtra("outputY", outputY);
	    // aspectX aspectY 是宽高的比例
	    intent.putExtra("aspectX", aspectX);
	    intent.putExtra("aspectY", aspectY);
	    intent.putExtra("scale", scale);
	    intent.putExtra("crop", "true");// crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
	    intent.putExtra("noFaceDetection", true);// 设置不识别人脸参数，注意：默认是识别的
	    // 注意在有的机子上，要是设置了保存的uri，这边再设置返回数据在bitmap中，在有的机型上会出现截图成功收不到onActivityResult回掉事件
	    // 真是奇葩，看了截图源码 不应该是这样的，所以最好是要是设置了uri就把return-data设置为false
	    if (android.os.Build.MANUFACTURER.equals("motorola")) {
		intent.putExtra("return-data", true);
	    } else {
		intent.putExtra("return-data", false);
	    }
	    // 设置截图后的保存路径
	    if (savedCropPhotoFile != null) {
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
			Uri.fromFile(savedCropPhotoFile));
	    }
	    // 设置压缩格式，其实默认的就是JPEG
	    intent.putExtra("outputFormat",
		    Bitmap.CompressFormat.JPEG.toString());
	}
    }

    /**
     * Fetch photo.
     * 
     * @param callback
     * @param photoSource
     *            The source of photo (eg: FROM_CAMERA ,FROM_MEDIA )
     */
    public void fetchPhotoFromCamera(PhotoResultCallback callback, File file) {
	savedTakePhotoFile = file;
	fetchPhoto(callback, FROM_CAMERA, null);
    }

    public void fetchPhotoFromCamera(PhotoResultCallback callback, File file,
	    CropParam cropParam) {
	savedTakePhotoFile = file;
	fetchPhoto(callback, FROM_CAMERA, cropParam);
	
    }

    public void fetchPhoto(PhotoResultCallback callback, int source,
	    CropParam cropParam) {
   
    	
	this.callback = callback;
	this.needCrop = (cropParam != null);
	this.cropParam = cropParam;
	this.source = source;

	switch (source) {
	case FROM_CAMERA:
	    startFetchFromCamera();
	    break;
	case FROM_MEDIA:
	    startFetchFromMedia();
	    break;
	}
    }

    private Bitmap getBitmapFrom(Uri bitmapUri) {
	InputStream in = null;
	try {
	    Log.i(TAG, "Get bitmap from " + bitmapUri.getPath());
	    ContentResolver cr = context.getContentResolver();
	    in = cr.openInputStream(bitmapUri);
	    return BitmapFactory.decodeStream(in);
	} catch (FileNotFoundException e) {
	    Log.e(TAG, "Uri : " + bitmapUri + ", Error : " + e.getMessage());
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		    // Ignore it.
		}
	    }
	}
	return null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (resultCode != Activity.RESULT_OK) {
	    return;
	}

	if (needCrop) {
	    doCrop(data);
	    return;
	}

	switch (requestCode) {
	case FROM_CAMERA:
	case FROM_MEDIA:
	case FROM_SYSTEM:
	case FROM_CROP:
	    if (callback != null && cropParam != null
		    && cropParam.savedCropPhotoFile != null
		    && cropParam.savedCropPhotoFile.length() > 0) {
		callback.onResult(cropParam.savedCropPhotoFile);
	    } else {
		// 这边的流程是发现在moto某些机型会出现截图没有保存在savedCropPhotoFile这文件中，因此这边坐下拿到bitmap写入该文件中
		// 注意必须return-data为true
		if (data != null) {
		    Bitmap bitmap = data.getParcelableExtra(BITMAP_PROP_NAME);
		    if (bitmap != null) {
			if (cropParam != null
				&& cropParam.savedCropPhotoFile != null) {
			    FileOutputStream fos = null;
			    try {
				fos = new FileOutputStream(
					cropParam.savedCropPhotoFile);
				if (fos != null) {
				    // 30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
				    bitmap.compress(Bitmap.CompressFormat.JPEG,
					    100, fos);
				}
				if (callback != null) {
				    callback.onResult(cropParam.savedCropPhotoFile);
				}
			    } catch (FileNotFoundException e) {
				e.printStackTrace();
			    } catch (OutOfMemoryError e) {
				e.printStackTrace();
			    } finally {
				if (fos != null) {
				    try {
					fos.close();
				    } catch (IOException e) {
					e.printStackTrace();
				    }
				    fos = null;
				}
			    }
			}
		    }
		}
	    }
	    break;
	}
    }

    /**
     * 进入照相功能
     */
    private void startFetchFromCamera() {
	try {
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    // 直接拍照后的图片保存在mImageFile中，注意:这样处理的话返回的data.getParcelableExtra("data")是空的
	    intent.putExtra(MediaStore.EXTRA_OUTPUT,
		    Uri.fromFile(savedTakePhotoFile));
	    // 设置相机拍照总是竖屏，不能横屏，暂时先这样处理，因为横屏会被回收，导致奔溃出错
	    // intent.putExtra(MediaStore.Images.Media.ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    intent.putExtra("isGraph", true);
	    context.startActivityForResult(intent, FROM_CAMERA);
	} catch (Exception e) {
	    Toast.makeText(context, "启动拍照失败，请稍后再试", Toast.LENGTH_SHORT).show();
	}

    }

    /**
     * 进入相册图库功能
     */
    private void startFetchFromMedia() {
	try {
	    Intent intent = null;
	    String phoneV = android.os.Build.BRAND;
	    if (phoneV != null && "vivo".equals(phoneV)) {
		intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
	    } else {
		intent = new Intent(Intent.ACTION_PICK, null);
		intent.putExtra("isGraph", true);
		intent.setDataAndType(
			MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
	    }
	    context.startActivityForResult(intent, FROM_MEDIA);
	} catch (Exception e) {
	    Toast.makeText(context, "调用图库出错了！您可以重试一下！", Toast.LENGTH_LONG)
		    .show();
	}
    }
}
