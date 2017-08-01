package com.cn.ncist.rq.activity;

import static com.cn.ncist.rq.constant.NoteListConstant.SHOW_AUTH_TOST_MESSAGE;
import static com.cn.ncist.rq.constant.NoteListConstant.ADD_COMMENT_FAIL;
import static com.cn.ncist.rq.constant.NoteListConstant.ADD_COMMENT_SUCCESS;
import static com.cn.ncist.rq.constant.NoteListConstant.NOTE_NOTEXISTS;
import static com.cn.ncist.rq.constant.NoteListConstant.QUERY_NOTE_SUCCESS;
import static com.cn.ncist.rq.constant.NoteListConstant.COMMENT_NOTEXISTS;
import static com.cn.ncist.rq.constant.NoteListConstant.QUERY_COMMENT_SUCCESS;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cn.ncist.rq.adapter.CommentListAdapter;
import com.cn.ncist.rq.adapter.NoteNoteListAdapter;
import com.cn.ncist.rq.constant.MainConstant;
import com.cn.ncist.rq.constant.NoteListConstant;
import com.cn.ncist.rq.entity.CommentInfo;
import com.cn.ncist.rq.entity.NationInfo;
import com.cn.ncist.rq.entity.NoteInfo;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.util.Tools;
import com.cn.ncist.rq.util.WebUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CommentActivity extends Activity {

	private Button bt_comment_back ;
	private RadioButton rb_comment_addcomment ;
	private ListView lv_comment_list ;
	private LinearLayout ll_comment_ll ;
	
	private AlertDialog.Builder dialog ;
	private CommentListAdapter commentListAdapter ;
	private List<CommentInfo> list ;
	
	private int noteId ;
	private int userId ;
	
	private Handler handler ;
	private Bundle bundle = new Bundle() ;
	private Message msg = new Message() ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_comment) ;
		
		initView() ;
		
		userId = getSharedPreferences("travel", MODE_PRIVATE).getInt("userId", 1) ;
		Intent intent = getIntent() ;
		noteId = intent.getIntExtra("noteId", 0) ;
		
		getCommentData() ;
		
		bt_comment_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getNote() ;
				finish() ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_enter2) ;
			}
		}) ;
		
		rb_comment_addcomment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				showCommentDialog() ;
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
						Toast.makeText(CommentActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
					    break ;
					case ADD_COMMENT_FAIL :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						Toast.makeText(CommentActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case ADD_COMMENT_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						getCommentData() ;
						Toast.makeText(CommentActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case NOTE_NOTEXISTS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						Toast.makeText(CommentActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case QUERY_NOTE_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						NoteInfo noteInfo = (NoteInfo) bundle.getSerializable("noteInfo") ;
						Intent intent = new Intent(CommentActivity.this, NoteDetileActivity.class) ;
						intent.putExtra("noteInfo", noteInfo) ;
						startActivity(intent) ;
						//Toast.makeText(CommentActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case COMMENT_NOTEXISTS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						commentListAdapter.addDataToList(list) ;
						ll_comment_ll.setBackgroundResource(R.drawable.common_default_kong) ;
						lv_comment_list.setVisibility(View.GONE) ;
						commentListAdapter.notifyDataSetChanged() ;
						Toast.makeText(CommentActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case QUERY_COMMENT_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						commentListAdapter.addDataToList(list) ;
						ll_comment_ll.setBackgroundResource(R.drawable.transparent) ;
						lv_comment_list.setVisibility(View.VISIBLE) ;
						commentListAdapter.notifyDataSetChanged() ;
						//Toast.makeText(MainActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					default:
						break ;
				}
			}
		} ;
	}

	public void getCommentData() {
		commentListAdapter = new CommentListAdapter(CommentActivity.this) ;
		lv_comment_list.setAdapter(commentListAdapter) ;
		
		new Thread(new Runnable() {//查询评论
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("noteId", noteId).put("action", "queryAllComment")) ;
					JSONArray resultArray = WebUtil.postRequest("NoteServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ;
							msg.what = NoteListConstant.COMMENT_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，暂时没有游记！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							list = new ArrayList<CommentInfo>() ;
							for (int i = 0; i < resultArray.length(); i++) {
								JSONObject jsonObj = resultArray.getJSONObject(i);
								CommentInfo commentInfo = new CommentInfo() ;
								commentInfo.setCommentId(jsonObj.getInt("commentId")) ;
								commentInfo.setCommentContent(jsonObj.getString("commentContent")) ;
								commentInfo.setCommentDate(jsonObj.getString("commentDate")) ;
								commentInfo.setNoteId(noteId) ;
								commentInfo.setResponseContent(jsonObj.getString("responseContent")) ;
								commentInfo.setResponseDate(jsonObj.getString("responseDate")) ;
								commentInfo.setCommentUser(jsonObj.getString("commentUser")) ;
								commentInfo.setUserTx(jsonObj.getString("userTx")) ;
					            list.add(commentInfo) ;
					        }
						}
						//noteListAdapter.addDataToList(notelist) ;
						msg = new Message() ;
						msg.what = NoteListConstant.QUERY_COMMENT_SUCCESS ;
						bundle.putString("msg" , "查询游记成功！") ;
						msg.setData(bundle) ;
						handler.sendMessage(msg) ;
					}
				} catch (JSONException e) {
					msg = new Message() ;
					msg.what = MainConstant.SHOW_AUTH_TOST_MESSAGE ;
			        bundle.putString("msg" , "网络未连接，请检查您的网络后重新修改") ;
			        msg.setData(bundle) ;
			    	handler.sendMessage(msg) ;
					e.printStackTrace();
				}
			}
		}).start() ;
	}
	
	public void showCommentDialog() {
		LayoutInflater factory = LayoutInflater.from(CommentActivity.this) ;
		View view = factory.inflate(R.layout.dialog_addcomment, null) ;
		
		final EditText et_addcomment_content = (EditText) view.findViewById(R.id.et_addcomment_content) ;
		
		dialog = new AlertDialog.Builder(CommentActivity.this) ;
		dialog.setView(view).create();
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss() ;
			}
		}) ;
		
		dialog.setPositiveButton("添加", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				addComment(et_addcomment_content.getText().toString()) ;
			}
		}) ;
		dialog.show() ;
	}
	
	public void addComment(final String commentContent) {
		new Thread(new Runnable() {//添加评论
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject()
							.put("noteId", noteId)
							.put("userId", userId)
							.put("commentContent", commentContent)
							.put("commentDate", Tools.getCurrentDateWithAllTime())
							.put("action", "addComment")) ;
					JSONArray resultArray = WebUtil.postRequest("NoteServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ;
							msg.what = NoteListConstant.ADD_COMMENT_FAIL ;
							bundle.putString("msg" , "很抱歉，添加评论失败！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							msg = new Message() ;
							msg.what = NoteListConstant.ADD_COMMENT_SUCCESS ;
							bundle.putString("msg" , "添加成功！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						}
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
	
	public void getNote() {
		new Thread(new Runnable() {//查询游记
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("noteId", noteId).put("action", "queryNote")) ;
					JSONArray resultArray = WebUtil.postRequest("NoteServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ;
							msg.what = MainConstant.NOTE_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，查询游记失败！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							JSONObject jsonObj = resultArray.getJSONObject(0);
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
							
							msg = new Message() ;
							msg.what = NoteListConstant.QUERY_NOTE_SUCCESS ;
							bundle.putString("msg" , "查询成功！") ;
							bundle.putSerializable("noteInfo", noteInfo) ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						}
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
		bt_comment_back = (Button) findViewById(R.id.bt_comment_back) ;
		rb_comment_addcomment = (RadioButton) findViewById(R.id.rb_comment_addcomment) ;
		lv_comment_list = (ListView) findViewById(R.id.lv_comment_list) ;
		ll_comment_ll = (LinearLayout) findViewById(R.id.ll_comment_ll) ;
	}
	
}
