package com.soul.project.story.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.soul.project.application.adapter.CommentsAdapter;
import com.soul.project.application.bean.CommentBean;
import com.soul.project.application.bean.GridViewBean;
import com.soul.project.application.util.BitmapCache;
import com.soul.project.application.view.Constants;
import com.soul.project.application.view.ImageCycleView;
import com.soul.project.application.view.ImageCycleView.ImageCycleViewListener;

public class GVDetailActivity extends Activity implements OnClickListener{

	private GridViewBean bean;
	private ImageCycleView scrollView;
	private List<String> imgUrls = new ArrayList<String>();
	private List<String> adTexts = new ArrayList<String>();
	private int width;
	private int maxWidth;
	private int maxHeight;
	private ImageLoader mImageLoader;
	private int additional = 5; //轮播控件的额外高度
	private List<CommentBean> list = new ArrayList<CommentBean>();
	
	private CommentsAdapter adapter;
	
	private ListView listviewComments; // 评论的列表
	private LinearLayout layoutEditComment; // 评论编写和提交的布局视图容器
	private Button btnCommentSubmit; // 评论提交
	private EditText etCommentEdit; // 评论编辑框
	private TextView txtCommentBtn; // 功能块布局中的评论功能按钮
	private TextView txtGoodBtn;    // 功能块布局中的点赞功能按钮
	private TextView txtShareBtn;   // 功能块布局中的分享功能按钮
	private TextView txtIntroduction;// 简介说明
	private TextView txtNumOfLook ;  // 热度数量
	private TextView txtAuthor ;	 // 本作的作者
	private TextView txtOffice;		 // 作者官职
	private ImageButton ibContact;   // 联系按钮  可以考虑或换成头像
	private boolean isShowEditComment = false;// 是否显示评论编辑控件
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gv_dateil_layout);
		mImageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
		mImageLoader.init(ImageLoaderConfiguration.createDefault(GVDetailActivity.this));
		getIntentData();
		createData();
		initViews();
		initEvent();
		initValue();
	}


	private void initValue() {
		// TODO Auto-generated method stub
		list.add(new CommentBean("你好美女，很高兴认识你", "上官宇[中大夫]", "2015-10-23 18:30", "http:www.baidu.com/image.png"));
		list.add(new CommentBean("你的头发好长啊，哈哈哈哈哈", "张先玄[上大夫]", "2015-10-23 18:30", "http:www.baidu.com/image.png"));
		list.add(new CommentBean("你的假发是在哪里买的？", "张菲[大鸿胪]", "2015-10-23 18:30", "http:www.baidu.com/image.png"));
		list.add(new CommentBean("你的下巴是在哪家医院做的，挺好的", "韩婷[下大夫]", "2015-10-23 18:30", "http:www.baidu.com/image.png"));
		list.add(new CommentBean("你好，我叫张定远", "张定远[御史大夫]", "2015-10-23 18:30", "http:www.baidu.com/image.png"));
		
		
		adapter = new CommentsAdapter(this, list);
		listviewComments.setAdapter(adapter);
	}


	private void initEvent() {
		// TODO Auto-generated method stub
		txtCommentBtn.setOnClickListener(this);
		txtGoodBtn.setOnClickListener(this);
		txtShareBtn.setOnClickListener(this);
		btnCommentSubmit.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txtCommentBtn:
			if(!isShowEditComment)
			{
				isShowEditComment = true;
				layoutEditComment.setVisibility(View.VISIBLE);
			}
			else
			{
				isShowEditComment = false;
				layoutEditComment.setVisibility(View.GONE);
			}
			break;
		case R.id.txtGoodBtn:break;
		case R.id.txtShareBtn:break;
		case R.id.btnSubmit:break;
		default:
			break;
		}
	}


	private void createData() {
		// TODO Auto-generated method stub
		imgUrls.add("http://f.hiphotos.baidu.com/image/pic/item/bd315c6034a85edf405207cf4d540923dc547504.jpg");
		imgUrls.add("http://f.hiphotos.baidu.com/image/pic/item/8644ebf81a4c510fa42d1bf66459252dd52aa575.jpg");
		imgUrls.add("http://b.hiphotos.baidu.com/image/pic/item/d043ad4bd11373f08b02bb65a00f4bfbfbed040e.jpg");
		imgUrls.add("http://a.hiphotos.baidu.com/image/pic/item/f7246b600c338744cbb13271530fd9f9d72aa042.jpg");
		imgUrls.add("http://d.hiphotos.baidu.com/image/pic/item/a686c9177f3e6709fae9f8f639c79f3df9dc55c9.jpg");
		
		adTexts.add("清晨倾城之容马可尼马可尼");
		adTexts.add("初秋沉鱼之貌马可尼马可尼");
		adTexts.add("冬末孤傲之冷艳马可尼马可尼");
		adTexts.add("夏日夏荷之奔放马可尼马可尼");
		adTexts.add("春日融融之暖心马可尼马可尼");
	}

	private void initViews() {
		// TODO Auto-generated method stub
		scrollView = (ImageCycleView)this.findViewById(R.id.activity_index_autoscrollview);

		int height = (int) (maxWidth / 2.0);
		//也可以在代码中设置宽高 
		scrollView.setLayoutParams(new LinearLayout.LayoutParams(maxWidth, height+additional));
		scrollView.setWidthAndHeight(maxWidth, height+additional);
		scrollView.setImageResources(imgUrls, adTexts, mAdCycleViewListener);
		
		listviewComments = (ListView)this.findViewById(R.id.lvCommentsList);
		layoutEditComment = (LinearLayout)this.findViewById(R.id.layoutEditComment);
		btnCommentSubmit = (Button)this.findViewById(R.id.btnSubmit);
		etCommentEdit = (EditText)this.findViewById(R.id.etCommentValue);
		txtAuthor = (TextView)this.findViewById(R.id.txtAuthor);
		
		// 评论 点赞  分享 
		txtCommentBtn = (TextView)this.findViewById(R.id.txtCommentBtn);
		txtGoodBtn = (TextView)this.findViewById(R.id.txtGoodBtn);
		txtShareBtn = (TextView)this.findViewById(R.id.txtShareBtn);
		
		txtIntroduction = (TextView)this.findViewById(R.id.txtIntroduction);
		txtNumOfLook = (TextView)this.findViewById(R.id.txtNumOfLook);
		txtOffice = (TextView)this.findViewById(R.id.txtOffice);
		
		ibContact = (ImageButton)this.findViewById(R.id.ibContact);
	}
	

	/**
	 * 轮播图控件接口处理
	 */
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener()
	{
		@Override
		public void onImageClick(int position, View imageView)
		{
			//ToastUtil.show(Shouye.this, "正在执行跳转", ToastUtil.INFO);
		}


		@Override
		public void displayImage(String imageURL, ImageView imageView)
		{
		    // imageView是一个ImageView实例  
		    // ImageLoader.getImageListener的第二个参数是默认的图片resource id  
		    // 第三个参数是请求失败时候的资源id，可以指定为0  
//		    ImageListener listener = ImageLoader.getImageListener(imageView, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);  
//		    mImageLoader.get(bean.getImageUrl(), listener, maxWidth, maxHeight);
			mImageLoader.displayImage(imageURL, imageView, Constants.IM_IMAGE_OPTIONS);
		}
	};
	
	
	@Override
	protected void onResume()
	{
		super.onResume();
		scrollView.startImageCycle();
	};


	@Override
	protected void onPause()
	{
		super.onPause();
		scrollView.pushImageCycle();
	}


	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		scrollView.pushImageCycle();
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
		maxWidth = Integer.valueOf(intent.getIntExtra("width",0));
		maxHeight= Integer.valueOf(intent.getIntExtra("height", 0));
		
		Log.i("XU", "w="+maxWidth+"  h="+maxHeight);
	}




}
