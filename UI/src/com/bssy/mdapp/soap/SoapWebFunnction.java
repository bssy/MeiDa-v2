package com.bssy.mdapp.soap;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Log;

public class SoapWebFunnction {
	private Context context;
	public static SoapWebFunnction funnction = null;
	
	private SoapWebFunnction(Context context)
	{
		this.context = context;
	}

	public static SoapWebFunnction getInstance(Context context)
	{
		if(funnction == null)
		{
			funnction = new SoapWebFunnction(context);
		}
		return funnction;
	}
	
	/**
	 * 连接webservice
	 * @author 许仕永(xsy)
	 * des: 
	 * @param methodName 方法名
	 * @param properts   参数
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public String connect(String methodName,Map<String, Object> properts) throws IOException, XmlPullParserException
	{
		if(methodName == null || properts == null)
			return null;
		
		String nameSpace="http://cn.com.bps/";//"http://www.bps.com.cn/HelloWorld"; 
//		String methodName= "getName"; 
		String SOAP_ACTION = nameSpace + methodName; 
		//这里使用localhost或者127.0.0.1会连接失败  而且末尾不能加?wsdl
		String URL = IP.IP+"/CXFDemoService/services/HelloWorldCXF";
        //创建httpTransportSE传输对象
        HttpTransportSE ht = new HttpTransportSE(URL);
        ht.debug = true;
        //使用soap1.2协议创建Envelop对象
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //实例化SoapObject对象
        SoapObject request = new SoapObject(nameSpace, methodName);

        Set<String> keySet = properts.keySet();
        Iterator<String> iterator = keySet.iterator();

        // 参数名一定要和服务器上的一致
        while(iterator.hasNext())
        {
        	String key = iterator.next();
        	request.addProperty(key,properts.get(key));
        }
        
        envelope.bodyOut = request;
        
        Log.i("XU", "ss2=");
        ht.call(SOAP_ACTION, envelope);
    	
			Log.i("XU", "ss4=");
			//当服务器返回的值是String是要用下面这句
			Object object =envelope.getResponse();
			String b = object.toString();//((SoapObject) object).getProperty(0).toString();
			Log.i("XU", "sssss="+b);
			return b;
	}
	
	/** 登录验证 **/
	public String login(String method, String arg1, String arg2) throws XmlPullParserException
	{
		String nameSpace = "http://cn.com.bps/";// "http://www.bps.com.cn/HelloWorld";
		String methodName = "login";
		String SOAP_ACTION = nameSpace + methodName;
		// 而且末尾不能加?wsdl
		String URL = IP.IP + "/CXFDemoService/services/HelloWorldCXF";
		// 创建httpTransportSE传输对象
		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;
		// 使用soap1.2协议创建Envelop对象
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		// 实例化SoapObject对象
		SoapObject request = new SoapObject(nameSpace, methodName);

		request.addProperty("userName", arg1);
		request.addProperty("passWord", arg2);
		// 将SoapObject对象设置为SoapSerializationEnvelope对象的传出SOAP消息
		envelope.bodyOut = request;
		try
		{
			// 调用webService
			ht.call(SOAP_ACTION, envelope);

			// 当服务器返回的值是String是要用下面这句
			Object object = envelope.getResponse();
			String b = object.toString();
			// String b2 = ((SoapObject) object).getProperty(0).toString();
			return b;
		} catch (Exception e)
		{
		}
		return null;
	}

	/** 注册验证 **/
	public String register(String method, String arg1, String arg2)
	{
		String nameSpace = "http://cn.com.bps/";// "http://www.bps.com.cn/HelloWorld";
		String methodName = method;
		String SOAP_ACTION = nameSpace + methodName;
		// 而且末尾不能加?wsdl
		String URL = IP.IP + "/CXFDemoService/services/HelloWorldCXF";
		// 创建httpTransportSE传输对象
		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;
		// 使用soap1.2协议创建Envelop对象
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		// 实例化SoapObject对象
		SoapObject request = new SoapObject(nameSpace, methodName);

		request.addProperty("userName", arg1);
		request.addProperty("passWord", arg2);
		// 将SoapObject对象设置为SoapSerializationEnvelope对象的传出SOAP消息
		envelope.bodyOut = request;
		try
		{
			// 调用webService
			ht.call(SOAP_ACTION, envelope);
			
			// 当服务器返回的值是String是要用下面这句
			Object object = envelope.getResponse();
			String b = object.toString();
			Log.i("XU", b);
			// String b2 = ((SoapObject) object).getProperty(0).toString();
			return b;
		} catch (Exception e)
		{
		}
		return null;
	}
}
