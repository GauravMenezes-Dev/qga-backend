package com.program.qga.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.program.qga.model.Question;
import com.program.qga.repository.QuestionRepository;
import com.program.qga.service.SequenceGenerator;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/questions")
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class QuestionController {

	private QuestionRepository qRepo;
	private SequenceGenerator sqs;
	
	@GetMapping("/getAllQuestions")
	public ResponseEntity<List<Question>> getAllItems(){
		List<Question> listQ = qRepo.findAll();
		return ResponseEntity.status(200).body(listQ);
	}
	
	@GetMapping("/getQuestionByRandom")
	public ResponseEntity<Question> getQuestionByRandom(){
		List<Question> listQ = qRepo.findAll();
		if(!listQ.isEmpty())
		{
			Random rand = new Random();
			Question randomElement = listQ.get(rand.nextInt(listQ.size()));
			return ResponseEntity.status(200).body(randomElement);
		}
		else
		{
			Question q = new Question(0, "Name one time where you really regretted doing something but it actually felt good.");
			return ResponseEntity.status(200).body(q);
		}
	}
	
	@PostMapping(path = "/addQuestion",
			consumes = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE 
						}, 
			produces = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE 
					 })
	public ResponseEntity<Question> addQuestion(@RequestBody Question question){
		Question addedQ = new Question();
		addedQ.setId(sqs.getSeqNum(Question.SEQ_NM));
		addedQ.setQuestion(question.getQuestion());
		qRepo.save(addedQ);
		return ResponseEntity.status(200).body(addedQ);
	}
	
	@PostMapping(path = "/updQuestion",
			consumes = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE 
						}, 
			produces = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE 
					 })
	public ResponseEntity<Question> updQuestion(@RequestBody Question question){
		Optional<Question> addedQ = qRepo.findById(question.getId());
		if(!addedQ.isEmpty()) {
			Question addedQues = addedQ.get();
			addedQues.setQuestion(question.getQuestion());
			return ResponseEntity.status(200).body(addedQues); 
		}
		return ResponseEntity.status(404).body(new Question());
	}

	@PostMapping(path = "/delQuestion",
			consumes = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE 
						}, 
			produces = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE 
		})
	public ResponseEntity<Question> delQuestion(@RequestBody Question question){
		Optional<Question> data = qRepo.findById(question.getId());
		if(data.isPresent())
		{	
			qRepo.deleteById(data.get().getId());
			return ResponseEntity.status(200).body(data.get());
		}
		else
			return ResponseEntity.status(404).body(new Question());
	}
	
}
