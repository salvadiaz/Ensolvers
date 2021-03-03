package com.example.todolistapp.repo;

import com.example.todolistapp.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FolderRepo extends JpaRepository<Folder, Integer> {
}
