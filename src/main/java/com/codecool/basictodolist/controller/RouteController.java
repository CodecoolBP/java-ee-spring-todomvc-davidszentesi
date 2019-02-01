package com.codecool.basictodolist.controller;

import com.codecool.basictodolist.model.Status;
import com.codecool.basictodolist.model.Todo;
import com.codecool.basictodolist.repository.TodoRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RouteController {

    @Autowired
    private TodoRepository todoRepository;

    private static final String SUCCESS = "{\"success\":true}";

    @PostMapping(value = "/addTodo")
    public String addNewTodo(@RequestParam("todo-title") String title) {
        Todo todo = Todo.builder().title(title).status(Status.ACTIVE).build();
        todoRepository.save(todo);
        return SUCCESS;
    }


    @PostMapping("/list")
    public String listById(@RequestParam("status") String status) {
        List<Todo> daos = new ArrayList<>();

        switch (status) {
            case "":
                daos = todoRepository.findAll();
                break;
            case "active":
                daos = todoRepository.findByStatus(Status.ACTIVE);
                break;
            case "complete":
                daos = todoRepository.findByStatus(Status.COMPLETE);
                break;
        }

        JSONArray arr = new JSONArray();
        for (Todo dao : daos) {
            JSONObject jo = new JSONObject();
            jo.put("id", dao.getId());
            jo.put("title", dao.getTitle());
            jo.put("completed", dao.isComplete());
            arr.put(jo);
        }
        return arr.toString(2);
    }


    @Transactional
    @DeleteMapping(value = "/todos/completed")
    public String removeAllCompletedTodo() {
        todoRepository.deleteByStatus(Status.COMPLETE);
        return SUCCESS;
    }


    @PutMapping(value = "/todos/toggle_all")
    public String toggleAllStatus(@RequestParam("toggle-all") String toggle) {
        boolean complete = toggle.equals("true");
        List<Todo> todos = todoRepository.findAll();
        todos.forEach(t -> t.setStatus(complete ? Status.COMPLETE : Status.ACTIVE));
        todoRepository.saveAll(todos);
        return SUCCESS;
    }


    @Transactional
    @DeleteMapping(value = "/todos/{id}")
    public String removeTodoById(@PathVariable("id") String id) {
        todoRepository.deleteById(Integer.parseInt(id));
        return SUCCESS;
    }


    @PutMapping(value = "/todos/{id}")
    public String updateTodoById(@PathVariable("id") String id, @RequestParam("todo-title") String title) {
        Todo todo = todoRepository.findById(Integer.parseInt(id));
        todo.setTitle(title);
        todoRepository.save(todo);
        return SUCCESS;
    }


    @GetMapping(value = "/todos/{id}")
    public void findTodoById(@PathVariable("id") String id) {
        todoRepository.findById(Integer.parseInt(id)).getTitle();
    }


    @PutMapping("/todos/{id}/toggle_status")
    public String toggleStatusById(@PathVariable("id") String id, @RequestParam("status") String status) {
        boolean completed = status.equals("true");
        Todo todo = todoRepository.findById(Integer.parseInt(id));

        if (completed) {
            todo.setStatus(Status.COMPLETE);
        } else {
            todo.setStatus(Status.ACTIVE);
        }

        todoRepository.save(todo);
        return SUCCESS;
    }

}
