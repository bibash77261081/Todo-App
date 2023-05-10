package com.example.todoapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.adapter.TodoListAdapter;
import com.example.todoapp.database.Todo;
import com.example.todoapp.viewmodel.TodoViewModel;

import java.util.List;

public class TodoListFragment extends Fragment {
    private TodoViewModel todoViewModel;
    private TodoListAdapter todoListAdapter;

    public TodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        todoListAdapter = new TodoListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(todoListAdapter);

        // Set up the click listener for each item
//        todoListAdapter.setOnItemClickListener((position) -> {
//            Todo todo = todos.get(position);
//            showTodoDetailPage(todo);
//        });

        todoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                todoListAdapter.setTodos(todos);
            }
        });

        return view;
    }

//    private void showTodoDetailPage(Todo todo) {
//        // Create a new instance of the todo detail fragment and pass the todo data
//        TodoDetailFragment todoDetailFragment = TodoDetailFragment.newInstance(todo);
//
//        // Navigate to the todo detail fragment using a FragmentTransaction
//        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//        transaction.replace(R.id.container, todoDetailFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
}
