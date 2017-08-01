package com.cn.ncist.rq.adapter;

import java.util.ArrayList;

import com.cn.ncist.rq.activity.AlbumActivity;
import com.cn.ncist.rq.activity.ShowAllPhoto;
import com.cn.ncist.rq.util.BitmapCache;
import com.cn.ncist.rq.util.BitmapCache.ImageCallback;
import com.cn.ncist.rq.util.ImageItem;
import com.cn.ncist.rq.util.Res;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * �������ʾ���а���ͼƬ���ļ��е�������
 *
 * @author king
 * @QQ:595163260
 * @version 2014��10��18��  ����11:49:44
 */
public class FolderAdapter extends BaseAdapter {

	private Context mContext;
	private Intent mIntent;
	private DisplayMetrics dm;
	BitmapCache cache;
	final String TAG = getClass().getSimpleName();
	public FolderAdapter(Context c) {
		cache = new BitmapCache();
		init(c);
	}

	// ��ʼ��
	public void init(Context c) {
		mContext = c;
		mIntent = ((Activity) mContext).getIntent();
		dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
	}

	

	@Override
	public int getCount() {
		return AlbumActivity.contentList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	ImageCallback callback = new ImageCallback() {
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap,
				Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals((String) imageView.getTag())) {
					((ImageView) imageView).setImageBitmap(bitmap);
				} else {
					Log.e(TAG, "callback, bmp not match");
				}
			} else {
				Log.e(TAG, "callback, bmp null");
			}
		}
	};

	private class ViewHolder {
		//
		public ImageView backImage;
		// ����
		public ImageView imageView;
		public ImageView choose_back;
		// �ļ�������
		public TextView folderName;
		// �ļ��������ͼƬ����
		public TextView fileNum;
	}
	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					Res.getLayoutID("plugin_camera_select_folder"), null);
			holder = new ViewHolder();
			holder.backImage = (ImageView) convertView
					.findViewById(Res.getWidgetID("file_back"));
			holder.imageView = (ImageView) convertView
					.findViewById(Res.getWidgetID("file_image"));
			holder.choose_back = (ImageView) convertView
					.findViewById(Res.getWidgetID("choose_back"));
			holder.folderName = (TextView) convertView.findViewById(Res.getWidgetID("name"));
			holder.fileNum = (TextView) convertView.findViewById(Res.getWidgetID("filenum"));
			holder.imageView.setAdjustViewBounds(true);
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,dipToPx(65)); 
//			lp.setMargins(50, 0, 50,0); 
//			holder.imageView.setLayoutParams(lp);
			holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		String path;
		if (AlbumActivity.contentList.get(position).imageList != null) {
			
			//path = photoAbsolutePathList.get(position);
			//����ͼƬ·��
			path = AlbumActivity.contentList.get(position).imageList.get(0).imagePath;
			// ��folderName����ֵΪ�ļ�������
			//holder.folderName.setText(fileNameList.get(position));
			holder.folderName.setText(AlbumActivity.contentList.get(position).bucketName);
			
			// ��fileNum�����ļ�����ͼƬ����
			//holder.fileNum.setText("" + fileNum.get(position));
			holder.fileNum.setText("" + AlbumActivity.contentList.get(position).count);
			
		} else
			path = "android_hybrid_camera_default";
		if (path.contains("android_hybrid_camera_default"))
			holder.imageView.setImageResource(Res.getDrawableID("plugin_camera_no_pictures"));
		else {
//			holder.imageView.setImageBitmap( AlbumActivity.contentList.get(position).imageList.get(0).getBitmap());
			final ImageItem item = AlbumActivity.contentList.get(position).imageList.get(0);
			holder.imageView.setTag(item.imagePath);
			cache.displayBmp(holder.imageView, item.thumbnailPath, item.imagePath,
					callback);
		}
		// Ϊ������Ӽ���
		holder.imageView.setOnClickListener(new ImageViewClickListener(
				position, mIntent,holder.choose_back));
		
		return convertView;
	}

	// Ϊÿһ���ļ��й����ļ�����
	private class ImageViewClickListener implements OnClickListener {
		private int position;
		private Intent intent;
		private ImageView choose_back;
		public ImageViewClickListener(int position, Intent intent,ImageView choose_back) {
			this.position = position;
			this.intent = intent;
			this.choose_back = choose_back;
		}
		
		public void onClick(View v) {
			ShowAllPhoto.dataList = (ArrayList<ImageItem>) AlbumActivity.contentList.get(position).imageList;
			Intent intent = new Intent();
			String folderName = AlbumActivity.contentList.get(position).bucketName;
			intent.putExtra("folderName", folderName);
			intent.setClass(mContext, ShowAllPhoto.class);
			mContext.startActivity(intent);
			choose_back.setVisibility(v.VISIBLE);
		}
	}

	public int dipToPx(int dip) {
		return (int) (dip * dm.density + 0.5f);
	}

}
