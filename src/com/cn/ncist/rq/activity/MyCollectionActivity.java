package com.cn.ncist.rq.activity;

import com.cn.ncist.rq.lyglclient.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MyCollectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_mycollection) ;
	}

	
}
