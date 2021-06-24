package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SuspendedUser {

	@Id
	private Integer id;
	private String name;
	private Integer salary;
	private Date time;
	
	
	public SuspendedUser() {
		super();
	}

	public SuspendedUser(Integer id, String name, Integer salary, Date time) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.time = time;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	
}
