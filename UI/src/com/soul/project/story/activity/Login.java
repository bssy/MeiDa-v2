package com.soul.project.story.activity;

import com.soul.project.application.util.SharePreference;
import com.soul.project.application.util.ToastUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener
{
	private EditText edtUser,edtPwd;
	private Button btnLogin,btnRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
//		if(!"-1".equals(SharePreference.getInstance(this).getValue("pid")))
//		{
//			Intent intent = new Intent(this, UserInfoActivity.class);
//			startActivity(intent);
//			finish();
//		}
		initView();
		addEvent();
	}

	private void addEvent()
	{
		// TODO Auto-generated method stub
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
	}

	private void initView()
	{
		// TODO Auto-generated method stub
		edtPwd = (EditText)this.findViewById(R.id.password);
		edtUser = (EditText)this.findViewById(R.id.accounts);
		
		btnLogin = (Button)this.findViewById(R.id.login);
		btnRegister = (Button)this.findViewById(R.id.regist);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.login  :
				if(!"-1".equals(SharePreference.getInstance(Login.this).getValue("pid")))
				{
					Intent intent = new Intent(Login.this, UserInfoActivity.class);
					startActivity(intent);
				}
				else
				{
					ToastUtil.show(Login.this, "您还没有帐号，请先注册", ToastUtil.WARN);
					Intent intent = new Intent(Login.this, RegisterActivity.class);
					startActivity(intent);
				}
				break;
			case R.id.regist :
				Intent intent = new Intent(Login.this, RegisterActivity.class);
				startActivity(intent);
				break;

			default :
				break;
		}
	}
}
