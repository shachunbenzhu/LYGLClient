package com.cn.ncist.rq.module;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 
 * @author ��ٻ
 * @date 2017-5-29
 *
 */
public class DestinationNextGridView extends GridView {

	//private boolean haveScrollbar = true ; 
	
	public DestinationNextGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DestinationNextGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DestinationNextGridView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	//�����Ƿ���ScrollBar����Ҫ��ScollView����ʾʱ��Ӧ������Ϊfalse�� Ĭ��Ϊ true
	/*public void setHaveScrollbar(boolean haveScrollbar) {   
        this.haveScrollbar = haveScrollbar ;   
    } */
	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
        //if (haveScrollbar == false) {   
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST) ;   
            super.onMeasure(widthMeasureSpec, expandSpec) ;   
        /*} else {   
            super.onMeasure(widthMeasureSpec, heightMeasureSpec) ;   
        }  */ 
    }  

}
