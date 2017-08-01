package com.cn.ncist.rq.entity;

import java.io.Serializable;

import android.R.integer;

public class NoteInfo implements Serializable {
	private Integer note_id ;
	private String note_title ;
	private String note_date ;
	private Integer note_days ;
	private String note_expend ;
	private String note_partner ;
	private Integer note_zannumber ;
	private Integer note_seenumber ;
	private Integer note_commentnumber ;
	private Integer user_id ;
	private String note_bgimg ;
	private String note_img ;
	private String note_address ;
	private String note_content ;
	
	private String user_name ;
	private String user_touxiang ;
	public Integer getNote_id() {
		return note_id;
	}
	public void setNote_id(Integer noteId) {
		note_id = noteId;
	}
	public String getNote_title() {
		return note_title;
	}
	public void setNote_title(String noteTitle) {
		note_title = noteTitle;
	}
	public String getNote_date() {
		return note_date;
	}
	public void setNote_date(String noteDate) {
		note_date = noteDate;
	}
	public Integer getNote_days() {
		return note_days;
	}
	public void setNote_days(Integer noteDays) {
		note_days = noteDays;
	}
	public String getNote_expend() {
		return note_expend;
	}
	public void setNote_expend(String note_expend) {
		this.note_expend = note_expend;
	}
	public String getNote_partner() {
		return note_partner;
	}
	public void setNote_partner(String notePartner) {
		note_partner = notePartner;
	}
	public Integer getNote_zannumber() {
		return note_zannumber;
	}
	public void setNote_zannumber(Integer noteZannumber) {
		note_zannumber = noteZannumber;
	}
	public Integer getNote_seenumber() {
		return note_seenumber;
	}
	public void setNote_seenumber(Integer noteSeenumber) {
		note_seenumber = noteSeenumber;
	}
	public Integer getNote_commentnumber() {
		return note_commentnumber;
	}
	public void setNote_commentnumber(Integer noteCommentnumber) {
		note_commentnumber = noteCommentnumber;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer userId) {
		user_id = userId;
	}
	public String getNote_bgimg() {
		return note_bgimg;
	}
	public void setNote_bgimg(String noteBgimg) {
		note_bgimg = noteBgimg;
	}
	public String getNote_img() {
		return note_img;
	}
	public void setNote_img(String noteImg) {
		note_img = noteImg;
	}
	public String getNote_address() {
		return note_address;
	}
	public void setNote_address(String noteAddress) {
		note_address = noteAddress;
	}
	public String getNote_content() {
		return note_content;
	}
	public void setNote_content(String noteContent) {
		note_content = noteContent;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getUser_touxiang() {
		return user_touxiang;
	}
	public void setUser_touxiang(String userTouxiang) {
		user_touxiang = userTouxiang;
	}
	
}
