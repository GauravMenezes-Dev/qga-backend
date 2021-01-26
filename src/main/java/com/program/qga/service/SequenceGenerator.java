package com.program.qga.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.program.qga.sequence.DBSequence;

@Service
public class SequenceGenerator {
	@Autowired
	private MongoOperations mongoOps;
	
	
	public int getSeqNum(String seq_nm) {
		//get Sequence number
		Query query = new Query(Criteria.where("id").is(seq_nm));
		//update
		Update upd = new Update().inc("seqNo",1);
		//modify in document
		DBSequence counter = mongoOps.findAndModify(
				query, 
				upd, 
				options().returnNew(true).upsert(true), 
				DBSequence.class
		);
		return !Objects.isNull(counter) ? counter.getSeqNo() : 1;
	}
}
