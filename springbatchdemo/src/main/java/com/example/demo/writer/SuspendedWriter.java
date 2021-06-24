package com.example.demo.writer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.InactiveUser;
import com.example.demo.model.SuspendedUser;
import com.example.demo.model.User;
import com.example.demo.repository.InactiveUserRepository;
import com.example.demo.repository.SuspendedUserRepository;

@Component
public class SuspendedWriter implements ItemWriter<User>{

	
	@Autowired
	SuspendedUserRepository userRepo;
	
	@Override
	public void write(List<? extends User> items) throws Exception {
		// TODO Auto-generated method stub
		List<SuspendedUser> newList = items.stream()
				.map(item -> new SuspendedUser(item.getId(),item.getName(),item.getSalary(),item.getTime()))
				.collect(Collectors.toList());
//		for(User user: items) {
//			ActiveUser aUser = new ActiveUser(user.getId(),user.getName(),user.getSalary(),user.getTime());
//			userRepo.save(aUser);
//		}
		userRepo.saveAll(newList);
	}

}
