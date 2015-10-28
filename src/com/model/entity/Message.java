package com.model.entity;

public class Message {
	String backNews;

	public Message(String backNews) {
		this.backNews = backNews;
	}
	public Message() {
		this.backNews = "";
	}

	public String getBackNews() {
		return backNews;
	}

	public void setBackNews(String backNews) {
		this.backNews = backNews;
	}
	
}
