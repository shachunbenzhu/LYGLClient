package com.cn.ncist.rq.entity;

import java.io.Serializable;

import android.R.integer;

public class ProvinceInfo implements Serializable {
	private int provinceId;
	private String provinceName;
	
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	
}
