package com.cn.ncist.rq.activity;

import com.cn.ncist.rq.entity.DestinationInfo;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class StrategyCatalogActivity extends Activity implements OnClickListener {

	private Button bt_strategycatalog_back ;
	private Button bt_strategycatalog_search ;
	private Button bt_strategycatalog_share ;
	private TextView tv_strategycatalog_descri ;
	private LinearLayout ll_strategycatalog_ll ;
	private RadioButton rb_strategycatalog_download ;
	
	DestinationInfo destinationInfo ;
	
	private AlertDialog.Builder dialog ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_strategycatalog) ;
		
		Intent intent = getIntent() ;
		destinationInfo = (DestinationInfo) intent.getSerializableExtra("destinationInfo") ;
		
		initView() ;
		
		tv_strategycatalog_descri.setText(destinationInfo.getDestinationName()) ;
		
		rb_strategycatalog_download.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(StrategyCatalogActivity.this, "下载完成！", Toast.LENGTH_SHORT).show() ;
			}
		}) ;
	}
	
	public void showShareDialog() {
		LayoutInflater factory = LayoutInflater.from(StrategyCatalogActivity.this) ;
		View view = factory.inflate(R.layout.dialog_share, null) ;
		
		dialog = new AlertDialog.Builder(StrategyCatalogActivity.this) ;
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
		case R.id.bt_strategycatalog_back:
			finish() ;
			overridePendingTransition(R.anim.anim_entry, R.anim.anim_enter2) ;
			break;
		case R.id.bt_strategycatalog_search:
			
			break;
		case R.id.bt_strategycatalog_share:
			showShareDialog() ;
			break;
		case R.id.ll_strategycatalog_ll :
			Intent intent = new Intent(StrategyCatalogActivity.this, DestinationDescriptionActivity.class) ;
			intent.putExtra("destinationInfo", destinationInfo) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry, R.anim.anim_out) ;
			break ;
		default:
			break;
		}
	}
	
	public void initView() { 
		bt_strategycatalog_back = (Button) findViewById(R.id.bt_strategycatalog_back) ;
		bt_strategycatalog_search = (Button) findViewById(R.id.bt_strategycatalog_search) ;
		bt_strategycatalog_share = (Button) findViewById(R.id.bt_strategycatalog_share) ;
		tv_strategycatalog_descri = (TextView) findViewById(R.id.tv_strategycatalog_descri) ;
		ll_strategycatalog_ll = (LinearLayout) findViewById(R.id.ll_strategycatalog_ll) ;
		rb_strategycatalog_download = (RadioButton) findViewById(R.id.rb_strategycatalog_download) ;
		
		bt_strategycatalog_back.setOnClickListener(this) ;
		bt_strategycatalog_search.setOnClickListener(this) ;
		bt_strategycatalog_share.setOnClickListener(this) ;
		ll_strategycatalog_ll.setOnClickListener(this) ;
		
	}

}
