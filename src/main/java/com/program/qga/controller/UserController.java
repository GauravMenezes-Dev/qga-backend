package com.program.qga.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.program.qga.model.CreateUserRespMod;
import com.program.qga.model.LoginUserRequestModel;
import com.program.qga.model.LoginUserResponseModel;
import com.program.qga.model.UserModel;
import com.program.qga.repository.UserRepo;
import com.program.qga.service.Pbkdf2Service;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class UserController {

	private UserRepo uRepo;
	private Pbkdf2Service passComparer;
	
	@PostMapping("/login")
	public ResponseEntity<LoginUserResponseModel> login(@RequestBody LoginUserRequestModel userData){
		Optional<UserModel> u = uRepo.findById(userData.getUsername());
		LoginUserResponseModel returnData = new LoginUserResponseModel();
		if(u.isEmpty())
		{
			
			returnData.setMessage("User not found. Please try again.");
			return ResponseEntity.status(404).body(returnData);
		}
		else {
			if(passComparer.compare(userData.getPassword(), u.get().getPassword())) {
				returnData.setUsername(u.get().getUsername());
				returnData.setRole(u.get().getRole());
				returnData.setMessage("Success!");
				return ResponseEntity.status(200).body(returnData);
			}
			else {
				returnData.setMessage("Credentials incorrect. Try again");
				return ResponseEntity.status(404).body(returnData);
			}
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<CreateUserRespMod> createUser(@RequestBody UserModel user){
		UserModel userSave = new UserModel();
		Optional<UserModel> jic = uRepo.findById(user.getUsername());
		CreateUserRespMod crm = new CreateUserRespMod();
		if(jic.isEmpty()) {
			userSave.setPassword(passComparer.encodeIt(user.getPassword()));
			userSave.setUsername(user.getUsername());
			userSave.setRole(user.getRole());
			UserModel savedUser = uRepo.save(userSave);
			crm.setUsername(savedUser.getUsername());
			crm.setRole(savedUser.getRole());
			crm.setMessage("Success");
			return ResponseEntity.status(200).body(crm);
		}
		else {
			crm.setMessage("User already exists");
			return ResponseEntity.status(404).body(crm);
		}
	}
	
}
