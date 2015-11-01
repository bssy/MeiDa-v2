package com.soul.project.story.activity;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.meida.app.api.API;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

	public class MyApplication extends Application{

		public static IWXAPI wxapi;
//		public static String mAPPid= "wx9972e13ac66616bf";
		private static final String tag = "SchoolApplication";

		private static Context context = null;
		private boolean homeStop = false;
		// 判断Home Activity是否为Stop状�?
		private final List<Activity> activityList = new LinkedList<Activity>();
//		private UserInfo userInfo = null;
		
		private static MyApplication instance;
		
		
		public static MyApplication getContext(){
			return instance;
		}


		@Override
		public void onCreate() {
			// TODO Auto-generated method stub
			super.onCreate();
			if(wxapi == null)
			{
				wxapi = WXAPIFactory.createWXAPI(this, API.AppID,  true); 
				wxapi.registerApp(API.AppID);
			}
			instance=this;
			
			initImageLoader(getApplicationContext());
		}
		
		/**
		 * 添加Activity到容器中
		 * 
		 * @param activity
		 */
		public void addActivity(Activity activity) {
			activityList.add(activity);
		}

		/**
		 * 
		 */
//		public void logout(Activity a, BaseHandler baseHandler) {
//			try {
//				// �?��其它应用
//				for (Activity activity : activityList) {
//					Log.i(tag,
//							"activity.getPackageName(): "
//									+ activity.getPackageName());
//					if ((!(activity.isFinishing())) && (a != activity)) {
//						activity.finish();
//					}
//				}
//				activityList.clear();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

		/**
		 * 遍历�?��的Activity并finish
		 */
		public void exit() {

			// �?��其它应用
			for (Activity activity : activityList) {
				Log.i(tag,
						"activity.getPackageName(): " + activity.getPackageName());
				if (!(activity.isFinishing())) {
					activity.finish();
				}
			}
			activityList.clear();

			/*
			 * ActivityManager activityMgr = (ActivityManager)
			 * this.getSystemService(ACTIVITY_SERVICE);
			 * activityMgr.restartPackage(getPackageName());
			 */

			// 跳转到桌�?
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);

			android.os.Process.killProcess(android.os.Process.myPid());
		}

		/**
		 * 从容器中删除Activity
		 * 
		 * @param activity
		 */
		public void removeActivity(Activity activity) {
			activityList.remove(activity);
		}

		/**
		 * 判断容器中是否存在这个Activity
		 * 
		 * @param activity
		 * @return
		 */
		public boolean isActivityContain(Activity activity) {
			return activityList.contains(activity);
		}

		/**
		 * @return the homeStop
		 */
		public boolean isHomeStop() {
			return homeStop;
		}

		/**
		 * @param homeStop
		 *            the homeStop to set
		 */
		public void setHomeStop(boolean homeStop) {
			this.homeStop = homeStop;
		}


		public static void initImageLoader(Context context) {
			// This configuration tuning is custom. You can tune every option, you
			// may tune some of them,
			// or you can create default configuration by
			// ImageLoaderConfiguration.createDefault(this);
			// method.
//			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//					context).threadPriority(Thread.NORM_PRIORITY - 2)
//					.denyCacheImageMultipleSizesInMemory()
//					.discCacheFileNameGenerator(new Md5FileNameGenerator())
//					.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging() // Not
//																					// necessary
//																					// in
//																					// common
//					.build();
//			// Initialize ImageLoader with configuration.
//			ImageLoader.getInstance().init(config);
		}
	}
