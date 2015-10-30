package com.soul.project.story.activity;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.soul.project.application.adapter.ListViewAdapter;
import com.soul.project.application.bean.News;
import com.soul.project.application.dialog.CustomProgressDialog;
import com.soul.project.application.util.SaxParse;
import com.soul.project.application.util.ToastUtil;

public class ListViewActivity extends Activity implements OnItemClickListener
{
	private ListView listView;
	private ImageView btnBack,btnHome;
	private TextView txtTitle;
	private List<News> list ;
	private int type = -1;
	String strUrl = "http://www.zhiyin.cn/index.php?m=content&c=rss&rssid=199";
	ProgressDialog dialog;
	private String typeName;
	String[] array = new String[]{"短篇小说","校园小说","言情小说","爱情小说","搞笑小说","谈情说爱","感人故事","都市小说","爱情真谛","小小说","情感小说","爱情测试"};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.listview_activity);
		type = getIntent().getIntExtra("type", -1);
		
		getUrl();
		
		initView();
		getData();
	}
	
	private void getUrl()
	{
		switch (type)
		{
			case 1  :typeName = array[type-1];strUrl = "http://www.aikann.com/data/rss/1.xml";break;
			case 2  :typeName = array[type-1];strUrl = "http://www.aikann.com/data/rss/2.xml";break;
			case 4  :typeName = array[type-1];strUrl = "http://www.aikann.com/data/rss/7.xml";break;
			case 5  :typeName = array[type-1];strUrl = "http://www.aikann.com/data/rss/3.xml";break;
			case 6  :typeName = array[type-1];strUrl = "http://www.aikann.com/data/rss/4.xml";break;
			case 7  :typeName = array[type-1];strUrl = "http://www.aikann.com/data/rss/13.xml";break;
			case 8  :typeName = array[type-1];strUrl = "http://www.aikann.com/data/rss/8.xml";break;
			case 9  :typeName = array[type-1];strUrl = "http://www.aikann.com/data/rss/9.xml";break;
			case 10 :typeName = array[type-1];strUrl = "http://www.aikann.com/data/rss/5.xml";break;
			case 11 :typeName = array[type-1];strUrl = "http://www.aikann.com/data/rss/11.xml";break;
			case 12 :typeName = array[type-1];strUrl = "http://www.aikann.com/data/rss/12.xml";break;
			case 13 :typeName = array[type-1];strUrl = "http://www.aikann.com/data/rss/10.xml";break;
			default :
				break;
		}
	}

	private void getData()
	{
		if(type != -1)
		{
			dialog = CustomProgressDialog.getProgressDiaolgNoTitle(ListViewActivity.this, "正在拼命加载...", 19);
			LoadData data = new LoadData();
			data.execute("加载数据");
		}
	}
	
	private class LoadData extends AsyncTask
	{
		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Object result)
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			ListViewAdapter adapter = new ListViewAdapter(ListViewActivity.this, list);
			listView.setAdapter(adapter);
		}

		@Override
		protected Object doInBackground(Object... params)
		{
//			InputStream in = null;
			HttpURLConnection conn;
			URL url = null;
			try
			{
				url = new URL(strUrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				conn.getInputStream();
			}
			catch (Exception e)
			{
				Log.i("XU", "异常1=="+e.toString());
//				ToastUtil.show(ListViewActivity.this, "请检查网络是否联通", ToastUtil.ERROR);
			}

			try
			{
				Log.i("XU", "开始解析");
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();
				XMLReader reader = parser.getXMLReader();
				SaxParse handler = new SaxParse();
				reader.setContentHandler(handler);
				
				InputStreamReader streamReader = new InputStreamReader(url.openStream(),"gb2312");
				InputSource is = new InputSource(streamReader); 
				
				reader.parse(is); 
				list = handler.getDatas();
				streamReader.close();
			}
			catch (Exception e)
			{
				Log.i("XU", "异常2=="+e.toString());
//				ToastUtil.show(ListViewActivity.this, "解析出现异常", ToastUtil.ERROR);
			}
			return list;
		}

	}

	private void initView()
	{
		listView = (ListView)this.findViewById(R.id.listview);
		listView.setOnItemClickListener(this);
		txtTitle = (TextView)this.findViewById(R.id.lv_txt_title);
		txtTitle.setText(typeName);
		
		btnBack = (ImageView) this.findViewById(R.id.lv_button_back);
		btnBack.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ListViewActivity.this.finish();
			}
		});
		btnHome = (ImageView) this.findViewById(R.id.lv_button_pre);
		btnHome.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(ListViewActivity.this, MainActivity.class);
				overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		News news = list.get(position);
		Intent intent = new Intent(ListViewActivity.this, DetailsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("url", news.getUrl());
		bundle.putString("title", news.getTitle());
		bundle.putString("time", news.getPubTime());
		bundle.putString("typeName", typeName);
		intent.putExtra("data", bundle);
		
		startActivity(intent);
	}
}
