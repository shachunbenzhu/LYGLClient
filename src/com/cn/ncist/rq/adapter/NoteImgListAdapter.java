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
import android.widget.TextView;

public class NoteImgListAdapter extends BaseAdapter {

	List<String> list = new ArrayList<String>() ;
	Context context ;
	
	public NoteImgListAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public NoteImgListAdapter(Context context) {
		super();
		this.context = context;
	}

	public void addDataToList(List<String> list) {
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
	public String getItem(int position) {
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
		View view = LayoutInflater.from(context).inflate(R.layout.inflater_listview_noteimg, null) ;
		ImageView iv_noteimglist_img = (ImageView) view.findViewById(R.id.iv_noteimglist_img) ;
		
		iv_noteimglist_img.setBackgroundResource(Tools.getImageResId(Tools.formatString(list.get(position)))) ;
		
		return view;
	}
}
