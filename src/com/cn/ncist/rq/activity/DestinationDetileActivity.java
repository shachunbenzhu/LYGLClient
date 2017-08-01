package com.cn.ncist.rq.activity;

import static com.cn.ncist.rq.constant.MainConstant.DESTINATION_NOTEXISTS;
import static com.cn.ncist.rq.constant.MainConstant.QUERY_DESTINATION_SUCCESS;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cn.ncist.rq.adapter.DestinationAdapter;
import com.cn.ncist.rq.adapter.DestinationNextAdapter;
import com.cn.ncist.rq.constant.MainConstant;
import com.cn.ncist.rq.entity.DestinationInfo;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.module.DestinationNextGridView;
import com.cn.ncist.rq.util.Tools;
import com.cn.ncist.rq.util.WebUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DestinationDetileActivity extends Activity implements OnClickListener,OnCheckedChangeListener {

	private LinearLayout ll_destinationdetile_ll ;
	private Button bt_destinationdetile_back ;
	private Button bt_destinationdetile_share ;
	private TextView tv_destinationdetile_name ;
	private TextView tv_destinationdetile_pinyin ;
	private TextView tv_destinationdetile_number ;
	private Button bt_destinationdetile_cyx ;
	private Button bt_destinationdetile_complete ;
	
	private RadioButton rb_destinationdetile_jd ;
	private RadioButton rb_destinationdetile_ms ;
	private RadioButton rb_destinationdetile_gw ;
	private RadioButton rb_destinationdetile_wl ;
	private RadioButton rb_destinationdetile_jp ;
	private RadioButton rb_destinationdetile_zs ;
	private RadioButton rb_destinationdetile_dj ;
	private RadioButton rb_destinationdetile_hcp ;
	
	private LinearLayout ll_destinationdetile_llgv ;
	private GridView gv_destinationdetile_gv ;
	private DestinationNextAdapter destinationNextAdapter ;
	
	private List<DestinationInfo> destinationlist = new ArrayList<DestinationInfo>() ;
	DestinationInfo destinationInfo = new DestinationInfo() ;
	private Handler handler ;
	private Bundle bundle = new Bundle() ;
	private Message msg = new Message() ;
	
	private AlertDialog.Builder dialog ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_destinationdetile) ;
		
		Intent intent = getIntent() ;
		destinationInfo = (DestinationInfo) intent.getSerializableExtra("destinationInfo") ;
		/*destinationInfo.setDestinationId(1) ;
		destinationInfo.setDestinationName("太原") ;
		destinationInfo.setDestinationPinyin("taiyuan") ;
		destinationInfo.setDestinationNumber(125) ;
		destinationInfo.setNationName("中国") ;
		destinationInfo.setProvinceName("山西省") ;
		destinationInfo.setDestinationImg("./images/common_default_bg.png") ;
		destinationInfo.setDestinationImgarr("./images/common_default_bg.png;./images/common_default_notebg.png;") ;
		destinationInfo.setDestinationDesc("山西省隶属于中国") ;*/
		
		initView() ;
		
		ll_destinationdetile_ll.setBackgroundResource(Tools.getImageResId(Tools.formatString(destinationInfo.getDestinationImg()))) ;
		tv_destinationdetile_name.setText(destinationInfo.getDestinationName()) ;
		tv_destinationdetile_pinyin.setText(destinationInfo.getDestinationPinyin()) ;
		tv_destinationdetile_number.setText(destinationInfo.getDestinationNumber() + "") ;
		
		getDestinationNextData(destinationInfo.getProvinceName() , destinationInfo.getDestinationId()) ;
		
		gv_destinationdetile_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(DestinationDetileActivity.this, DestinationDetileActivity.class) ;
				intent.putExtra("destinationInfo", destinationlist.get(arg2)) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			}
		}) ;
		
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case DESTINATION_NOTEXISTS :
					bundle = msg.getData();
					String showmessage = bundle.getString("msg") ;
					destinationNextAdapter.addDataToList(destinationlist) ;
					ll_destinationdetile_llgv.setBackgroundResource(R.drawable.common_default_kong) ;
					gv_destinationdetile_gv.setVisibility(View.GONE) ;
					destinationNextAdapter.notifyDataSetChanged() ;
					Toast.makeText(DestinationDetileActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
					break ;
				case QUERY_DESTINATION_SUCCESS :
					bundle = msg.getData();
					showmessage = bundle.getString("msg") ;
					destinationNextAdapter.addDataToList(destinationlist) ;
					ll_destinationdetile_llgv.setBackgroundResource(R.drawable.transparent) ;
					gv_destinationdetile_gv.setVisibility(View.VISIBLE) ;
					destinationNextAdapter.notifyDataSetChanged() ;
					//Toast.makeText(DestinationDetileActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
					break ;
				default:
					break;
				}
			}
		} ;
	}
	
	public void showShareDialog() {
		LayoutInflater factory = LayoutInflater.from(DestinationDetileActivity.this) ;
		View view = factory.inflate(R.layout.dialog_share, null) ;
		
		dialog = new AlertDialog.Builder(DestinationDetileActivity.this) ;
		dialog.setView(view).create();
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss() ;
			}
		}) ;
		dialog.show() ;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_destinationdetile_back:
			finish() ;
			overridePendingTransition(R.anim.anim_entry, R.anim.anim_enter2) ;
			break;
		case R.id.bt_destinationdetile_share:
			showShareDialog() ;
			break;
		case R.id.bt_destinationdetile_cyx :
			bt_destinationdetile_cyx.setBackgroundResource(R.drawable.blueset) ;
			Intent intent = new Intent(DestinationDetileActivity.this, DestinationDescriptionActivity.class) ;
			intent.putExtra("destinationInfo", destinationInfo) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry, R.anim.anim_out) ;
			break ;
		case R.id.bt_destinationdetile_complete :
			intent = new Intent(DestinationDetileActivity.this, StrategyCatalogActivity.class) ;
			intent.putExtra("destinationInfo", destinationInfo) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry, R.anim.anim_out) ;
			break ;
		default:
			break;
		}
	}
	

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.rb_destinationdetile_jd:
			if (isChecked) {
				Uri uri = Uri.parse("http://piao.ctrip.com/") ;
				Intent intent = new Intent(Intent.ACTION_VIEW, uri) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			}
			break;
		case R.id.rb_destinationdetile_ms:
			if (isChecked) {
				Uri uri = Uri.parse("https://waimai.baidu.com/waimai?qt=find") ;
				Intent intent = new Intent(Intent.ACTION_VIEW, uri) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			}
			break;
		case R.id.rb_destinationdetile_gw:
			if (isChecked) {
				//http://hotel.qunar.com/
				Uri uri = Uri.parse("https://www.jd.com/") ;
				Intent intent = new Intent(Intent.ACTION_VIEW, uri) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			}
			break;
		case R.id.rb_destinationdetile_wl:
			if (isChecked) {
				Uri uri = Uri.parse("http://huodong.ctrip.com/#ctm_ref=ctr_hp_ttd") ;
				Intent intent = new Intent(Intent.ACTION_VIEW, uri) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			}
			break;
		case R.id.rb_destinationdetile_jp:
			if (isChecked) {
				//http://hotel.qunar.com/
				Uri uri = Uri.parse("https://flight.qunar.com/") ;
				Intent intent = new Intent(Intent.ACTION_VIEW, uri) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			}
			break;
		case R.id.rb_destinationdetile_zs:
			if (isChecked) {
				//http://hotel.qunar.com/
				Uri uri = Uri.parse("http://inn.ctrip.com/") ;
				Intent intent = new Intent(Intent.ACTION_VIEW, uri) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			}
			break;
		case R.id.rb_destinationdetile_dj:
			if (isChecked) {
				//http://hotel.qunar.com/
				Uri uri = Uri.parse("http://vacations.ctrip.com/dingzhi") ;
				Intent intent = new Intent(Intent.ACTION_VIEW, uri) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			}
			break;
		case R.id.rb_destinationdetile_hcp :
			if (isChecked) {
				Uri uri = Uri.parse("http://trains.ctrip.com/TrainBooking/SearchTrain.aspx###") ;
				Intent intent = new Intent(Intent.ACTION_VIEW, uri) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			}
			break ;
		default:
			break ;
		}
	}
	
	public void getDestinationNextData(final String provinceName , final int destinationId) {
		destinationNextAdapter = new DestinationNextAdapter(DestinationDetileActivity.this) ;
		gv_destinationdetile_gv.setAdapter(destinationNextAdapter) ;
		
		new Thread(new Runnable() {//查询景点
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("provinceName", provinceName).put("action", "queryAllDestination")) ;
					JSONArray resultArray = WebUtil.postRequest("DestinationServlet", params, handler) ;
					
					if (resultArray != null) {
							destinationlist = new ArrayList<DestinationInfo>() ;
							int i ;
							for (i = 0; i < resultArray.length(); i++) {
								JSONObject jsonObj = resultArray.getJSONObject(i);
								if (destinationId == jsonObj.getInt("destinationId")) {
									continue ;
								}
								DestinationInfo destinationInfo = new DestinationInfo() ;
								destinationInfo.setDestinationId(jsonObj.getInt("destinationId")) ;
								destinationInfo.setDestinationName(jsonObj.getString("destinationName")) ;
								destinationInfo.setDestinationPinyin(jsonObj.getString("destinationPinyin")) ;
								destinationInfo.setDestinationNumber(jsonObj.getInt("destinationNumber")) ;
								destinationInfo.setDestinationImg(jsonObj.getString("destinationImg")) ;
								destinationInfo.setProvinceName(jsonObj.getString("provinceName")) ;
								destinationlist.add(destinationInfo) ;
								if (i == 5) {
									break ;
								}
					        }
							if (i == 1) {
								msg = new Message() ;
								msg.what = MainConstant.DESTINATION_NOTEXISTS ;
								bundle.putString("msg" , "很抱歉，该省份暂时没有其他景点！") ;
								msg.setData(bundle) ;
								handler.sendMessage(msg) ;
							} else {
								msg = new Message() ;
								msg.what = MainConstant.QUERY_DESTINATION_SUCCESS ;
								bundle.putString("msg" , "查询景点成功！") ;
								msg.setData(bundle) ;
								handler.sendMessage(msg) ;
							}
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
	
	/*public void onResume() {
		super.onResume();
		
	}*/

	public void initView() { 
		ll_destinationdetile_ll = (LinearLayout) findViewById(R.id.ll_destinationdetile_ll) ;
		bt_destinationdetile_back = (Button) findViewById(R.id.bt_destinationdetile_back) ;
		bt_destinationdetile_share = (Button) findViewById(R.id.bt_destinationdetile_share) ;
		tv_destinationdetile_name = (TextView) findViewById(R.id.tv_destinationdetile_name) ;
		tv_destinationdetile_pinyin = (TextView) findViewById(R.id.tv_destinationdetile_pinyin) ;
		tv_destinationdetile_number = (TextView) findViewById(R.id.tv_destinationdetile_number) ;
		bt_destinationdetile_cyx = (Button) findViewById(R.id.bt_destinationdetile_cyx) ;
		bt_destinationdetile_complete = (Button) findViewById(R.id.bt_destinationdetile_complete) ;
		
		gv_destinationdetile_gv = (GridView) findViewById(R.id.gv_destinationdetile_gv) ;
		ll_destinationdetile_llgv = (LinearLayout) findViewById(R.id.ll_destinationdetile_llgv) ;
		
		rb_destinationdetile_jd = (RadioButton) findViewById(R.id.rb_destinationdetile_jd) ;
		rb_destinationdetile_ms = (RadioButton) findViewById(R.id.rb_destinationdetile_ms) ;
		rb_destinationdetile_gw = (RadioButton) findViewById(R.id.rb_destinationdetile_gw) ;
		rb_destinationdetile_wl = (RadioButton) findViewById(R.id.rb_destinationdetile_wl) ;
		rb_destinationdetile_jp = (RadioButton) findViewById(R.id.rb_destinationdetile_jp) ;
		rb_destinationdetile_zs = (RadioButton) findViewById(R.id.rb_destinationdetile_zs) ;
		rb_destinationdetile_dj = (RadioButton) findViewById(R.id.rb_destinationdetile_dj) ;
		rb_destinationdetile_hcp = (RadioButton) findViewById(R.id.rb_destinationdetile_hcp) ;
		
		
		bt_destinationdetile_back.setOnClickListener(this) ;
		bt_destinationdetile_share.setOnClickListener(this) ;
		bt_destinationdetile_cyx.setOnClickListener(this) ;
		bt_destinationdetile_complete.setOnClickListener(this) ;
	}

}
