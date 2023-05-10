package com.example.todoapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.database.Todo;
import com.example.todoapp.database.TodoDao;

import java.util.List;

public class TodoRepository {
    private TodoDao todoDao;
    private LiveData<List<Todo>> allTodos;

    public TodoRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        todoDao = database.todoDao();
        allTodos = todoDao.getAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }

    public void insert(Todo todo) {
        AppDatabase.databaseWriteExecutor.execute(() -> todoDao.insert(todo));
    }

    public void update(Todo todo) {
        AppDatabase.databaseWriteExecutor.execute(() -> todoDao.update(todo));
    }

    public void delete(Todo todo) {
        AppDatabase.databaseWriteExecutor.execute(() -> todoDao.delete(todo));
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> todoDao.deleteAll());
    }
}

