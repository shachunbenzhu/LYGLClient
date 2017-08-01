package com.cn.ncist.rq.activity;

import com.cn.ncist.rq.lyglclient.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 退出系统
 * 
 * @author 任倩
 * @date 2017-5-18
 *
 */
public class ExitActivity extends Activity implements OnClickListener {

	private Button bt_exit_yes ;
	private Button bt_exit_no ;
	private LinearLayout ll_exit ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_exit) ;
		initView() ;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_exit_yes:
			SharedPreferences sharedPreferences = getSharedPreferences("travel", MODE_PRIVATE) ;
		    Editor editor = sharedPreferences.edit() ;
		    editor.putInt("userId", 0) ;
		    editor.putString("userName", "") ;
		    editor.commit() ;
			finish() ;
			MainActivity.instance.finish() ;
			break;
		case R.id.bt_exit_no:
			finish() ;
			break;
		case R.id.ll_exit:
			Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", Toast.LENGTH_SHORT).show() ;
		default:
			break;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish() ;
		return super.onTouchEvent(event);
	}

	public void initView() {
		bt_exit_yes = (Button) findViewById(R.id.bt_exit_yes) ;
		bt_exit_no = (Button) findViewById(R.id.bt_exit_no) ;
		ll_exit = (LinearLayout) findViewById(R.id.ll_exit) ;
		
		bt_exit_yes.setOnClickListener(this) ;
		bt_exit_no.setOnClickListener(this) ;
		ll_exit.setOnClickListener(this) ;
	}

	
	
}
