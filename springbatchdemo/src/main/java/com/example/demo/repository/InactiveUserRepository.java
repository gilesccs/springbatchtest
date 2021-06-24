package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.InactiveUser;

@Repository
public interface InactiveUserRepository extends JpaRepository<InactiveUser, Integer>{

}
