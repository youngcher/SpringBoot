package com.ezen.demo.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	public boolean addPerson(Person p) {
		Person p2 = personRepository.save(p);
		return p2.getNum() > 0;
	}
	
}
