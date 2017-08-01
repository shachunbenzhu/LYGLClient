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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DestinationStrategyAdapter extends BaseAdapter {

	List<DestinationInfo> list = new ArrayList<DestinationInfo>() ;
	Context context ;
	int clickitem = -1;//标识选择的Item
	
	public DestinationStrategyAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public DestinationStrategyAdapter(Context context) {
		super();
		this.context = context;
	}

	public void addDataToList(List<DestinationInfo> list) {
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
		View view = LayoutInflater.from(context).inflate(R.layout.inflater_listview_destinationstrategy, null) ;
		
		DestinationInfo destinationInfo = list.get(position) ;
		RelativeLayout rl_destinationstrategy_item = (RelativeLayout) view.findViewById(R.id.rl_destinationstrategy_item) ;
		TextView tv_destinationstrategy_name = (TextView) view.findViewById(R.id.tv_destinationstrategy_name) ;
		TextView tv_destinationstrategy_pinyin = (TextView) view.findViewById(R.id.tv_destinationstrategy_pinyin) ;
		final ImageView iv_destinationstrategy_download = (ImageView) view.findViewById(R.id.iv_destinationstrategy_download) ;
		
		rl_destinationstrategy_item.setBackgroundResource(Tools.getImageResId(Tools.formatString(destinationInfo.getDestinationImg()))) ;
		tv_destinationstrategy_name.setText(destinationInfo.getDestinationName()) ;
		tv_destinationstrategy_pinyin.setText(destinationInfo.getDestinationPinyin()) ;
		
		iv_destinationstrategy_download.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*iv_destinationstrategy_download.setBackgroundResource(R.drawable.transparent) ;
				iv_destinationstrategy_download.setBackgroundResource(R.drawable.strategycatalog_complete) ;*/
				Toast.makeText(context, "恭喜您，下载成功！", Toast.LENGTH_SHORT).show() ;
			}
		}) ;
		return view ;
	}
}
