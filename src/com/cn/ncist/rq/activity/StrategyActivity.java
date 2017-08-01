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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cn.ncist.rq.adapter.DestinationAdapter;
import com.cn.ncist.rq.adapter.DestinationStrategyAdapter;
import com.cn.ncist.rq.adapter.NationGridViewAdapter;
import com.cn.ncist.rq.constant.MainConstant;
import com.cn.ncist.rq.entity.DestinationInfo;
import com.cn.ncist.rq.entity.NationInfo;
import com.cn.ncist.rq.entity.NoteInfo;
import com.cn.ncist.rq.entity.ProvinceInfo;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.util.Tools;
import com.cn.ncist.rq.util.WebUtil;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class StrategyActivity extends Activity implements OnClickListener {

	private Button bt_strategy_back ;
	private Button bt_strategy_search ;
	private GridView gv_strategy_nation ;
	private ListView lv_strategy_des ;
	private LinearLayout ll_strategy_ll ;
	
	List<NationInfo> nationlist = new ArrayList<NationInfo>() ;
	private NationGridViewAdapter nationGridViewAdapter ;
	//List<ProvinceInfo> provincelist = new ArrayList<ProvinceInfo>() ;
	private DestinationStrategyAdapter destinationAdapter ;
	List<DestinationInfo> destinationlist = new ArrayList<DestinationInfo>() ;
	private String firstNationName ;
	
	private Handler handler ;
	private Bundle bundle = new Bundle() ;
	private Message msg = new Message() ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_strategy) ;
		
		initView() ;
		
		gv_strategy_nation.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				nationGridViewAdapter.setSelection(arg2) ;
				getDestinationData(nationGridViewAdapter.getItem(arg2).getNationName()) ;
			}
		}) ;
		
		lv_strategy_des.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(StrategyActivity.this, StrategyCatalogActivity.class) ;
				intent.putExtra("destinationInfo", destinationAdapter.getItem(arg2)) ;
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
						Toast.makeText(StrategyActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
					    break ;
					case NATION_NOTEXISTS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						gv_strategy_nation.setVisibility(View.GONE) ;
						Toast.makeText(StrategyActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case QUERY_NATION_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						firstNationName = bundle.getString("firstNationName") ;
						nationGridViewAdapter.addDataToList(nationlist) ;
						gv_strategy_nation.setVisibility(View.VISIBLE) ;
						nationGridViewAdapter.notifyDataSetChanged() ;
						//Toast.makeText(StrategyActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case DESTINATION_NOTEXISTS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						destinationAdapter.addDataToList(destinationlist) ;
						ll_strategy_ll.setBackgroundResource(R.drawable.common_default_no_data) ;
						lv_strategy_des.setVisibility(View.GONE) ;
						destinationAdapter.notifyDataSetChanged() ;
						Toast.makeText(StrategyActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case QUERY_DESTINATION_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						destinationAdapter.addDataToList(destinationlist) ;
						ll_strategy_ll.setBackgroundResource(R.drawable.transparent) ;
						lv_strategy_des.setVisibility(View.VISIBLE) ;
						destinationAdapter.notifyDataSetChanged() ;
						//Toast.makeText(StrategyActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					default:
						break ;
				}
			}
		} ;
	}
	
	public void getNationData() {
		nationGridViewAdapter = new NationGridViewAdapter(StrategyActivity.this) ;
		gv_strategy_nation.setAdapter(nationGridViewAdapter) ;
		
		new Thread(new Runnable() {//查询国家
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("action", "queryAllNation")) ;
					JSONArray resultArray = WebUtil.postRequest("DestinationServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ;
							msg.what = MainConstant.NATION_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，暂时没有国家！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							nationlist = new ArrayList<NationInfo>() ;
							firstNationName = resultArray.getJSONObject(0).getString("nationName") ;
							for (int i = 0; i < resultArray.length(); i++) {
								JSONObject jsonObj = resultArray.getJSONObject(i);
								NationInfo nationInfo = new NationInfo() ;
								nationInfo.setNationId(jsonObj.getInt("nationId")) ;
								nationInfo.setNationName(jsonObj.getString("nationName")) ;
					            nationlist.add(nationInfo) ;
					        }
						}
						msg = new Message() ;
						msg.what = MainConstant.QUERY_NATION_SUCCESS ;
						bundle.putString("msg" , "查询国家成功！") ;
						bundle.putString("firstNationName" , resultArray.getJSONObject(0).getString("nationName")) ;
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
		//return firstNationName ;
	}
	
	
	public void getDestinationData(final String nationName) {
		destinationAdapter = new DestinationStrategyAdapter(StrategyActivity.this) ;
		lv_strategy_des.setAdapter(destinationAdapter) ;
		
		new Thread(new Runnable() {//查询景点
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("nationName", nationName==null?firstNationName:nationName).put("action", "queryDestination")) ;
					JSONArray resultArray = WebUtil.postRequest("DestinationServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ;
							msg.what = MainConstant.DESTINATION_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，该省份暂时没有景点！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ; 
						} else { 
							destinationlist = new ArrayList<DestinationInfo>() ;
							for (int i = 0; i < resultArray.length(); i++) {
								JSONObject jsonObj = resultArray.getJSONObject(i);
								DestinationInfo destinationInfo = new DestinationInfo() ;
								destinationInfo.setDestinationId(jsonObj.getInt("destinationId")) ;
								destinationInfo.setDestinationName(jsonObj.getString("destinationName")) ;
								destinationInfo.setDestinationPinyin(jsonObj.getString("destinationPinyin")) ;
								destinationInfo.setDestinationNumber(jsonObj.getInt("destinationNumber")) ;
								destinationInfo.setDestinationImg(jsonObj.getString("destinationImg")) ;
								destinationInfo.setDestinationImgarr(jsonObj.getString("destinationImgarr")) ;
								destinationInfo.setDestinationDesc(jsonObj.getString("destinationDesc")) ;
								destinationInfo.setProvinceName(jsonObj.getString("provinceName")) ;
								
								destinationlist.add(destinationInfo) ;
					        }
						}
						msg = new Message() ;
						msg.what = MainConstant.QUERY_DESTINATION_SUCCESS ;
						bundle.putString("msg" , "查询景点成功！") ;
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
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_strategy_back:
			finish() ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_enter2) ;
			break ;
		case R.id.bt_strategy_search:
			break ;
		default:
			break ;
		}
		
	}
	
	public void initView() {
		bt_strategy_back = (Button) findViewById(R.id.bt_strategy_back) ;
		bt_strategy_search = (Button) findViewById(R.id.bt_strategy_search) ;
		gv_strategy_nation = (GridView) findViewById(R.id.gv_strategy_nation) ;
		lv_strategy_des = (ListView) findViewById(R.id.lv_strategy_des) ;
		ll_strategy_ll = (LinearLayout) findViewById(R.id.ll_strategy_ll) ;
		
		bt_strategy_back.setOnClickListener(this) ;
		bt_strategy_search.setOnClickListener(this) ;
	}
	
	public void onResume() {
		super.onResume();
		getNationData() ;
		getDestinationData(null) ;
	}

}
