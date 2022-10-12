package com.example.jwtyoutube.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.jwtyoutube.entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, String>{

}
