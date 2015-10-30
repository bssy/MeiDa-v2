package com.soul.project.story.activity;

import java.util.ArrayList;
import java.util.List;

import com.soul.project.application.bean.GridViewBean;
import com.soul.project.application.view.MyImgScroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

public class GVDetailActivity extends Activity{

	private GridViewBean bean;
	private MyImgScroll scrollView;
	private LinearLayout scrollViewPointLayout;
	private List<String> imgUrls = new ArrayList<String>();
	private List<String> adTexts = new ArrayList<String>();
	private int width;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gv_dateil_layout);
		getIntentData();
		createData();
		initViews();
	}

	private void createData() {
		// TODO Auto-generated method stub
		imgUrls.add("http://f.hiphotos.baidu.com/image/pic/item/bd315c6034a85edf405207cf4d540923dc547504.jpg");
		imgUrls.add("http://f.hiphotos.baidu.com/image/pic/item/8644ebf81a4c510fa42d1bf66459252dd52aa575.jpg");
		imgUrls.add("http://b.hiphotos.baidu.com/image/pic/item/d043ad4bd11373f08b02bb65a00f4bfbfbed040e.jpg");
		imgUrls.add("http://a.hiphotos.baidu.com/image/pic/item/f7246b600c338744cbb13271530fd9f9d72aa042.jpg");
		imgUrls.add("http://d.hiphotos.baidu.com/image/pic/item/a686c9177f3e6709fae9f8f639c79f3df9dc55c9.jpg");
		
		adTexts.add("清晨倾城之容");
		adTexts.add("初秋沉鱼之貌");
		adTexts.add("冬末孤傲之冷艳");
		adTexts.add("夏日夏荷之奔放");
		adTexts.add("春日融融之暖心");
	}

	private void initViews() {
		// TODO Auto-generated method stub
		scrollView = (MyImgScroll)this.findViewById(R.id.activity_index_autoscrollview);
		scrollViewPointLayout = (LinearLayout)this.findViewById(R.id.ss_ll_points);

		//也可以在代码中设置宽高 
		scrollView.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width * 1 / 3)));
		
		scrollView.start(this, imgUrls, 5, scrollViewPointLayout, R.layout.point, R.id.pointItem, R.drawable.dot_focused, R.drawable.dot_normal, adTexts);
	}

	private void getIntentData() {
		
		Intent intent = getIntent();
		
		bean = new GridViewBean();
		bean.setAuthor(intent.getStringExtra("author"));
		bean.setBallot(intent.getIntExtra("ballot", 0));//.getIntExtra("ballto"));
		bean.setDescription(intent.getStringExtra("desc"));
		bean.setGrade(intent.getIntExtra("grade",0));
		bean.setImageUrl(intent.getStringExtra("imageUrl"));
		bean.setNumber(intent.getStringExtra("number"));
		bean.setType(intent.getIntExtra("type",0));
		bean.setTitle(intent.getStringExtra("title"));
		width = Integer.valueOf(intent.getIntExtra("width",0));
	}
}
