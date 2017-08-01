/**
 * 
 */
package com.cn.ncist.rq.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cn.ncist.rq.activity.MainActivity;
import com.cn.ncist.rq.foldingmenu.FoldingLayout;
import com.cn.ncist.rq.foldingmenu.OnFoldListener;
import com.cn.ncist.rq.lyglclient.R;

/**
 * @author 任倩
 * @date 2017-5-28
 * 应该在Activity中放置，OneNote中折叠书架菜单FoledingMenu项目
 *
 */
public class FoldingMenu {

	/************************************************折叠菜单**********************************************/
	private void handleAnimation(final View bar, final FoldingLayout foldingLayout, View parent, final View nextParent) {
        
        final ImageView arrow = (ImageView) parent.findViewWithTag("service_arrow");
        final int FOLD_ANIMATION_DURATION = 1000;
        
        foldingLayout.setFoldListener(new OnFoldListener() {
            
            @Override
            public void onStartFold(float foldFactor) {
                
                bar.setClickable(true);
                arrow.setBackgroundResource(R.drawable.common_icon_arrow_up_small);
                resetMarginToTop(foldingLayout, foldFactor, nextParent);
            }
            
            @Override
            public void onFoldingState(float foldFactor, float foldDrawHeight) {
                bar.setClickable(false);
                resetMarginToTop(foldingLayout, foldFactor, nextParent);
            }
            
            @Override
            public void onEndFold(float foldFactor) {
                
                bar.setClickable(true);
                arrow.setBackgroundResource(R.drawable.common_icon_arrow_down_small);
                resetMarginToTop(foldingLayout, foldFactor, nextParent);
            }
        });
        
        animateFold(foldingLayout, FOLD_ANIMATION_DURATION);
    }
    
    private void resetMarginToTop(View view, float foldFactor, View nextParent) {
        
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) nextParent.getLayoutParams();
        //lp.topMargin =(int)( - view.getMeasuredHeight() * foldFactor) + dp2px(MainActivity.this, 10);
        nextParent.setLayoutParams(lp);
    }
    
    private void setMarginToTop(float foldFactor, View nextParent) {
        
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) nextParent.getLayoutParams();
        //lp.topMargin =(int)( - dp2px(MainActivity.this, 135) * foldFactor) + dp2px(MainActivity.this, 10);
        nextParent.setLayoutParams(lp);
    }
    
    @SuppressLint("NewApi") 
    public void animateFold(FoldingLayout foldLayout, int duration) {
        float foldFactor = foldLayout.getFoldFactor();

        ObjectAnimator animator = ObjectAnimator.ofFloat(foldLayout,
                "foldFactor", foldFactor, foldFactor > 0 ? 0 : 1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(0);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }
    
    public final static int dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

}
