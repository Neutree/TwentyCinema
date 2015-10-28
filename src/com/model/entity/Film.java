package com.model.entity;

import java.util.Date;




public class Film {
	String name;
	String company;
	String director;
	String room_name;
	String number;
	double price;
	Date start_time;
	Date over_time;
	String introduction;
	String picture;
	String booked;
	public String getBooked() {
		return booked;
	}
	public void setBooked(String booked) {
		this.booked = booked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getOver_time() {
		return over_time;
	}
	public void setOver_time(Date over_time) {
		this.over_time = over_time;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	@Override
	public String toString() {
		return "Film [name=" + name + ", company=" + company + ", director="
				+ director + ", room_name=" + room_name + ", number=" + number
				+ ", price=" + price + ", start_time=" + start_time
				+ ", over_time=" + over_time + ", introduction=" + introduction
				+ ", picture=" + picture + "]";
	}
	
}
