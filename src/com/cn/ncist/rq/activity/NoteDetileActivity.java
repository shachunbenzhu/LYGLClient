package com.cn.ncist.rq.activity;

import static com.cn.ncist.rq.constant.NoteListConstant.UPDATE_NOTE_FAIL;
import static com.cn.ncist.rq.constant.NoteListConstant.UPDATE_NOTE_SUCCESS;
import static com.cn.ncist.rq.constant.NoteListConstant.SHOW_AUTH_TOST_MESSAGE;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cn.ncist.rq.adapter.NoteImgListAdapter;
import com.cn.ncist.rq.constant.MainConstant;
import com.cn.ncist.rq.constant.NoteListConstant;
import com.cn.ncist.rq.entity.NationInfo;
import com.cn.ncist.rq.entity.NoteInfo;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.util.Tools;
import com.cn.ncist.rq.util.WebUtil;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.transition.Visibility;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class NoteDetileActivity extends Activity implements OnClickListener,OnCheckedChangeListener {

	private Button bt_notedetile_back ;
	private Button bt_notedetile_collection ;
	private Button bt_notedetile_more ;
	private TextView tv_notedetile_title ;
	private TextView tv_notedetile_date ;
	private TextView tv_notedetile_name ;
	private TextView tv_notedetile_seenum ;
	private TextView tv_notedetile_days ;
	private TextView tv_notedetile_expend ;
	private TextView tv_notedetile_partner ;
	private TextView tv_notedetile_content ;
	private ImageView iv_notedetile_bg ;
	private ListView lv_notedetile_img ;
	private RadioButton rb_notedetile_zan ;
	private RadioButton rb_notedetile_comment ;
	
	private NoteImgListAdapter noteImgListAdapter = new NoteImgListAdapter(NoteDetileActivity.this) ;
	
	NoteInfo noteInfo ;
	private int userId ;
	private int noteId ;
	private List<String> noteImgs = new ArrayList<String>() ;
	
	private Handler handler ;
	private Bundle bundle = new Bundle() ;
	private Message msg = new Message() ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_notedetile) ;
	
		
		initView() ;
		
		userId = getSharedPreferences("travel", MODE_PRIVATE).getInt("userId", 1) ;
		Intent intent = getIntent() ;
		
		if (intent.getBooleanExtra("noPic", false)) {
			iv_notedetile_bg.setVisibility(View.GONE) ;
			lv_notedetile_img.setVisibility(View.GONE) ;
		} else {
			iv_notedetile_bg.setVisibility(View.VISIBLE) ;
			lv_notedetile_img.setVisibility(View.VISIBLE) ;
		}
		
		noteInfo = (NoteInfo) intent.getSerializableExtra("noteInfo") ;
		noteId = noteInfo.getNote_id() ;
		tv_notedetile_title.setText(noteInfo.getNote_title()) ;
		tv_notedetile_date.setText(noteInfo.getNote_date()) ;
		tv_notedetile_name.setText(noteInfo.getUser_name()) ;
		tv_notedetile_seenum.setText(noteInfo.getNote_seenumber().toString()) ;
		tv_notedetile_days.setText(noteInfo.getNote_days().toString()) ;
		tv_notedetile_expend.setText(noteInfo.getNote_expend()) ;
		tv_notedetile_partner.setText(noteInfo.getNote_partner()) ;
		tv_notedetile_content.setText(noteInfo.getNote_content()) ;
		iv_notedetile_bg.setBackgroundResource(Tools.getImageResId(Tools.formatString(noteInfo.getNote_bgimg()))) ;
		rb_notedetile_zan.setText("赞  " + noteInfo.getNote_zannumber()) ;
		rb_notedetile_comment.setText("评论  " + noteInfo.getNote_commentnumber()) ;
		
		String img[] = noteInfo.getNote_img().split(";") ;
		for(int i = 0; i < img.length; i++){                      //遍历字符串数组	
			noteImgs.add(img[i]) ;
		}
		
		lv_notedetile_img.setAdapter(noteImgListAdapter) ;
		noteImgListAdapter.addDataToList(noteImgs) ;
		noteImgListAdapter.notifyDataSetChanged() ;
		
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
					case SHOW_AUTH_TOST_MESSAGE :
						bundle = msg.getData();
						String showmessage = bundle.getString("msg") ;
						Toast.makeText(NoteDetileActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
					    break ;
					case UPDATE_NOTE_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						rb_notedetile_zan.setText("赞  " + bundle.getInt("noteZan")) ;
						Toast.makeText(NoteDetileActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
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
		case R.id.bt_notedetile_back:
			finish() ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_enter2) ;
			break;
		case R.id.bt_notedetile_collection :
			if (userId == 1) {
				Toast.makeText(NoteDetileActivity.this, "请先登录！", Toast.LENGTH_SHORT).show() ;
			} else {
				bt_notedetile_collection.setBackgroundResource(R.drawable.note_collected) ;
			}			
			break ;
		case R.id.bt_notedetile_more :
			Intent intent = new Intent(NoteDetileActivity.this, NoteDetileTopRightDialog.class);
			startActivity(intent);
			break ;
		default:
			break;
		}
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.rb_notedetile_zan:
			updateZan() ;
			break;
		case R.id.rb_notedetile_comment :
			Intent intent = new Intent(NoteDetileActivity.this, CommentActivity.class);
			intent.putExtra("noteId", noteId) ;
			startActivity(intent);		
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			break ;
		default:
			break;
		}
	}
	
	public void updateZan() {
		new Thread(new Runnable() {//更新点赞数量，加1
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("noteId", noteId).put("action", "updateNationZan")) ;
					JSONArray resultArray = WebUtil.postRequest("NoteServlet", params, handler) ;
					
					if (resultArray != null) {
						int noteZan = resultArray.getJSONObject(0).getInt("noteZan") ;
						
						msg = new Message() ;
						msg.what = NoteListConstant.UPDATE_NOTE_SUCCESS ;
						bundle.putString("msg" , "点赞成功！") ;
						bundle.putInt("noteZan", noteZan) ;
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
		bt_notedetile_back = (Button) findViewById(R.id.bt_notedetile_back) ;
		bt_notedetile_collection = (Button) findViewById(R.id.bt_notedetile_collection) ;
		bt_notedetile_more = (Button) findViewById(R.id.bt_notedetile_more) ;
		
		tv_notedetile_title = (TextView) findViewById(R.id.tv_notedetile_title) ;
		tv_notedetile_date = (TextView) findViewById(R.id.tv_notedetile_date) ;
		tv_notedetile_name = (TextView) findViewById(R.id.tv_notedetile_name) ;
		tv_notedetile_seenum = (TextView) findViewById(R.id.tv_notedetile_seenum) ;
		tv_notedetile_days = (TextView) findViewById(R.id.tv_notedetile_days) ;
		tv_notedetile_expend = (TextView) findViewById(R.id.tv_notedetile_expend) ;
		tv_notedetile_partner = (TextView) findViewById(R.id.tv_notedetile_partner) ;
		tv_notedetile_content = (TextView) findViewById(R.id.tv_notedetile_content) ;
		
		iv_notedetile_bg = (ImageView) findViewById(R.id.iv_notedetile_bg) ;
		lv_notedetile_img = (ListView) findViewById(R.id.lv_notedetile_img) ;
		rb_notedetile_zan = (RadioButton) findViewById(R.id.rb_notedetile_zan) ;
		rb_notedetile_comment = (RadioButton) findViewById(R.id.rb_notedetile_comment) ;
		
		bt_notedetile_back.setOnClickListener(this) ;
		bt_notedetile_collection.setOnClickListener(this) ;
		bt_notedetile_more.setOnClickListener(this) ;
		rb_notedetile_zan.setOnCheckedChangeListener(this) ;
		rb_notedetile_comment.setOnCheckedChangeListener(this) ;
	}

}
