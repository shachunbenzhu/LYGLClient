package com.cn.ncist.rq.activity;

import static com.cn.ncist.rq.constant.LoginConstant.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cn.ncist.rq.constant.LoginConstant;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.util.WebUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText et_login_username;
	private EditText et_login_pwd;

	private CheckBox cb_login_showpwd;
	private CheckBox cb_login_rempwd;

	private Button bt_login_log ;
	private Button bt_login_register;
	private Button bt_login_reset;
	private Button bt_main ;
	
	private ProgressDialog progressDialog ;
	
	private Handler handler ;
	private Bundle bundle = new Bundle() ;
	private Message msg = new Message() ;
	
	private int userId ;
	private String userName ;
	
	/*private UserInfo userInfo ;*/
	
	private AlertDialog d ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		init() ;
		
		Intent intent = getIntent() ;
		userId = intent.getIntExtra("userId", 1) ;
		et_login_username.setText(intent.getStringExtra("userName")) ;
		et_login_pwd.setText(intent.getStringExtra("pwd")) ;	
		
		et_login_username.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				final String userName_e = et_login_username.getText().toString() ;
				if (!hasFocus) {					
					if (et_login_username.getText().length() < 0) {
						Toast.makeText(LoginActivity.this, "�������û���", Toast.LENGTH_SHORT).show() ;
					} else {
						userName = getSharedPreferences("travel", MODE_PRIVATE).getString("userName", "") ;
						if(!userName.equals("") && userName_e.equals(userName)) {		
							Toast.makeText(LoginActivity.this, userName + "Ϊ��ǰʹ���û�,�����¼", Toast.LENGTH_SHORT).show(); 
							LoginActivity.this.finish();
					    	Intent intent = new Intent(LoginActivity.this , MainActivity.class) ;
							startActivity(intent);
							overridePendingTransition(R.anim.anim_enter1, R.anim.anim_out1);
					    }
						if(!userName.equals("") && !(userName_e.equals(userName))) {
						    Toast.makeText(LoginActivity.this, "��ǰ�Ѿ����û�������ע��", Toast.LENGTH_SHORT).show(); 
						    LoginActivity.this.finish();
						    Intent intent = new Intent(LoginActivity.this , MainActivity.class) ;
							startActivity(intent);
							overridePendingTransition(R.anim.anim_enter1, R.anim.anim_out1);
						} else {
							new Thread(new Runnable() {//��ѯ��¼���û����Ƿ����
								@Override
								public void run() {
									try {
										JSONArray params = new JSONArray().put(new JSONObject().put("userName", et_login_username.getText().toString()).put("action", "testUserName")) ;
										JSONArray resultArray = WebUtil.postRequest("LoginServlet", params, handler) ;
										
										if (resultArray != null) {
											int userId = resultArray.getJSONObject(0).getInt("userId") ;
											if (userId == -1) {
												msg = new Message() ;
												msg.what = LoginConstant.USERNAME_NOTEXISTS ;
												bundle.putString("msg" , "������û��������ڣ����������룡") ;
												msg.setData(bundle) ;
												handler.sendMessage(msg) ;
											} else {
												String repwd = resultArray.getJSONObject(0).getString("repwd") ;
												if (repwd.equals("yes")) {
													msg = new Message() ;
													msg.what = LoginConstant.AUTO_SET_PWD ;
													bundle.putString("msg" , "�û���ס���벢�Զ����ã�") ;
													bundle.putString("pwd", resultArray.getJSONObject(0).getString("pwd")) ;
													msg.setData(bundle) ;
													handler.sendMessage(msg) ;
												}
											}
										 }
									} catch (JSONException e) {
										msg = new Message() ;
										msg.what = LoginConstant.SHOW_AUTH_TOST_MESSAGE ;
								        bundle.putString("msg" , "����δ���ӣ�������������������޸�") ;
								        msg.setData(bundle) ;
								    	handler.sendMessage(msg) ;
										e.printStackTrace();
									}
								}
							}).start() ;
						}
					}
				}
			}
		}) ;
		
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case SHOW_AUTH_TOST_MESSAGE :
					bundle = msg.getData();
					String showmessage = bundle.getString("msg") ;
					progressDialog.dismiss() ;
					Toast.makeText(LoginActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
					break ;
				case USERNAME_NOTEXISTS :
					bundle = msg.getData();
					showmessage = bundle.getString("msg") ;
					progressDialog.dismiss() ;
					Toast.makeText(LoginActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
					
					et_login_username.setText("") ;
					et_login_username.setFocusable(true) ;
					et_login_username.setFocusableInTouchMode(true) ;
					et_login_username.requestFocus() ;
					et_login_username.requestFocusFromTouch() ;
					break ;
				case PWD_ERROR :
					bundle = msg.getData();
					showmessage = bundle.getString("msg") ;
					progressDialog.dismiss() ;
					Toast.makeText(LoginActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
					
					et_login_pwd.setText("") ;
					et_login_username.setFocusable(true) ;
					et_login_username.setFocusableInTouchMode(true) ;
					et_login_username.requestFocus() ;
					et_login_username.requestFocusFromTouch() ;
					break ;
				case LOGIN_SUCCESS :
					bundle = msg.getData();
					showmessage = bundle.getString("msg") ;
					progressDialog.dismiss() ;
					Toast.makeText(LoginActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
					 
				    Intent intent = new Intent(LoginActivity.this , MainActivity.class) ;
				    SharedPreferences sharedPreferences = getSharedPreferences("travel", MODE_PRIVATE) ;
				    Editor editor = sharedPreferences.edit() ;
				    editor.putInt("userId", userId) ;
				    editor.putString("userName", userName) ;
				    editor.commit() ;
				    startActivity(intent);
				    finish() ;
				    overridePendingTransition(R.anim.anim_enter1, R.anim.anim_out1);
				    break ;
				case UPDATE_REPWD_SUCCESS :
					bundle = msg.getData();
					showmessage = bundle.getString("msg") ;
					Toast.makeText(LoginActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
					break ;
				case AUTO_SET_PWD :
					bundle = msg.getData();
					showmessage = bundle.getString("msg") ;
					String pwd = bundle.getString("pwd") ;
					cb_login_rempwd.setChecked(true) ;
					et_login_pwd.setText(pwd) ;
					Toast.makeText(LoginActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
					break ;
				default:
					break ;
				}
			}
		} ;
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cb_login_showpwd:
			if (LoginActivity.this.cb_login_showpwd.isChecked()) {
				LoginActivity.this.et_login_pwd
						.setTransformationMethod(HideReturnsTransformationMethod
								.getInstance());
			} else {
				LoginActivity.this.et_login_pwd
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
			}
			break;
		case R.id.cb_login_rempwd:
			final String userName_e = et_login_username.getText().toString() ;
			if (cb_login_rempwd.isChecked()) {
				new Thread(new Runnable() {//���ļ�ס�����״̬
					@Override
					public void run() {
						try {
							JSONArray params = new JSONArray().put(new JSONObject().put("userName", userName_e).put("repwd", "yes").put("action", "updateRePwd")) ;
							JSONArray resultArray = WebUtil.postRequest("LoginServlet", params, handler) ;
							
							if (resultArray != null) {
								String result = resultArray.getJSONObject(0).getString("result") ;
								if (result.equals("ok")) {
									msg = new Message() ;
									msg.what = LoginConstant.UPDATE_REPWD_SUCCESS ;
									bundle.putString("msg" , "�޸��Ƿ��ס����״̬�ɹ���") ;
									msg.setData(bundle) ;
									handler.sendMessage(msg) ;
								}
							 }
						} catch (JSONException e) {
							msg = new Message() ;
							msg.what = LoginConstant.SHOW_AUTH_TOST_MESSAGE ;
					        bundle.putString("msg" , "����δ���ӣ�������������������޸�") ;
					        msg.setData(bundle) ;
					    	handler.sendMessage(msg) ;
							e.printStackTrace();
						}
					}
				}).start() ;
			} else {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							JSONArray params = new JSONArray().put(new JSONObject().put("userName", userName_e).put("repwd", "no").put("action", "updateRePwd")) ;
							JSONArray resultArray = WebUtil.postRequest("LoginServlet", params, handler) ;
							
							if (resultArray != null) {
								String result = resultArray.getJSONObject(0).getString("result") ;
								if (result.equals("ok")) {
									msg = new Message() ;
									msg.what = LoginConstant.UPDATE_REPWD_SUCCESS ;
									bundle.putString("msg" , "�޸��Ƿ��ס����״̬�ɹ���") ;
									msg.setData(bundle) ;
									handler.sendMessage(msg) ;
								}
							 }
						} catch (JSONException e) {
							msg = new Message() ;
							msg.what = LoginConstant.SHOW_AUTH_TOST_MESSAGE ;
					        bundle.putString("msg" , "����δ���ӣ�������������������޸�") ;
					        msg.setData(bundle) ;
					    	handler.sendMessage(msg) ;
							e.printStackTrace();
						}
					}
				}).start() ;
			}
			break;
		case R.id.bt_login_log:
			final String userName_et = et_login_username.getText().toString() ;
			final String pwd = et_login_pwd.getText().toString() ;
			if (userName_et == null || userName_et.equals("")) {
				et_login_username.setFocusable(true) ;
				et_login_username.setFocusableInTouchMode(true) ;
				et_login_username.requestFocus() ;
				et_login_username.requestFocusFromTouch() ;
				Toast.makeText(LoginActivity.this, "�������û���", Toast.LENGTH_SHORT).show() ;
			} else if (pwd == null || pwd.equals("")) {
				et_login_pwd.setFocusable(true) ;
				et_login_pwd.setFocusableInTouchMode(true) ;
				et_login_pwd.requestFocus() ;
				et_login_pwd.requestFocusFromTouch() ;
				Toast.makeText(LoginActivity.this, "����������", Toast.LENGTH_SHORT).show() ;
			} else if (pwd.length() < 4) {
				et_login_pwd.setFocusable(true) ;
				et_login_pwd.setFocusableInTouchMode(true) ;
				et_login_pwd.requestFocus() ;
				et_login_pwd.requestFocusFromTouch() ;
				Toast.makeText(LoginActivity.this, "���벻����С��4λ", Toast.LENGTH_SHORT).show() ;
			}else {
			    progressDialog.show() ;
			    new Thread(new Runnable() {
			    	@Override
			    	public void run() {
			    		try {
			    				 
			    			JSONArray params = new JSONArray().put(new JSONObject().put("userName", userName_et).put("pwd", pwd).put("action", "login")) ;
			    				 /*List<NameValuePair> params = new ArrayList<NameValuePair>() ;//ʹ��params����Ҫ���ݵ�Post����
			    				 params.add(new BasicNameValuePair("userName", userName_et)) ;
			    				 params.add(new BasicNameValuePair("pwd", pwd)) ;
			    				 params.add(new BasicNameValuePair("action", "login")) ;*/
			    			JSONArray result = WebUtil.postRequest("LoginServlet", params, handler) ;
			    			if (result != null) {
								userId = result.getJSONObject(0).getInt("userId") ;
								userName = result.getJSONObject(0).getString("userName") ;
			    			}
			    			if (userId == -1) {
			    				msg = new Message() ; 
			    				msg.what = LoginConstant.USERNAME_NOTEXISTS ;
			    				bundle.putString("msg" , "��������û��������ڣ�") ;
			    				msg.setData(bundle) ;
			    				handler.sendMessage(msg) ;
							} else if (userId == 0) {
								msg = new Message() ; 
			    				msg.what = LoginConstant.PWD_ERROR ;
			    				bundle.putString("msg" , "��������������") ;
			    				msg.setData(bundle) ;
			    				handler.sendMessage(msg) ;
							} else {
								msg = new Message() ; 
			    				msg.what = LoginConstant.LOGIN_SUCCESS ;
			    				bundle.putString("msg" , "��¼�ɹ���") ;
			    				msg.setData(bundle) ;
			    				handler.sendMessage(msg) ;
							}
						} catch (Exception e) {//��������쳣�ͷ���handler
							msg = new Message() ; 
						    msg.what = LoginConstant.SHOW_AUTH_TOST_MESSAGE ;
						    bundle.putString("msg" , "����δ���ӣ�����������������µ�½") ;
						    msg.setData(bundle) ;
						    handler.sendMessage(msg) ;
						}
			    	}
			    }).start() ;
			}
			break ;
		case R.id.bt_login_register:
			Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			break ;
		case R.id.bt_login_reset:
			et_login_username.setText(null);
			et_login_pwd.setText(null);
			break ;
		case R.id.bt_main :
			intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			break ;
		default:
			break;
		}
	}
	
	
	public void init() {
		et_login_username = (EditText) super.findViewById(R.id.et_login_username);
		et_login_pwd = (EditText) super.findViewById(R.id.et_login_pwd);

		cb_login_showpwd = (CheckBox) super.findViewById(R.id.cb_login_showpwd);
		cb_login_rempwd = (CheckBox) super.findViewById(R.id.cb_login_rempwd);

		bt_login_log = (Button) super.findViewById(R.id.bt_login_log);
		bt_login_register = (Button) super.findViewById(R.id.bt_login_register);
		bt_login_reset = (Button) super.findViewById(R.id.bt_login_reset);
		bt_main = (Button) super.findViewById(R.id.bt_main) ;
		
		progressDialog = new ProgressDialog(LoginActivity.this) ;
		progressDialog.setTitle("��¼��") ;
		progressDialog.setMessage("��¼�У����Ͼͺã�") ;
		
		cb_login_showpwd.setOnClickListener(this) ;
		cb_login_rempwd.setOnClickListener(this) ;
		bt_login_log.setOnClickListener(this) ;
		bt_login_register.setOnClickListener(this) ;
		bt_login_reset.setOnClickListener(this) ;
		bt_main.setOnClickListener(this) ;
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			overridePendingTransition(R.anim.anim_entry, R.anim.anim_enter2);
			finish();
		}
		return false;
	}

}
