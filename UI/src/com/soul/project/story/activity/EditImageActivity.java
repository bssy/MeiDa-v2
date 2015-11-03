package com.soul.project.story.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sdjkjw.a.m;
import com.sky.utils.BitmapUtil;
import com.sky.utils.IceFilter;
import com.sky.utils.SoftGlowFilter;
import com.sky.view.HorizontalListView;
import com.soul.project.application.adapter.EditImageListViewAdapter;
import com.soul.project.application.bean.GridViewBean;
import com.soul.project.application.component.ProgressDialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

public class EditImageActivity extends BaseActivity
{
	private final int DEAL_IMAGE_SUCCESS = 0x8023;
	private final int LOAD_SUCCESS = 0x0001;
	// 效果缩略图列表
	private List<GridViewBean> list = new ArrayList<GridViewBean>();
	// 横向listview
	private HorizontalListView horizontalListView;
	private ImageView imageView;
	private EditImageListViewAdapter eAdapter;
	// 图片路径
	private String filePath;
	// 图片模式
	private String[] modes;

	private Intent intent;
	// 原图
	private Bitmap bitmap;
	// 小图
	private Bitmap littleBitmap;

	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case DEAL_IMAGE_SUCCESS:
					imageView.setImageBitmap((Bitmap) msg.obj);
					ProgressDialog.dismissProgressDialog();
					break;
				case LOAD_SUCCESS:
					imageView.setImageBitmap(bitmap);
					for (int i = 0; i < modes.length; i++)
					{
						GridViewBean gridViewBean = new GridViewBean();
						gridViewBean.setModeId(i);
						gridViewBean.setBitmap(dealBitmaps(i, littleBitmap));
						list.add(gridViewBean);
					}
					eAdapter = new EditImageListViewAdapter(EditImageActivity.this, modes, list, handler);
					horizontalListView.setAdapter(eAdapter);
					ProgressDialog.dismissProgressDialog();
					break;

				default:
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_image);
		ProgressDialog.showProgressDialog(this, "加载中...");
		initIntent();
		initView();
		initData();
	}

	private void initData()
	{
		modes = getResources().getStringArray(R.array.mode);
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				bitmap = BitmapUtil.readBitMap(EditImageActivity.this, new File(filePath));
				littleBitmap = BitmapUtil.scaleByRatio(bitmap, 300, 360);
				handler.sendEmptyMessage(LOAD_SUCCESS);

			}
		}).start();
	}

	private void initView()
	{
		horizontalListView = (HorizontalListView) this.findViewById(R.id.horizon_listview);
		horizontalListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3)
			{
				ProgressDialog.showProgressDialog(EditImageActivity.this, "处理中...");
				new Thread(new Runnable()
				{

					@Override
					public void run()
					{
						Bitmap tBitmap = dealBitmaps(arg2, EditImageActivity.this.bitmap);
						Message message = new Message();
						message.obj = tBitmap;
						message.what = DEAL_IMAGE_SUCCESS;
						handler.sendMessage(message);
						tBitmap = null;
					}
				}).start();

			}
		});
		imageView = (ImageView) this.findViewById(R.id.edit_image_imageview);

	}

	private void initIntent()
	{
		intent = this.getIntent();
		filePath = intent.getStringExtra("filepath");
	}

	public Bitmap dealBitmaps(int position, Bitmap bitmap)
	{
		Bitmap tempBitmap = null;
		switch (position)
		{
			case 0:
				// 原始
				tempBitmap = bitmap;
				break;
			case 1:
				// 模糊
				tempBitmap = BitmapUtil.getBlurImage(bitmap);
				break;
			case 2:
				// 素描
				tempBitmap = BitmapUtil.getSketchImage(bitmap);
				break;
			case 3:
				// 光照
				tempBitmap = BitmapUtil.getSunshineImage(bitmap, bitmap.getWidth() - bitmap.getWidth() / 4,
						bitmap.getHeight() - bitmap.getHeight() / 4);
				break;
			case 4:
				// 锐化
				tempBitmap = BitmapUtil.getSharpenImageAmeliorate(bitmap);
				break;
			case 5:
				// 浮雕
				tempBitmap = BitmapUtil.getSmbossImage(bitmap);
				break;
			case 6:
				// 柔化
				tempBitmap = BitmapUtil.getBlurImageAmeliorate(bitmap);
				break;
			case 7:
				// 怀旧
				tempBitmap = BitmapUtil.getOldRemeberImage(bitmap);
				break;
			case 8:
				// 水平镜像
				tempBitmap = BitmapUtil.getReverseBitmap(bitmap, 0);
				break;
			case 9:
				// 负片
				tempBitmap = BitmapUtil.getFilmImage(bitmap);
				break;
			case 10:
				// 倒影
				tempBitmap = BitmapUtil.getReflectionImageWithOrigin(bitmap);
				break;
			case 11:
				// 圆角
				tempBitmap = BitmapUtil.getRoundedCornerBitmap(bitmap, 22);
				break;
			case 12:
				// 黑白
				tempBitmap = BitmapUtil.getBlackAndWhiteImage(bitmap);
				break;
			case 13:
				// 垂直镜像
				tempBitmap = BitmapUtil.getReverseBitmap(bitmap, 1);
				break;
			case 14:
				// 灰度
				tempBitmap = BitmapUtil.getGrayImage(bitmap);
				break;
			case 15:
				// 彩画
				tempBitmap = new IceFilter(bitmap).imageProcess().getDstBitmap();
				break;
			case 16:
				// 美白
				tempBitmap = new SoftGlowFilter(bitmap, 10, 0.1f, 0.1f).imageProcess().getDstBitmap();
				break;

		}
		return tempBitmap;
	}
}
