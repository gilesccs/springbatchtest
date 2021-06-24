package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ActiveUser;

public interface ActiveUserRepository extends JpaRepository<ActiveUser, Integer>{

}
