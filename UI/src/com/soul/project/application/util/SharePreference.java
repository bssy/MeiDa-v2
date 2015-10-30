package com.soul.project.application.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Description : 小数据参数记录类  用于保存小数据短期数据 
 * Create time : 2014-12-21 下午7:42:18
 * Project name: yihuan
 * File name   : SharePreference.java
 * Encoded     : UTF-8
 * @author     许仕永
 * @JKD        JDK 1.6.0_21 
 * @version    v1.0.0
 */
public class SharePreference {

	private Context context;
	private SharedPreferences sp;
	private static SharePreference instance;
	
	private SharePreference(Context context)
	{
		this.context = context;
	}
	
	/**
	 * 获取实例
	 * @param context
 	 * @param filename 本偏好文件的名字
	 * @return 返回本类实例
	 */
	public static SharePreference getInstance(Context context)
	{
		if(instance == null)
			instance = new SharePreference(context);
		return instance;
	}
	
	/**
	 * 传入键值对
	 * @param key  键
	 * @param value 值
	 */
	public void setValue(String key,String value)
	{
		//为防止出错，在此添加判断来减少发生强退事件的发生
		if(value == null)
		{
			value = "null";
			Log.v("System.out", key+":"+value);
		}
		sp = context.getSharedPreferences("yihua_value.txt", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	/**
	 * 传入键值对
	 * @param key  键
	 * @param value 值
	 */
	public void setValue(String key,int value)
	{
		//为防止出错，在此添加判断来减少发生强退事件的发生
		sp = context.getSharedPreferences("yihua_value.txt", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}

//	
	
	/**
	 * 寻键 取值
	 * @param key 欲取的值的键
	 * @return 返回传入键的值，如果无法取值则默认返回值为-1
	 */
	public String getValue(String key)
	{
		sp = context.getSharedPreferences("yihua_value.txt", Context.MODE_PRIVATE);
		return sp.getString(key, "-1");
	}
	/**
	 * 寻键 取值
	 * @param key 欲取的值的键
	 * @return 返回传入键的值，如果无法取值则默认返回值为-1
	 */
	public int getIntValue(String key)
	{
		sp = context.getSharedPreferences("yihua_value.txt", Context.MODE_PRIVATE);
		return sp.getInt(key, -1);
	}
	
	/**记录最近一次登录的帐号**/
	public void setLastLoginInfo(boolean state, String userName,String passWord)
	{
		sp = context.getSharedPreferences("lastLoginInfo.txt", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("userName", userName);
		editor.putString("passWord", passWord);
		editor.putBoolean("state", state);
		editor.commit();
	}
}
