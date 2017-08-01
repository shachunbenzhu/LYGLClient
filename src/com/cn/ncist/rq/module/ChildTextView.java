package com.cn.ncist.rq.module;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class ChildTextView extends TextView {

	public ChildTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ChildTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ChildTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void setTypeface(Typeface tf, int style) {
        if (style == Typeface.BOLD) {
             super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/STKAITI.TTF"));
         } else {
            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/STKAITI.TTF"));
         }
   }

}