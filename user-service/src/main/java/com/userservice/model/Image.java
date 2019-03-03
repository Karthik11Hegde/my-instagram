package com.userservice.model;

import java.util.List;

import org.bson.types.ObjectId;

public class Image {

	private ObjectId _id;
	private String userID;
	private ObjectId imageID;
	private String description;
	private Boolean isProfile;
	private Long likes;
	private List<Comment> comments;

	public Image() {

	}

	public Image(String userID, ObjectId imageID, String description, Boolean isProfile, Long likes,
			List<Comment> comments) {
		super();
		this.userID = userID;
		this.imageID = imageID;
		this.description = description;
		this.isProfile = isProfile;
		this.likes = likes;
		this.comments = comments;
	}

	public Image(ObjectId _id, String userID, ObjectId imageID, String description, Boolean isProfile, Long likes,
			List<Comment> comments) {
		super();
		this._id = _id;
		this.userID = userID;
		this.imageID = imageID;
		this.description = description;
		this.isProfile = isProfile;
		this.likes = likes;
		this.comments = comments;
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

	public ObjectId getImageID() {
		return imageID;
	}

	public void setImageID(ObjectId imageID) {
		this.imageID = imageID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsProfile() {
		return isProfile;
	}

	public void setIsProfile(Boolean isProfile) {
		this.isProfile = isProfile;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
