package com.example.todoapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.MainActivity;
import com.example.todoapp.R;
import com.example.todoapp.adapter.TodoListAdapter;
import com.example.todoapp.database.Todo;
import com.example.todoapp.viewmodel.TodoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TodoListFragment extends Fragment implements TodoListAdapter.OnTodoItemClickListener {
    private TodoViewModel todoViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddTodo;
    private TodoListAdapter todoListAdapter;

    public TodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        todoViewModel = new ViewModelProvider(requireActivity()).get(TodoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        todoListAdapter = new TodoListAdapter(this);
        recyclerView.setAdapter(todoListAdapter);

        fabAddTodo = view.findViewById(R.id.fabAddTodo);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        todoViewModel.getAllTodos().observe(getViewLifecycleOwner(), todos -> {
            todoListAdapter.setTodos(todos);
        });

        fabAddTodo.setOnClickListener(v -> ((MainActivity) requireActivity()).navigateToAddTodo());

    }

    @Override
    public void onTodoItemClick(Todo todo) {
        ((MainActivity)getActivity()).navigateToTodoDetail();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.todo_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        switch (item.getItemId()){
            case R.id.menu_delete_all:
                builder.setTitle("Delete All Todos");
                builder.setMessage("Are you sure you want to delete all todos?");
                builder.setPositiveButton("Delete", (dialog, which) -> todoViewModel.deleteAll());
                builder.setNegativeButton("Cancel", null);
                builder.show();
                return true;

            case R.id.menu_delete_completed:
                builder.setTitle("Delete Completed Todos");
                builder.setMessage("Are you sure you want to delete all completed todos?");
                builder.setPositiveButton("Delete", (dialog, which) -> todoViewModel.deleteCompleted());
                builder.setNegativeButton("Cancel", null);
                builder.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

