package com.soul.project.story.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soul.project.application.bean.XMLUserBean;
import com.soul.project.application.component.ProgressDialog;
import com.soul.project.application.util.HttpUtil;
import com.soul.project.application.util.NetWorkUtil;
import com.soul.project.application.util.SharePreference;
import com.soul.project.application.util.ToastUtil;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class UserInfoActivity extends Activity
{
	private TextView txtUser,txtMoney; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		txtUser = (TextView)this.findViewById(R.id.txtAccount);
		txtMoney = (TextView)this.findViewById(R.id.txtMoney);
		
		if(!"-1".equals(SharePreference.getInstance(UserInfoActivity.this).getValue("pid")))
		{
			if(NetWorkUtil.isNetworkAvailableAndTip(UserInfoActivity.this))
			{
				try
				{
					new MyTask().execute("do");
				}
				catch (Exception e)
				{
					// TODO: handle exception
					ToastUtil.show(UserInfoActivity.this, "服务器奔溃", ToastUtil.ERROR);
					SharePreference.getInstance(UserInfoActivity.this).setValue("pid", "-1");
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
			map.put("pid", SharePreference.getInstance(UserInfoActivity.this).getValue("pid"));
			map.put("type", "1");
			return HttpUtil.post("adQuery", map);
		}

		@Override
		protected void onPostExecute(Object result)
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ProgressDialog.dismissProgressDialog();
			if(result != null)
			{
				Gson gson = new Gson();
				List<XMLUserBean> bean = gson.fromJson(result.toString(), new TypeToken<List<XMLUserBean>>(){}.getType());
				if(bean != null && bean.size() > 0)
				{
					txtMoney.setText("您的金额有: "+(Integer.valueOf(bean.get(0).getGold())/10.0)+" 元");
					txtUser.setText("您的帐号是: "+bean.get(0).getPid());
				}
				if(bean.size() <= 0)
				{
					SharePreference.getInstance(UserInfoActivity.this).setValue("pid", "-1");
					ToastUtil.show(UserInfoActivity.this, "数据出错",ToastUtil.INFO);
				}
			}
			else
			{
				ToastUtil.show(UserInfoActivity.this, "获取信息失败",ToastUtil.INFO);
			}
		}

		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
			ProgressDialog.showProgressDialog(UserInfoActivity.this, "正在获取");
		}
		
	}
}
