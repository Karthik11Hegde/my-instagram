package com.imageservice.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.imageservice.model.Image;

public interface ImageRepository extends MongoRepository<Image, ObjectId> {

	abstract List<Image> findByUserID(String userID);
	abstract Image findByImageID(String imageID);
}
