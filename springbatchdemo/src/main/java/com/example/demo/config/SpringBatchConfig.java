package com.example.demo.config;

//import javax.batch.api.chunk.ItemProcessor;
//import javax.batch.api.chunk.ItemReader;
//import javax.batch.api.chunk.ItemWriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import com.example.demo.writer.*;

import com.example.demo.model.User;
import com.example.demo.writer.UserClassifier;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory
			, ItemReader<User> itemReader, ItemProcessor<User, User> itemProcessor,
			ItemWriter<User> itemWriter) throws Exception {
		Step step = stepBuilderFactory.get("ETL-file-LOAD")
				.<User, User>chunk(100)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(classifierUserCompositeItemWriter())
				.build(); //step has reader processor and writer
		
		Job job = jobBuilderFactory.get("ETL-LOAD")
			.incrementer(new RunIdIncrementer())
			.start(step ) // if multiple steps --> .flow(step).next(step)
			.build();
		
		return job;
	}
	
	@Bean 
	public ActiveWriter ActiveWriter(){
		return new ActiveWriter();
	}
	
	@Bean 
	public InactiveWriter InactiveWriter(){
		return new InactiveWriter();
	}
	
	@Bean 
	public SuspendedWriter SuspendedWriter(){
		return new SuspendedWriter();
	}
	
	@Bean 
	public DBWriter oDBWriter(){
		return new DBWriter();
	}
	
	@Bean
	@Primary
    public ClassifierCompositeItemWriter<User> classifierUserCompositeItemWriter() throws Exception {
        ClassifierCompositeItemWriter<User> compositeItemWriter = new ClassifierCompositeItemWriter<>();
        compositeItemWriter.setClassifier(new UserClassifier(oDBWriter(),SuspendedWriter(),ActiveWriter(),InactiveWriter()));
        return compositeItemWriter;
    }
	
	@Bean
	public FlatFileItemReader<User> itemReader(@Value("${input}") Resource resource){
		FlatFileItemReader<User> ffiReader = new FlatFileItemReader<>();
		ffiReader.setResource(resource);
		ffiReader.setName("CSV-reader");
		ffiReader.setLinesToSkip(1);
		ffiReader.setLineMapper(lineMapper()); // need to map reader to pojo User object.
		return ffiReader;
	}

	@Bean
	public LineMapper<User> lineMapper() {
		// TODO Auto-generated method stub
		
		DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] {"id","name","status","salary"});
		BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(User.class);
		
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		return defaultLineMapper;
	}
}
