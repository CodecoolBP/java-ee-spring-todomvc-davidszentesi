package com.codecool.basictodolist.controller;

import com.codecool.basictodolist.model.Todo;
import com.codecool.basictodolist.model.TodoDao;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RouteController {

    private static final String SUCCESS = "{\"success\":true}";

    @PostMapping(value = "/addTodo")
    public String addNewTodo(@RequestParam("todo-title") String title) {
        Todo newTodo = Todo.create(title);
        TodoDao.add(newTodo);
        return SUCCESS;
    }

    @PostMapping("/list")
    public String listById(@RequestParam("status") String status) {
        List<Todo> daos = TodoDao.ofStatus(status);
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

    @DeleteMapping(value = "/todos/completed")
    public String removeAllCompletedTodo() {
        TodoDao.removeCompleted();
        return SUCCESS;
    }

    @PutMapping(value = "/todos/toggle_all")
    public String toggleAllStatus(@RequestParam("toggle-all") String toggle) {
        TodoDao.toggleAll(toggle.equals("true"));
        return SUCCESS;
    }

    @DeleteMapping(value = "/todos/{id}")
    public String removeTodoById(@PathVariable("id") String id) {
        TodoDao.remove(id);
        return SUCCESS;
    }

    @PutMapping(value = "/todos/{id}")
    public String updateTodoById(@PathVariable("id") String id, @RequestParam("todo-title") String title) {
        TodoDao.update(id, title);
        return SUCCESS;
    }

    @GetMapping(value = "/todos/{id}")
    public void findTodoById(@PathVariable("id") String id) {
        TodoDao.find(id).getTitle();
    }

    @PutMapping("/todos/{id}/toggle_status")
    public String toggleStatusById(@PathVariable("id") String id, @RequestParam("status") String status) {
        boolean completed = status.equals("true");
        TodoDao.toggleStatus(id, completed);
        return SUCCESS;
    }

}
