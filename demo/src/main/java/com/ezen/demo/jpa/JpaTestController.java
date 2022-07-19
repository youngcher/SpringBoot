package com.ezen.demo.jpa;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/jpa")
public class JpaTestController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("")
	public String jpaTest() {
		return "JPA Test";
	}
	
	@GetMapping("/save")
	public ResponseEntity saveUser() {
		
		User user = new User();
		user.setUname("smith");
		user.setEmail("smith@gmail.com");
		
		User savedUser = userRepository.save(user); //레코드 추가
		
		boolean saved = user.getUname().equals(savedUser.getUname());
		
		logger.debug("num={}, uname={}, email={}", savedUser.getNum(), savedUser.getUname(), savedUser.getEmail());
		
		ResponseEntity<User> resEntity = new ResponseEntity<>(savedUser,HttpStatus.OK); //OK, NOT_FOUND
		
		return resEntity;
		//return new ResponseEntity<>(savedUser,HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<User>> getList() {
		List<User> list = userRepository.findAll();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	
	@GetMapping("/findbyid/{num}")
	public ResponseEntity<User> findById(@PathVariable("num")int num){
		
		
		Optional<User> op = userRepository.findById(num);
		
		User user = op.get();
		
		if(op.isEmpty()) {
			//검색실패
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<User>> findByUname(@PathVariable("name")String name){
		return new ResponseEntity<>(userRepository.findByUname(name),HttpStatus.OK);
	}
	
	@GetMapping("/nameEmail/{name}/{email}")
	public ResponseEntity<List<User>> findByName(@PathVariable("name")String name,
			@PathVariable("email")String email){
		return new ResponseEntity<>(userRepository.findByUnameAndEmail(name,email),HttpStatus.OK);
	}
	
	@GetMapping("/update/{num}/{name}/{email}")
	public ResponseEntity updateUser(@PathVariable("num")int num,
			@PathVariable("name")String name, @PathVariable("email")String email){
		
		User user = new User();
		user.setNum(num);
		user.setUname(name);
		user.setEmail(email);
		User savedUser = userRepository.save(user);
		ResponseEntity<User> resEntity = new ResponseEntity<>(savedUser,HttpStatus.OK);
		return resEntity;
	}
	
	@GetMapping("/delet/{num}")
	public String deleteById(@PathVariable("num")int num) {
		
		Optional<User> op = userRepository.findById(num);
		if(op.isPresent()) {
			userRepository.deleteById(num);
			return "삭제 성공";
		} else {
			return "삭제 실패";
		}
	}
	
	
	@GetMapping("/jpql/{start}/{end}")
	public ResponseEntity<List<User>> getAllBet(@PathVariable("start")int start,
			@PathVariable("end")int end){
		return new ResponseEntity<>(userRepository.findAllNumBet(start, end),HttpStatus.OK);
	}
	
	
}
