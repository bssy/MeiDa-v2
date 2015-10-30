/**
 * 文件名：NetWorkUtil.java 2015-2-28 上午10:13:50
 * @author Administrator 	许仕永
 */
package com.soul.project.application.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Description : Create time : 2015-2-28 上午10:13:50 Project name: yihuan File
 * name : NetWorkUtil.java Encoded : UTF-8
 * 
 * @author 许仕永
 * @JKD JDK 1.6.0_21
 * @version v1.0.0
 */
public class NetWorkUtil
{
	/**
	 * 判断是否有可用网络  (运行环境  UI线程)
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context)
	{
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if( connectivityManager == null )
		{
			return false;
		} else
		{
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if( networkInfo != null && networkInfo.length > 0 )
			{
				for ( int i = 0; i < networkInfo.length; i++ )
				{
					// 判断当前网络状态是否为连接状态
					if( networkInfo[i].getState() == NetworkInfo.State.CONNECTED )
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 判断是否有可用网络  并在如果网络不可用的情况下生成提示吐司(运行环境  UI线程)
	 * @param context
	 * @return false不可用 | true可用
	 */
	public static boolean isNetworkAvailableAndTip(Context context)
	{
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if( connectivityManager == null )
		{
			ToastUtil.show(context, "网络不可用", ToastUtil.WARN);
			return false;
		} else
		{
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if( networkInfo != null && networkInfo.length > 0 )
			{
				for ( int i = 0; i < networkInfo.length; i++ )
				{
					// 判断当前网络状态是否为连接状态
					if( networkInfo[i].getState() == NetworkInfo.State.CONNECTED )
					{
						return true;
					}
				}
			}
		}
		ToastUtil.show(context, "网络不可用", ToastUtil.WARN);
		return false;
	}
}
