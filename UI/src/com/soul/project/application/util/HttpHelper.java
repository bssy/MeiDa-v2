package com.soul.project.application.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * HTTP缂冩垹绮剁拋鍧楁６閻╃鍙ч弬瑙勭《
 * 
 * @author Li Bin
 */
public class HttpHelper {
	private static final String URL = "";
	private static final String CHARSET = "utf-8";
	public static String USERUA = null;

	/**
	 * 閸欐埊鎷�http鐠囬攱鐪伴敍宀冨箯瀵版鎼锋惔鏃�殶閿燂拷
	 * 
	 * @param entity
	 *            閸栧懎鎯堢拠閿嬬湴鐎圭偘缍嬫穱鈩冧紖閻ㄥ嚧equestEntity鐎圭偘绶�
	 * @return 鏉╂柨娲栭張宥呭閸ｃ劎顏崫宥呯安閻ㄥ嚙SON鐎涙顑佹稉鑼波閿燂拷
	 * @throws Exception
	 */
	public synchronized static String execute(RequestEntity entity)
			throws Exception {
		String jsonResult = "";

		String url = URL + entity.getUrl();
		switch (entity.getMethod()) {
		case HttpMethod.GET:
			jsonResult = get(url, entity.getTextFields());
			break;
		case HttpMethod.POST:
//			if (entity instanceof MultipartFormEntity) {
//				MultipartFormEntity multipartFormEntity = (MultipartFormEntity) entity;
//				jsonResult = postMultipartForm(url, multipartFormEntity);
//			} else {
					jsonResult = post(url, entity.getTextFields());
//			}
			break;
		}

		return jsonResult;
	}

	/**
	 * 閹笛嗩攽GET鐠囬攱鐪�
	 */
	private synchronized static String get(String url,
			Map<String, Object> params) throws Exception {
		String jsonResult = "";
		InputStream is = null;
		try {
			String urlEncodedForm = toUrlEncodedFormParams(params);
			url = url + "?" + urlEncodedForm;
			System.out.println("url:" + url);
			System.out.println("params:" + params);
			HttpURLConnection conn = getHttpURLConnection(url);
			//鐠囬攱鐪伴敓锟�//			conn.setRequestProperty("app_id", "pp_tw_sdk_1_channel_2"); 
//			conn.setRequestProperty("User-Agent", USERUA);
//			conn.setRequestProperty("secret", "2d09fedbb0fe5226acc704c08faf452d");
			
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				jsonResult = read(is);
			} else {
				throw (new Exception());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw (e);
		} catch (IOException e) {
			e.printStackTrace();
			throw (e);
		} finally {
			closeStream(is);
		}

		return jsonResult;
	}


	/**
	 * 閹笛嗩攽http post鐠囬攱鐪�
	 * 
	 * @param url
	 *            鐠囬攱鐪伴惃鍕箛閸斺�娅掔粩鐥憄i閻ㄥ嫰鎽奸敓锟�
	 * @param params
	 *            閸栧懎鎯堢拠閿嬬湴閸欏倹鏆熼惃鍑猘p
	 * @return 鏉╂柨娲朖son閺嶇厧绱￠惃鍕惙鎼存梹鏆熼敓锟�
	 * @throws Exception
	 */
	public synchronized static String post(String url,
			Map<String, Object> params) throws Exception {
		String jsonResult = "";
		OutputStream os = null;
		InputStream is = null;

		try {
			HttpURLConnection conn = getHttpURLConnection(url);
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			conn.setRequestMethod("POST");
//			conn.setRequestProperty("app_id", "pp_tw_sdk_1_channel_2"); 
//			conn.setRequestProperty("User-Agent", USERUA);
//			conn.setRequestProperty("secret", "2d09fedbb0fe5226acc704c08faf452d");

			os = conn.getOutputStream();
			String urlEncodedForm = toUrlEncodedFormParams(params);
			os.write(urlEncodedForm.getBytes());
			os.flush();

			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				jsonResult = read(is);
				Log.i("Post", jsonResult);
			} else {
				throw (new Exception());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw (e);
		} catch (IOException e) {
			e.printStackTrace();
			throw (e);
		} finally {
			closeStream(is);
			closeStream(os);
		}
		return jsonResult;
	}

	/**
	 * 閹笛嗩攽http GET鐠囬攱鐪伴敍宀冨箯閸欐牠鎽奸幒銉﹀灇閸旂喎鎮楅惃鍕翻閸忋儲绁﹂敍灞艰礋娴滃棔绗呮潪鑺ユ瀮娴犺埖妞傛担璺ㄦ暏
	 * 
	 * @param url
	 *            鐠囬攱鐪伴惃鍕箛閸斺�娅掔粩鐥憄i閻ㄥ嫰鎽奸敓锟�
	 * @return 鏉╂柨娲栭敓锟介敓鏂ゆ嫹鏉堟挸鍙嗗ù浣割嚠鐠炩�鐤勯敓锟�
	 */
	public synchronized static InputStream getInputStream(String url) {
		InputStream is = null;
		try {
			HttpURLConnection conn = getHttpURLConnection(url);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

	/**
	 * 閼惧嘲绶盚ttpURLConnection鏉╃偞甯寸�鐐扮伐
	 * 
	 * @param strURL
	 *            閺堝秴濮熼崳銊ь伂api閻ㄥ嫰鎽奸敓锟�
	 * @return 鏉╂柨娲朒ttpURLConnection鐎圭偘绶�
	 * @throws IOException
	 */
	private static HttpURLConnection getHttpURLConnection(String strURL)
			throws IOException {
		URL url = new URL(strURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(15000);
		return conn;
	}

	/**
	 * 娴犲氦绶崗銉︾ウ娑擃叀顕伴崙鐑樻瀮閺堫兛淇婇敓锟�
	 * 
	 * @param is
	 *            閹稿洤鐣鹃惃鍕翻閸忋儲绁�
	 * @return
	 * @throws IOException
	 */
	private static String read(InputStream is) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[128];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}

		String text = new String(out.toByteArray(), "utf-8");
		out.flush();
		closeStream(out);
		return text;
	}

	/**
	 * 鐏忓棗瀵橀崥鐜tp post鐠囬攱鐪伴弫鐗堝祦閻ㄥ埓ap閿涘矁袙閺嬫劒璐烾rlEncoded閺嶇厧绱￠惃鍕摟缁楋缚瑕�
	 * 
	 * @param params
	 *            閸栧懎鎯堢拠閿嬬湴閸欏倹鏆熼惃鍑猘p
	 * @return 鏉╂柨娲栫憴锝嗙�閸氬海娈慤rlEncoded閺嶇厧绱￠惃鍕摟缁楋缚瑕�
	 */
	private static String toUrlEncodedFormParams(Map<String, Object> params) {
		StringBuffer strBuffer = new StringBuffer();
		if (params != null) {
			Set<String> keySet = params.keySet();
			Iterator<String> i = keySet.iterator();
			while (i.hasNext()) {
				String key = i.next();
				String value = params.get(key).toString();
				strBuffer.append(key);
				strBuffer.append("=");
				strBuffer.append(value);
				if (i.hasNext()) {
					strBuffer.append("&");
				}
			}
		} else {
		}
		return strBuffer.toString();
	}

//	/**
//	 * 閹笛嗩攽http post鐠囬攱鐪�閸欐埊鎷�婢跺秴鎮庣悰銊ュ礋閺佺増宓侀敍灞筋洤娑撳﹣绱堕弬鍥︽閺冩儼鐨熼悽銊︻劃閺傝纭�
//	 * 
//	 * @param url
//	 *            鐠囬攱鐪伴惃鍕箛閸斺�娅掔粩鐥憄i閻ㄥ嫰鎽奸敓锟�
//	 * @param entity
//	 *            閸栧懎鎯圡ultipart Form閺嶇厧绱￠惃鍕嚞濮瑰倸鐤勯敓锟�
//	 * @return 鏉╂柨娲朖son閺嶇厧绱￠惃鍕惙鎼存梹鏆熼敓锟�
//	 * 
//	 */
//	public synchronized static String postMultipartForm(String url,
//			MultipartFormEntity entity) {
//		String BOUNDARY = java.util.UUID.randomUUID().toString();
//		String PREFIX = "--", LINEND = "\r\n";
//		String MULTIPART_FROM_DATA = "multipart/form-data";
//
//		String resultStr = null;
//		HttpURLConnection conn = null;
//		DataOutputStream outStream = null;
//		try {
//			conn = getHttpURLConnection(url);
//
//			conn.setDoInput(true);// 閸忎浇顔忔潏鎾冲弳
//			conn.setDoOutput(true);// 閸忎浇顔忔潏鎾冲毉
//			conn.setUseCaches(false);
//			conn.setRequestMethod("POST"); // Post閺傜懓绱�
//			conn.setRequestProperty("connection", "keep-alive");
//			conn.setRequestProperty("Charset", CHARSET);
//
//			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
//					+ ";boundary=" + BOUNDARY);
//
//			outStream = new DataOutputStream(conn.getOutputStream());
//
//			// 妫ｆ牕鍘涚紒鍕閺傚洦婀扮猾璇茬�閻ㄥ嫬寮敓锟�
//			if (entity.getTextFields() != null) {
//				StringBuilder sb = new StringBuilder();
//				for (Map.Entry<String, Object> entry : entity.getTextFields()
//						.entrySet()) {
//					sb.append(PREFIX);
//					sb.append(BOUNDARY);
//					sb.append(LINEND);
//					sb.append("Content-Disposition: form-data; name=\""
//							+ entry.getKey() + "\"" + LINEND);
//					sb.append("Content-Type: text/plain; charset=" + CHARSET
//							+ LINEND);
//					sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
//					sb.append(LINEND);
//					sb.append(entry.getValue().toString());
//					sb.append(LINEND);
//				}
//				outStream.write(sb.toString().getBytes());
//			}
//
//			if (entity.getFileField() != null) {
//				StringBuilder sb1 = new StringBuilder();
//				sb1.append(PREFIX);
//				sb1.append(BOUNDARY);
//				sb1.append(LINEND);
//				sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
//						+ entity.getFileFieldName() + "\"" + LINEND);
//				sb1.append("Content-Type: application/octet-stream; charset="
//						+ CHARSET + LINEND);
//				sb1.append(LINEND);
//				outStream.write(sb1.toString().getBytes());
//
//				InputStream is = new FileInputStream(entity.getFileField());
//				byte[] buffer = new byte[1024];
//				int len = 0;
//				while ((len = is.read(buffer)) != -1) {
//					outStream.write(buffer, 0, len);
//				}
//
//				closeStream(is);
//				outStream.write(LINEND.getBytes());
//			}
//
//			// 鐠囬攱鐪扮紒鎾存将閺嶅浄鎷�
//			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
//			outStream.write(end_data);
//			outStream.flush();
//			resultStr = read(conn.getInputStream());
//
//			Log.d("httpPost", "url:" + url);
//			Log.d("httpPost", "result:" + resultStr);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			closeStream(outStream);
//			conn.disconnect();
//		}
//		return resultStr;
//	}

	/**
	 * 閸忔娊妫碔O閿燂拷
	 * 
	 * @param obj
	 *            閿燂拷閿熸枻鎷锋潏鎾冲弳濞翠焦鍨ㄦ潏鎾冲毉濞翠礁顕挒鈥崇杽閿燂拷
	 */
	public static void closeStream(Object obj) {
		if (obj != null && obj instanceof InputStream) {
			InputStream is = (InputStream) obj;
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (obj != null && obj instanceof OutputStream) {
			OutputStream os = (OutputStream) obj;
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isNetWokrConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info != null) {
			return info.isConnected();
		}
		return false;
	}

}
