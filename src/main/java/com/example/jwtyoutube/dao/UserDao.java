package com.example.jwtyoutube.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.jwtyoutube.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, String> {

}
