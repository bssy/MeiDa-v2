package com.soul.project.story.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sky.utils.BitmapUtil;
import com.sky.utils.PhotoHelper;
import com.sky.utils.PhotoHelper.CropParam;
import com.sky.utils.PhotoHelper.PhotoResultCallback;
import com.soul.project.application.adapter.GridViewAdapter;
import com.soul.project.application.bean.GridViewBean;
import com.soul.project.application.component.ProgressDialog;
import com.soul.project.application.util.ToastUtil;
import com.soul.project.application.view.AppTitleBar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MyImageActivity extends BaseActivity implements OnClickListener
{
	// 加载本地图片成功
	private final int LOGADIMAGE_SUCCESS = 0x8023;
	// 本地图片文件夹路径不存在
	private final int LOADIMAGE_PATH_ERR = 0x0001;
	// 截图完成后去美图界面请求码
	private final int GO_EDIT_IMAGE = 0x0002;

	private ImageView img_camare;

	private GridView gridView;
	private GridViewAdapter gridViewAdapter;

	private PhotoHelper photoHelper;
	// 拍照图片保存路径
	private String savedTakePhotoPath;
	// 截图图片保存路径
	private String savedCropPhotoPath;

	// 我的图片列表
	private List<GridViewBean> bitmaps = new ArrayList<GridViewBean>();
	// 屏幕宽
	private int width;
	// 屏幕高
	private int height;
	private AppTitleBar appTitleBar;

	Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case LOGADIMAGE_SUCCESS:
					gridViewAdapter = new GridViewAdapter(MyImageActivity.this, bitmaps, width, height);
					gridView.setAdapter(gridViewAdapter);
					ProgressDialog.dismissProgressDialog();
					break;
				case LOADIMAGE_PATH_ERR:
					ProgressDialog.dismissProgressDialog();
					ToastUtil.show(MyImageActivity.this, "您还没有任何图片,请点击左下角进行拍照!", ToastUtil.INFO, 3);
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_gridview_layout);
		ProgressDialog.showProgressDialog(this, "加载中...");
		photoHelper = new PhotoHelper(this);
		getWH();
		initView();
	}
	
	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		initParameter();
		getLoaclBitmaps(savedCropPhotoPath);
	}

	private void initParameter()
	{
		savedTakePhotoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DCIM"
				+ File.separator + "Camera" + File.separator;
		savedCropPhotoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
				+ "MDImgfolder" + File.separator;
	}

	private void initView()
	{
		appTitleBar = (AppTitleBar) this.findViewById(R.id.apptitlebar);

		img_camare = (ImageView) this.findViewById(R.id.img_camera);
		img_camare.setVisibility(View.VISIBLE);
		img_camare.setOnClickListener(this);
		gridView = (GridView) this.findViewById(R.id.gridview);
		gridView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				GridViewBean bean = bitmaps.get(arg2);
				Intent intent = new Intent(MyImageActivity.this, EditImageActivity.class);
				intent.putExtra("filepath", bean.getFilepath());
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.img_camera:
				photoHelper.fetchPhotoFromCamera(new PhotoResultCallback()
				{
					@Override
					public void onResult(File savedCropPhotoFile)
					{
						Log.i("XU", " 启动编辑页面");
						Intent intent = new Intent(MyImageActivity.this, EditImageActivity.class);
						intent.putExtra("filepath", savedCropPhotoFile.getPath());
						startActivity(intent);
					}
				}, new File(savedTakePhotoPath + BitmapUtil.getPhotoFileName()), new CropParam(new File(
						savedCropPhotoPath + BitmapUtil.getPhotoFileName())));
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		photoHelper.onActivityResult(requestCode, resultCode, data);
	}

	// 获取本地保存图片
	private void getLoaclBitmaps(final String path)
	{
		if(bitmaps != null && bitmaps.size() > 0)
			bitmaps.clear();
		
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				File file = new File(path);
				if (file.exists())
				{
					File[] files = file.listFiles();
					for (int i = 0; i < files.length; i++)
					{
						Bitmap bitmap = BitmapUtil.compressImageFromFile(files[i].getPath());
						GridViewBean gridViewBean = new GridViewBean();
						gridViewBean.setBitmap(bitmap);
						gridViewBean.setFilename(files[i].getName());
						gridViewBean.setFilepath(files[i].getPath());
						bitmaps.add(gridViewBean);
					}
					handler.sendEmptyMessage(LOGADIMAGE_SUCCESS);
				}
				else
				{
					handler.sendEmptyMessage(LOADIMAGE_PATH_ERR);
				}
			}
		}).start();
	}

	private void getWH()
	{
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels; // 屏幕宽度（像素）
		height = metric.heightPixels; // 屏幕高度（像素）
	}
}
