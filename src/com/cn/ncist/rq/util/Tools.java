package com.cn.ncist.rq.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cn.ncist.rq.lyglclient.R;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Tools {

	public Tools() {
		// TODO Auto-generated constructor stub
	}
	
	public static double stringToDouble(String string) {
		return Double.parseDouble(string) ;
	}
	
	/**
	 * 根据图片名称获取资源ID
	 * @param imageName
	 * @return
	 */
	public static int getImageResId(String imageName) {
		int resId = 0 ;				// 图片资源Id
		Class draw = R.drawable.class;
		try {
		     Field field = draw.getDeclaredField(imageName);
		     resId = field.getInt(imageName);
		} catch (Exception e) {
		     e.printStackTrace();
		}
		
		/*int resId = Tools.getImageResId(list.get(position).getTypeImageName()) ;
		Drawable imageLeft ;
		imageLeft = context.getResources().getDrawable(resId) ;
		typename_chart_tv.setCompoundDrawablesWithIntrinsicBounds(imageLeft, null, null, null) ;*/
		
		return resId ;
	}
	
	public static String formatString(String str) {
		int start = str.lastIndexOf("/") ;
		int end ;
		if (start != -1) {
			end = str.indexOf(".", start) ;
			if (end != -1) {
				str = str.substring(start + 1 , end) ;
			} else {
				str = str.substring(start + 1) ;
			}
		} else {
			end = str.indexOf(".") ;
			if (end != -1) {
				str = str.substring(0 , end) ;
			}
		}
		return str ;
	}
	
	/**
	 * 获取当前年
	 * @return
	 */
	public static String getCurrentDateWithYear() {
		Date date = (Date) new Date() ;
		SimpleDateFormat sm = new SimpleDateFormat("yyyy年") ;
		return sm.format(date) ;
	}
	
	/**
	 * 获取当前月
	 * @return
	 */
	public static String getCurrentDateWithMonth() {
		Date date = (Date) new Date() ;
		SimpleDateFormat sm = new SimpleDateFormat("MM月") ;
		return sm.format(date) ;
	}
	
	/**
	 * 获取当前月日
	 * @return
	 */
	public static String getCurrentDateWithMonthAndDay() {
		Date date = (Date) new Date() ;
		SimpleDateFormat sm = new SimpleDateFormat("MM月dd日") ;
		return sm.format(date) ;
	}
	
	/**
	 * 获取当前年月
	 * @return
	 */
	public static String getCurrentDateWithYearAndMonth() {
		Date date = (Date) new Date() ;
		SimpleDateFormat sm = new SimpleDateFormat("yyyy年MM月") ;
		return sm.format(date) ;
	}
	
	public static String getCurrentDateWithYearMonthDay() {
		Date date = (Date) new java.util.Date() ;
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd") ;
		return sm.format(date) ;
	}
	
	/**
	 * 获取当前年月日
	 * @return
	 */
	public static String getCurrentDateWithAll() {
		Date date = (Date) new java.util.Date() ;
		SimpleDateFormat sm = new SimpleDateFormat("yyyy年MM月dd日") ;
		return sm.format(date) ;
	}
	
	public static String getCurrentDateWithAllTime() {
		Date date = (Date) new java.util.Date() ;
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss") ;
		return sm.format(date) ;
	}
	
	/**
	 * 获取当前年月日时间
	 * @return
	 */
	public static String getCurrentDateWithAllAndTime() {
		Date date = (Date) new java.util.Date() ;
		SimpleDateFormat sm = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss") ;
		return sm.format(date) ;
	}
	
	/**
	 * 获取上一年的日期
	 * @param date
	 * @return
	 */
	public static String getPreviousYear(String date) {
		//System.out.println(date + "111ccc");
		String dateString = null ;
		SimpleDateFormat sm = new SimpleDateFormat("yyyy年") ;
		try {
			Date d = sm.parse(date) ;
			
			Calendar calendar = Calendar.getInstance() ;
			calendar.setTime(d) ;
			calendar.add(Calendar.YEAR, -1) ;		// 0表示不更改，-1表示减1
			calendar.add(Calendar.MONTH, 0) ;
			calendar.add(Calendar.DAY_OF_MONTH, 0) ;
			
			dateString = sm.format(calendar.getTime()) ;
			//System.out.println(dateString + "111ddd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateString;
		
	}
	
	/**
	 * 获取下一年的日期
	 * @param date
	 * @return
	 */
	public static String getNextYear(String date) {
		
		String dateString = null ;
		SimpleDateFormat sm = new SimpleDateFormat("yyyy年") ;
		try {
			Date d = sm.parse(date) ;
			
			Calendar calendar = Calendar.getInstance() ;
			calendar.setTime(d) ;
			calendar.add(Calendar.YEAR, 1) ;		
			calendar.add(Calendar.MONTH, 0) ;
			calendar.add(Calendar.DAY_OF_MONTH, 0) ;
			
			dateString = sm.format(calendar.getTime()) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateString;
	}
	
	
	/**
	 * 获取上一月的日期
	 * @param date
	 * @return
	 */
	public static String getPreviousMonth(String date) {
		String dateString = null ;
		SimpleDateFormat sm = new SimpleDateFormat("yyyy年MM月") ;
		try {
			Date d = sm.parse(date) ;
			
			Calendar calendar = Calendar.getInstance() ;
			calendar.setTime(d) ;
			calendar.add(Calendar.YEAR, 0) ;		// 0表示不更改，-1表示减1
			calendar.add(Calendar.MONTH, -1) ;
			calendar.add(Calendar.DAY_OF_MONTH, 0) ;
			
			dateString = sm.format(calendar.getTime()) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateString;
		
	}
	
	/**
	 * 获取下一月的日期
	 * @param date
	 * @return
	 */
	public static String getNextMonth(String date) {
		
		String dateString = null ;
		SimpleDateFormat sm = new SimpleDateFormat("yyyy年MM月") ;
		try {
			Date d = sm.parse(date) ;
			
			Calendar calendar = Calendar.getInstance() ;
			calendar.setTime(d) ;
			calendar.add(Calendar.YEAR, 0) ;		// 0表示不更改，-1表示减1
			calendar.add(Calendar.MONTH, 1) ;
			calendar.add(Calendar.DAY_OF_MONTH, 0) ;
			
			dateString = sm.format(calendar.getTime()) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateString;
	}
	
	/**
	 * 获取上一天的日期
	 * @param date
	 * @return
	 */
	public static String getPreviousDay(String date) {
		String dateString = null ;
		SimpleDateFormat sm = new SimpleDateFormat("yyyy年MM月dd日") ;
		try {
			Date d = sm.parse(date) ;
			
			Calendar calendar = Calendar.getInstance() ;
			calendar.setTime(d) ;
			calendar.add(Calendar.YEAR, 0) ;		// 0表示不更改，-1表示减1
			calendar.add(Calendar.MONTH, 0) ;
			calendar.add(Calendar.DAY_OF_MONTH, -1) ;
			
			dateString = sm.format(calendar.getTime()) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateString;
		
	}
	
	/**
	 * 获取下一天的日期
	 * @param date
	 * @return
	 */
	public static String getNextDay(String date) {
		
		String dateString = null ;
		SimpleDateFormat sm = new SimpleDateFormat("yyyy年MM月dd日") ;
		try {
			Date d = sm.parse(date) ;
			
			Calendar calendar = Calendar.getInstance() ;
			calendar.setTime(d) ;
			calendar.add(Calendar.YEAR, 0) ;		// 0表示不更改，-1表示减1
			calendar.add(Calendar.MONTH, 0) ;
			calendar.add(Calendar.DAY_OF_MONTH, 1) ;
			
			dateString = sm.format(calendar.getTime()) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateString;
	}
	
	public static int dip2px(Context context, float dipValue){              
        final float scale = context.getResources().getDisplayMetrics().density;                   
        return (int)(dipValue * scale + 0.5f);           
    }  
	
    public static int px2dip(Context context, float pxValue){                  
        final float scale = context.getResources().getDisplayMetrics().density;                   
        return (int)(pxValue / scale + 0.5f);           
    }   
	
}
