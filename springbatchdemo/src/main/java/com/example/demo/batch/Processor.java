package com.example.demo.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Component
public class Processor implements ItemProcessor<User, User>{

	@Autowired
	UserRepository urepo;
	
	private static final Map<String, String> STATUS = new HashMap<>();
	
	public Processor() {
		STATUS.put("active", "A");
		STATUS.put("suspend", "S");
		STATUS.put("inactive", "I");
	}
	
	@Override
	public User process(User user) throws Exception{
		Integer id = user.getId();
		User intended = urepo.findById(id).orElse(null);
		
//		String status = user.getStatus();
//		String stat = STATUS.get(status);
//		intended.setStatus(stat);
		intended.setTime(new Date());
//		System.out.println(String.format("converted from [%s] to [%s]", status, stat));
		return intended;
	}
}
