package com.example.todoapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.TypeConverters;

import com.example.todoapp.database.DateTypeConverter;
import com.example.todoapp.database.Todo;
import com.example.todoapp.repository.TodoRepository;

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

    public LiveData<Todo> getTodoById(int todoId) {
        return todoRepository.getTodoById(todoId);
    }

    public void insert(Todo todo) {
        todoRepository.insert(todo);
    }

    public void update(Todo todo) {
        todoRepository.update(todo);
    }

    public void delete(Todo todo) {
        todoRepository.delete(todo);
    }

    public void deleteAll(){
        todoRepository.deleteAll();
    }

    public void deleteCompleted(){
        todoRepository.deleteCompleted();
    }

    public void deleteById(int todoId){
        todoRepository.deleteById(todoId);
    }

    public LiveData<List<Todo>> searchTodos(String searchText) {
        return todoRepository.searchTodos(searchText);
    }
}

