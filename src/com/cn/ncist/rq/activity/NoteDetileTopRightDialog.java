package com.cn.ncist.rq.activity;


import com.cn.ncist.rq.lyglclient.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

/***
 * 右侧弹出的Dialog
 * 
 * @author 任倩
 * @date 2017-5-31
 * 
 */
public class NoteDetileTopRightDialog extends Activity implements OnClickListener  {
	private LinearLayout ll_notedetile_right_dialog;
	
	private LinearLayout ll_notedetile_tpfx ;
	private LinearLayout ll_notedetile_ymfx ;
	private LinearLayout ll_notedetile_wtms ;

	private AlertDialog.Builder dialog ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notedetile_top_right_dialog);

		initView() ;
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_notedetile_right_dialog:
			Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.ll_notedetile_tpfx:
			showShareDialog() ;
			break;
		case R.id.ll_notedetile_ymfx:
			showShareDialog() ;
			break ;
		case R.id.ll_notedetile_wtms:
			finish() ;
			/*Intent intent = null ;
			intent.putExtra("noPic", true) ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;*/
			break ;
		default:
			break;
		}
	}
	
	public void showShareDialog() {
		LayoutInflater factory = LayoutInflater.from(NoteDetileTopRightDialog.this) ;
		View view = factory.inflate(R.layout.dialog_share, null) ;
		
		dialog = new AlertDialog.Builder(NoteDetileTopRightDialog.this) ;
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
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
	
	public void initView() {
		ll_notedetile_right_dialog = (LinearLayout) findViewById(R.id.ll_notedetile_right_dialog);
		ll_notedetile_tpfx = (LinearLayout) findViewById(R.id.ll_notedetile_tpfx);
		ll_notedetile_ymfx = (LinearLayout) findViewById(R.id.ll_notedetile_ymfx);
		ll_notedetile_wtms = (LinearLayout) findViewById(R.id.ll_notedetile_wtms);
		
		ll_notedetile_right_dialog.setOnClickListener(this) ;
		ll_notedetile_tpfx.setOnClickListener(this) ;
		ll_notedetile_ymfx.setOnClickListener(this) ;
		ll_notedetile_wtms.setOnClickListener(this) ;
	}
}
