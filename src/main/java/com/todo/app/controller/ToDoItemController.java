package com.todo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;

import com.todo.app.entities.ToDoItem;
import com.todo.app.entities.User;
import com.todo.app.service.TodoItemService;
import com.todo.app.service.UserService;



@Controller
public class ToDoItemController {
	
	
	@Autowired
	TodoItemService itemService ;
	
	 @Autowired
	 private UserService userService;

	
	@GetMapping("/toDoItems")
	public ModelAndView getToDoItems() {
		ModelAndView model = new ModelAndView();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User user = userService.findByEmail(currentUserEmail);
		
        model.addObject("user", user);
		
		model.setViewName("toDoItems") ;
		
		return model ;
	}
	
	
	
	@PostMapping("/toDoItems")
	public ModelAndView saveToDoItems(@ModelAttribute ToDoItem items) {
		
		ModelAndView model = new ModelAndView() ;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User user = userService.findByEmail(currentUserEmail);
        
        items.setUser(user) ; 
		
		itemService.saveItems(items) ;
		
		
		
		model.addObject("user", user) ;
		
		 model.setViewName("redirect:/home") ;
		 
		 return model ;  
	}
	
	
	@GetMapping("/remove/{itemId}/{userId}")
	public String removeItem(@PathVariable("itemId") int itemId, @PathVariable("userId") int userId) {
		
	
		 
	 itemService.removeItem(itemId) ;
		
		
		
		
		return "redirect:/home";  
	}
	
	
	@GetMapping("/itemCompleted/{itemId}/{userId}")
	public String itemCompleted(@PathVariable("itemId") int itemId, @PathVariable("userId") int userId) {
		
	 itemService.setComplete(itemId) ;	
		
		
		
		
		return "redirect:/home";
	}
	
	
}
