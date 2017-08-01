package com.cn.ncist.rq.activity;

import static com.cn.ncist.rq.constant.NoteListConstant.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cn.ncist.rq.adapter.NoteListAdapter;
import com.cn.ncist.rq.constant.NoteListConstant;
import com.cn.ncist.rq.entity.NoteInfo;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.util.Tools;
import com.cn.ncist.rq.util.WebUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class NoteListActivity extends Activity {

	private LinearLayout ll_notelist_ll ;
	private Button bt_notelist_back ;
	private ListView lv_notelist_list ;
	private NoteListAdapter noteListAdapter ;
	List<NoteInfo> notelist = new ArrayList<NoteInfo>() ;
	
	private Handler handler ;
	private Bundle bundle = new Bundle() ;
	private Message msg = new Message() ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_notelist) ;
		
		initView() ;
		
		bt_notelist_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish() ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_enter2) ;
			}
		}) ;
		
		lv_notelist_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				NoteInfo noteInfo = noteListAdapter.getItem(arg2) ;
				Intent intent = new Intent(NoteListActivity.this, NoteDetileActivity.class) ;
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
						Toast.makeText(NoteListActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
					    break ;
					case NOTE_NOTEXISTS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						noteListAdapter.addDataToList(notelist) ;
						ll_notelist_ll.setBackgroundResource(R.drawable.common_default_kong) ;
						lv_notelist_list.setVisibility(View.GONE) ;
						noteListAdapter.notifyDataSetChanged() ;
						Toast.makeText(NoteListActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
						break ;
					case QUERY_NOTE_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						noteListAdapter.addDataToList(notelist) ;
						ll_notelist_ll.setBackgroundResource(R.drawable.transparent) ;
						lv_notelist_list.setVisibility(View.VISIBLE) ;
						noteListAdapter.notifyDataSetChanged() ;
						//Toast.makeText(NoteListActivity.this , showmessage , Toast.LENGTH_LONG).show() ;
						break ;
					default:
						break ;
				}
			}
		} ;
	}
	
	public void getData() {
		noteListAdapter = new NoteListAdapter(NoteListActivity.this) ;
		lv_notelist_list.setAdapter(noteListAdapter) ;
		
		new Thread(new Runnable() {//查询游记
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("noteAddress", "").put("action", "queryAllNote")) ;
					JSONArray resultArray = WebUtil.postRequest("NoteServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ;
							msg.what = NoteListConstant.NOTE_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，暂时没有游记！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							notelist = new ArrayList<NoteInfo>() ;
							for (int i = 0; i < resultArray.length(); i++) {
					            /*JSONObject jsonObj = resultArray.getJSONObject(i);
					            NoteInfo noteInfo = (NoteInfo) jsonObj.get("noteModel") ; */ 
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
								noteInfo.setUser_name(jsonObj.getString("noteUser")) ;
								noteInfo.setUser_touxiang(jsonObj.getString("userTx")) ;
								noteInfo.setNote_seenumber(jsonObj.getInt("noteLlnum")) ;
								noteInfo.setNote_zannumber(jsonObj.getInt("noteZnum")) ;
								noteInfo.setNote_commentnumber(jsonObj.getInt("notePlnum")) ;
					            notelist.add(noteInfo) ;
					        }
						}
						//noteListAdapter.addDataToList(notelist) ;
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
	
	public void onResume() {
		super.onResume();
		getData() ;
	}
	
	private void initView() {
		lv_notelist_list = (ListView) findViewById(R.id.lv_notelist_list) ;
		ll_notelist_ll = (LinearLayout) findViewById(R.id.ll_notelist_ll) ;
		bt_notelist_back = (Button) findViewById(R.id.bt_notelist_back) ;
	}

	
}
