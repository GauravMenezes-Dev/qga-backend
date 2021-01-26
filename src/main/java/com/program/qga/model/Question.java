package com.program.qga.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Question {
	
	@Transient
	public static final String SEQ_NM = "q_seq";
	
	@Id
	private int id;
	private String question;
}
