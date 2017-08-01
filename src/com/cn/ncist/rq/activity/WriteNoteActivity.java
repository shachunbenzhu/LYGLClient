package com.cn.ncist.rq.activity ;

import static com.cn.ncist.rq.constant.NoteListConstant.SAVE_NOTE_FAIL;
import static com.cn.ncist.rq.constant.NoteListConstant.SAVE_NOTE_SUCCESS;
import static com.cn.ncist.rq.constant.MainConstant.DESTINATION_NOTEXISTS;
import static com.cn.ncist.rq.constant.MainConstant.QUERY_DESTINATION_SUCCESS;
import static com.cn.ncist.rq.constant.MainConstant.SHOW_AUTH_TOST_MESSAGE;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.mapapi.utils.f;
import com.cn.ncist.rq.adapter.DestinationAdapter;
import com.cn.ncist.rq.adapter.DestinationListAdapter;
import com.cn.ncist.rq.adapter.QuestionAdapter;
import com.cn.ncist.rq.constant.MainConstant;
import com.cn.ncist.rq.entity.DestinationInfo;
import com.cn.ncist.rq.entity.NoteInfo;
import com.cn.ncist.rq.entity.QuestionInfo;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.util.Bimp;
import com.cn.ncist.rq.util.FileUtils;
import com.cn.ncist.rq.util.ImageItem;
import com.cn.ncist.rq.util.PublicWay;
import com.cn.ncist.rq.util.Res;
import com.cn.ncist.rq.util.Tools;
import com.cn.ncist.rq.util.WebUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 首页面activity
 * tempSelectBitmap存放的是当前选中的临时图片
 *
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日  下午11:48:34
 */
public class WriteNoteActivity extends Activity {

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;//PopupWindow是Android上自定义弹出窗口
	private LinearLayout ll_popup;
	public static Bitmap bimap ;
	
	private Button bt_writenote_qx ;
	private Button bt_writenote_fb ;
	private RelativeLayout rl_writenote_des ;
	private EditText et_writenote_title ;
	private EditText et_writenote_content ;
	private TextView tv_writenote_des ;
	
	private AlertDialog.Builder dialog ;
	
	List<DestinationInfo> alldestinationlist = new ArrayList<DestinationInfo>() ;
	private String destinationName = "" ; 
	
	private Handler handler ;
	private Bundle bundle = new Bundle() ;
	private Message msg = new Message() ;
	
	private int userId ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Res.init(this);
		bimap = BitmapFactory.decodeResource(
				getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.activity_writenote, null);
		setContentView(parentView);
		Init();
		
		userId = getSharedPreferences("travel", MODE_PRIVATE).getInt("userId", 0) ;
		if (userId == 1) {
			Toast.makeText(WriteNoteActivity.this, "您当前只是游客，建议您进行登录！", Toast.LENGTH_SHORT) ;
			showLoginDialog() ;
		}
		
		bt_writenote_fb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveData("") ;
			}
		}) ;
		
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
					case SHOW_AUTH_TOST_MESSAGE :
						bundle = msg.getData();
						String showmessage = bundle.getString("msg") ;
						Toast.makeText(WriteNoteActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
					    break ;
					case DESTINATION_NOTEXISTS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						Toast.makeText(WriteNoteActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case QUERY_DESTINATION_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						destinationListAdapter.addDataToList(alldestinationlist) ;
						destinationListAdapter.notifyDataSetChanged() ;
						break ;
					case SAVE_NOTE_FAIL :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						Toast.makeText(WriteNoteActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
					    break ;
					case SAVE_NOTE_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						Toast.makeText(WriteNoteActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
					    break ;
					default:
						break ;
				}
			}
		} ;
	}

	public void showLoginDialog() {
		LayoutInflater factory = LayoutInflater.from(WriteNoteActivity.this) ;
		View view = factory.inflate(R.layout.dialog_service_tel, null) ;
		
		dialog = new AlertDialog.Builder(WriteNoteActivity.this) ;
		dialog.setTitle("是否登录") ;
		dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(WriteNoteActivity.this, LoginActivity.class) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry, R.anim.anim_out) ;
			}
		}) ;
		dialog.setNegativeButton("否", null) ;
		dialog.setIcon(R.drawable.common_default_dialog) ;
		dialog.create() ;
		dialog.show() ;
	}
	
	public void saveData(final String questionLabel) {
		
		new Thread(new Runnable() {//保存游记
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject()
							.put("noteTitle", et_writenote_title.getText().toString())
							.put("noteDate", Tools.getCurrentDateWithYearMonthDay())
							.put("userId", userId)
							.put("noteDays", 1)
							.put("noteExpend", 0)
							.put("notePartner", "")
							.put("noteAddress", destinationName == null ? "" : destinationName)
							.put("noteContent", et_writenote_content.getText().toString())
							.put("noteBg", "")
							.put("noteImg", "")
							.put("action", "saveNote")) ;
					JSONArray resultArray = WebUtil.postRequest("NoteServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ; 
							msg.what = MainConstant.QUESTION_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，发布游记失败！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							msg = new Message() ;
							msg.what = MainConstant.QUERY_QUESTION_SUCCESS ;
							bundle.putString("msg" , "发布成功！") ;
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
	
	public void getDestinationData() {

		new Thread(new Runnable() {//查询景点
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("provinceName", "").put("action", "queryAllDestination")) ;
					JSONArray resultArray = WebUtil.postRequest("DestinationServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ;
							msg.what = MainConstant.DESTINATION_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，该省份暂时没有景点！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							alldestinationlist = new ArrayList<DestinationInfo>() ;
							for (int i = 0; i < resultArray.length(); i++) {
								JSONObject jsonObj = resultArray.getJSONObject(i);
								DestinationInfo destinationInfo = new DestinationInfo() ;
								destinationInfo.setDestinationId(jsonObj.getInt("destinationId")) ;
								destinationInfo.setDestinationName(jsonObj.getString("destinationName")) ;
								destinationInfo.setDestinationPinyin(jsonObj.getString("destinationPinyin")) ;
								destinationInfo.setDestinationNumber(jsonObj.getInt("destinationNumber")) ;
								destinationInfo.setDestinationImg(jsonObj.getString("destinationImg")) ;
								destinationInfo.setDestinationImgarr(jsonObj.getString("destinationImgarr")) ;
								destinationInfo.setDestinationDesc(jsonObj.getString("destinationDesc")) ;
								destinationInfo.setProvinceName(jsonObj.getString("provinceName")) ;
								alldestinationlist.add(destinationInfo) ;
					        }
						}
						msg = new Message() ;
						msg.what = MainConstant.QUERY_DESTINATION_SUCCESS ;
						bundle.putString("msg" , "查询景点成功！") ;
						msg.setData(bundle) ;
						handler.sendMessage(msg) ;
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
	
	
	DestinationListAdapter destinationListAdapter ;
	public void showDestinationDialog() {
		LayoutInflater factory = LayoutInflater.from(WriteNoteActivity.this) ;
		View view = factory.inflate(R.layout.dialog_alldestination, null) ;
		
		final ListView lv_alldestination_des = (ListView) view.findViewById(R.id.lv_alldestination_des) ;
		
		
		destinationListAdapter = new DestinationListAdapter(WriteNoteActivity.this) ;
		lv_alldestination_des.setAdapter(destinationListAdapter) ;
		getDestinationData() ;
		
		dialog = new AlertDialog.Builder(WriteNoteActivity.this) ;
		dialog.setTitle("选择目的地") ;
		dialog.setIcon(R.drawable.common_default_dialog) ;
		dialog.setView(view).create();
		final Dialog dio = dialog.show() ;
		
		lv_alldestination_des.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				dio.dismiss() ;
				destinationName = destinationListAdapter.getItem(arg2).getDestinationName() ;
				tv_writenote_des.setText(destinationName) ;
			}
		}) ;
	}
	
	public void Init() {
		bt_writenote_qx = (Button) findViewById(R.id.bt_writenote_qx) ;
		bt_writenote_fb = (Button) findViewById(R.id.bt_writenote_fb) ;
		et_writenote_title = (EditText) findViewById(R.id.et_writenote_title) ;
		rl_writenote_des = (RelativeLayout) findViewById(R.id.rl_writenote_des) ;
		et_writenote_content = (EditText) findViewById(R.id.et_writenote_content) ;
		tv_writenote_des = (TextView) findViewById(R.id.tv_writenote_des) ;
		
		bt_writenote_qx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish() ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_enter2) ;
			}
		}) ;
		
		rl_writenote_des.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDestinationDialog() ;
			}
		}) ;
		
		pop = new PopupWindow(WriteNoteActivity.this);
		
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view
				.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view
				.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view
				.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(WriteNoteActivity.this,
						AlbumActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		
		noScrollgridview = (GridView) findViewById(R.id.gv_writenote_noScrollgridview);	
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					Log.i("ddddddd", "----------");
					ll_popup.startAnimation(AnimationUtils.loadAnimation(WriteNoteActivity.this,R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(WriteNoteActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});

	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if(Bimp.tempSelectBitmap.size() == 9){
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position ==Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	private static final int TAKE_PICTURE = 0x000001;

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//添加相机的外部启动
		
		/*
		 * 假若从T1Activity跳转到下一个Text2Activity，而当这个Text2Activity调用了finish()方法以后，
		 * 程序会自动跳转回T1Activity，并调用前一个T1Activity中的onActivityResult( )方法
		 * 
		 * TAKE_PICTURE要>=0
		 * */
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
				
				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");//传回来的值
				FileUtils.saveBitmap(bm, fileName);
				
				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			for(int i=0;i<PublicWay.activityList.size();i++){
				if (null != PublicWay.activityList.get(i)) {
					PublicWay.activityList.get(i).finish();
				}
			}
			System.exit(0);
		}
		return true;
	}

}

