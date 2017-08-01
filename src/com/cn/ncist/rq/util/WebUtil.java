package com.cn.ncist.rq.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import com.cn.ncist.rq.constant.Constant;
import com.cn.ncist.rq.constant.LoginConstant;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class WebUtil {

	public WebUtil() {
	}

	/**
	 * 使用Get请求，获取HttpResponse响应
	 * @param actionName
	 * @return
	 */
	public static JSONArray getRequest(String actionName) {
		String returnValue = "" ;
		JSONArray result = null ;
		
		HttpGet httpGet = new HttpGet(Constant.SERVER_URL + actionName) ;
		HttpClient httpClient = new DefaultHttpClient() ;
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet) ;//执行请求，获取响应结果
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { //响应通过
				returnValue = EntityUtils.toString(httpResponse.getEntity(), "UTF-8") ;
				result = new JSONArray(returnValue) ;
			} else { //响应未通过
				Log.d("服务器响应代码", (new Integer(httpResponse.getStatusLine().getStatusCode())).toString()) ;
				return null ;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result ;
	}
	
	/**
	 * 使用Post请求，获取HttpResponse响应
	 * @param actionName
	 * @param params
	 * @param handler
	 * @return
	 */
	public static JSONArray postRequest(String servletName , JSONArray params , Handler handler) {
		String returnValue = "" ;
		JSONArray result = null ;
		HttpParams httpParams = new BasicHttpParams() ;
		httpParams.setParameter("charset", "UTF-8") ;
		HttpClient httpClient = new DefaultHttpClient(httpParams) ;
		HttpPost httpPost = new HttpPost(Constant.SERVER_URL + servletName) ;
		
		try {
			/*HttpEntity httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8) ;//设置字符集,List<NameValuePair>
			httpPost.setEntity(httpEntity) ;*///设置参数实体
			httpPost.setEntity(new StringEntity(params.toString(), "UTF-8")) ;
			HttpResponse httpResponse = httpClient.execute(httpPost) ;//获取HttpResponse实例
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { //响应通过
				returnValue = EntityUtils.toString(httpResponse.getEntity(), "UTF-8") ;
				result = new JSONArray(returnValue) ;
			} else { //响应未通过
				Log.d("服务器响应代码", (new Integer(httpResponse.getStatusLine().getStatusCode())).toString()) ;
				//sendHandlerMsg(LoginConstant.SHOW_AUTH_TOST_MESSAGE , "服务器响应无结果！" , handler	) ; 
				return null ;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown() ;
		}
		return result ;
	}
	
	//发送错误handler信息
	public static void sendHandlerMsg(int msgwhat , String errorpoint , Handler h) {
		//开启错误对话框
		Message msg = new Message() ;
		Bundle b = new Bundle() ;
		msg.what = msgwhat ;
		b.putString("msg", errorpoint);
		msg.setData(b);
		h.sendMessage(msg);
	}
}
