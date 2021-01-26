package com.program.qga.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.program.qga.sequence.DBSequence;

public interface DBSequenceRepo extends MongoRepository<DBSequence, String> {

}
