package com.cn.ncist.rq.activity;

import static com.cn.ncist.rq.constant.MainConstant.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cn.ncist.rq.adapter.DestinationAdapter;
import com.cn.ncist.rq.adapter.DestinationListAdapter;
import com.cn.ncist.rq.adapter.MyPageAdapter;
import com.cn.ncist.rq.adapter.MyViewpagerAdapter;
import com.cn.ncist.rq.adapter.NationAdapter;
import com.cn.ncist.rq.adapter.NoteNoteListAdapter;
import com.cn.ncist.rq.adapter.QuestionAdapter;
import com.cn.ncist.rq.constant.MainConstant;
import com.cn.ncist.rq.entity.DestinationInfo;
import com.cn.ncist.rq.entity.NationInfo;
import com.cn.ncist.rq.entity.NoteInfo;
import com.cn.ncist.rq.entity.ProvinceInfo;
import com.cn.ncist.rq.entity.QuestionInfo;
import com.cn.ncist.rq.foldingmenu.FoldingLayout;
import com.cn.ncist.rq.foldingmenu.OnFoldListener;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.util.Tools;
import com.cn.ncist.rq.util.WebUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.R.integer;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader.ForceLoadContentObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主页面
 * 
 * @author 任倩
 * @date 2017-5-18
 *
 */
public class MainActivity extends Activity implements OnClickListener,OnCheckedChangeListener {

	public static MainActivity instance = null;
	
	private ViewPager vp ;
	private RadioGroup radioGroup ;
	private RadioButton homepageRadioButton ;
	private RadioButton destiRadioButton ;
	private RadioButton wrinoteRadioButton ;
	private RadioButton noteRadioButton ;
	private RadioButton serviceRadioButton ;
	
	private int ResId[] = {R.layout.activity_homepage , R.layout.activity_destination , R.layout.activity_note , R.layout.activity_service} ;
	private ArrayList<View> arrayList = new ArrayList<View>() ;
	private LayoutInflater layoutInflater ;
	
	private LinearLayout mClose;
	private LinearLayout mCloseBtn;
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;
	private View layout;
	
	/*****************HomePage*******************/
	private Button bt_homepage_user ;
	private EditText et_homepage_search ;
	private ImageButton ib_homepage_right ;
	private RadioButton rb_homepage_dw ;
	private RadioButton rb_homepage_gl ;
	private RadioButton rb_homepage_jdian ;
	private RadioButton rb_homepage_yd ;
	private RadioButton rb_homepage_hcp ;
	private RadioButton rb_homepage_ms ;
	private RadioButton rb_homepage_jp ;
	private RelativeLayout rl_homepage_ttnote ;
	private LinearLayout ll_homepage_notedetile ;
	private ImageView iv_homepage_bg ;
	private TextView tv_homepage_title ;
	private ImageView iv_homepage_tx ;
	private RadioButton rb_homepage_llnum ;
	private RadioButton rb_homepage_plnum ;
	
	private View slidingMenuView ;
	private SlidingMenu slidingMenu ;
	private Button bt_slidingmenu_scan ;
	private ImageView iv_slidemenu_touxiang ;
	private TextView tv_slidemenu_click_login ;
	private RelativeLayout rl_slidingmenu_mynote ;
	private RelativeLayout rl_slidingmenu_myshoucang ;
	
	/*****************Destination*******************/
	private Button bt_destination_search ;
	//private GridView gv_destination_nation ;
	private GridView gv_destination_des ;
	private LinearLayout ll_destination_ll ;
	
	private ExpandableListView expandableListView ;
	//private NationAdapter nationAdapter ;
	List<NationInfo> nationlist = new ArrayList<NationInfo>() ;
	List<ProvinceInfo> provincelist = new ArrayList<ProvinceInfo>() ;
	private DestinationAdapter destinationAdapter ;
	List<DestinationInfo> destinationlist = new ArrayList<DestinationInfo>() ;
	
	Map<String, List<ProvinceInfo>> map = new HashMap<String, List<ProvinceInfo>>() ;
	
	/*private String firstNationName ;//不需要，只需要从map从取
	private String provinceName ;*/
	
	/*****************Note*******************/
	private Button bt_note_search ;
	private Button bt_note_mynote ;
	private RadioButton rb_note_des ;
	private RadioButton rb_note_sx ;
	private RadioButton rb_note_tj ;
	
	Drawable des ;
	Drawable desRight ;
	Drawable sx ;
	Drawable sxRight ;
	Drawable tj ;
	Drawable tjRight ;
	
	private LinearLayout ll_note_ll ;
	private ListView lv_note_list ;
	private NoteNoteListAdapter noteNoteListAdapter ;
	List<NoteInfo> notelist = new ArrayList<NoteInfo>() ;
	
	List<DestinationInfo> alldestinationlist = new ArrayList<DestinationInfo>() ;
	private String destinationName = "" ; 
	/*****************Service*******************/
	private ViewPager vp_service_zxzx;
	private List<View> list = new ArrayList<View>();
	private List<ImageView> listImage = new ArrayList<ImageView>();
	private RadioButton rb_service_tel ;
	private AlertDialog.Builder dialog ;
	
	private RelativeLayout rl_service_worktime ;
	
	private RadioGroup rg_service_rg ;
	private RadioButton rb_service_cy ;
	private RadioButton rb_service_tdgq ;
	private RadioButton rb_service_cywt ;
	private RadioButton rb_service_hyhd ;
	private LinearLayout ll_service_list ;
	private ListView lv_question_lv ;
	private QuestionAdapter questionAdapter ;
	List<QuestionInfo> questionlist = new ArrayList<QuestionInfo>() ; ;
	
	private Handler handler ;
	private Bundle bundle = new Bundle() ;
	private Message msg = new Message() ;
	private int noteId ;
	private NoteInfo noteOneInfo_homepage = new NoteInfo() ;//主页跳转到游记详情页的信息
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		instance = this;
		
		initView() ;
        initData() ;
        
      //将PagerAdapter封装成类
        vp.setAdapter(new MyPageAdapter(arrayList)) ;
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					homepageRadioButton.setChecked(true) ;
					break;
				case 1:
					destiRadioButton.setChecked(true) ;
					break;					
				case 2:
					noteRadioButton.setChecked(true) ;
					break;	
				case 3:
					serviceRadioButton.setChecked(true) ;
					break;	
				default:
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {				
			}
		}) ;
        
        //按钮选择状态变化的监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.rb_main_homepage:
					vp.setCurrentItem(0) ;
					/*homepageRadioButton.setTextColor(R.drawable.blueset) ;
					destiRadioButton.setTextColor(R.drawable.black) ;
					noteRadioButton.setTextColor(R.drawable.black) ;
					serviceRadioButton.setTextColor(R.drawable.black) ;*/
					break ;
				case R.id.rb_main_destination:
					vp.setCurrentItem(1) ;
					/*homepageRadioButton.setTextColor(R.drawable.black) ;
					destiRadioButton.setTextColor(R.drawable.blueset) ;
					noteRadioButton.setTextColor(R.drawable.black) ;
					serviceRadioButton.setTextColor(R.drawable.black) ;*/
					break ;
				case R.id.rb_main_writenote:
					Intent intent = new Intent(MainActivity.this, WriteNoteActivity.class) ;
					startActivity(intent) ;
					overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
					break ;
				case R.id.rb_main_note:
					vp.setCurrentItem(2) ;
					/*homepageRadioButton.setTextColor(R.drawable.black) ;
					destiRadioButton.setTextColor(R.drawable.black) ;
					noteRadioButton.setTextColor(R.drawable.blueset) ;
					serviceRadioButton.setTextColor(R.drawable.black) ;*/
					break ;
				case R.id.rb_main_service:
					vp.setCurrentItem(3) ;
					/*homepageRadioButton.setTextColor(R.drawable.black) ;
					destiRadioButton.setTextColor(R.drawable.black) ;
					noteRadioButton.setTextColor(R.drawable.black) ;
					serviceRadioButton.setTextColor(R.drawable.blueset) ;*/
					break ;
				default:
					break;
				}				
			}
		}) ;
        
        /**********************Destination*************************/
        /*gv_destination_nation.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> arg0, final View arg1, final int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				nationAdapter.setSelection(arg2) ;
				nationAdapter.notifyDataSetChanged() ;
				//getDestinationData(nationAdapter.getItem(arg2).getNationName()) ;
				
			    String TAG_ITEM = "service_item";
			    int mNumberOfFolds = 3;
			    
				GridView gridView = (GridView) arg0 ;
				final ListAdapter listAdapter = gridView.getAdapter() ;
				final RelativeLayout rl_nationlist_rl = (RelativeLayout) arg1.findViewById(R.id.rl_nationlist_rl) ;
				RelativeLayout rl_bar_layout = (RelativeLayout) arg1.findViewById(R.id.rl_bar_layout) ;
				final FoldingLayout foldingLayout = (FoldingLayout) rl_nationlist_rl.findViewWithTag(TAG_ITEM) ;
				rl_bar_layout.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (arg2 == listAdapter.getCount()) {
							handleAnimation(v, foldingLayout, rl_nationlist_rl, listAdapter.getView(arg2+1, arg1, arg0).findViewById(R.id.v_nation_bottom_view));
						} else {
							handleAnimation(v, foldingLayout, rl_nationlist_rl, listAdapter.getView(arg2+1, arg1, arg0).findViewById(R.id.rl_nationlist_rl));
						}
					}
				}) ;
				
				foldingLayout.setNumberOfFolds(mNumberOfFolds);
				animateFold(foldingLayout, 350);
				if (arg2 == listAdapter.getCount()) {
					setMarginToTop(1, listAdapter.getView(arg2+1, arg1, arg0).findViewById(R.id.v_nation_bottom_view));
				} else {
					setMarginToTop(1, listAdapter.getView(arg2+1, arg1, arg0).findViewById(R.id.rl_nationlist_rl));
				}
			}
		}) ;*/
        
        initExpandableListAdapterData() ;
        final MyExpandableListAdapter myadapter = new MyExpandableListAdapter() ;
        expandableListView.setAdapter(myadapter) ;
        myadapter.notifyDataSetChanged() ;
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
			    String provinceName = myadapter.getChild(groupPosition, childPosition) ;
			    getDestinationData(provinceName) ;
			    
			    destinationAdapter.addDataToList(destinationlist) ;
				ll_destination_ll.setBackgroundResource(R.drawable.transparent) ;
				gv_destination_des.setVisibility(View.VISIBLE) ;
				destinationAdapter.notifyDataSetChanged() ;
				return false;
			}
		}) ;
        
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return false;
			}
		}) ;
        
        gv_destination_des.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(MainActivity.this, DestinationDetileActivity.class) ;
				intent.putExtra("destinationInfo", destinationAdapter.getItem(arg2)) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			}
		}) ;
        
        /**********************Note*************************/
        getNoteData(destinationName) ;
        
        lv_note_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				NoteInfo noteInfo = noteNoteListAdapter.getItem(arg2) ;
				Intent intent = new Intent(MainActivity.this, NoteDetileActivity.class) ;
				intent.putExtra("noteInfo", noteInfo) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			}
		}) ;
        
        /**********************Service*************************/
        getQuestionData("常用") ;
        
        MyViewpagerAdapter adapter = new MyViewpagerAdapter();
		vp_service_zxzx.setAdapter(adapter);
		
		//ViewPager上的视图
		View v1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.inflater_viewpager_service_item1, null);
		View v2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.inflater_viewpager_service_item2,null);
		
		list.add(v1);
		list.add(v2);
		adapter.addDataToList(list);
		adapter.notifyDataSetChanged();
        
		//ViewPager的滑动事件
		vp_service_zxzx.setOnPageChangeListener(new OnPageChangeListener() {
					
			@Override
			public void onPageSelected(int arg0) {
				for(int i=0;i<list.size();i++){
					if(i == arg0){
						listImage.get(i).setBackgroundResource(R.drawable.common_point_normal);
					}else{
						listImage.get(i).setBackgroundResource(R.drawable.common_point_select);
					}
				}
			}
				
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
					
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		rg_service_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.rb_service_cy:
					rb_service_cy.setTextColor(R.drawable.blueset) ;
					rb_service_tdgq.setTextColor(R.drawable.black) ;
					rb_service_cywt.setTextColor(R.drawable.black) ;
					rb_service_hyhd.setTextColor(R.drawable.black) ;
					getQuestionData("常用") ;
					break ;
				case R.id.rb_service_tdgq:
					rb_service_cy.setTextColor(R.drawable.black) ;
					rb_service_tdgq.setTextColor(R.drawable.blueset) ;
					rb_service_cywt.setTextColor(R.drawable.black) ;
					rb_service_hyhd.setTextColor(R.drawable.black) ;
					getQuestionData("退订改期") ;
					break ;
				case R.id.rb_service_cywti:
					rb_service_cy.setTextColor(R.drawable.black) ;
					rb_service_tdgq.setTextColor(R.drawable.black) ;
					rb_service_cywt.setTextColor(R.drawable.blueset) ;
					rb_service_hyhd.setTextColor(R.drawable.black) ;
					getQuestionData("出游问题") ;
					break ;
				case R.id.rb_service_hyhd:
					rb_service_cy.setTextColor(R.drawable.black) ;
					rb_service_tdgq.setTextColor(R.drawable.black) ;
					rb_service_cywt.setTextColor(R.drawable.black) ;
					rb_service_hyhd.setTextColor(R.drawable.blueset) ;
					getQuestionData("会员活动") ;
					break ;
				default:
					break;
				}				
			}
		}) ;
		
		lv_question_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				QuestionInfo questionInfo = questionAdapter.getItem(arg2) ;
				Intent intent = new Intent(MainActivity.this, QuestionDetileActivity.class) ;
				intent.putExtra("questionInfo", questionInfo) ;
				startActivity(intent) ;
				overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
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
						Toast.makeText(MainActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
					    break ;
					case TTNOTE_NOTEXISTS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						Toast.makeText(MainActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
					    break ;
					case QUERY_TTNOTE_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						NoteInfo noteInfo = (NoteInfo) bundle.getSerializable("noteInfo") ;
						iv_homepage_bg.setBackgroundResource(Tools.getImageResId(Tools.formatString(noteInfo.getNote_bgimg()))) ;
						tv_homepage_title.setText(noteInfo.getNote_title()) ;
						iv_homepage_tx.setImageResource(Tools.getImageResId(Tools.formatString(noteInfo.getUser_touxiang()))) ;
						//获取到的int类型值必须转化为字符串类型						
						rb_homepage_llnum.setText(noteInfo.getNote_seenumber().toString()) ;
						rb_homepage_plnum.setText(noteInfo.getNote_commentnumber().toString()) ;
						//Toast.makeText(MainActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
					    break ;
					case DESTINATION_NOTEXISTS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						destinationAdapter.addDataToList(destinationlist) ;
						ll_destination_ll.setBackgroundResource(R.drawable.common_default_kong) ;
						gv_destination_des.setVisibility(View.GONE) ;
						destinationAdapter.notifyDataSetChanged() ;
						Toast.makeText(MainActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case QUERY_DESTINATION_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						destinationAdapter.addDataToList(destinationlist) ;
						ll_destination_ll.setBackgroundResource(R.drawable.transparent) ;
						gv_destination_des.setVisibility(View.VISIBLE) ;
						destinationAdapter.notifyDataSetChanged() ;
						
						destinationListAdapter.addDataToList(alldestinationlist) ;
						destinationListAdapter.notifyDataSetChanged() ;
						//Toast.makeText(MainActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case NOTE_NOTEXISTS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						noteNoteListAdapter.addDataToList(notelist) ;
						ll_note_ll.setBackgroundResource(R.drawable.common_default_kong) ;
						lv_note_list.setVisibility(View.GONE) ;
						noteNoteListAdapter.notifyDataSetChanged() ;
						Toast.makeText(MainActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case QUERY_NOTE_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						noteNoteListAdapter.addDataToList(notelist) ;
						ll_note_ll.setBackgroundResource(R.drawable.transparent) ;
						lv_note_list.setVisibility(View.VISIBLE) ;
						noteNoteListAdapter.notifyDataSetChanged() ;
						//Toast.makeText(MainActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case QUESTION_NOTEXISTS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						ll_service_list.setBackgroundResource(R.drawable.common_default_kong) ;
						lv_question_lv.setVisibility(View.GONE) ;
						Toast.makeText(MainActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					case QUERY_QUESTION_SUCCESS :
						bundle = msg.getData();
						showmessage = bundle.getString("msg") ;
						questionAdapter.clear() ;
						questionAdapter.addDataToList(questionlist) ;
						ll_service_list.setBackgroundResource(R.drawable.transparent) ;
						lv_question_lv.setVisibility(View.VISIBLE) ;
						questionAdapter.notifyDataSetChanged() ;
						//Toast.makeText(MainActivity.this , showmessage , Toast.LENGTH_SHORT).show() ;
						break ;
					default:
						break ;
				}
			}
		} ;
	}

	public void initData() {
		layoutInflater = LayoutInflater.from(MainActivity.this) ; 
		for (int i = 0; i < ResId.length; i++) {
			View view = layoutInflater.inflate(ResId[i], null) ;
			switch (i) {
			case 0:
				initHomePageView(view) ;
				break;
			case 1:
				initDestiView(view) ;
				break;
			case 2:
				initNoteView(view) ;
				break;
			case 3:
				initServiceView(view) ;
				break;
			default:
				break;
			}
			arrayList.add(view) ; 
		}
		
		/**********************Homepage*************************/
		new Thread(new Runnable() {//查询最新最精华的游记
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("action", "queryTtNote")) ;
					JSONArray resultArray = WebUtil.postRequest("NoteServlet", params, handler) ;
					/*String jsonStr = resultArray.toString() ;
					
					//接收{}对象，此处接收数组对象会有异常  
			        if(jsonStr.indexOf("[") != -1){  
			            jsonStr = jsonStr.replace("[", "");  
			        }  
			        if(jsonStr.indexOf("]") != -1){  
			            jsonStr = jsonStr.replace("]", "");  
			        }  
			          
			        net.sf.json.JSONObject obj = new net.sf.json.JSONObject().fromObject(jsonStr);//将json字符串转换为json对象  
			        NoteInfo noteInfo = (NoteInfo) net.sf.json.JSONObject.toBean(obj, NoteInfo.class) ;//将建json对象转换为Person对象
*/			        
					if (resultArray != null) {
						JSONObject jsonObj = resultArray.getJSONObject(0);
						//NoteInfo noteInfo = (NoteInfo) jsonObj.get("noteModel") ;
						noteId = jsonObj.getInt("noteId") ;
						if (noteId == -1) {
							msg = new Message() ;
							msg.what = MainConstant.TTNOTE_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，暂无游记！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							msg = new Message() ; 
							msg.what = MainConstant.QUERY_TTNOTE_SUCCESS ;
							bundle.putString("msg" , "查询头条游记成功！") ;
				            //实体实现序列化
							NoteInfo noteInfo = new NoteInfo() ;
							noteInfo.setNote_id(noteId) ;
							noteInfo.setNote_title(jsonObj.getString("noteTitle")) ;
							noteInfo.setNote_date(jsonObj.getString("noteDate")) ;
							noteInfo.setNote_days(jsonObj.getInt("noteDays")) ;
							noteInfo.setNote_expend(jsonObj.getString("noteExpend")) ;
							noteInfo.setNote_partner(jsonObj.getString("notePartner")) ;
							noteInfo.setNote_content(jsonObj.getString("noteContent")) ;
							noteInfo.setNote_address(jsonObj.getString("noteAddress")) ;
							noteInfo.setNote_bgimg(jsonObj.getString("noteBg")) ;
							noteInfo.setNote_img(jsonObj.getString("noteImg")) ;
							noteInfo.setUser_name(jsonObj.getString("noteUser")) ;
							noteInfo.setUser_touxiang(jsonObj.getString("userTx")) ;
							noteInfo.setNote_seenumber(jsonObj.getInt("noteLlnum")) ;
							noteInfo.setNote_zannumber(jsonObj.getInt("noteZnum")) ;
							noteInfo.setNote_commentnumber(jsonObj.getInt("notePlnum")) ;
							
							noteOneInfo_homepage = noteInfo ;
				            bundle.putSerializable("noteInfo", noteInfo) ;
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
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_homepage_user:
			slidingMenu.toggle(true) ;
			break;
		case R.id.ib_homepage_right:
			Intent intent = new Intent(MainActivity.this, HomePageTopRightDialog.class);
			startActivity(intent);
			break;
		case R.id.rl_homepage_ttnote:
			intent = new Intent(MainActivity.this, NoteListActivity.class) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			break ;
		case R.id.ll_homepage_notedetile:
			intent = new Intent(MainActivity.this, NoteDetileActivity.class) ;
			intent.putExtra("noteInfo", noteOneInfo_homepage) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			break ;
		case R.id.bt_slidingmenu_scan:
			break ;
		case R.id.iv_slidemenu_touxiang:
			intent = new Intent(MainActivity.this, LoginActivity.class) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			break ;
		case R.id.tv_slidemenu_click_login:
			intent = new Intent(MainActivity.this, LoginActivity.class) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			break ;
		case R.id.rl_slidingmenu_mynote:
			intent = new Intent(MainActivity.this, MyNoteActivity.class) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			break ;
		case R.id.rl_slidingmenu_myshoucang:
			intent = new Intent(MainActivity.this, MyCollectionActivity.class) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			break ;
		case R.id.bt_destination_search :
			break ;
		case R.id.bt_note_search :
			break ;
		case R.id.bt_note_mynote :
			intent = new Intent(MainActivity.this, MyNoteActivity.class) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			break ;
		case R.id.rl_service_worktime:
			intent = new Intent(MainActivity.this, ServiceWorkTimeActivity.class) ;
			startActivity(intent) ;
			overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
			break ;
		default:
			break;
		}
	}
	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
			case R.id.rb_homepage_dw:
				if (isChecked) {
					Intent intent = new Intent(MainActivity.this, DingweiActivity.class) ;
					startActivity(intent) ;
					overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
					Toast.makeText(MainActivity.this, "定位成功!", Toast.LENGTH_SHORT).show() ;
				}
				break;
			case R.id.rb_homepage_gl:
				if (isChecked) {
					Intent intent = new Intent(MainActivity.this, StrategyActivity.class) ;
					startActivity(intent) ;
					overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
				}
				break;
			case R.id.rb_homepage_jdian:
				if (isChecked) {
					//http://hotel.qunar.com/
					Uri uri = Uri.parse("http://hotel.qunar.com/") ;
					Intent intent = new Intent(Intent.ACTION_VIEW, uri) ;
					startActivity(intent) ;
					overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
				}
				break;
			case R.id.rb_homepage_yd:
				if (isChecked) {
					Intent intent = new Intent(MainActivity.this, QitaOrderActivity.class) ;
					startActivity(intent) ;
					overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
				}
				break;
			case R.id.rb_homepage_hcp:
				if (isChecked) {
					//http://hotel.qunar.com/
					Uri uri = Uri.parse("http://trains.ctrip.com/TrainBooking/SearchTrain.aspx###") ;
					Intent intent = new Intent(Intent.ACTION_VIEW, uri) ;
					startActivity(intent) ;
					overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
				}
				break;
			case R.id.rb_homepage_jp:
				if (isChecked) {
					//http://hotel.qunar.com/
					Uri uri = Uri.parse("https://flight.qunar.com/") ;
					Intent intent = new Intent(Intent.ACTION_VIEW, uri) ;
					startActivity(intent) ;
					overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
				}
				break;
			case R.id.rb_homepage_ms:
				if (isChecked) {
					//http://hotel.qunar.com/
					Uri uri = Uri.parse("https://waimai.baidu.com/waimai?qt=find") ;
					Intent intent = new Intent(Intent.ACTION_VIEW, uri) ;
					startActivity(intent) ;
					overridePendingTransition(R.anim.anim_entry , R.anim.anim_out) ;
				}
				break;
			case R.id.rb_note_des :
				if (isChecked) {
					rb_note_des.setTextColor(R.drawable.blueset) ;
					rb_note_des.setCompoundDrawablesWithIntrinsicBounds(null, null, desRight, null) ;
					rb_note_sx.setTextColor(R.drawable.black) ;
					rb_note_sx.setCompoundDrawablesWithIntrinsicBounds(null, null, sx, null) ;
					rb_note_tj.setTextColor(R.drawable.black) ;
					rb_note_tj.setCompoundDrawablesWithIntrinsicBounds(null, null, tj, null) ;
					showDestinationDialog() ;
				}
				break ;
			case R.id.rb_note_sx :
				if (isChecked) {
					rb_note_des.setTextColor(R.drawable.black) ;
					rb_note_des.setCompoundDrawablesWithIntrinsicBounds(null, null, des, null) ;
					rb_note_sx.setTextColor(R.drawable.blueset) ;
					rb_note_sx.setCompoundDrawablesWithIntrinsicBounds(null, null, sxRight, null) ;
					rb_note_tj.setTextColor(R.drawable.black) ;
					rb_note_tj.setCompoundDrawablesWithIntrinsicBounds(null, null, tj, null) ;
				}
				break ;
			case R.id.rb_note_tj :
				if (isChecked) {
					rb_note_des.setTextColor(R.drawable.black) ;
					rb_note_des.setCompoundDrawablesWithIntrinsicBounds(null, null, des, null) ;
					rb_note_sx.setTextColor(R.drawable.black) ;
					rb_note_sx.setCompoundDrawablesWithIntrinsicBounds(null, null, sx, null) ;
					rb_note_tj.setTextColor(R.drawable.blueset) ;
					rb_note_tj.setCompoundDrawablesWithIntrinsicBounds(null, null, tjRight, null) ;
				}
				break ;
			case R.id.rb_service_tel:
				if (isChecked) {
					showServiceTelDialog() ;
				}
				break ;
			default:
				break;
		}
	}
	
	/************************************************目的地**********************************************/
	
	public void initExpandableListAdapterData() {
		
		map = new HashMap<String, List<ProvinceInfo>>() ;
		nationlist.clear() ;
		provincelist.clear() ;
		destinationlist.clear() ;
		
		destinationAdapter = new DestinationAdapter(MainActivity.this) ;
		gv_destination_des.setAdapter(destinationAdapter) ;
		
		destinationListAdapter = new DestinationListAdapter(MainActivity.this) ;
		getNationData() ;
		
		/*for(int i = 0; i < nationlist.size(); i++) {
    		getProvinceData(nationlist.get(i).getNationName()) ;
    		map.put(nationlist.get(i).getNationName(), provincelist) ;
    	}*/
		//getDestinationData(map.get(0).get(0).getProvinceName()) ;
	}
	
	class MyExpandableListAdapter extends BaseExpandableListAdapter {

		/*private Context context ;
		private List<NationInfo> nationlistinfos ;
		private Map<String, List<ProvinceInfo>> mapinfos ;
		
		
		public MyExpandableListAdapter(Context context,
				List<NationInfo> nationlistinfos,
				Map<String, List<ProvinceInfo>> mapinfos) {
			super();
			this.context = context;
			this.nationlistinfos = nationlistinfos;
			this.mapinfos = mapinfos;
		}*/
		
		//自己定义一个获得文字信息的方法
        /*TextView getTextView() {
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 64);
            TextView textView = new TextView(MainActivity.this);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setGravity(Gravity.RIGHT);
            textView.setPadding(0, 0, 5, 0);
            textView.setTextSize(20);
            textView.setTextColor(R.drawable.blueset);
            return textView;
        }*/
        
		@Override
		public String getChild(int groupPosition, int childPosition) {
			return map.get(nationlist.get(groupPosition).getNationName()).get(childPosition).getProvinceName();
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			String key = nationlist.get(groupPosition).getNationName() ;
			String info = map.get(key).get(childPosition).getProvinceName() ;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) MainActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
				convertView = inflater.inflate(R.layout.inflater_expandablelistview_province, null) ;
			}
			TextView tv = (TextView) convertView.findViewById(R.id.tv_province_name) ;
			tv.setText(info);
			return tv;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			String keyString = nationlist.get(groupPosition).getNationName() ;
			System.out.println(keyString + "aaaaa") ;
			System.out.println(map.size()) ;
			int siz = map.get(keyString).size() ;
			return map.get(keyString).size() ;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return nationlist.get(groupPosition).getNationName() ;
		}

		@Override
		public int getGroupCount() {
			return nationlist.size() ;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition ;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			/*LinearLayout ll = new LinearLayout(MainActivity.this);
            ll.setOrientation(0);
            
            TextView textView = getTextView();
            textView.setTextColor(Color.BLACK);
            textView.setText(getGroup(groupPosition).toString());
            ll.addView(textView);*/
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) MainActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
				convertView = inflater.inflate(R.layout.inflater_expandablelistview_nation, null) ;
			}
			
			if (isExpanded) {
				convertView.setBackgroundResource(R.drawable.blueset) ;
			} else {
				convertView.setBackgroundResource(R.drawable.bg_graywhite) ;
			}
			TextView tv = (TextView) convertView.findViewById(R.id.tv_nation_name) ;
			tv.setText(nationlist.get(groupPosition).getNationName()) ;
			return tv;
		}

		@Override
		public boolean hasStableIds() {
			return true ;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true ;
		}
		
	}
	
	public void getNationData() {
		/*nationAdapter = new NationAdapter(MainActivity.this) ;
		gv_destination_nation.setAdapter(nationAdapter) ;*/
		
		new Thread(new Runnable() {//查询国家
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("action", "queryAllNation")) ;
					JSONArray resultArray = WebUtil.postRequest("DestinationServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ;
							msg.what = MainConstant.NATION_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，暂时没有国家！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							nationlist = new ArrayList<NationInfo>() ;
							//firstNationName = resultArray.getJSONObject(0).getString("nationName") ;
							for (int i = 0; i < resultArray.length(); i++) {
								JSONObject jsonObj = resultArray.getJSONObject(i);
								NationInfo nationInfo = new NationInfo() ;
								nationInfo.setNationId(jsonObj.getInt("nationId")) ;
								nationInfo.setNationName(jsonObj.getString("nationName")) ;
					            nationlist.add(nationInfo) ;
					        }
							int flag = 1 ;
							for(int i = 0; i < nationlist.size(); i++) {
					    		//getProvinceData(nationlist.get(i).getNationName()) ;
								params = new JSONArray().put(new JSONObject().put("nationName", nationlist.get(i).getNationName()).put("action", "queryAllProvince")) ;
								resultArray = WebUtil.postRequest("DestinationServlet", params, handler) ;
								
								if (resultArray != null) {
									int result1 = resultArray.getJSONObject(0).getInt("result") ;
									if (result1 == -1) {
										msg = new Message() ;
										msg.what = MainConstant.DESTINATION_NOTEXISTS ;
										bundle.putString("msg" , "很抱歉，该国暂时没有省份、景点！") ;
										msg.setData(bundle) ;
										handler.sendMessage(msg) ;
										flag = 0 ;
									} else {
										provincelist = new ArrayList<ProvinceInfo>() ;
										//provinceName = resultArray.getJSONObject(0).getString("provinceName") ;
										for (int j = 0; j < resultArray.length(); j++) {
											JSONObject jsonObj = resultArray.getJSONObject(j);
											ProvinceInfo provinceInfo = new ProvinceInfo() ;
											provinceInfo.setProvinceId(jsonObj.getInt("provinceId")) ;
											provinceInfo.setProvinceName(jsonObj.getString("provinceName")) ;
								            provincelist.add(provinceInfo) ;
								        }
										map.put(nationlist.get(i).getNationName(), provincelist) ;
									}
								}
					    	}
							
							if (flag == 1) {
								String nationString = nationlist.get(0).getNationName() ;
								String provinceName = map.get(nationString).get(0).getProvinceName() ;
								//getDestinationData(provinceName) ;
									params = new JSONArray().put(new JSONObject().put("provinceName", provinceName).put("action", "queryAllDestination")) ;
									resultArray = WebUtil.postRequest("DestinationServlet", params, handler) ;
									
									if (resultArray != null) {
										result = resultArray.getJSONObject(0).getInt("result") ;
										if (result == -1) {
										} else {
											destinationlist = new ArrayList<DestinationInfo>() ;
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
													destinationlist.add(destinationInfo) ;
									        }
											msg = new Message() ;
											msg.what = MainConstant.QUERY_DESTINATION_SUCCESS ;
											bundle.putString("msg" , "查询成功！") ;
											msg.setData(bundle) ;
											handler.sendMessage(msg) ;
										}
									}
							}
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
	
	public void getProvinceData(final String nationName) {
		
		new Thread(new Runnable() {//查询省份
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("nationName", nationName).put("action", "queryAllProvince")) ;
					JSONArray resultArray = WebUtil.postRequest("DestinationServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ;
							msg.what = MainConstant.PROVINCE_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，该国暂时没有省份！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							provincelist = new ArrayList<ProvinceInfo>() ;
							//provinceName = resultArray.getJSONObject(0).getString("provinceName") ;
							for (int i = 0; i < resultArray.length(); i++) {
								JSONObject jsonObj = resultArray.getJSONObject(i);
								ProvinceInfo provinceInfo = new ProvinceInfo() ;
								provinceInfo.setProvinceId(jsonObj.getInt("provinceId")) ;
								provinceInfo.setProvinceName(jsonObj.getString("provinceName")) ;
					            provincelist.add(provinceInfo) ;
					        }
						}
						msg = new Message() ;
						msg.what = MainConstant.QUERY_PROVINCE_SUCCESS ;
						bundle.putString("msg" , "查询省份成功！") ;
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
	
	public void getDestinationData(final String provinceName) {
		
		new Thread(new Runnable() {//查询景点
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("provinceName", provinceName).put("action", "queryAllDestination")) ;
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
							destinationlist = new ArrayList<DestinationInfo>() ;
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
								//if (provinceName.equals("")) {
									alldestinationlist.add(destinationInfo) ;
								//} else {
									destinationlist.add(destinationInfo) ;
								//}
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
	
    
    /************************************************游记**********************************************/
	public void getNoteData(final String noteAddress) {
		noteNoteListAdapter = new NoteNoteListAdapter(MainActivity.this) ;
		lv_note_list.setAdapter(noteNoteListAdapter) ;
		
		new Thread(new Runnable() {//查询游记
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("noteAddress", noteAddress).put("action", "queryAllNote")) ;
					JSONArray resultArray = WebUtil.postRequest("NoteServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ;
							msg.what = MainConstant.NOTE_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，暂时没有游记！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							notelist = new ArrayList<NoteInfo>() ;
							for (int i = 0; i < resultArray.length(); i++) {
					            /*JSONObject jsonObj = resultArray.getJSONObject(i);
					            NoteInfo noteInfo = (NoteInfo) jsonObj.get("noteModel") ; */ 
								JSONObject jsonObj = resultArray.getJSONObject(i);
								NoteInfo noteInfo = new NoteInfo() ;
								noteInfo.setNote_id(jsonObj.getInt("noteId")) ;
								noteInfo.setNote_title(jsonObj.getString("noteTitle")) ;
								noteInfo.setNote_date(jsonObj.getString("noteDate")) ;
								noteInfo.setNote_days(jsonObj.getInt("noteDays")) ;
								noteInfo.setNote_expend(jsonObj.getString("noteExpend")) ;
								noteInfo.setNote_partner(jsonObj.getString("notePartner")) ;
								noteInfo.setNote_content(jsonObj.getString("noteContent")) ;
								noteInfo.setNote_address(jsonObj.getString("noteAddress")) ;
								noteInfo.setNote_bgimg(jsonObj.getString("noteBg")) ;
								noteInfo.setNote_img(jsonObj.getString("noteImg")) ;
								noteInfo.setUser_name(jsonObj.getString("noteUser")) ;
								noteInfo.setUser_touxiang(jsonObj.getString("userTx")) ;
								noteInfo.setNote_seenumber(jsonObj.getInt("noteLlnum")) ;
								noteInfo.setNote_zannumber(jsonObj.getInt("noteZnum")) ;
								noteInfo.setNote_commentnumber(jsonObj.getInt("notePlnum")) ;
					            notelist.add(noteInfo) ;
					        }
						}
						//noteListAdapter.addDataToList(notelist) ;
						msg = new Message() ;
						msg.what = MainConstant.QUERY_NOTE_SUCCESS ;
						bundle.putString("msg" , "查询游记成功！") ;
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
		LayoutInflater factory = LayoutInflater.from(MainActivity.this) ;
		View view = factory.inflate(R.layout.dialog_alldestination, null) ;
		
		final ListView lv_alldestination_des = (ListView) view.findViewById(R.id.lv_alldestination_des) ;
		
		destinationListAdapter = new DestinationListAdapter(MainActivity.this) ;
		lv_alldestination_des.setAdapter(destinationListAdapter) ;
		getDestinationData("") ;
		
		dialog = new AlertDialog.Builder(MainActivity.this) ;
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
				getNoteData(destinationName) ;
			}
		}) ;
	}
	
	/************************************************Service**********************************************/
	public void showServiceTelDialog() {
		LayoutInflater factory = LayoutInflater.from(MainActivity.this) ;
		View view = factory.inflate(R.layout.dialog_service_tel, null) ;
		
		dialog = new AlertDialog.Builder(MainActivity.this) ;
		dialog.setTitle("客服") ;
		dialog.setIcon(R.drawable.common_default_dialog) ;
		dialog.setView(view).create();
		dialog.show() ;
	}
	
	public void getQuestionData(final String questionLabel) {
		questionAdapter = new QuestionAdapter(MainActivity.this) ;
		
		lv_question_lv.setAdapter(questionAdapter) ;
		
		new Thread(new Runnable() {//查询问题
			@Override
			public void run() {
				try {
					JSONArray params = new JSONArray().put(new JSONObject().put("questionLabel", questionLabel).put("action", "queryQuestion")) ;
					JSONArray resultArray = WebUtil.postRequest("QuestionServlet", params, handler) ;
					
					if (resultArray != null) {
						int result = resultArray.getJSONObject(0).getInt("result") ;
						if (result == -1) {
							msg = new Message() ;
							msg.what = MainConstant.QUESTION_NOTEXISTS ;
							bundle.putString("msg" , "很抱歉，暂时没有问题！") ;
							msg.setData(bundle) ;
							handler.sendMessage(msg) ;
						} else {
							questionlist = new ArrayList<QuestionInfo>() ;
							for (int i = 0; i < resultArray.length(); i++) {
					            JSONObject jsonObj = resultArray.getJSONObject(i);
					            QuestionInfo questionInfo = new QuestionInfo() ;
					            questionInfo.setQuestionId(jsonObj.getInt("questionId")) ;
					            questionInfo.setQuestionContent(jsonObj.getString("questionContent")) ;
					            questionInfo.setQuestionAnswer(jsonObj.getString("questionAnswer")) ;
					            questionInfo.setQuestionLabel(questionLabel) ;
					            questionlist.add(questionInfo) ;
					        }
						}
						
						msg = new Message() ;
						msg.what = MainConstant.QUERY_QUESTION_SUCCESS ;
						bundle.putString("msg" , "查询问题成功！") ;
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
	
	/************************************************初始化控件*********************************************/
	public void slidingMenu() {
		slidingMenu = new SlidingMenu(MainActivity.this) ;
		slidingMenu.attachToActivity(MainActivity.this, SlidingMenu.SLIDING_CONTENT) ;
		slidingMenu.setMode(SlidingMenu.LEFT) ;
		slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setMenu(R.layout.slidingmenu) ;
	}
	
	public void initHomePageView(View view) {
		//注意是view.，否则控件找不到
		bt_homepage_user = (Button) view.findViewById(R.id.bt_homepage_user) ;
		et_homepage_search = (EditText) view.findViewById(R.id.et_homepage_search) ;
		ib_homepage_right = (ImageButton) view.findViewById(R.id.ib_homepage_right) ;
		
		rb_homepage_dw = (RadioButton) view.findViewById(R.id.rb_homepage_dw) ;
		rb_homepage_gl = (RadioButton) view.findViewById(R.id.rb_homepage_gl) ;
		rb_homepage_jdian = (RadioButton) view.findViewById(R.id.rb_homepage_jdian) ;
		rb_homepage_yd = (RadioButton) view.findViewById(R.id.rb_homepage_yd) ;
		rb_homepage_hcp = (RadioButton) view.findViewById(R.id.rb_homepage_hcp) ;
		rb_homepage_jp = (RadioButton) view.findViewById(R.id.rb_homepage_jp) ;
		rb_homepage_ms = (RadioButton) view.findViewById(R.id.rb_homepage_ms) ;
		
		rl_homepage_ttnote = (RelativeLayout) view.findViewById(R.id.rl_homepage_ttnote) ;
		ll_homepage_notedetile = (LinearLayout) view.findViewById(R.id.ll_homepage_notedetile) ;
		iv_homepage_bg = (ImageView) view.findViewById(R.id.iv_homepage_bg) ;
		tv_homepage_title = (TextView) view.findViewById(R.id.tv_homepage_title) ;
		iv_homepage_tx = (ImageView) view.findViewById(R.id.iv_homepage_tx) ;
		rb_homepage_llnum = (RadioButton) view.findViewById(R.id.rb_homepage_llnum) ;
		rb_homepage_plnum = (RadioButton) view.findViewById(R.id.rb_homepage_plnum) ;
		
		bt_homepage_user.setOnClickListener(this) ;
		ib_homepage_right.setOnClickListener(this) ;
		rl_homepage_ttnote.setOnClickListener(this) ;
		ll_homepage_notedetile.setOnClickListener(this) ;
		
		rb_homepage_dw.setOnCheckedChangeListener(this) ;
		rb_homepage_gl.setOnCheckedChangeListener(this) ;
		rb_homepage_jdian.setOnCheckedChangeListener(this) ;
		rb_homepage_yd.setOnCheckedChangeListener(this) ;
		rb_homepage_hcp.setOnCheckedChangeListener(this) ;
		rb_homepage_jp.setOnCheckedChangeListener(this) ;
		rb_homepage_ms.setOnCheckedChangeListener(this) ;

		// 下面是SlidingMenu的定义以及SlidingMenu中的组件初始化
		/*slidingMenu() ;
		slidingMenuView = slidingMenu.getMenu() ;
		bt_slidingmenu_scan = (Button) slidingMenuView.findViewById(R.id.bt_slidingmenu_scan) ;
		iv_slidemenu_touxiang = (ImageView) slidingMenuView.findViewById(R.id.iv_slidemenu_touxiang) ;
		tv_slidemenu_click_login = (TextView) slidingMenuView.findViewById(R.id.tv_slidemenu_click_login) ;
		rl_slidingmenu_mynote = (RelativeLayout) slidingMenu.findViewById(R.id.rl_slidingmenu_mynote) ;
		rl_slidingmenu_myshoucang = (RelativeLayout) slidingMenu.findViewById(R.id.rl_slidingmenu_myshoucang) ;
		
		tv_slidemenu_click_login.setText("点击登录...") ;
		
		bt_slidingmenu_scan.setOnClickListener(this) ;
		iv_slidemenu_touxiang.setOnClickListener(this) ;
		tv_slidemenu_click_login.setOnClickListener(this) ;
		rl_slidingmenu_mynote.setOnClickListener(this) ;
		rl_slidingmenu_myshoucang.setOnClickListener(this) ;*/
	}
	public void initDestiView(View view) {
		bt_destination_search = (Button) view.findViewById(R.id.bt_destination_search) ;
		
		//gv_destination_nation = (GridView) view.findViewById(R.id.gv_destination_nation) ;
		expandableListView = (ExpandableListView) view.findViewById(R.id.elv_destination_nati) ;
		gv_destination_des = (GridView) view.findViewById(R.id.gv_destination_des) ;
		ll_destination_ll = (LinearLayout) view.findViewById(R.id.ll_destination_ll) ;
		
		bt_destination_search.setOnClickListener(this) ;
	}
	public void initNoteView(View view) {
		bt_note_search = (Button) view.findViewById(R.id.bt_note_search) ;
		bt_note_mynote = (Button) view.findViewById(R.id.bt_note_mynote) ;
		rb_note_des = (RadioButton) view.findViewById(R.id.rb_note_des) ;
		rb_note_sx = (RadioButton) view.findViewById(R.id.rb_note_sx) ;
		rb_note_tj = (RadioButton) view.findViewById(R.id.rb_note_tj) ;
		
		des = getResources().getDrawable(R.drawable.common_ico_filter) ;
		desRight = getResources().getDrawable(R.drawable.common_ico_filter_pressed) ;
		sx = getResources().getDrawable(R.drawable.common_ico_filter) ;
		sxRight = getResources().getDrawable(R.drawable.common_ico_filter_pressed) ;
		tj = getResources().getDrawable(R.drawable.common_ico_arrow_down) ;
		tjRight = getResources().getDrawable(R.drawable.common_ico_arrow_down_selected) ;
		
		ll_note_ll = (LinearLayout) view.findViewById(R.id.ll_note_ll) ;
		lv_note_list = (ListView) view.findViewById(R.id.lv_note_list) ;
		
		bt_note_search.setOnClickListener(this) ;
		bt_note_mynote.setOnClickListener(this) ;
		
		rb_note_des.setOnCheckedChangeListener(this) ;
		rb_note_sx.setOnCheckedChangeListener(this) ;
		rb_note_tj.setOnCheckedChangeListener(this) ;
	}
	public void initServiceView(View view) {
		vp_service_zxzx = (ViewPager) view.findViewById(R.id.vp_service_zxzx);
		rb_service_tel = (RadioButton) view.findViewById(R.id.rb_service_tel) ;
		
		listImage.add((ImageView)view.findViewById(R.id.iv_service_guide1));
		listImage.add((ImageView)view.findViewById(R.id.iv_service_guide2));
		
		rl_service_worktime = (RelativeLayout) view.findViewById(R.id.rl_service_worktime) ;
		rg_service_rg = (RadioGroup) view.findViewById(R.id.rg_service_rg) ;
		rb_service_cy = (RadioButton) view.findViewById(R.id.rb_service_cy) ;
		rb_service_tdgq = (RadioButton) view.findViewById(R.id.rb_service_tdgq) ;
		rb_service_cywt = (RadioButton) view.findViewById(R.id.rb_service_cywti) ;
		rb_service_hyhd = (RadioButton) view.findViewById(R.id.rb_service_hyhd) ;
		ll_service_list = (LinearLayout) view.findViewById(R.id.ll_service_list) ;
		lv_question_lv = (ListView) view.findViewById(R.id.lv_question_lv) ;
		
		rb_service_tel.setOnCheckedChangeListener(this) ;
		rl_service_worktime.setOnClickListener(this) ;
	}
	
	//初始化布局
	public void initView() {
		vp = (ViewPager) findViewById(R.id.vp_main_vp) ;
		radioGroup = (RadioGroup) findViewById(R.id.rg_main_rg) ;
		homepageRadioButton = (RadioButton) findViewById(R.id.rb_main_homepage) ;
		destiRadioButton = (RadioButton) findViewById(R.id.rb_main_destination) ;
		wrinoteRadioButton = (RadioButton) findViewById(R.id.rb_main_writenote) ;
		noteRadioButton = (RadioButton) findViewById(R.id.rb_main_note) ;	
		serviceRadioButton = (RadioButton) findViewById(R.id.rb_main_service) ;
	}
	
	/*public void onResume() {
		super.onResume();
		initExpandableListAdapterData() ;
		//getDestinationData(provinceName) ;
		getNoteData(destinationName) ;
		getQuestionData("常用") ;
	}*/
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 获取back键
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 如果 Menu已经打开 ，先关闭Menu
			if (menu_display) {
				menuWindow.dismiss();
				menu_display = false;
			} else {
				/*Intent intent = new Intent();
				intent.setClass(MainActivity.this, ExitActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.anim_entry, R.anim.anim_enter2);*/
				MainActivity.instance.finish() ;
			}
		}
		// 获取 Menu键
		else if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (!menu_display) {
				// 获取LayoutInflater实例
				inflater = (LayoutInflater) this
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				layout = inflater.inflate(R.layout.main_exit_menu, null);

				// 将layout加入到PopupWindow,后两个参数是width和height
				menuWindow = new PopupWindow(layout, LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT);
				// 设置弹出效果
				// menuWindow.showAsDropDown(layout);
				// menuWindow.showAsDropDown(null, 0, layout.getHeight());
				// 设置layout在PopupWindow中显示的位置
				menuWindow.showAtLocation(this.findViewById(R.id.rl_main),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
				// 获取main_menu中的控件
				mClose = (LinearLayout) layout.findViewById(R.id.ll_menu_close);
				mCloseBtn = (LinearLayout) layout
						.findViewById(R.id.ll_menu_close_btn);
				
				mCloseBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, ExitActivity.class);
						startActivity(intent);
						menuWindow.dismiss(); // 响应点击事件之后关闭Menu
					}
				});
				menu_display = true;
			} else {
				// 如果当前已经为显示状态，则隐藏起来
				menuWindow.dismiss();
				menu_display = false;
			}
			return false;
		}
		return false;
	}

}
