package com.example.todolistapp.controller;

import com.example.todolistapp.domain.Folder;
import com.example.todolistapp.domain.Todo;
import com.example.todolistapp.repo.TodoRepo;
import com.example.todolistapp.repo.FolderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FolderController {

    @Autowired
    FolderRepo folderRepo;
    @Autowired
    TodoRepo todoRepo;

    @GetMapping(value = "/folder")
    public ResponseEntity<?> findAll(){
        List<Folder> folders = folderRepo.findAll();

        return ResponseEntity.ok(folders);
    }

    @PostMapping(value = "/folder")
    public ResponseEntity<?> create(@RequestBody Folder folder){
        Folder newFolder = folderRepo.save(folder);

        return ResponseEntity.ok(newFolder);
    }

    @PutMapping(value = "/folder/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Folder folder){
        Optional<Folder> folderOpt = folderRepo.findAll()
                                                .stream()
                                                .filter(item -> item.getId().equals(id))
                                                .findAny();

        if (folderOpt.isPresent()){
            Folder item = folderOpt.get();
            item.setName(folder.getName());
            return ResponseEntity.ok(item);
        }
        return null;
    }

    @DeleteMapping(value = "/folder/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        Optional<Folder> itemToDeleteOpt = folderRepo.findById(id);


        if (itemToDeleteOpt.isPresent()){
            Folder itemToDelete = itemToDeleteOpt.get();
            folderRepo.delete(itemToDelete);

            List<Todo> todos = todoRepo.findAll();

            for (Todo todo : todos){
                if(todo.getFolderId().equals(id)){
                    todoRepo.delete(todo);
                }
            }

            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.ok("{\"message\": \"did not exist\"}");
    }
}
