package com.example.jwtyoutube.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jwtyoutube.dao.UserDao;
import com.example.jwtyoutube.entity.JwtRequest;
import com.example.jwtyoutube.entity.JwtResponse;
import com.example.jwtyoutube.entity.User;
import com.example.jwtyoutube.util.JwtUtil;


@Service
public class JwtService implements UserDetailsService  {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		String username = jwtRequest.getUsername();
		String userPassword = jwtRequest.getUserPassword();
		authenticate(username, userPassword);
		final UserDetails userDetails =  loadUserByUsername(username); 
		
		String newGeneratedToken =  jwtUtil.generateToken(userDetails);
		User user = userDao.findById(username).get();
		
		return new JwtResponse(user, newGeneratedToken);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user =	userDao.findById(username).get();
		
		if(user!=null) {
			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(),getAuthorities(user) );
		}else{
			throw new UsernameNotFoundException("Username is not valid");
		}
	}

	private Set<SimpleGrantedAuthority> getAuthorities(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
		
		user.getRole().forEach(role->{
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
		});
		return authorities;
	}
	private void authenticate(String userName, String userPassword) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,userPassword));
		}catch(DisabledException e) {
			throw new Exception("User is disabled");
		} catch(BadCredentialsException e) {
			throw new Exception("Bad credential from user");
		}
	}
}
