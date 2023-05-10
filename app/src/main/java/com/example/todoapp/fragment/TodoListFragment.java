package com.example.todoapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
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

import com.example.todoapp.R;
import com.example.todoapp.adapter.TodoListAdapter;
import com.example.todoapp.database.Todo;
import com.example.todoapp.viewmodel.TodoViewModel;

import java.util.List;

public class TodoListFragment extends Fragment implements TodoListAdapter.OnTodoItemClickListener {
    private TodoViewModel todoViewModel;
    private RecyclerView recyclerView;
    private TodoListAdapter todoListAdapter;

    public TodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        todoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                todoListAdapter.setTodos(todos);
            }
        });
    }

    @Override
    public void onTodoItemClick(Todo todo) {
//        Bundle bundle = new Bundle();
//        bundle.putInt("todoId", todo.getId());
//        Navigation.findNavController(requireView())
//                .navigate(R.id.action_todoListFragment_to_todoDetailFragment, bundle);
    }
}

