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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyNoteListAdapter extends BaseAdapter {

	List<NoteInfo> list = new ArrayList<NoteInfo>() ;
	Context context ;
	
	public MyNoteListAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public MyNoteListAdapter(Context context) {
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
		View view = LayoutInflater.from(context).inflate(R.layout.inflater_listview_mynote, null) ;
		
		RelativeLayout rl_mynote_bg = (RelativeLayout) view.findViewById(R.id.rl_mynote_bg) ;
		
		TextView tv_mynote_title = (TextView) view.findViewById(R.id.tv_mynote_title) ;
		RadioButton rb_mynote_llnum = (RadioButton) view.findViewById(R.id.rb_mynote_llnum) ;
		RadioButton rb_mynote_plnum = (RadioButton) view.findViewById(R.id.rb_mynote_plnum) ;
		RadioButton rb_mynote_zannum = (RadioButton) view.findViewById(R.id.rb_mynote_zannum) ;
		TextView tv_mynote_date = (TextView) view.findViewById(R.id.tv_mynote_date) ;
		
		NoteInfo noteInfo = list.get(position) ;
		rl_mynote_bg.setBackgroundResource(Tools.getImageResId(Tools.formatString(noteInfo.getNote_bgimg()))) ;
		tv_mynote_title.setText(noteInfo.getNote_title()) ;
		rb_mynote_llnum.setText(noteInfo.getNote_seenumber().toString()) ;
		rb_mynote_plnum.setText(noteInfo.getNote_commentnumber().toString()) ;
		rb_mynote_zannum.setText(noteInfo.getNote_zannumber().toString()) ;
		tv_mynote_date.setText(noteInfo.getNote_date()) ;
		
		return view;
	}
}
