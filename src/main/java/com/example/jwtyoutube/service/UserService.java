package com.example.jwtyoutube.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwtyoutube.dao.RoleDao;
import com.example.jwtyoutube.dao.UserDao;
import com.example.jwtyoutube.entity.Role;
import com.example.jwtyoutube.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	public User registerNewUser (User user) {
		return userDao.save(user);
	}

	public void initRoleAndUser() {
		Role role = new Role();
		role.setRoleName("Admin");
		role.setRoleDescription("Admin Role");
		roleDao.save(role);
		Role userRole = new Role();
		userRole.setRoleName("User");
		userRole.setRoleDescription("Default role for newly created record ");
		roleDao.save(userRole);
		
		User adminUser = new User();
		adminUser.setUserFirstName("admin");
		adminUser.setUserLastName("admin");
		adminUser.setUserName("admin123");
		adminUser.setUserPassword(getEncodedPassword("admin@pass"));
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(role);
		adminUser.setRole(adminRoles);
		userDao.save(adminUser);
		
		User user = new User();
		user.setUserFirstName("raj");
		user.setUserLastName("sharman");
		user.setUserName("raj123");
		user.setUserPassword(getEncodedPassword("raj@pass"));
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(userRole);
		user.setRole(userRoles);
		userDao.save(user);
	}
	
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
