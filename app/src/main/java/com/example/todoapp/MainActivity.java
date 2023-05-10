package com.example.todoapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.todoapp.fragment.AddTodoFragment;
import com.example.todoapp.fragment.TodoListFragment;
import com.example.todoapp.viewmodel.TodoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAddTodo;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        fabAddTodo = findViewById(R.id.fabAddTodo);
        fabAddTodo.setOnClickListener(v -> {
            // Navigate to AddTodoFragment
            navigateToAddTodoFragment();
        });

        // Display the TodoListFragment initially
        displayTodoListFragment();
    }

    private void displayTodoListFragment() {
        TodoListFragment todoListFragment = new TodoListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, todoListFragment)
                .commit();
    }

    private void navigateToAddTodoFragment() {
        AddTodoFragment addTodoFragment = new AddTodoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, addTodoFragment)
                .addToBackStack(null)
                .commit();
    }
}

