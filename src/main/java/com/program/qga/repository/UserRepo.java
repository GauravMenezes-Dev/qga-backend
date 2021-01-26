package com.program.qga.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.program.qga.model.UserModel;

public interface UserRepo extends MongoRepository<UserModel, String> {

}
