package com.model.entity;

public class LogClass {
	String name;
	String password;
	String backNews;
	boolean isSucced;
	public LogClass() {
		backNews="";
		isSucced=false;
	}
	public boolean isSucced() {
		return isSucced;
	}
	public void setSucced(boolean isSucced) {
		this.isSucced = isSucced;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBackNews() {
		return backNews;
	}
	public void setBackNews(String backNews) {
		this.backNews = backNews;
	}
	
}
