package com.cn.ncist.rq.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import com.cn.ncist.rq.lyglclient.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class WelcomeActivity extends Activity {

	private int i = 10;
	private Timer timer = null;
	private TimerTask task = null;
	private TextView time_tv ;
	private com.cn.ncist.rq.module.SkipButton skip_bt ;
	//private ImageView app_image ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_welcome) ;
		
		initView() ;
		
		startTime() ;
		
		//Animation animation = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.anim_welcome) ;
		//app_image.startAnimation(animation) ;
		
		skip_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				timer.cancel();
				Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
				finish() ;
			}
		}) ;
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			time_tv.setText(msg.arg1+"");
			startTime();
		};
	};
	
	public void startTime(){
		/*
		 * 
		 * Timer是一种定时器工具，用来在一个后台线程计划执行指定任务。它可以计划执行一个任务一次或反复多次。
		 * TimerTask一个抽象类，它的子类代表一个可以被Timer计划的任务。
		 * 
		 */
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				i--;
				Message  message = mHandler.obtainMessage();
				message.arg1 = i;
				mHandler.sendMessage(message);
				
				if (i == 1) {
					timer.cancel();
					Intent intent = new Intent(WelcomeActivity.this, MainActivity.class) ;
					startActivity(intent) ;
					overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
					finish() ;
				}
			}
		};
		timer.schedule(task, 1000);
	}
	
	public void initView() {
		time_tv = (TextView) findViewById(R.id.tv_welcome_time) ;
		skip_bt = (com.cn.ncist.rq.module.SkipButton) findViewById(R.id.bt_welcome_skip) ;
		
		//app_image = (ImageView) findViewById(R.id.app_image) ;
	}
}
