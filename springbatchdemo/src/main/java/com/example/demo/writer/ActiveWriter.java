package com.example.demo.writer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.ActiveUser;
import com.example.demo.model.User;
import com.example.demo.repository.ActiveUserRepository;
import com.example.demo.repository.UserRepository;

@Component
public class ActiveWriter implements ItemWriter<User>{

	@Autowired
	ActiveUserRepository userRepo;
	
	@Autowired
	UserRepository uRepo;
	
	@Override
	public void write(List<? extends User> items) throws Exception {
		// TODO Auto-generated method stub
		
		List<ActiveUser> newList = items.stream()
				.map(item -> new ActiveUser(item.getId(),item.getName(),item.getSalary(),item.getTime()))
				.collect(Collectors.toList());
//		for(User user: items) {
//			ActiveUser aUser = new ActiveUser(user.getId(),user.getName(),user.getSalary(),user.getTime());
//			userRepo.save(aUser);
//		}
		userRepo.saveAll(newList);
		uRepo.saveAll(items);
	}

}
