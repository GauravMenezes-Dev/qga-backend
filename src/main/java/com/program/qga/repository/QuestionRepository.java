package com.program.qga.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.program.qga.model.Question;

public interface QuestionRepository extends MongoRepository<Question, Integer> {

}
