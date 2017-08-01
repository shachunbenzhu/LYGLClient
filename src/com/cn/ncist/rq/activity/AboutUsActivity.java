package com.cn.ncist.rq.activity;

import com.cn.ncist.rq.lyglclient.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;

public class AboutUsActivity extends Activity {

	private Button bt_aboutus_back ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_aboutus) ;
		
		initView() ;
		
		bt_aboutus_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish() ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_enter2) ;
			}
		}) ;
	}

	public void initView() {
		bt_aboutus_back = (Button) findViewById(R.id.bt_aboutus_back) ;
	}
	
}
