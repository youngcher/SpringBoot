package com.ezen.demo.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService 
{
	@Autowired
	private PersonRepository repository;
	
	@Autowired
	private RequestMenuRepository reqRepository;
	
	public boolean save(Person p)
	{
		Person p2 = repository.save(p);
		return p2.getNum() > 0;
	}
	
	public boolean saveAll(List<RequestMenu> list)
	{
		reqRepository.saveAll(list);
		return true;
	}
	
	public List<Person> findAll()
	{
		return repository.findAll();
	}
	
	public Person findById(long num)
	{
		return repository.findById(num).get();
	}
	
	public boolean update(Person p)
	{
		return false;
	}
	
	public boolean delete(long num)
	{
		return false;
	}
}
