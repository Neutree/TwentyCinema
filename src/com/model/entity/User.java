package com.model.entity;

public class User {
	String name;
	String password;
	String role;
	String enable;
	String backNews;
	public String getBackNews() {
		return backNews;
	}
	public void setBackNews(String backNews) {
		this.backNews = backNews;
	}
	public User()	{
		backNews="";
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", role="
				+ role + ", enable=" + enable + ", backNews=" + backNews + "]";
	}

}
