package com.todo.app.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.app.entities.User;
import com.todo.app.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository ; 
	
	 @Autowired
	    private PasswordEncoder passwordEncoder;
	
	public void saveUser(User user) { 
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user); 
    }
	

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
	  
	  
	  @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        User user = userRepository.findByEmail(email);
	        if (user == null) {
	            throw new UsernameNotFoundException("User not found");
	        }
	        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
	    }
 
}
