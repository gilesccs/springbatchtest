package com.example.demo.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

import com.example.demo.model.User;

public class UserClassifier implements Classifier<User, ItemWriter<? super User>> {
	 
    private static final long serialVersionUID = 1L;
     
    private ItemWriter<User> aItemWriter;
    private ItemWriter<User> bItemWriter;
	private ItemWriter<User> cItemWriter;
	private ItemWriter<User> dItemWriter;
 
    public UserClassifier(ItemWriter<User> evenItemWriter, ItemWriter<User> oddItemWriter, ItemWriter<User> thirdItemWriter, ItemWriter<User> fItemWriter) {
        this.aItemWriter = evenItemWriter;
        this.bItemWriter = oddItemWriter;
        this.cItemWriter = thirdItemWriter;
        this.dItemWriter = fItemWriter;
    }

	public UserClassifier(DBWriter aItemWriter, SuspendedWriter bItemWriter, ActiveWriter cItemWriter,
			InactiveWriter dItemWriter) {
		// TODO Auto-generated constructor stub
		this.aItemWriter = aItemWriter;
        this.bItemWriter = bItemWriter;
        this.cItemWriter = cItemWriter;
        this.dItemWriter = dItemWriter;
	}

	@Override
	public ItemWriter<? super User> classify(User user) {
		if(user.getStatus().equals("S")) {
			return bItemWriter;
		}else if(user.getStatus().equals("A")) {
			return cItemWriter;
		}else {
			return dItemWriter;
		}
	}
}