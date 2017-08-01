package com.cn.ncist.rq.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cn.ncist.rq.entity.NoteInfo;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.module.ChildTextView;
import com.cn.ncist.rq.util.Tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NoteListAdapter extends BaseAdapter {

	List<NoteInfo> list = new ArrayList<NoteInfo>() ;
	Context context ;
	
	public NoteListAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public NoteListAdapter(Context context) {
		super();
		this.context = context;
	}

	public void addDataToList(List<NoteInfo> list) {
		this.list = list ;
	}
	
	public void clear() {
		list.clear() ;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public NoteInfo getItem(int position) {
		// TODO Auto-generated method stub
		return  list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.inflater_listview_notelist, null) ;
		
		TextView tv_notelist_month = (TextView) view.findViewById(R.id.tv_notelist_month) ;	
		TextView tv_notelist_day = (TextView) view.findViewById(R.id.tv_notelist_day) ;	
		ImageView iv_notelist_tx = (ImageView) view.findViewById(R.id.iv_notelist_tx) ;
		ChildTextView tv_notelist_user = (ChildTextView) view.findViewById(R.id.tv_notelist_user) ;
		TextView tv_notelist_title = (TextView) view.findViewById(R.id.tv_notelist_title) ;
		RadioButton rb_notelist_llnum = (RadioButton) view.findViewById(R.id.rb_notelist_llnum) ;
		RadioButton rb_notelist_plnum = (RadioButton) view.findViewById(R.id.rb_notelist_plnum) ;
		RadioButton rb_notelist_zannum = (RadioButton) view.findViewById(R.id.rb_notelist_zannum) ;
		LinearLayout ll_notelist_bg = (LinearLayout) view.findViewById(R.id.ll_notelist_bg) ;
		
		NoteInfo noteInfo = list.get(position) ;
		String date = noteInfo.getNote_date() ;
		String[] dateArr = date.split("-") ;
		tv_notelist_month.setText(dateArr[1]) ;
		tv_notelist_day.setText(dateArr[2]) ;
		iv_notelist_tx.setBackgroundResource(Tools.getImageResId(Tools.formatString(noteInfo.getUser_touxiang()))) ;
		tv_notelist_user.setText(noteInfo.getUser_name()) ;
		tv_notelist_title.setText(noteInfo.getNote_title()) ;
		rb_notelist_llnum.setText(noteInfo.getNote_seenumber().toString()) ;
		rb_notelist_plnum.setText(noteInfo.getNote_commentnumber().toString()) ;
		rb_notelist_zannum.setText(noteInfo.getNote_zannumber().toString()) ;
		ll_notelist_bg.setBackgroundResource(Tools.getImageResId(Tools.formatString(noteInfo.getNote_bgimg()))) ;
		return view;
	}
}
