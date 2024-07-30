package com.todo.app.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.todo.app.entities.ToDoItem;
import com.todo.app.entities.User;
import com.todo.app.service.TodoItemService;
import com.todo.app.service.UserService;

@Controller
public class UserController {
	
	
	@Autowired
	TodoItemService itemService ;
	
	 @Autowired
	 private UserService userService;
	  
	 
	 
	 @GetMapping("/home")
	    public ModelAndView home() {
		 	ModelAndView model = new ModelAndView() ;
		 
		 	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		        String currentUserEmail = authentication.getName();
		        User user = userService.findByEmail(currentUserEmail);
		        
		        int id = user.getId() ;
		        
		        List<ToDoItem> items = itemService.getAllItems(id) ;
		        
	        model.addObject("user", user);
	        model.addObject("items", items) ; 
	        
	        model.setViewName("home") ;
	        return model;
	    }
	 
	  
	 @GetMapping("/login")
	    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {
		 	ModelAndView model = new ModelAndView() ;
		 
	        if (error != null) {
	            model.addObject("errorMessage", true);
	        }
	        
	        model.setViewName("login") ;
	        return model ;  
	    }
	 
	
	  
	 
	 @GetMapping("/register")
	    public ModelAndView showRegistrationForm() {
		 	ModelAndView model = new ModelAndView() ;
		 
		 	
		 	
	        model.addObject("user", new User());
	        return model; 
	    }
	  
	 @PostMapping("/register")
	    public ModelAndView registerUser(@ModelAttribute("user") User user) {
		 ModelAndView model = new ModelAndView() ;
		 
		 if (userService.emailExists(user.getEmail())) {
			 	
	            model.addObject("errorMessage", true);
	           
	            model.setViewName("register") ;
	            return model ;
	        }
		 
	        userService.saveUser(user);
	        
	        model.setViewName("redirect:/login") ;
	        return model;
	    }
	 

}
