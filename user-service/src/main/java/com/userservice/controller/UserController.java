package com.userservice.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.userservice.model.Image;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	DiscoveryClient discoveryClient;

	@PostMapping("/user")
	public User registerUser(@RequestBody User user) {
		try {
			return userRepository.insert(user);
		} catch (DuplicateKeyException duplicateKeyException) {
			throw new DuplicateKeyException("userID selected is already present");
		}
	}

	@GetMapping("/user/all")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/user/all/{userID}")
	public User getUser(@PathVariable(value = "userID") String userID) {
		return userRepository.findByUserID(userID);
	}

	@GetMapping("/user/profileimage")
	public ResponseEntity<byte[]> getImage(@RequestParam(value = "userID") String userID) {
		User user = userRepository.findByUserID(userID);
		String profile_photo = user.getProfile_photo();

		List<ServiceInstance> list = discoveryClient.getInstances("image-service");
		String url = list.get(0).getUri().toString() + "/api/image";
		System.out.println(url);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("imageID", profile_photo);

		System.out.println(profile_photo);

		ResponseEntity<byte[]> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
				byte[].class);
//		ResponseEntity<InputStream> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, InputStream.class);
		System.out.println("response body " + response);

		return response;
	}

	@DeleteMapping("/user")
	public void deleteUser(@RequestBody User user) {
		userRepository.delete(user);
	}

	@PostMapping("/user/profileimage")
	public User postProfileImage(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("userID") String userID, @RequestParam("description") String description) throws IOException {
//		String url = "http://localhost:8002/api/image/upload";
		List<ServiceInstance> list = discoveryClient.getInstances("image-service");
		String url = list.get(0).getUri().toString() + "/api/image/upload";

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
//		body.put("file", Arrays.asList(multipartFile.getResource()));
//		body.put("userID", Arrays.asList(userID));
//		body.put("description", Arrays.asList(description));
//		body.put("isProfile", Arrays.asList(true));
		body.add("file", multipartFile.getResource());
		body.add("userID", userID);
		body.add("description", description);
		body.add("isProfile", true);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		ResponseEntity<Image> response = restTemplate.postForEntity(url, requestEntity, Image.class);

		ObjectId imageID = response.getBody().getImageID();

		User user = getUser(userID);
		user.setProfile_photo(imageID.toString());
		return mongoOperations.save(user);
	}

}
