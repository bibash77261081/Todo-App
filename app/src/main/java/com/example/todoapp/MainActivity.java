package com.example.todoapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.todoapp.adapter.TodoListAdapter;
import com.example.todoapp.fragment.AddTodoFragment;
import com.example.todoapp.fragment.TodoDetailFragment;
import com.example.todoapp.fragment.TodoListFragment;
import com.example.todoapp.viewmodel.TodoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{

    private static final String TAG_TODO_LIST = "todo_list";
    private static final String TAG_TODO_DETAIL = "todo_detail";
    private static final String TAG_TODO_ADD = "todo_add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            replaceFragment(new TodoListFragment(), TAG_TODO_LIST);
        }
    }

    public void navigateToAddTodo() {
        AddTodoFragment addTodoFragment = new AddTodoFragment();
        replaceFragment(addTodoFragment, TAG_TODO_ADD);
    }

    public void navigateToTodoList() {
        TodoListFragment todoListFragment = new TodoListFragment();
        replaceFragment(todoListFragment, TAG_TODO_LIST);
    }

    public void navigateToTodoDetail(int todoId) {
        TodoDetailFragment todoDetailFragment = TodoDetailFragment.newInstance(todoId);
        replaceFragment(todoDetailFragment, TAG_TODO_DETAIL);
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}

