package com.example.todoapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.database.Todo;
import com.example.todoapp.database.TodoDao;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TodoRepository {
    private TodoDao todoDao;
    private LiveData<List<Todo>> allTodos;
    private Executor executor;

    public TodoRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        todoDao = database.todoDao();
        allTodos = todoDao.getAllTodos();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }

    public void getTodoById(int todoId) {
        executor.execute(() -> todoDao.getTodoById(todoId));
    }
    public void insert(Todo todo) {
        executor.execute(() -> todoDao.insert(todo));
    }

    public void update(Todo todo) {
        executor.execute(() -> todoDao.update(todo));
    }

    public void delete(Todo todo) {
        executor.execute(() -> todoDao.delete(todo));
    }

    public void deleteAll() {
        executor.execute(() -> todoDao.deleteAll());
    }
    public void deleteCompleted() {
        executor.execute(() -> todoDao.deleteCompleted());
    }
}


