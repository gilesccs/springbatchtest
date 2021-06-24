package com.example.demo.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;

@Component
public class InactiveWriter implements ItemWriter<User>{

	@Override
	public void write(List<? extends User> items) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
