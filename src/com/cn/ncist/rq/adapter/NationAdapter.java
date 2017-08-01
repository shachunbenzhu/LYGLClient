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

public class NationAdapter extends BaseAdapter {

	List<NationInfo> list = new ArrayList<NationInfo>() ;
	Context context ;
	int clickitem = -1;//标识选择的Item
    private String TAG_ITEM = "service_item";
    
	public NationAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public NationAdapter(Context context) {
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
		View view = LayoutInflater.from(context).inflate(R.layout.inflater_gridview_nation, null) ;
		
		TextView tv_nation_name = (TextView) view.findViewById(R.id.tv_nation_name) ;
		final LinearLayout rl_nationlist_rl = (LinearLayout) view.findViewById(R.id.rl_nationlist_rl) ;
		RelativeLayout rl_bar_layout = (RelativeLayout) view.findViewById(R.id.rl_bar_layout) ;
		final FoldingLayout foldingLayout = (FoldingLayout) rl_nationlist_rl.findViewWithTag(TAG_ITEM) ;
		View v_nation_bottom_view = view.findViewById(R.id.v_nation_bottom_view) ;
		
		tv_nation_name.setText(list.get(position).getNationName()) ;
		
		if (clickitem == position) {
			tv_nation_name.setTextColor(R.drawable.blueset) ;
			rl_nationlist_rl.setBackgroundColor(R.drawable.white) ;
		} else {
			tv_nation_name.setTextColor(R.drawable.black) ;
			rl_nationlist_rl.setBackgroundColor(R.drawable.bg_graywhite) ;
		}
		
		return view;
	}

}
