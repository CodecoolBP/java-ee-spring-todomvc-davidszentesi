package com.codecool.basictodolist;

import com.codecool.basictodolist.model.Todo;
import com.codecool.basictodolist.model.TodoDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BasicTodoList {

    public static void main(String[] args) {
        addSampleData();
        SpringApplication.run(BasicTodoList.class, args);
    }

    private static void addSampleData() {
        TodoDao.add(Todo.create("first TODO item"));
        TodoDao.add(Todo.create("second TODO item"));
        TodoDao.add(Todo.create("third TODO item"));
    }

}

