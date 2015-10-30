package com.soul.project.story.activity;

import com.soul.project.application.util.Activity2Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

public class SplashActivity extends Activity {

	private int delayTime = 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		
		Message msg = new Message();
		msg.what = 1;
		handler.sendMessageDelayed(msg, delayTime); 
	}
	
	Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			Activity2Activity.gotoNewActivity(SplashActivity.this, MainActivity.class);
		};
	};
	
	/**加载应用需要用到的持久性数据*/
	class LoadAppData extends AsyncTask
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
