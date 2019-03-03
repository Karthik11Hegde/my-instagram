package com.userservice.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

	@Id
	private String objID;
	@Indexed(unique = true)
	private String userID;
	private String name;
	private Date dob;
	private Boolean isMarried;
	private String place;
	private String profile_photo;

	public User(String userID, String name, Date dob, Boolean isMarried, String place, String profile_photo) {
		super();
		this.userID = userID;
		this.name = name;
		this.dob = dob;
		this.isMarried = isMarried;
		this.place = place;
		this.profile_photo = profile_photo;
	}

	public String getObjID() {
		return objID;
	}

	public void setObjID(String objID) {
		this.objID = objID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Boolean getIsMarried() {
		return isMarried;
	}

	public void setIsMarried(Boolean isMarried) {
		this.isMarried = isMarried;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getProfile_photo() {
		return profile_photo;
	}

	public void setProfile_photo(String profile_photo) {
		this.profile_photo = profile_photo;
	}

	@Override
	public String toString() {
		return "User details " + this.userID + " " + this.name + " " + this.place;
	}
}
