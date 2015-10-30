/**
 * 文件名：HttpUtil.java 2014-12-23 下午4:42:06
 * @author Administrator 	许仕永
 */
package com.soul.project.application.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import android.content.Context;

/**
 * 地区id和state都因为展示需要在本地写死 实际运营需要更改 Description : 网络请求工具 Create time : 2014-12-23
 * 下午4:42:06 Project name: yihuan File name : HttpUtil.java Encoded : UTF-8
 * 
 * @author 许仕永
 * @JKD JDK 1.6.0_21
 * @version v1.0.0
 */
public class HttpUtil
{
	/**
	 * 判断传入的String是否是网址格式
	 * @param url
	 * @return true是网址格式 | false不是网址格式
	 */
	public static boolean isValidHttpUrl(String url)
	{
		String tempString = url.substring(0, 7);
		if(!"http://".equals(tempString))
		{
			return false;
		}
		return true;
	}
	
//	/**
//	 * 划屏广告发布
//	 * @param context
//	 * @param pathToOurFile
//	 * @param urlServer
//	 * @param strAdTypeId
//	 * @param strAdAreaId
//	 * @param strLeftGet
//	 * @param strRightGet
//	 * @param strSingleShareGet
//	 * @param strDailyMaxGetTime
//	 * @param strShowTime1
//	 * @param strShowTime2
//	 * @param startTime
//	 * @param endTime
//	 * @param type
//	 * @return
//	 */
//	public static String post(Context context, String pathToOurFile, String urlServer, String strAdTypeId, String strAdAreaId, String strLeftGet, String strRightGet,
//			/**String strSingleShareGet, String strDailyMaxGetTime,*/ String strShowTime1, String strShowTime2, String startTime, String endTime, int type,String strAdUrl,String strAdContent,String uuid)
//	{
//		try
//		{
//			AddressData addressData = UserDatas.getAddressInfoData(context);
//			HttpClient httpclient = new DefaultHttpClient();
//			// 设置通信协议版本
//			httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//
//			HttpPost httppost = new HttpPost(urlServer);
//			File file = new File(pathToOurFile);
//
//			MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE); // 文件传输
//			ContentBody cbFile = new FileBody(file);
//			mpEntity.addPart("filedata", cbFile); // <input type="file"
//			mpEntity.addPart("yhid", new StringBody(UserDatas.getYhid(context)));
//			mpEntity.addPart("typeid", new StringBody(strAdTypeId));
//			mpEntity.addPart("areaid", new StringBody(strAdAreaId));// 区域id待定
//			mpEntity.addPart("leftscratching", new StringBody(strLeftGet));
//			mpEntity.addPart("rightscratching", new StringBody(strRightGet));
//			mpEntity.addPart("adplaytime", new StringBody(strShowTime1 + "-" + strShowTime2));
//			mpEntity.addPart("x_coordinate", new StringBody(addressData.getX()));
//			mpEntity.addPart("y_coordinate", new StringBody(addressData.getY()));
//			mpEntity.addPart("state", new StringBody("0"));
//			mpEntity.addPart("lefturl", new StringBody(strAdUrl,Charset.forName("UTF-8")));
//			mpEntity.addPart("adtext", new StringBody(strAdContent,Charset.forName("UTF-8")));
//			mpEntity.addPart("starttime", new StringBody(startTime));
//			mpEntity.addPart("endtime", new StringBody(endTime));
//			mpEntity.addPart("type", new StringBody(type + ""));
//			mpEntity.addPart("uuid",new StringBody(uuid));
////			mpEntity.addPart("reward", new StringBody(strSingleShareGet));
////			mpEntity.addPart("max", new StringBody(strDailyMaxGetTime));
//
//			httppost.setEntity(mpEntity);
//
//			HttpResponse response = null;
//
//			response = httpclient.execute(httppost);
//			HttpEntity resEntity = response.getEntity();
//			if( resEntity != null )
//			{
//				return resEntity.toString();
//			}
//		}
//		catch (ClientProtocolException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//
//	/**
//	 * 首页广告提交
//	 * 
//	 * @param context
//	 * @param pathToOurFile
//	 *            文件路径
//	 * @param urlServer
//	 *            服务器地址
//	 * @param strAdTypeId
//	 *            广告类型
//	 * @param strAdAreaId
//	 * @param strAdCommission
//	 * @param strAdPrice
//	 * @param strSingleShareGet
//	 * @param strDailyMaxGetTime
//	 * @param strAdTitle
//	 * @param strShowTime
//	 * @param type
//	 * @return 提交结果
//	 */
//	public static String post(Context context, String pathToOurFile, String urlServer, String strAdTypeId, String strAdAreaId,/** String strAdCommission, String strAdPrice,
//			String strSingleShareGet, String strDailyMaxGetTime,*/ String strAdTitle, String strShowTime, String typeid, String startTime, String endTime, int type,String strAdUrl,String uuid)
//	{
//		try
//		{
//			AddressData addressData = UserDatas.getAddressInfoData(context);
//			HttpClient httpclient = new DefaultHttpClient();
//			// 设置通信协议版本
//			httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//
//			HttpPost httppost = new HttpPost(urlServer);
//			File file = new File(pathToOurFile);
//
//			MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);//, null, Charset.forName("UTF-8"));// new
//			ContentBody cbFile = new FileBody(file);
//			mpEntity.addPart("filedata", cbFile); // <input type="file"
//			mpEntity.addPart("yhid", new StringBody(UserDatas.getYhid(context)));
//			mpEntity.addPart("typeid", new StringBody(strAdTypeId));
//			mpEntity.addPart("areaid", new StringBody(strAdAreaId));// 区域id待定
//			mpEntity.addPart("adtext", new StringBody(strAdTitle, Charset.forName("UTF-8")));
//			mpEntity.addPart("adurl", new StringBody(strAdUrl, Charset.forName("UTF-8")));
//			mpEntity.addPart("adplaytime", new StringBody(strShowTime));
//			mpEntity.addPart("x_coordinate", new StringBody(addressData.getX()));
//			mpEntity.addPart("y_coordinate", new StringBody(addressData.getY()));
//
//			mpEntity.addPart("starttime", new StringBody(startTime));
//			mpEntity.addPart("endtime", new StringBody(endTime));
//			mpEntity.addPart("type", new StringBody(type + ""));
//			mpEntity.addPart("uuid",new StringBody(uuid));
////			mpEntity.addPart("reward", new StringBody(strSingleShareGet));
////			mpEntity.addPart("max", new StringBody(strDailyMaxGetTime));
//
//			mpEntity.addPart("state", new StringBody("0"));
//
//			httppost.setEntity(mpEntity);
//
//			HttpResponse response = null;
//
//			response = httpclient.execute(httppost);
//			HttpEntity resEntity = response.getEntity();
//			if( resEntity != null )
//			{
//				return resEntity.toString();
//			}
//		}
//		catch (ClientProtocolException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//
//	/**
//	 * 添加 商户平台流动广告
//	 * 
//	 * @param context
//	 *            上下文
//	 * @param pathToOurFile
//	 *            图片文件路径
//	 * @param urlServer
//	 *            服务地址
//	 * @param strYhid
//	 *            亿划号
//	 * @param strContent
//	 *            广告内容
//	 * @param strUrl
//	 *            广告链接地址
//	 * @param strIsShow
//	 *            是否显示
//	 * @return
//	 */
//	public static String post(Context context, String pathToOurFile, String urlServer, String strYhid, String strContent, String strUrl, String strIsShow)
//	{
//		try
//		{
//			HttpClient httpclient = new DefaultHttpClient();
//			// 设置通信协议版本
//			httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//			HttpPost httppost = new HttpPost(urlServer);
//
//			File file = new File(pathToOurFile);
//			MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);//, null, Charset.forName("UTF-8"));// new
//																																// MultipartEntity();
//			ContentBody cbFile = new FileBody(file);
//			mpEntity.addPart("filedata", cbFile); // <input type="file"
//			mpEntity.addPart("shopid", new StringBody(UserDatas.getYhid(context)));
//			mpEntity.addPart("context", new StringBody(strContent, Charset.forName("UTF-8")));// (new
//																								// StringBody(strContent));
//			mpEntity.addPart("url", new StringBody(strUrl, Charset.forName("UTF-8")));
//			mpEntity.addPart("shows", new StringBody(strIsShow));
//
//			httppost.setEntity(mpEntity);
//
//			HttpResponse response = null;
//			response = httpclient.execute(httppost);
//			HttpEntity resEntity = response.getEntity();
//
//			if( resEntity != null )
//			{
//				return resEntity.getContentType().toString();
//			}
//		}
//		catch (ClientProtocolException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}


	/**
	 * 普通请求
	 * @param url
	 * @param map
	 * @return
	 */
	public static String post(String url, Map<String, Object> map)
	{
		// 构造一个请求实体类来封装 url、请求的方法、map参数
		RequestEntity entity = new RequestEntity(url, HttpMethod.POST, map);
		String resultJson = null;
		try
		{
			resultJson = HttpHelper.execute(entity);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return resultJson;
	}
}
