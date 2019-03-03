package com.imageservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.imageservice.model.Comment;
import com.imageservice.model.Image;
import com.imageservice.repository.ImageRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

@RestController
@RequestMapping("/api")
public class ImageController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	GridFsOperations gridFsOperations;

	@Autowired
	MongoOperations mongoOperations;

	@GetMapping("/image/all")
	public List<Image> getAllImages() {
		return imageRepository.findAll();
	}

	@GetMapping("/image/user/{userID}")
	public List<Image> getAllImagesOfUser(@PathVariable("userID") String userID) {
		return imageRepository.findByUserID(userID);
	}

	@GetMapping("/image")
	public @ResponseBody ResponseEntity<byte[]> getImage(@RequestParam("imageID") String imageID)
			throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(imageID));
		GridFSFile gridfsFile = gridFsOperations.findOne(query);
		if(gridfsFile == null) {
			throw new Exception("Profile Image is not set");
		}

		GridFsResource resource = gridFsOperations.getResource(gridfsFile);
		byte[] copyToByteArray = StreamUtils.copyToByteArray(resource.getInputStream());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(copyToByteArray, headers, HttpStatus.OK);
	}

	@PostMapping("/image/upload")
	public Image uploadImage(@RequestParam("file") MultipartFile multipartFile, @RequestParam("userID") String userID,
			@RequestParam("description") String description, @RequestParam("isProfile") boolean isProfile)
			throws IOException {
		DBObject metadata = new BasicDBObject();
		metadata.put("type", multipartFile.getContentType());

		ObjectId objectId = gridFsOperations.store(multipartFile.getInputStream(), multipartFile.getOriginalFilename(),
				metadata);

		Image image = new Image(userID, objectId, description, isProfile, 0L, new ArrayList<>());
		return imageRepository.insert(image);
	}

	@PostMapping("/image/like")
	public Image likeImage(@RequestParam("imageID") ObjectId objectId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("imageID").is(objectId));

		Image image = mongoOperations.findOne(query, Image.class);
		image.setLikes(image.getLikes() + 1);
		return mongoOperations.save(image);
	}

	@PostMapping("/image/comment")
	public Image postComment(@RequestParam("imageID") ObjectId objectId, @RequestParam("userID") String userID,
			@RequestParam("comment") String comment) {
		Query query = new Query();
		query.addCriteria(Criteria.where("imageID").is(objectId));

		Comment commentObj = new Comment(userID, comment, 0L);
		commentObj.set_id(new ObjectId());

		Image image = mongoOperations.findOne(query, Image.class);
		image.getComments().add(commentObj);
		return mongoOperations.save(image);
	}

	@PostMapping("/image/comment/like")
	public void likeComment(@RequestParam("commentID") ObjectId commentID) {
		Query query = new Query();
		query.addCriteria(Criteria.where("comments._id").is(commentID));

		Image comment = mongoOperations.findOne(query, Image.class);

//		Comment findByCommentID = commentRepository.findByCommentID(commentID);
//		System.out.println(findByCommentID);

//		 mongoOperations.save(comment);
	}

}
