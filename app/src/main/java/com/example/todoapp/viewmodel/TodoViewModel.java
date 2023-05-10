package com.example.todoapp.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.TypeConverters;

import com.example.todoapp.database.DateTypeConverter;
import com.example.todoapp.database.Todo;
import com.example.todoapp.repository.TodoRepository;

import java.util.Date;
import java.util.List;

@TypeConverters(DateTypeConverter.class)
public class TodoViewModel extends AndroidViewModel {
    private TodoRepository todoRepository;
    private LiveData<List<Todo>> allTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        todoRepository = new TodoRepository(application);
        allTodos = todoRepository.getAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }

    public void insert() {
        Todo todo = new Todo();
        todoRepository.insert(todo);
    }

    public void update() {
        Todo todo = new Todo();
        todoRepository.update(todo);
    }

    public void updateTodoTitleAndDetail(String title, String detail) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDetail(detail);
//        todo.setDate(date);
        todoRepository.update(todo);
    }

    public void delete() {
        Todo todo = new Todo();
        todoRepository.delete(todo);
    }

    public void deleteAll(){
        todoRepository.deleteAll();
    }
}

