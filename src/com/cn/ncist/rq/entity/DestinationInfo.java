package com.cn.ncist.rq.entity;

import java.io.Serializable;

import android.R.integer;

public class DestinationInfo implements Serializable {
	private int destinationId ;
	private String destinationName ;
	private String destinationPinyin ;
	private int destinationNumber ;
	private String destinationImg ;
	private String destinationImgarr ;
	private String destinationDesc ;
	
	private String nationName ;
	private String provinceName ;
	
	public int getDestinationId() {
		return destinationId;
	}
	public void setDestinationId(int destinationId) {
		this.destinationId = destinationId;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getDestinationPinyin() {
		return destinationPinyin;
	}
	public void setDestinationPinyin(String destinationPinyin) {
		this.destinationPinyin = destinationPinyin;
	}
	public int getDestinationNumber() {
		return destinationNumber;
	}
	public void setDestinationNumber(int destinationNumber) {
		this.destinationNumber = destinationNumber;
	}
	public String getDestinationImg() {
		return destinationImg;
	}
	public void setDestinationImg(String destinationImg) {
		this.destinationImg = destinationImg;
	}
	public String getDestinationImgarr() {
		return destinationImgarr;
	}
	public void setDestinationImgarr(String destinationImgarr) {
		this.destinationImgarr = destinationImgarr;
	}
	public String getDestinationDesc() {
		return destinationDesc;
	}
	public void setDestinationDesc(String destinationDesc) {
		this.destinationDesc = destinationDesc;
	}
	public String getNationName() {
		return nationName;
	}
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
}
