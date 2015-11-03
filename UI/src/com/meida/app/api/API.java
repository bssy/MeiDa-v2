package com.meida.app.api;

public interface API {


	
	/** HOST 主机**/
	String Host = "192.168.1.35/";  // ||localhost:8080
	
	// 微信
	String AppID = "wx9972e13ac66616bf";
	String AppSecret = "5cc0dace9fb82c488dd2006dc442f6d7";
	
	// QQ
	String QQAPPID = "1104799437";
	String QQAPPKEY = "zQKefzrKZ3BvDGeC";
	String SCOPE = "all";///"get_user_info,get_simple_userinfo,get_user_profile,get_app_friends";
//	连接失败java.io.IOException: Unable to start Service Discovery

	/** 蓝牙适配 UUID	**/
	String BLUE_TOOTH_API_UUID = "00001101-0000-1000-8000-00805F9B34FB";//"11223344-5566-7788-99AA-BBCCDDEEFF00";
//	"00001101-0000-1000-8000-00805F9B34FB"
	/** 登陆API	**/  
	String apiLogin = "/signin";
	
	/** 注册 API  **/
	String apiRegister = "/signup";
	

}
