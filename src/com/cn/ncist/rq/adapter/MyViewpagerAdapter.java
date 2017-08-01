package com.cn.ncist.rq.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyViewpagerAdapter extends PagerAdapter {
	
	
	private List<View> list = new ArrayList<View>();
	public void addDataToList(List<View> info){
		
		if(info!=null){
			list.addAll(info);
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		View v = list.get(position);
		container.addView(v);
		return v;
	}
	
	

}
