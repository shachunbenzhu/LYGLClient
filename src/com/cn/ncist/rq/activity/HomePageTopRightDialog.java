package com.cn.ncist.rq.activity;


import com.cn.ncist.rq.lyglclient.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

/***
 * 首页点击魔法棒右侧弹出的Dialog
 * 
 * @author 任倩
 * @date 2017-5-18
 * 
 */
public class HomePageTopRightDialog extends Activity implements OnClickListener  {
	private LinearLayout ll_homepage_right_dialog;
	
	private LinearLayout ll_homepage_log ;
	private LinearLayout ll_homepage_scan ;
	private LinearLayout ll_homepage_about ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage_top_right_dialog);

		initView() ;
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_homepage_right_dialog:
			Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.ll_homepage_log:
			Uri uri = Uri.parse("http://118.230.132.65:8080/LYGLSystem/login.action") ;
			Intent intent = new Intent(Intent.ACTION_VIEW, uri) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			break;
		case R.id.ll_homepage_scan:
			break ;
		case R.id.ll_homepage_about:
			intent = new Intent(HomePageTopRightDialog.this, AboutUsActivity.class) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			break ;
		default:
			break;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
	
	public void initView() {
		ll_homepage_right_dialog = (LinearLayout) findViewById(R.id.ll_homepage_right_dialog);
		ll_homepage_log = (LinearLayout) findViewById(R.id.ll_homepage_log);
		ll_homepage_scan = (LinearLayout) findViewById(R.id.ll_homepage_scan);
		ll_homepage_about = (LinearLayout) findViewById(R.id.ll_homepage_about);
		
		ll_homepage_right_dialog.setOnClickListener(this) ;
		ll_homepage_log.setOnClickListener(this) ;
		ll_homepage_scan.setOnClickListener(this) ;
		ll_homepage_about.setOnClickListener(this) ;
	}
}
