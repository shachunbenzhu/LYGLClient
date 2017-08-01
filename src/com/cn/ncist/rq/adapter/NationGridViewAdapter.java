package com.cn.ncist.rq.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cn.ncist.rq.entity.NationInfo;
import com.cn.ncist.rq.foldingmenu.FoldingLayout;
import com.cn.ncist.rq.lyglclient.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NationGridViewAdapter extends BaseAdapter {

	List<NationInfo> list = new ArrayList<NationInfo>() ;
	Context context ;
	int clickitem = -1;//标识选择的Item
    
	public NationGridViewAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public NationGridViewAdapter(Context context) {
		super();
		this.context = context;
	}

	public void addDataToList(List<NationInfo> list) {
		this.list = list ;
	}
	
	public void clear() {
		list.clear() ;
	}

	
	public void setSelection(int position) {
		clickitem = position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public NationInfo getItem(int position) {
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
		View view = LayoutInflater.from(context).inflate(R.layout.inflater_gridview_allnation, null) ;
		
		TextView tv_allnation_name = (TextView) view.findViewById(R.id.tv_allnation_name) ;
		LinearLayout rl_allnationlist_rl = (LinearLayout) view.findViewById(R.id.rl_allnationlist_rl) ;
		tv_allnation_name.setText(list.get(position).getNationName()) ;
		
		/*if (clickitem == position) {
			rl_allnationlist_rl.setBackgroundColor(R.drawable.transparent) ;
			rl_allnationlist_rl.setBackgroundColor(R.drawable.white) ;
		} else {
			rl_allnationlist_rl.setBackgroundColor(R.drawable.transparent) ;
			rl_allnationlist_rl.setBackgroundColor(R.drawable.bg_qgraywhite) ;
		}*/
		
		return view;
	}

}
