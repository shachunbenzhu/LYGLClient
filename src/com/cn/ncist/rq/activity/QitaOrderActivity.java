package com.cn.ncist.rq.activity;

import com.cn.ncist.rq.lyglclient.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class QitaOrderActivity extends Activity {

	private Button bt_qitaorder_back ;
	
	private RadioGroup rg_qitaorder_jt ;
	private RadioGroup rg_qitaorder_zs ;
	private RadioGroup rg_qitaorder_dj ;
	private RadioGroup rg_qitaorder_cxbb ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_qitaorder) ;
		
		initView() ;
		
		bt_qitaorder_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish() ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_enter2) ;
			}
		}) ;
		
		rg_qitaorder_jt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.rb_qitaorder_jp:
					Toast.makeText(QitaOrderActivity.this, "vv", Toast.LENGTH_SHORT).show() ;
					break ;
				default:
					break;
				}				
			}
		}) ;
		rg_qitaorder_zs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.rb_qitaorder_jdian:
					break ;
				default:
					break;
				}				
			}
		}) ;
		rg_qitaorder_dj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
	
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.rb_qitaorder_ly:
					break ;
				default:
					break;
				}				
			}
		}) ;
		rg_qitaorder_cxbb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.rb_qitaorder_wf:
					break ;
				default:
					break;
				}				
			}
		}) ;
	}
	
	public void initView() {
		bt_qitaorder_back = (Button) findViewById(R.id.bt_qitaorder_back) ;
		
		rg_qitaorder_jt = (RadioGroup) findViewById(R.id.rg_qitaorder_jt) ;
		rg_qitaorder_zs = (RadioGroup) findViewById(R.id.rg_qitaorder_zs) ;
		rg_qitaorder_dj = (RadioGroup) findViewById(R.id.rg_qitaorder_dj) ;
		rg_qitaorder_cxbb = (RadioGroup) findViewById(R.id.rg_qitaorder_cxbb) ;
	}
}
