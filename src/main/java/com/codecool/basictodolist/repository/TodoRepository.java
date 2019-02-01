package com.codecool.basictodolist.repository;

import com.codecool.basictodolist.model.Status;
import com.codecool.basictodolist.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByStatus(Status status);

    void deleteByStatus(Status status);

    void deleteById(int id);

    Todo findById(int id);

}
