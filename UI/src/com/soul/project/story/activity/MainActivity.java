package com.soul.project.story.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sdjkjw.Uxmejr;
import com.soul.project.application.adapter.MainFragmentAdapter;
import com.soul.project.application.base.HandlerMessage;
import com.soul.project.application.component.AlertDialog;
import com.soul.project.application.data.dummy.HomeData;
import com.soul.project.application.data.dummy.HomeGridView;
import com.soul.project.application.fragment.HomeFragment;
import com.soul.project.application.util.LogUtil;
import com.soul.project.application.util.Utils;
import com.soul.project.application.view.MainToolsBar;
import com.zhend.Slait;

public class MainActivity extends FragmentActivity implements TabListener, OnClickListener
{
	private final static String tag = "MainActivity";
	private ViewPager mViewPager;
	private MainFragmentAdapter mMainFragmentAdapter;
	private List<Fragment> fragments;
	private MainToolsBar bar;
	private NotificationManager mNotificationManager; // Notification Manager
	private Notification notification; // Notification
	private int restartCount = 0;
	private ArrayList<HomeGridView> list;
	public static int barWidth = 0;
	private TextView txtPageNum;
	private int pageNum = 0;
	// 退出
	private ImageButton imgBtnExit;
	// 设置
	private ImageButton imgBtnSetting;
	//用户
	private ImageButton imgBtnUser;
	// private UserInfo userInfo = null;
	private String msgNotification;
	public static Slait sckoel;

	private void loadAd()
	{
		Uxmejr u = Uxmejr.getInstance(getApplicationContext());
		u.r(getApplicationContext(), "5440b4f83cfbafdceb2d1a038804e032", 0);
		
		
		sckoel = Slait.getInstance(getApplicationContext(), "e584c9fd14e036bb3f4ecf6481f303a9");
		sckoel.load(getApplicationContext());
//		sckoel.show(MainActivity.this);
		sckoel.r1(getApplicationContext(), 4, 1, true, false, false);
		sckoel.r2(getApplicationContext(), true,true, 100);
	}
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 初始化布�?
		try
		{
			setContentView(R.layout.layout_main);
			
			// 添加必要库
			loadAd();
			
			Utils.context = MainActivity.this;
			if(!Utils.checkNet())
				Utils.AlertNetError();
			
			list = HomeData.getFunction4Teacher();
			buildNotification("1");
			bar = (MainToolsBar) findViewById(R.id.toolsBar);
			
			imgBtnExit = (ImageButton) bar.findViewById(R.id.imgbtn_toolsbar_exit);
			imgBtnExit.setOnClickListener(MainActivity.this);
			imgBtnSetting = (ImageButton) bar.findViewById(R.id.imgbtn_toolsbar_setting);
			imgBtnSetting.setOnClickListener(MainActivity.this);
			imgBtnUser = (ImageButton)bar.findViewById(R.id.imgbtn_toolsbar_user);
			imgBtnUser.setOnClickListener(this);
			
			txtPageNum = (TextView) findViewById(R.id.txtPageNum);
			txtPageNum.setTextColor(Color.BLACK);
			float scale = this.getResources().getDisplayMetrics().density;
			txtPageNum.setPadding((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 2, 0, 0, 0);
			barWidth = bar.getWidth();
			// 实例化一个list 存放fragment
			fragments = new ArrayList<Fragment>();
			// 获取ViewPager
			mViewPager = (ViewPager) this.findViewById(R.id.viewPagerMian);
			// 实例化主页的fragment，每页放置8个模块，模8以判断页码
			if( (list.size() % 8) == 0 )
			{
				pageNum = list.size() / 8;
			} else
			{
				pageNum = list.size() / 8 + 1;
			}
			txtPageNum.setText("1/" + pageNum);
			
			for ( int i = 0; i < pageNum; i++ )
			{
				ArrayList<HomeGridView> pageList = new ArrayList<HomeGridView>();
				for ( int j = i * 8; (j < ((i + 1) * 8)) && (j < list.size()); j++ )
				{
					pageList.add(list.get(j));
				}
				HomeFragment homeFragment = new HomeFragment(this, pageList,sckoel);
				// 将fragment存放到list
				fragments.add(homeFragment);
			}
			// 实例化MainFragmentAdapter
			mMainFragmentAdapter = new MainFragmentAdapter(this, getSupportFragmentManager(), fragments);
			mViewPager.setAdapter(mMainFragmentAdapter);
			mViewPager.setOnPageChangeListener(new OnPageChangeListener()
			{
				@Override
				public void onPageSelected(int position)
				{
					txtPageNum.setText((position + 1) + "/" + pageNum);
				}


				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2)
				{
				}


				@Override
				public void onPageScrollStateChanged(int arg0)
				{
				}
			});
		}
		catch (Exception e)
		{
			LogUtil.i(tag, e.toString());
		}
		
	}

	private void login()
	{
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{

	}


	@SuppressLint("NewApi")
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		int index = tab.getPosition();
		mViewPager.setCurrentItem(index);
	}


	@Override
	public void onTabUnselected(Tab artabg0, FragmentTransaction ft)
	{

	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return super.onKeyDown(keyCode, event);
	}


	/**
	 * 屏蔽每个Activity里的返回键，统一由TabActivity获取按键
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event)
	{
		// 由于键盘事件有一个按下去和上来，如果没有用&&判断，会相应两次
		if( event.getKeyCode() == KeyEvent.KEYCODE_BACK )
		{
			if( restartCount == 0 )
			{
				restartCount++;
				return true;
				// } else if (customMenu.isShowing()) { // 判断菜单是否打开
				// customMenu.dismiss();
				// restartCount = 0;
				// return true;
			} else if( restartCount == 1 )
			{ // 隐藏
				hide();
				((MyApplication) this.getApplication()).setHomeStop(true);
				restartCount = 0;
				return true;
			} else
			{
				restartCount = 0;
				return super.dispatchKeyEvent(event);
			}
		}
		return super.dispatchKeyEvent(event);
	}


	private void buildNotification(String username)
	{
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification(R.drawable.appicon, getString(R.string.app_name), System.currentTimeMillis());
		try
		{
			// 加载类，如果直接通过类名，会在点击时重新加载页面，无法恢复最后页面状态。
			Intent contentIntent = new Intent(MainActivity.this, Class.forName("com.soul.project.story.activity.MainActivity"));
			contentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, contentIntent, 0);
			// pendingIntent.send(0, new OnFinished() {
			//
			// @Override
			// public void onSendFinished(PendingIntent pendingIntent, Intent
			// intent,
			// int resultCode, String resultData, Bundle resultExtras) {
			// if (userInfo == null) {
			// try {
			// userInfo = ((MyApplication)
			// MainActivity.this.getApplication())
			// .getUserInfo(MainActivity.this, baseHandler);
			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// }
			// }, baseHandler);
			notification.setLatestEventInfo(MainActivity.this, getString(R.string.app_name), "", pendingIntent);
			notification.flags |= Notification.FLAG_ONGOING_EVENT;
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
		}
		catch (ClassNotFoundException ex)
		{
			Log.e(tag, "buildNotification: " + ex.getMessage());
		}
	}


	/**
	 * 系统隐藏操作 跳转到桌面
	 */
	private void hide()
	{
		// 显示Notification
		mNotificationManager.notify(HandlerMessage.MainNotification.getMsgWhat(), notification);

		// 跳转桌面
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}


	/**
	 * @return the restartCount
	 */
	public int getRestartCount()
	{
		return restartCount;
	}


	/**
	 * @param restartCount
	 *            the restartCount to set
	 */
	public void setRestartCount(int restartCount)
	{
		this.restartCount = restartCount;
	}


	@Override
	protected void onPause()
	{
		super.onPause();
	}


	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		((MyApplication) this.getApplication()).removeActivity(this);
	}


	/**
	 * 拦截所有的STOP状态，例如（Home键，突然来电等等）
	 * 
	 * @author Mentago
	 */
	@Override
	protected void onStop()
	{
		if( !((MyApplication) this.getApplication()).isHomeStop() )
		{
			mNotificationManager.notify(HandlerMessage.MainNotification.getMsgWhat(), notification);
		}

		super.onStop();
	}


	/**
	 * 从Stop状态返回调用Restart
	 */
	@Override
	protected void onRestart()
	{
		try
		{
			// if (userInfo == null) {
			//
			// userInfo = ((MyApplication) this.getApplication())
			// .getUserInfo(this, baseHandler);
			// }
			// 设置标志位
			((MyApplication) this.getApplication()).setHomeStop(false);
			// 关闭Notification
			mNotificationManager.cancel(HandlerMessage.MainNotification.getMsgWhat());

			super.onRestart();
			if( !((MyApplication) this.getApplication()).isActivityContain(this) )
			{
				((MyApplication) this.getApplication()).addActivity(this);
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * 也可能调用Start
	 */
	@Override
	protected void onStart()
	{
		// 设置标志位
		((MyApplication) this.getApplication()).setHomeStop(false);

		super.onStart();
		//
		if( !((MyApplication) this.getApplication()).isActivityContain(this) )
		{
			((MyApplication) this.getApplication()).addActivity(this);
		}
	}


	@Override
	protected void onResume()
	{
		try
		{
			// System.out.println("onResumeonResumeonResume");
			// if (userInfo == null) {
			//
			// userInfo = ((MyApplication) this.getApplication())
			// .getUserInfo(this, baseHandler);
			//
			// }
			super.onResume();
			restartCount = 0;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.imgbtn_toolsbar_exit :
				exit();
				break;
			case R.id.imgbtn_toolsbar_setting :
				toSettingActivity();
				break;
			case R.id.imgbtn_toolsbar_user:
				login();
				break;
			default :
				break;
		}
	}

//	BaseHandler baseHandler = new BaseHandler(this)
//	{
//		@Override
//		public void handleMessage(Message msg)
//		{
//			super.handleMessage(msg);
//		}
//	};


	private void toSettingActivity()
	{
		// Intent intent = new Intent(MainActivity.this,SettingActivity.class);
		// startActivity(intent);
	}


	private void exit()
	{
		AlertDialog.showSimpleAlertDialog(this, R.drawable.dialog_alert_icon, getString(R.string.exit_confirm), getString(R.string.exit_content), getString(R.string.confirm),
				new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						AlertDialog.dialog.dismiss();
						((MyApplication) MainActivity.this.getApplication()).exit();
					}
				}, getString(R.string.back), new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						AlertDialog.dialog.dismiss();
					}
				});
	}
}
