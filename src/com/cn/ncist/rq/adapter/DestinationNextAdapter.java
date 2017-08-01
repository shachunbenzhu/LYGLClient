package com.cn.ncist.rq.adapter;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

import com.cn.ncist.rq.entity.DestinationInfo;
import com.cn.ncist.rq.lyglclient.R;
import com.cn.ncist.rq.util.Tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DestinationNextAdapter extends BaseAdapter {

	List<DestinationInfo> list = new ArrayList<DestinationInfo>() ;
	Context context ;
	
	public DestinationNextAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public DestinationNextAdapter(Context context) {
		super();
		this.context = context;
	}

	public void addDataToList(List<DestinationInfo> list) {
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
	public DestinationInfo getItem(int position) {
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
		View view = LayoutInflater.from(context).inflate(R.layout.inflater_gridview_destinationnext, null) ;
		
		DestinationInfo destinationInfo = list.get(position) ;
		Button bt_destinationdetile_next = (Button) view.findViewById(R.id.bt_destinationdetile_next) ;
		
		bt_destinationdetile_next.setText(destinationInfo.getDestinationName()) ;
		
		return view;
	}
}
