package com.cn.ncist.rq.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cn.ncist.rq.entity.QuestionInfo;
import com.cn.ncist.rq.lyglclient.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QuestionAdapter extends BaseAdapter {

	List<QuestionInfo> list = new ArrayList<QuestionInfo>() ;
	Context context ;
	
	public QuestionAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public QuestionAdapter(Context context) {
		super();
		this.context = context;
	}

	public void addDataToList(List<QuestionInfo> list) {
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
	public QuestionInfo getItem(int position) {
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
		View view = LayoutInflater.from(context).inflate(R.layout.inflater_listview_question, null) ;
		
		TextView tv_question_content = (TextView) view.findViewById(R.id.tv_question_content) ;		
		tv_question_content.setText(list.get(position).getQuestionContent()) ;
		
		return view;
	}
}
