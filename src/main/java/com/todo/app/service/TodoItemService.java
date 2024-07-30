package com.todo.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.app.entities.ToDoItem;
import com.todo.app.repository.ToDoItemRepository;





@Component
public class TodoItemService {
	
	@Autowired
	ToDoItemRepository itemRepository ; 
	
	
	public void saveItems(ToDoItem items) {
		this.itemRepository.save(items); 
	}

	

	  
	public List<ToDoItem> getAllItems(int id){
		
		List<ToDoItem> list = new ArrayList<>() ;
		
		list = itemRepository.findItemsByUserId(id) ;
		
		
		return list ;
		
	}
	
	
	public void removeItem(int itemId) {
		
		this.itemRepository.deleteById(itemId);
		
	
	}
	 
	public void setComplete(int id) {
		 this.itemRepository.setItemComplete(id);
	}
	
	
	 
}
