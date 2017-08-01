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

public class NoteNoteListAdapter extends BaseAdapter {

	List<NoteInfo> list = new ArrayList<NoteInfo>() ;
	Context context ;
	
	public NoteNoteListAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public NoteNoteListAdapter(Context context) {
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
		View view = LayoutInflater.from(context).inflate(R.layout.inflater_listview_note_notelist, null) ;
		
		RelativeLayout rl_notelist_note = (RelativeLayout) view.findViewById(R.id.rl_notelist_note) ;
		TextView tv_note_flag = (TextView) view.findViewById(R.id.iv_note_flag) ;	
		ImageView iv_note_tx = (ImageView) view.findViewById(R.id.iv_note_tx) ;
		ChildTextView tv_note_user = (ChildTextView) view.findViewById(R.id.tv_note_user) ;
		TextView tv_note_title = (TextView) view.findViewById(R.id.tv_note_title) ;
		RadioButton rb_note_llnum = (RadioButton) view.findViewById(R.id.rb_note_llnum) ;
		RadioButton rb_note_plnum = (RadioButton) view.findViewById(R.id.rb_note_plnum) ;
		RadioButton rb_note_zannum = (RadioButton) view.findViewById(R.id.rb_note_zannum) ;
		TextView tv_note_date = (TextView) view.findViewById(R.id.tv_note_date) ;
		
		int ResId[] = {R.drawable.note_flag1 , R.drawable.note_flag2 , R.drawable.note_flag3 , R.drawable.note_flag4
				 , R.drawable.note_flag5 , R.drawable.note_flag6 , R.drawable.note_flag7 , R.drawable.note_flag8
				 , R.drawable.note_flag9 , R.drawable.note_flag10} ;
		
		NoteInfo noteInfo = list.get(position) ;
		int n = (int) (Math.random() * 10) ;
		tv_note_flag.setBackgroundResource(ResId[n]) ;
		tv_note_flag.setText(noteInfo.getNote_address()) ;	
		rl_notelist_note.setBackgroundResource(Tools.getImageResId(Tools.formatString(noteInfo.getNote_bgimg()))) ;
		
		iv_note_tx.setBackgroundResource(Tools.getImageResId(Tools.formatString(noteInfo.getUser_touxiang()))) ;
		tv_note_user.setText(noteInfo.getUser_name()) ;
		tv_note_title.setText(noteInfo.getNote_title()) ;
		rb_note_llnum.setText(noteInfo.getNote_seenumber().toString()) ;
		rb_note_plnum.setText(noteInfo.getNote_commentnumber().toString()) ;
		rb_note_zannum.setText(noteInfo.getNote_zannumber().toString()) ;
		tv_note_date.setText(noteInfo.getNote_date()) ;
		
		return view;
	}
}
