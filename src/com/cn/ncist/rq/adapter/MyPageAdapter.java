package com.cn.ncist.rq.adapter;

import java.util.ArrayList;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyPageAdapter extends PagerAdapter {

	ArrayList<View> arrayList ;
	
	public MyPageAdapter() {
		super();
	}

	public MyPageAdapter(ArrayList<View> arrayList) {
		super();
		this.arrayList = arrayList;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object ;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position,
			Object object) {
		container.removeView(arrayList.get(position)) ;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(arrayList.get(position)) ;
		return arrayList.get(position) ;
	}

}
