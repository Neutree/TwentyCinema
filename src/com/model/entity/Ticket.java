package com.model.entity;

public class Ticket {
	
	int number;
	String user_name;
	String session_number;
	String status;
	String sit_number;
	double price;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getSession_number() {
		return session_number;
	}
	public void setSession_number(String session_number) {
		this.session_number = session_number;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSit_number() {
		return sit_number;
	}
	public void setSit_number(String sit_number) {
		this.sit_number = sit_number;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Ticket [number=" + number + ", user_name=" + user_name
				+ ", session_number=" + session_number + ", status=" + status
				+ ", sit_number=" + sit_number + ", price=" + price + "]";
	}
}
