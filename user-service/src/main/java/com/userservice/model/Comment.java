package com.userservice.model;

import org.bson.types.ObjectId;

public class Comment {

	private ObjectId _id;
	private String userID;
	private String comment;
	private Long likes;

	public Comment() {
		
	}
	
	public Comment(String userID, String comment, Long likes) {
		super();
		this.userID = userID;
		this.comment = comment;
		this.likes = likes;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

}
