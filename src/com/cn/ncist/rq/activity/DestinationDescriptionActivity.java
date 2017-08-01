package com.cn.ncist.rq.activity;

import com.cn.ncist.rq.entity.DestinationInfo;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class DestinationDescriptionActivity extends Activity implements OnClickListener , ViewFactory {

	private Button bt_destinationdescription_back ;
	private Button bt_destinationdescription_share ;
	private Button bt_destinationdescription_complete ;
	
	private TextView tv_destinationdescription_name ;
	private TextView tv_strategycatalog_descri ;
	
	private RelativeLayout rl_destinationdescription_gs ;
	private LinearLayout ll_destinationdescription_jtgs ;
	private RelativeLayout rl_destinationdescription_xqzb ;
	private LinearLayout ll_destinationdescription_jtxqzb ;
	
	private ImageSwitcher is_destinationdescription_switcher ;
	@SuppressWarnings("deprecation")
	private Gallery gl_destinationdescription_gallery ;
	
	private DestinationInfo destinationInfo ;
	
	private AlertDialog.Builder dialog ;
	
	private String[] mThumbStrings;
	private int[] mThumbIds;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		setContentView(R.layout.activity_destinationdescription) ;
		
		Intent intent = getIntent() ;
		destinationInfo = (DestinationInfo) intent.getSerializableExtra("destinationInfo") ;
		
		initView() ;
		
		mThumbStrings = destinationInfo.getDestinationImgarr().split(";") ;
		mThumbIds = new int[mThumbStrings.length] ;
		for (int i = 0; i < mThumbStrings.length; i++) {
			mThumbIds[i] = Tools.getImageResId(Tools.formatString(mThumbStrings[i])) ;
		}
		
		tv_destinationdescription_name.setText(destinationInfo.getDestinationName()) ;
		tv_strategycatalog_descri.setText(destinationInfo.getDestinationDesc()) ;
		
		is_destinationdescription_switcher.setFactory(this);
		is_destinationdescription_switcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
		is_destinationdescription_switcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));

		gl_destinationdescription_gallery.setAdapter(new ImageAdapter(this));
		gl_destinationdescription_gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				is_destinationdescription_switcher.setImageResource(mThumbIds[arg2]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	
	@Override
	public View makeView() {
		ImageView i = new ImageView(this) ;
        i.setBackgroundColor(R.drawable.white) ;
        i.setScaleType(ImageView.ScaleType.FIT_CENTER) ;
        i.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT)) ;
        return i ;
	}
	
	public void showShareDialog() {
		LayoutInflater factory = LayoutInflater.from(DestinationDescriptionActivity.this) ;
		View view = factory.inflate(R.layout.dialog_share, null) ;
		
		dialog = new AlertDialog.Builder(DestinationDescriptionActivity.this) ;
		dialog.setView(view).create();
		dialog.setNegativeButton("È¡Ïû", new DialogInterface.OnClickListener() {
			
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
		case R.id.bt_destinationdescription_back:
			finish() ;
			overridePendingTransition(R.anim.anim_entry, R.anim.anim_enter2) ;
			break;
		case R.id.bt_destinationdescription_share:
			showShareDialog() ;
			break;
		case R.id.bt_destinationdescription_complete :
			Intent intent = new Intent(DestinationDescriptionActivity.this, StrategyCatalogActivity.class) ;
			intent.putExtra("destinationInfo", destinationInfo) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry, R.anim.anim_out) ;
			break ;
		case R.id.rl_destinationdescription_gs :
			if (ll_destinationdescription_jtgs.getVisibility() == View.VISIBLE) {
				ll_destinationdescription_jtgs.setVisibility(View.GONE) ;
			} else {
				ll_destinationdescription_jtgs.setVisibility(View.VISIBLE) ;
			}
			break ;
		case R.id.rl_destinationdescription_xqzb :
			if (ll_destinationdescription_jtxqzb.getVisibility() == View.VISIBLE) {
				ll_destinationdescription_jtxqzb.setVisibility(View.GONE) ;
			} else {
				ll_destinationdescription_jtxqzb.setVisibility(View.VISIBLE) ;
			}
			break ;
		default:
			break;
		}
	}
	
	public class ImageAdapter extends BaseAdapter {
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(mContext);

            i.setImageResource(mThumbIds[position]);
            i.setAdjustViewBounds(true);
            i.setLayoutParams(new Gallery.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            i.setBackgroundResource(R.drawable.picture_frame);
            return i;
        }

        private Context mContext;

    }
	
	public void initView() { 
		bt_destinationdescription_back = (Button) findViewById(R.id.bt_destinationdescription_back) ;
		bt_destinationdescription_share = (Button) findViewById(R.id.bt_destinationdescription_share) ;
		bt_destinationdescription_complete = (Button) findViewById(R.id.bt_destinationdescription_complete) ;
		tv_destinationdescription_name = (TextView) findViewById(R.id.tv_destinationdescription_name) ;
		tv_strategycatalog_descri = (TextView) findViewById(R.id.tv_strategycatalog_descri) ;
		
		rl_destinationdescription_gs = (RelativeLayout) findViewById(R.id.rl_destinationdescription_gs) ;
		ll_destinationdescription_jtgs = (LinearLayout) findViewById(R.id.ll_destinationdescription_jtgs) ;
		rl_destinationdescription_xqzb = (RelativeLayout) findViewById(R.id.rl_destinationdescription_xqzb) ;
		ll_destinationdescription_jtxqzb = (LinearLayout) findViewById(R.id.ll_destinationdescription_jtxqzb) ;
		
		is_destinationdescription_switcher = (ImageSwitcher) findViewById(R.id.is_destinationdescription_switcher) ;
		gl_destinationdescription_gallery = (Gallery) findViewById(R.id.gl_destinationdescription_gallery) ;
		
		bt_destinationdescription_back.setOnClickListener(this) ;
		bt_destinationdescription_share.setOnClickListener(this) ;
		bt_destinationdescription_complete.setOnClickListener(this) ;
		
		rl_destinationdescription_gs.setOnClickListener(this) ;
		rl_destinationdescription_xqzb.setOnClickListener(this) ;
	}

}
