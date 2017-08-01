package com.cn.ncist.rq.activity;

import static com.cn.ncist.rq.constant.MainConstant.DESTINATION_NOTEXISTS;
import static com.cn.ncist.rq.constant.MainConstant.NATION_NOTEXISTS;
import static com.cn.ncist.rq.constant.MainConstant.NOTE_NOTEXISTS;
import static com.cn.ncist.rq.constant.MainConstant.PROVINCE_NOTEXISTS;
import static com.cn.ncist.rq.constant.MainConstant.QUERY_DESTINATION_SUCCESS;
import static com.cn.ncist.rq.constant.MainConstant.QUERY_NATION_SUCCESS;
import static com.cn.ncist.rq.constant.MainConstant.QUERY_NOTE_SUCCESS;
import static com.cn.ncist.rq.constant.MainConstant.QUERY_PROVINCE_SUCCESS;
import static com.cn.ncist.rq.constant.MainConstant.QUERY_QUESTION_SUCCESS;
import static com.cn.ncist.rq.constant.MainConstant.QUERY_TTNOTE_SUCCESS;
import static com.cn.ncist.rq.constant.MainConstant.QUESTION_NOTEXISTS;
import static com.cn.ncist.rq.constant.MainConstant.SHOW_AUTH_TOST_MESSAGE;
import static com.cn.ncist.rq.constant.MainConstant.TTNOTE_NOTEXISTS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.ncist.rq.adapter.QuestionAboutAdapter;
import com.cn.ncist.rq.adapter.QuestionAdapter;
import com.cn.ncist.rq.constant.MainConstant;
import com.cn.ncist.rq.entity.NoteInfo;
import com.cn.ncist.rq.entity.QuestionInfo;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.util.Tools;
import com.cn.ncist.rq.util.WebUtil;

public class QuestionDetileActivity extends Activity implements OnCheckedChangeListener {

	private TextView tv_questiondetile_content ;
	private TextView tv_questiondetile_answer ;
	private Button bt_questiondetile_back ;
	private RadioButton rb_questiondetile_yy ;
	private RadioButton rb_questiondetile_wy ;
	
	private ListView lv_questiondetile_lv ; 
	private LinearLayout lv_questiondetile_list ;
	private QuestionAboutAdapter adapter ;
	private List<QuestionInfo> list ;
	private QuestionInfo info ;
	private String questionLabel ;
	private int questionId ;
	
	private Drawable yy ;
	private Drawable yyleft ;
	private Drawable wy ;
	private Drawable wyleft ;
	
	private Handler handler ;
	private Bundle bundle = new Bundle() ;
	private Message msg = new Message() ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questiondetile);
		
		initView() ;
		initData() ;
		
		tv_questiondetile_content.setText(info.getQuestionContent()) ;
		tv_questiondetile_answer.setText(info.getQuestionAnswer()) ;
		questionLabel = info.getQuestionLabel() ;
		questionId = info.getQuestionId() ;
		
		bt_questiondetile_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish() ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_enter2) ;
			}
		}) ;
		
		lv_questiondetile_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				QuestionInfo questionInfo = adapter.getItem(arg2) ;
				Intent intent = new Intent(QuestionDetileActivity.this, QuestionDetileActivity.class) ;
				intent.putExtra("questionInfo", questionInfo) ;	
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
						Toast.makeText(QuestionDetileActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
					    break ;
					case QUESTION_NOTEXISTS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						lv_questiondetile_list.setBackgroundResource(R.drawable.common_default_kong) ;
						lv_questiondetile_lv.setVisibility(View.GONE) ;
						Toast.makeText(QuestionDetileActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case QUERY_QUESTION_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						adapter.clear() ;
						adapter.addDataToList(list) ;
						lv_questiondetile_list.setBackgroundResource(R.drawable.transparent) ;
						lv_questiondetile_lv.setVisibility(View.VISIBLE) ;
						adapter.notifyDataSetChanged() ;
						//Toast.makeText(QuestionDetileActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					default:
						break ;
				}
			}
		} ;
	}
	
	public void getQuestionData(final String questionLabel) {
		adapter = new QuestionAboutAdapter(QuestionDetileActivity.this) ;
		lv_questiondetile_lv.setAdapter(adapter) ;
		
		new Thread(new Runnable() {//查询相关问题
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("questionLabel", questionLabel).put("action", "queryQuestion")) ;
					JSONArray resultArray = WebUtil.postRequest("QuestionServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ;
							msg.what = MainConstant.QUESTION_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，暂时没有相关问题！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							list = new ArrayList<QuestionInfo>() ;
							for (int i = 0; i < resultArray.length(); i++) {
					            JSONObject jsonObj = resultArray.getJSONObject(i);
					            QuestionInfo questionInfo = new QuestionInfo() ;
					            int question_id = jsonObj.getInt("questionId") ;
					            questionInfo.setQuestionId(question_id) ;
					            questionInfo.setQuestionContent(jsonObj.getString("questionContent")) ;
					            questionInfo.setQuestionAnswer(jsonObj.getString("questionAnswer")) ;
					            questionInfo.setQuestionLabel(questionLabel) ;
					            if (questionId == question_id) {
									continue ;
								} else {
									list.add(questionInfo) ;
								}
					        }
						}
						
						msg = new Message() ;
						msg.what = MainConstant.QUERY_QUESTION_SUCCESS ;
						bundle.putString("msg" , "查询相关问题成功！") ;
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
	
	public void initData() {
		Intent intent = getIntent() ;
		info = (QuestionInfo) intent.getSerializableExtra("questionInfo") ;
		
		/*info.setQuestionId(2) ;
		info.setQuestionContent("nishi") ;
		info.setQuestionAnswer("renqian") ;
		info.setQuestionLabel(questionLabel) ;*/
		questionLabel = info.getQuestionLabel() ;
		questionId = info.getQuestionId() ;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		initData() ;
		//shuaxin(questionLabel) ;
		getQuestionData(questionLabel) ;
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.rb_questiondetile_yy :
			if (isChecked) {
				rb_questiondetile_yy.setCompoundDrawablesWithIntrinsicBounds(yyleft, null, null, null) ;
				rb_questiondetile_wy.setCompoundDrawablesWithIntrinsicBounds(wy, null, null, null) ;
			}
			break ;
		case R.id.rb_questiondetile_wy :
			if (isChecked) {
				rb_questiondetile_yy.setCompoundDrawablesWithIntrinsicBounds(yy, null, null, null) ;
				rb_questiondetile_wy.setCompoundDrawablesWithIntrinsicBounds(wyleft, null, null, null) ;
			}
			break ;
		default:
			break;
		}
	}
	
	public void initView() {
		bt_questiondetile_back = (Button) findViewById(R.id.bt_questiondetile_back) ;
		tv_questiondetile_content = (TextView) findViewById(R.id.tv_questiondetile_content) ;
		tv_questiondetile_answer = (TextView) findViewById(R.id.tv_questiondetile_answer) ;
		rb_questiondetile_yy = (RadioButton) findViewById(R.id.rb_questiondetile_yy) ;
		rb_questiondetile_wy = (RadioButton) findViewById(R.id.rb_questiondetile_wy) ;
		lv_questiondetile_lv = (ListView) findViewById(R.id.lv_questiondetile_lv) ;
		lv_questiondetile_list = (LinearLayout) findViewById(R.id.lv_questiondetile_list) ;
		
		yy = getResources().getDrawable(R.drawable.common_zan1) ;
		yyleft = getResources().getDrawable(R.drawable.common_zan_good1) ;
		wy = getResources().getDrawable(R.drawable.common_nozan) ;
		wyleft = getResources().getDrawable(R.drawable.common_nozan_good) ;
		
		rb_questiondetile_yy.setOnCheckedChangeListener(this) ;
		rb_questiondetile_wy.setOnCheckedChangeListener(this) ;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
			overridePendingTransition(R.anim.anim_entry, R.anim.anim_enter2);
		}
		return false;
	}

}
