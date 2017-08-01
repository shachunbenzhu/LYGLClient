package com.cn.ncist.rq.activity;

import static com.cn.ncist.rq.constant.RegisterConstant.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cn.ncist.rq.constant.LoginConstant;
import com.cn.ncist.rq.constant.RegisterConstant;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.util.WebUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends Activity implements OnClickListener {

	private EditText et_register_username ;
	private EditText et_register_pwd ;
	
	/*private Spinner touxiang_spinner ;
	private GridView touxiang_gridview ;*/
	
	private Button bt_register_reg ;
	private Button bt_register_reset ;
	
	/*private TouxiangGridViewAdapter adapter ;
	
	private TouxiangInfo touxiangInfo ;
	private UserDao userDao ;
	
	private String touxiangType ;			// 根据此判断GridView中显示的内容
	private int touxiangId ;				// 根据GridView中选中的项获取头像Id*/
	private ProgressDialog progressDialog ;
	
	private Handler handler ;
	private Bundle bundle = new Bundle() ;
	private Message msg = new Message() ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		init() ;
		
		//touxiang_spinner.setOnItemSelectedListener(new OnItemSelectedListenerImpl()) ;

		et_register_username.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus) {					
					if (et_register_username.getText().length() < 0) {
						Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show() ;
					} else {
						userNameExit() ;	
					}
				}
			}
		}) ;
		
		/*touxiang_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				touxiangId = adapter.getItem(arg2).getTouxiangId() ;
			}
		}) ;*/
		
		handler = new Handler() {

			public void handleMessage(Message msg) {
				switch (msg.what) {
				case SHOW_AUTH_TOST_MESSAGE :
					bundle = msg.getData();
					String showmessage = bundle.getString("msg") ;
					progressDialog.dismiss() ;
					Toast.makeText(RegisterActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
					break ;
				case USERNAME_EXISTS :
					bundle = msg.getData();
					showmessage = bundle.getString("msg") ;
					progressDialog.dismiss() ;
					Toast.makeText(RegisterActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
					
					et_register_username.setText("") ;
					et_register_username.setFocusable(true) ;
					et_register_username.setFocusableInTouchMode(true) ;
					et_register_username.requestFocus() ;
					et_register_username.requestFocusFromTouch() ;
					break ;
				case REGISTER_SUCCESS :
					bundle = msg.getData();
					showmessage = bundle.getString("msg") ;
					String userName = bundle.getString("userName") ;
					String pwd = bundle.getString("pwd") ;
					progressDialog.dismiss() ;
					Toast.makeText(RegisterActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
					
				    Intent intent = new Intent(RegisterActivity.this , LoginActivity.class) ;
				    intent.putExtra("userName", userName) ;
					intent.putExtra("pwd", pwd) ;
				    startActivity(intent);
				    finish() ;
				    overridePendingTransition(R.anim.anim_enter1, R.anim.anim_out1);
				    break ;
				default:
					break ;
				}
			}
		} ;
	}
	
	/*public class OnItemSelectedListenerImpl implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			touxiangType = arg0.getItemAtPosition(arg2).toString() ;		// 得到选中的选项
			
			adapter.clear() ;
			TouxiangDao touxiangDao = new TouxiangDao() ;
			adapter.addDataToList(touxiangDao.searchTouxiangImageName(touxiangType)) ;
			adapter.notifyDataSetChanged() ;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
		
	}*/

	public void userNameExit() {
		new Thread(new Runnable() {//查询注册的用户名是否存在
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("userName", et_register_username.getText().toString()).put("action", "testUserName")) ;
					JSONArray resultArray = WebUtil.postRequest("LoginServlet", params, handler) ;
					
					if (resultArray != null) {
						int userId = resultArray.getJSONObject(0).getInt("userId") ;
						if (userId != -1) {
							msg = new Message() ;
							msg.what = RegisterConstant.USERNAME_EXISTS ;
							bundle.putString("msg" , "输入的用户名已存在，请重新输入！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						}
					 }
				} catch (JSONException e) {
					msg = new Message() ;
					msg.what = RegisterConstant.SHOW_AUTH_TOST_MESSAGE ;
			        bundle.putString("msg" , "网络未连接，请检查您的网络后重新修改") ;
			        msg.setData(bundle) ;
			    	handler.sendMessage(msg) ;
					e.printStackTrace();
				}
			}
		}).start() ;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_register_reg:
			final String userName = et_register_username.getText().toString() ;
			final String pwd = et_register_pwd.getText().toString() ;
			if (userName == null || userName.equals("")) {
				et_register_username.setFocusable(true) ;
				et_register_username.setFocusableInTouchMode(true) ;
				et_register_username.requestFocus() ;
				et_register_username.requestFocusFromTouch() ;
				Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show() ;
			} else if (pwd == null || pwd.equals("")) {
				et_register_pwd.setFocusable(true) ;
				et_register_pwd.setFocusableInTouchMode(true) ;
				et_register_pwd.requestFocus() ;
				et_register_pwd.requestFocusFromTouch() ;
				Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show() ;
			} else if (pwd.length() < 4) {
				et_register_pwd.setFocusable(true) ;
				et_register_pwd.setFocusableInTouchMode(true) ;
				et_register_pwd.requestFocus() ;
				et_register_pwd.requestFocusFromTouch() ;
				Toast.makeText(RegisterActivity.this, "密码不可以小于4位", Toast.LENGTH_SHORT).show() ;
			} else {
				/*UserInfo userInfo = new UserInfo(userName, password, touxiangId, Tools.getCurrentDateWithAllAndTime());
				int userId = userDao.insert(userInfo) ;
				
				AccountDao accountDao = new AccountDao() ;
				AccountInfo accountInfo = new AccountInfo(userId, "现金", 0.00, "现金", 1, "personal_account_xianjin") ;
				accountDao.addAccount(accountInfo) ;*/
				new Thread(new Runnable() {//注册用户
					@Override
					public void run() {
						try {
							JSONArray params = new JSONArray().put(new JSONObject().put("userName", userName).put("pwd", pwd).put("action", "register")) ;
							JSONArray resultArray = WebUtil.postRequest("LoginServlet", params, handler) ;
							
							if (resultArray != null) {
								String result = resultArray.getJSONObject(0).getString("result") ;
								if (result.equals("ok")) {
									msg = new Message() ;
									msg.what = RegisterConstant.REGISTER_SUCCESS ;
									bundle.putString("msg" , "注册用户成功！") ;
									bundle.putString("userName", userName) ;
									bundle.putString("pwd", pwd) ;
									msg.setData(bundle) ;
									handler.sendMessage(msg) ;
								}
							 }
						} catch (JSONException e) {
							msg = new Message() ;
							msg.what = LoginConstant.SHOW_AUTH_TOST_MESSAGE ;
					        bundle.putString("msg" , "网络未连接，请检查您的网络后重新修改") ;
					        msg.setData(bundle) ;
					    	handler.sendMessage(msg) ;
							e.printStackTrace();
						}
					}
				}).start() ;
			}
			break ;
		case R.id.bt_register_reset:
			et_register_username.setText(null) ;
			et_register_pwd.setText(null) ;
			//touxiangId = 0 ;
			break ;
		default:
			break ;
		}
	}
	
	public void init() {
		et_register_username = (EditText)super.findViewById(R.id.et_register_username) ;
		et_register_pwd = (EditText) super.findViewById(R.id.et_register_pwd) ;
		
		/*touxiang_spinner = (Spinner) findViewById(R.id.touxiang_spinner) ;
		touxiang_gridview = (GridView) findViewById(R.id.touxiang_gridview) ;*/
		
		bt_register_reg = (Button) super.findViewById(R.id.bt_register_reg) ;
		bt_register_reset = (Button) super.findViewById(R.id.bt_register_reset) ;
		
		progressDialog = new ProgressDialog(RegisterActivity.this) ;
		progressDialog.setTitle("注册中") ;
		progressDialog.setMessage("注册中，马上就好！") ;
		
		bt_register_reg.setOnClickListener(this) ;
		bt_register_reset.setOnClickListener(this) ;
		/*userDao = new UserDao() ;
		
		adapter = new TouxiangGridViewAdapter(RegisterActivity.this) ;
		touxiang_gridview.setAdapter(adapter) ;*/
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			overridePendingTransition(R.anim.anim_entry, R.anim.anim_enter2);
			finish();
		}
		return false;
	}

}


