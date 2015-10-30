package com.soul.project.story.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.soul.project.application.adapter.GridViewAdapter;
import com.soul.project.application.bean.GridViewBean;

public class GridviewActivity extends Activity implements OnItemClickListener, OnClickListener {
	
	GridView gridView;
	ImageView backView;
	ImageView uploadView;
	TextView txtTitle;
	List<GridViewBean> list = new ArrayList<GridViewBean>();
	int width;
	int height;
	int type; // 1|美女   2|美男   3|美景
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.image_gridview_layout);
		type = getIntent().getIntExtra("type",-1);
		
		Log.i("XU", "type=="+type);
		
		getWH();
		getDataValue();
		initViews();
		initEvent();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		gridView.setOnItemClickListener(this);
		backView.setOnClickListener(this);
	}

	private void getDataValue() {
		// TODO Auto-generated method stub
		String imageUrl1 = "http://f.hiphotos.baidu.com/image/pic/item/bd315c6034a85edf405207cf4d540923dc547504.jpg";
		GridViewBean bean1 = new GridViewBean(imageUrl1, "高卢第一女书画家", "打瞌睡的实践活动手机客户端升级快还是看机会", "2015001","章风" ,98, 2, 0);
		
		String imageUrl2 = "http://f.hiphotos.baidu.com/image/pic/item/8644ebf81a4c510fa42d1bf66459252dd52aa575.jpg";
		GridViewBean bean2 = new GridViewBean(imageUrl2, "济南航公大使", "打瞌睡的实践活动手机客户端升级快还是看机会", "2015001","章风" ,98, 2, 1);

		String imageUrl3 = "http://b.hiphotos.baidu.com/image/pic/item/d043ad4bd11373f08b02bb65a00f4bfbfbed040e.jpg";
		GridViewBean bean3 = new GridViewBean(imageUrl3, "杭州美女素颜图", "打瞌睡的实践活动手机客户端升级快还是看机会", "2015001","章风" ,98, 2, 0);

		String imageUrl4 = "http://a.hiphotos.baidu.com/image/pic/item/f7246b600c338744cbb13271530fd9f9d72aa042.jpg";
		GridViewBean bean4 = new GridViewBean(imageUrl4, "大家好，我叫颜卿", "打瞌睡的实践活动手机客户端升级快还是看机会", "2015001","章风" ,98, 2, 3);

		String imageUrl5 = "http://d.hiphotos.baidu.com/image/pic/item/a686c9177f3e6709fae9f8f639c79f3df9dc55c9.jpg";
		GridViewBean bean5 = new GridViewBean(imageUrl5, "广州陈圆圆素颜", "打瞌睡的实践活动手机客户端升级快还是看机会", "2015001","章风" ,98, 2, 2);

		String imageUrl6 = "http://e.hiphotos.baidu.com/image/pic/item/2cf5e0fe9925bc31a02ca8985cdf8db1cb1370b9.jpg";
		GridViewBean bean6 = new GridViewBean(imageUrl6, "洛阳初晨时间", "打瞌睡的实践活动手机客户端升级快还是看机会", "2015001","章风" ,98, 2, 3);

		list.add(bean6);
		list.add(bean5);
		list.add(bean4);
		list.add(bean3);
		list.add(bean2);
		list.add(bean1);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		gridView = (GridView)findViewById(R.id.gridview);
		gridView.setAdapter(new GridViewAdapter(this, list, width, height));
		
		backView = (ImageView)findViewById(R.id.iv_button_back);
		uploadView = (ImageView)findViewById(R.id.iv_button_upload);
		txtTitle = (TextView)findViewById(R.id.tv_txt_title);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(GridviewActivity.this, GVDetailActivity.class);
		GridViewBean bean = list.get(position);
		intent.putExtra("title", bean.getTitle());
		intent.putExtra("desc", bean.getDescription());
		intent.putExtra("author", bean.getAuthor());
		intent.putExtra("ballto", bean.getBallot());
		intent.putExtra("grade", bean.getGrade());
		intent.putExtra("number", bean.getNumber());
		intent.putExtra("type", bean.getType());
		intent.putExtra("imageUrl", bean.getImageUrl());
		intent.putExtra("width", width);
		intent.putExtra("height", height);
		startActivity(intent);
	}

	private void getWH()
	{
	    DisplayMetrics metric = new DisplayMetrics();  
	    getWindowManager().getDefaultDisplay().getMetrics(metric);  
	    width = metric.widthPixels;     // 屏幕宽度（像素）  
	    height = metric.heightPixels;   // 屏幕高度（像素）  
	    
	    Log.i("XU", "w="+width+"  h="+height);
	    float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）  
	    int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）  
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_button_back:
			finish();
			break;

		default:
			break;
		}
	}
}
