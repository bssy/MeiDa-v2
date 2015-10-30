package com.soul.project.story.activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdjkjw.Uxmejr;
import com.sdjkjw.a.c;
import com.soul.project.application.bean.AppInfo;
import com.soul.project.application.bean.XMLBean;
import com.soul.project.application.dialog.CustomProgressDialog;
import com.soul.project.application.util.HttpUtil;
import com.soul.project.application.util.NetWorkUtil;
import com.soul.project.application.util.SharePreference;
import com.soul.project.application.util.ToastUtil;
import com.zhend.Slait;

public class DetailsActivity extends Activity
{
	TextView txtTitle, txtPubTime, txtContent, txtType;
	ImageView btnBack, btnHome;
	String title, time, content, url, typeName;
	ProgressDialog dialog;
	int size = 0;


	// private void loadAd()
	// {
	// Uxmejr u = Uxmejr.getInstance(getApplicationContext());
	// u.r(getApplicationContext(), "5440b4f83cfbafdceb2d1a038804e032", 0);
	//
	//
	// sckoel = Slait.getInstance(getApplicationContext(),
	// "e584c9fd14e036bb3f4ecf6481f303a9");
	// sckoel.load(getApplicationContext());
	// sckoel.show(MainActivity.this);
	// sckoel.r1(getApplicationContext(), 4, 1, true, false, false);
	// sckoel.r2(getApplicationContext(), true,true, 100);
	// }
	public Slait sckoel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.details_layout);
		size = getSize();
		SharePreference.getInstance(DetailsActivity.this).setValue("size", size);
		
		if(MainActivity.sckoel != null)
			MainActivity.sckoel.show(this);
		else
			loadAd();

		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("data");// .getExtras();
		title = bundle.getString("title");
		url = bundle.getString("url");
		time = bundle.getString("time");
		typeName = bundle.getString("typeName");
		initView();

		if( url == null )
			ToastUtil.show(DetailsActivity.this, "未获取到内容", ToastUtil.ERROR);
		else
		{
			LoadData data = new LoadData();
			data.execute("执行");
		}
	}
	
	private void loadAd()
	{
		Uxmejr u = Uxmejr.getInstance(getApplicationContext());
		u.r(getApplicationContext(), "5440b4f83cfbafdceb2d1a038804e032", 0);
		
		
		sckoel = Slait.getInstance(getApplicationContext(), "e584c9fd14e036bb3f4ecf6481f303a9");
		sckoel.load(getApplicationContext());
		sckoel.show(DetailsActivity.this);
		sckoel.r1(getApplicationContext(), 4, 1, true, false, false);
		sckoel.r2(getApplicationContext(), true,true, 100);
	}
	
	@Override
	protected void onRestart()
	{
		// TODO Auto-generated method stub
		super.onRestart();
		if(!"-1".equals(SharePreference.getInstance(DetailsActivity.this).getValue("pid")))
		{
			if(size == (getSize()-1) && SharePreference.getInstance(DetailsActivity.this).getIntValue("size") == (getSize()-1))
			{
				if(NetWorkUtil.isNetworkAvailableAndTip(DetailsActivity.this))
				{
					new MyTask().execute("do");
				}
			}
		}
	}
	
	class MyTask extends AsyncTask
	{
		@Override
		protected Object doInBackground(Object... params)
		{
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pid", SharePreference.getInstance(DetailsActivity.this).getValue("pid"));
			map.put("type", "2");			
			return HttpUtil.post("adAdd", map);
		}

		@Override
		protected void onPostExecute(Object result)
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			com.soul.project.application.component.ProgressDialog.dismissProgressDialog();
			if(result != null)
			{
				Gson gson = new Gson();
				XMLBean bean = gson.fromJson(result.toString(), XMLBean.class);
				
				SharePreference.getInstance(DetailsActivity.this).setValue("size", size);
				if("true".equals(bean.getResult()))
					ToastUtil.show(DetailsActivity.this, "成功",ToastUtil.INFO);
				
				Intent intent = new Intent(DetailsActivity.this, UserInfoActivity.class);
				startActivity(intent);
			}
			else
			{
				ToastUtil.show(DetailsActivity.this, "失败",ToastUtil.INFO);
			}
		}

		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
			com.soul.project.application.component.ProgressDialog.showProgressDialog(DetailsActivity.this, "正在提交");
		}
	}
	
	
	private PackageManager packageManager;


	public /**List<AppInfo>*/int getSize()
	{
		packageManager = this.getPackageManager();
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		return packageInfos.size();
	}

	@Override
	protected void onUserLeaveHint()
	{
		// TODO Auto-generated method stub
		super.onUserLeaveHint();
		int newSize = getSize();
		if(size < newSize)
		{
			size = newSize;
		}
	}


	@Override
	public void onUserInteraction()
	{
		// TODO Auto-generated method stub
		super.onUserInteraction();
	}

	//
	// private void name(int i)
	// {
	// name2(i);
	// }
	//
	// private void name2(long i)
	// {
	//
	// }

	private class LoadData extends AsyncTask
	{
		@Override
		protected Object doInBackground(Object... params)
		{
			getContent(url);
			return null;
		}


		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = CustomProgressDialog.getProgressDiaolgNoTitle(DetailsActivity.this, "正在拼命加载...", 19);// ProgressDialog.show(DetailsActivity.this,
																											// "提示",
																											// "请稍等，正在拼命加载数据...");
		}


		@Override
		protected void onPostExecute(Object result)
		{
			dialog.dismiss();
			super.onPostExecute(result);
			if( content != null )
				txtContent.setText(content);
			else
				ToastUtil.show(DetailsActivity.this, "抱歉，未能加载到内容", ToastUtil.INFO);
			if( time != null )
				txtPubTime.setText(time);
			if( title != null )
				txtTitle.setText(title);
		}
	}

	Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			if( msg.what == 1 )
				ToastUtil.show(DetailsActivity.this, "获取数据内容异常", ToastUtil.ERROR);

		};
	};


	private void getContent(String url)
	{
		// System.out.println(URLTest.getContent(url));
		String s = getHtml(url);
		if( s == null )
		{
			handler.sendEmptyMessage(1);
			return;
		}
		// s = Html.fromHtml(s).toString();
		// Log.i("XU",
		// <div class="content">
		// <div class="dede_pages">
		// "s="+s);//"<div class="+'"'+"article_wz"+'"'+" id="+'"'+"endtext"+'"'+">");//"<div class="+'"'+"article_w"+'"'+" id="+'"'+"endtext"+'"'+">");
		int start = s.indexOf("<div class=" + '"' + "content");// ("（本文系知音网原创，未经许可请勿转载）");
		int stop = s.indexOf("<div class=" + '"' + "dede_pages" + '"' + ">");// ("[请本文作者与本网联系 以便奉寄稿酬][责任编辑");
		if( stop < 0 )
		{
			stop = s.indexOf("[请本文作者与本网联系 以便奉寄稿酬][责任编辑");
			if( stop < 0 )
			{
				stop = s.indexOf("<div class=" + '"' + "article_laiyuan" + '"' + ">");
				if( stop < 0 )
				{
					stop = s.indexOf("<div class=" + '"' + "zuozhe" + '"' + ">");
					if( stop < 0 )
					{
						stop = s.indexOf("<div class=" + '"' + "article_fy" + '"' + ">");
						if( stop < 0 )
						{
							stop = s.indexOf("<div class=" + '"' + "zt_wzrk2" + '"' + ">");
							if( stop < 0 )
							{
								stop = s.indexOf("<div class=" + '"' + "nr_shxia" + '"' + ">");
							}
						}
					}
				}
			} else
				stop = stop - ("</div><div class=" + '"' + "zuozhe" + '"' + ">").length() - 10;
		}
		if( start >= 0 && stop > 0 )
		{
			String Str2 = s.substring(start, stop);
			Str2 = Html.fromHtml(Str2).toString();
			content = Str2;
		} else
		{
			handler.sendEmptyMessage(1);
			content = "抱歉，为获取到有效内容\n可能原因：\n1、网络断开\n2、服务器连接异常..................";
		}
	}


	public String getHtml(String strUrl)
	{
		StringBuffer sb;
		try
		{
			URL url = new URL(strUrl);
			BufferedReader br = new BufferedReader(new InputStreamReader(url

			.openStream(), "gb2312"));

			String s = "";

			sb = new StringBuffer("");

			while ((s = br.readLine()) != null)
			{

				sb.append(s + "\r\n");

			}
			br.close();
			return sb.toString();
		}
		catch (Exception e)
		{
			return null;
		}
	}


	private void initView()
	{
		txtType = (TextView) this.findViewById(R.id.lv_txt_title);
		txtType.setText(typeName);
		txtTitle = (TextView) this.findViewById(R.id.details_title);
		txtPubTime = (TextView) this.findViewById(R.id.details_pubtime);
		txtContent = (TextView) this.findViewById(R.id.details_content);
		btnBack = (ImageView) this.findViewById(R.id.lv_button_back);
		btnBack.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				DetailsActivity.this.finish();
			}
		});
		btnHome = (ImageView) this.findViewById(R.id.lv_button_pre);
		btnHome.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				startActivity(intent);
			}
		});
	}


	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
