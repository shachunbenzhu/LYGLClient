package com.cn.ncist.rq.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cn.ncist.rq.entity.CommentInfo;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class CommentListAdapter extends BaseAdapter {

	List<CommentInfo> list = new ArrayList<CommentInfo>() ;
	Context context ;
	
	public CommentListAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public CommentListAdapter(Context context) {
		super();
		this.context = context;
	}

	public void addDataToList(List<CommentInfo> list) {
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
	public CommentInfo getItem(int position) {
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
		View view = LayoutInflater.from(context).inflate(R.layout.inflater_listview_comment, null) ;
		
		TextView tv_comment_username = (TextView) view.findViewById(R.id.tv_comment_username) ;	
		TextView tv_comment_date = (TextView) view.findViewById(R.id.tv_comment_date) ;	
		ImageView iv_comment_tx = (ImageView) view.findViewById(R.id.iv_comment_tx) ;
		Button bt_comment_repay = (Button) view.findViewById(R.id.bt_comment_repay) ;
		TextView tv_comment_content = (TextView) view.findViewById(R.id.tv_comment_content) ;

		LinearLayout ll_comment_repay = (LinearLayout) view.findViewById(R.id.ll_comment_repay) ;
		TextView tv_comment_respdate = (TextView) view.findViewById(R.id.tv_comment_respdate) ;	
		TextView tv_comment_respcontent = (TextView) view.findViewById(R.id.tv_comment_respcontent) ;	
		
		CommentInfo commentInfo = list.get(position) ;
		tv_comment_username.setText(commentInfo.getCommentUser()) ;
		tv_comment_date.setText(commentInfo.getCommentDate()) ;
		iv_comment_tx.setBackgroundResource(Tools.getImageResId(Tools.formatString(commentInfo.getUserTx()))) ;
		tv_comment_content.setText(commentInfo.getCommentContent()) ;
		tv_comment_respdate.setText(commentInfo.getResponseDate()) ;
		tv_comment_respcontent.setText(commentInfo.getResponseContent()) ;
		
		if (commentInfo.getResponseContent() == null || commentInfo.getResponseContent().equals("")) {
			ll_comment_repay.setVisibility(View.GONE) ;
		} else {
			ll_comment_repay.setVisibility(View.VISIBLE) ;
		}
		return view;
	}
}
