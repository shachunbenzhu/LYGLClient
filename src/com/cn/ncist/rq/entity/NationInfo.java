package com.cn.ncist.rq.entity;

import java.io.Serializable;

import android.R.integer;

public class NationInfo implements Serializable {
	private int nationId;
	private String nationName;
	
	public int getNationId() {
		return nationId;
	}
	public void setNationId(int nationId) {
		this.nationId = nationId;
	}
	public String getNationName() {
		return nationName;
	}
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	
}
