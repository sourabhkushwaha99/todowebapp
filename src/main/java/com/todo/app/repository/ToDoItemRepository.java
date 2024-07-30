package com.todo.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.todo.app.entities.ToDoItem;

import jakarta.transaction.Transactional;
 
public interface ToDoItemRepository extends CrudRepository<ToDoItem, Integer>{

	
	@Query(value="select * from items where user_id=:id order by completed asc, due_date asc,case when priority='High' then 0 when priority='Medium' then 1 when priority='Low' then 2 end", nativeQuery=true)
	public List<ToDoItem> findItemsByUserId(@Param("id") int id) ;
	
	@Transactional
	@Modifying
	@Query(value="UPDATE items SET completed = completed ^ 0x01 where id=:id", nativeQuery=true)
	public void setItemComplete(@Param("id") int id) ;
	
}
