package com.todo.app.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.app.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}  