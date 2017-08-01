package com.cn.ncist.rq.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;

/**
 * ����R�ļ����������
 * 
 * @author king
 * @QQ:595163260
 * @version 2014��10��18��  ����11:46:29
 */
public class Res {

	// �ļ�·����
	private static String pkgName;
	// R�ļ��Ķ���
	private static Resources resources;

	// ��ʼ���ļ���·����R��Դ
	public static void init(Context context) {
		pkgName = context.getPackageName();
		resources = context.getResources();
	}

	/**
	 * layout�ļ����µ�xml�ļ�id��ȡ
	 * 
	 */
	public static int getLayoutID(String layoutName) {
		return resources.getIdentifier(layoutName, "layout", pkgName);
	}

	// ��ȡ���ؼ���ID
	public static int getWidgetID(String widgetName) {
		return resources.getIdentifier(widgetName, "id", pkgName);
	}

	/**
	 * anim�ļ����µ�xml�ļ�id��ȡ
	 * 
	 */
	public static int getAnimID(String animName) {
		return resources.getIdentifier(animName, "anim", pkgName);
	}

	/**
	 * xml�ļ�����id��ȡ
	 * 
	 */
	public static int getXmlID(String xmlName) {
		return resources.getIdentifier(xmlName, "xml", pkgName);
	}

	// ��ȡxml�ļ�
	public static XmlResourceParser getXml(String xmlName) {
		int xmlId = getXmlID(xmlName);
		return (XmlResourceParser) resources.getXml(xmlId);
	}

	/**
	 * raw�ļ�����id��ȡ
	 * 
	 */
	public static int getRawID(String rawName) {
		return resources.getIdentifier(rawName, "raw", pkgName);
	}

	/**
	 * drawable�ļ������ļ���id
	 * 
	 */
	public static int getDrawableID(String drawName) {
		return resources.getIdentifier(drawName, "drawable", pkgName);
	}

	// ��ȡ��Drawable�ļ�
	public static Drawable getDrawable(String drawName) {
		int drawId = getDrawableID(drawName);
		return resources.getDrawable(drawId);
	}

	/**
	 * value�ļ���
	 * 
	 */
	// ��ȡ��value�ļ����µ�attr.xml���Ԫ�ص�id
	public static int getAttrID(String attrName) {
		return resources.getIdentifier(attrName, "attr", pkgName);
	}

	// ��ȡ��dimen.xml�ļ����Ԫ�ص�id
	public static int getDimenID(String dimenName) {
		return resources.getIdentifier(dimenName, "dimen", pkgName);
	}

	// ��ȡ��color.xml�ļ����Ԫ�ص�id
	public static int getColorID(String colorName) {
		return resources.getIdentifier(colorName, "color", pkgName);
	}

	// ��ȡ��color.xml�ļ����Ԫ�ص�id
	public static int getColor(String colorName) {
		return resources.getColor(getColorID(colorName));
	}

	// ��ȡ��style.xml�ļ����Ԫ��id
	public static int getStyleID(String styleName) {
		return resources.getIdentifier(styleName, "style", pkgName);
	}

	// ��ȡ��String.xml�ļ����Ԫ��id
	public static int getStringID(String strName) {
		return resources.getIdentifier(strName, "string", pkgName);
	}

	// ��ȡ��String.xml�ļ����Ԫ��
	public static String getString(String strName) {
		int strId = getStringID(strName);
		return resources.getString(strId);
	}

	// ��ȡcolor.xml�ļ����integer-arrayԪ��
	public static int[] getInteger(String strName) {
		return resources.getIntArray(resources.getIdentifier(strName, "array",
				pkgName));
	}

}
