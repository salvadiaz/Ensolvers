package com.example.todolistapp.controller;

import com.example.todolistapp.domain.Todo;
import com.example.todolistapp.repo.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {

    @Autowired
    TodoRepo todoRepo;


    @GetMapping(value = "/todos")
    public ResponseEntity<?> findAll(){
        List<Todo> todos = todoRepo.findAll();

        return ResponseEntity.ok(todos);
    }

    @PostMapping(value = "/todo")
    public ResponseEntity<?> createTodo(@RequestBody Todo newTodo) {
        Todo todo = newTodo;
        todo.setCompleted(false);
        todoRepo.save(todo);

        return ResponseEntity.ok(todo);
    }

    @PutMapping(value = "/todo/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable Integer id, @RequestBody Todo todoItem){
        Optional<Todo> todoOpt = todoRepo.findAll()
                                            .stream()
                                            .filter(item -> item.getId().equals(id))
                                            .findAny();

        if (todoOpt.isPresent()){
            Todo item = todoOpt.get();
            item.setCompleted(todoItem.getCompleted());
            item.setTitle(todoItem.getTitle());
            return ResponseEntity.ok(item);
        }
        return null;
    }

    @DeleteMapping(value = "todo/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Integer id) {
        Optional<Todo> itemToDeleteOpt = todoRepo.findById(id);

        if (itemToDeleteOpt.isPresent()){
            Todo itemToDelete = itemToDeleteOpt.get();
            todoRepo.delete(itemToDelete);

            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.ok("{\"message\": \"did not exist\"}");
    }
}
