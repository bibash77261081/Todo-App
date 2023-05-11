package com.example.todoapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todos")
    LiveData<List<Todo>> getAllTodos();

    @Query("SELECT * FROM todos WHERE id = :todoId")
    LiveData<Todo> getTodoById(int todoId);

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("DELETE FROM todos")
    void deleteAll();
}
