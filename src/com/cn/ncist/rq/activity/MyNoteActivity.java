package com.cn.ncist.rq.activity;

import static com.cn.ncist.rq.constant.NoteListConstant.NOTE_NOTEXISTS;
import static com.cn.ncist.rq.constant.NoteListConstant.QUERY_NOTE_SUCCESS;
import static com.cn.ncist.rq.constant.NoteListConstant.SHOW_AUTH_TOST_MESSAGE;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cn.ncist.rq.adapter.MyNoteListAdapter;
import com.cn.ncist.rq.adapter.NoteListAdapter;
import com.cn.ncist.rq.constant.NoteListConstant;
import com.cn.ncist.rq.entity.NoteInfo;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.util.WebUtil;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MyNoteActivity extends Activity implements OnClickListener {

	private Button bt_mynote_back ;
	private Button bt_mynote_write ;
	private ListView lv_mynote_list ;
	private LinearLayout ll_mynote_ll ;
	private MyNoteListAdapter myNoteListAdapter ;
	private List<NoteInfo> list = new ArrayList<NoteInfo>() ;
	
	private Handler handler ;
	private Bundle bundle = new Bundle() ;
	private Message msg = new Message() ;
	
	private int userId ;
	private String userName ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_mynote) ;
		
		initView() ;
		
		SharedPreferences sharedPreferences = getSharedPreferences("travel", MODE_PRIVATE) ;
		userId = sharedPreferences.getInt("userId", 1) ;
		userName = sharedPreferences.getString("userName", "") ;
		//userId = 13 ;
		
		if (userId == 1) {
			Toast.makeText(MyNoteActivity.this , "您未登录，请先登录！" , Toast.LENGTH_SHORT).show() ;
			ll_mynote_ll.setBackgroundResource(R.drawable.common_default_kong) ;
			lv_mynote_list.setVisibility(View.GONE) ;
		} else {
			getData() ;
		}
		
		lv_mynote_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				NoteInfo noteInfo = myNoteListAdapter.getItem(arg2) ;
				Intent intent = new Intent(MyNoteActivity.this, NoteDetileActivity.class) ;
				intent.putExtra("noteInfo", noteInfo) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
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
						Toast.makeText(MyNoteActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
					    break ;
					case NOTE_NOTEXISTS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						myNoteListAdapter.addDataToList(list) ;
						ll_mynote_ll.setBackgroundResource(R.drawable.common_default_kong) ;
						lv_mynote_list.setVisibility(View.GONE) ;
						myNoteListAdapter.notifyDataSetChanged() ;
						Toast.makeText(MyNoteActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
						break ;
					case QUERY_NOTE_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						myNoteListAdapter.addDataToList(list) ;
						ll_mynote_ll.setBackgroundResource(R.drawable.transparent) ;
						lv_mynote_list.setVisibility(View.VISIBLE) ;
						myNoteListAdapter.notifyDataSetChanged() ;
						//Toast.makeText(MyNoteActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
						break ;
					default:
						break ;
				}
			}
		} ;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_mynote_back:
			finish() ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_enter2) ;
			break;
		case R.id.bt_mynote_write :
			Intent intent = new Intent(MyNoteActivity.this, WriteNoteActivity.class) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
		default:
			break;
		}
	}
	
	public void getData() {
		myNoteListAdapter = new MyNoteListAdapter(MyNoteActivity.this) ;
		lv_mynote_list.setAdapter(myNoteListAdapter) ;
		
		new Thread(new Runnable() {//查询游记
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("userId", userId).put("action", "queryMyNote")) ;
					JSONArray resultArray = WebUtil.postRequest("NoteServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ;
							msg.what = NoteListConstant.NOTE_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，您暂时没有游记！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							list = new ArrayList<NoteInfo>() ;
							for (int i = 0; i < resultArray.length(); i++) {
								JSONObject jsonObj = resultArray.getJSONObject(i);
								NoteInfo noteInfo = new NoteInfo() ;
								noteInfo.setNote_id(jsonObj.getInt("noteId")) ;
								noteInfo.setNote_title(jsonObj.getString("noteTitle")) ;
								noteInfo.setNote_date(jsonObj.getString("noteDate")) ;
								noteInfo.setNote_days(jsonObj.getInt("noteDays")) ;
								noteInfo.setNote_expend(jsonObj.getString("noteExpend")) ;
								noteInfo.setNote_partner(jsonObj.getString("notePartner")) ;
								noteInfo.setNote_content(jsonObj.getString("noteContent")) ;
								noteInfo.setNote_address(jsonObj.getString("noteAddress")) ;
								noteInfo.setNote_bgimg(jsonObj.getString("noteBg")) ;
								noteInfo.setNote_img(jsonObj.getString("noteImg")) ;
								noteInfo.setUser_name(userName) ;
								noteInfo.setNote_seenumber(jsonObj.getInt("noteLlnum")) ;
								noteInfo.setNote_zannumber(jsonObj.getInt("noteZnum")) ;
								noteInfo.setNote_commentnumber(jsonObj.getInt("notePlnum")) ;
					            list.add(noteInfo) ;
					        }
						}
						msg = new Message() ;
						msg.what = NoteListConstant.QUERY_NOTE_SUCCESS ;
						bundle.putString("msg" , "查询游记成功！") ;
						msg.setData(bundle) ;
						handler.sendMessage(msg) ;
					}
				} catch (JSONException e) {
					msg = new Message() ;
					msg.what = NoteListConstant.SHOW_AUTH_TOST_MESSAGE ;
			        bundle.putString("msg" , "网络未连接，请检查您的网络后重新修改") ;
			        msg.setData(bundle) ;
			    	handler.sendMessage(msg) ;
					e.printStackTrace();
				}
			}
		}).start() ;
	}
	
	public void initView() {
		bt_mynote_back = (Button) findViewById(R.id.bt_mynote_back) ;
		bt_mynote_write = (Button) findViewById(R.id.bt_mynote_write) ;
		lv_mynote_list = (ListView) findViewById(R.id.lv_mynote_list) ;
		ll_mynote_ll = (LinearLayout) findViewById(R.id.ll_mynote_ll) ;
		
		bt_mynote_back.setOnClickListener(this) ;
		bt_mynote_write.setOnClickListener(this) ;
	}

}
