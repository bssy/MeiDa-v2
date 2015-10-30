package com.soul.project.story.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soul.project.application.bean.XMLBean;
import com.soul.project.application.component.ProgressDialog;
import com.soul.project.application.util.HttpUtil;
import com.soul.project.application.util.NetWorkUtil;
import com.soul.project.application.util.SharePreference;
import com.soul.project.application.util.ToastUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity
{
	private EditText edtUser,edtPwd,edtZFB;
	private Button btnRegister;
	private String strUser,strPwd,strZFB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		initView();
	}


	private void initView()
	{
		// TODO Auto-generated method stub
		edtPwd = (EditText)this.findViewById(R.id.password);
		edtUser = (EditText)this.findViewById(R.id.accounts);
		edtZFB = (EditText)this.findViewById(R.id.zfb);
		btnRegister = (Button)this.findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				strPwd = edtPwd.getText().toString();
				strUser= edtUser.getText().toString();
				strZFB = edtZFB.getText().toString();
				if(!isNullOrEmpty(strPwd) && !isNullOrEmpty(strUser) && !isNullOrEmpty(strZFB))
				{
					if(strUser.length() < 6)
						edtUser.setError("帐号至少要6位以上");
					else
					{
						if(strPwd.length() <6)
							edtPwd.setError("密码至少要6位以上");
						else
							if(NetWorkUtil.isNetworkAvailableAndTip(RegisterActivity.this))
							{
								new MyTask().execute("do");
							}
					}
				}
			}
		});
	}
	
	private boolean isNullOrEmpty(String s)
	{
		if(s != null || "".equals(s))
			return false;
		return true;
	}
	
	class MyTask extends AsyncTask
	{
		@Override
		protected Object doInBackground(Object... params)
		{
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", strZFB);
			map.put("pwd", strPwd);
			map.put("pid", strUser);
			map.put("type", "1");		
			
			return HttpUtil.post("adAdd", map);
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
				XMLBean bean = gson.fromJson(result.toString(), XMLBean.class);
				if(bean != null)
				{
					if("true".equals(bean.getResult()))
						ToastUtil.show(RegisterActivity.this, "操作成功",ToastUtil.INFO);

					SharePreference preference = SharePreference.getInstance(RegisterActivity.this);
					preference.setValue("pid", strUser);
					
					Intent intent = new Intent(RegisterActivity.this, UserInfoActivity.class);
					startActivity(intent);
				}
				else
				{ 
					SharePreference.getInstance(RegisterActivity.this).setValue("pid", "-1");
					ToastUtil.show(RegisterActivity.this, "数据出错",ToastUtil.INFO);
				}
					
			}
			else
			{
				ToastUtil.show(RegisterActivity.this, "注册失败",ToastUtil.INFO);
			}
		}

		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
			ProgressDialog.showProgressDialog(RegisterActivity.this, "正在提交");
		}
		
		
	}
}
