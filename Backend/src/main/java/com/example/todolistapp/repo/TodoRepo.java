package com.example.todolistapp.repo;

import com.example.todolistapp.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRepo extends JpaRepository<Todo, Integer> {
}
