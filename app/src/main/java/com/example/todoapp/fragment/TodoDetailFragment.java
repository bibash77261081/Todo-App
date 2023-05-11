package com.example.todoapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.todoapp.MainActivity;
import com.example.todoapp.R;
import com.example.todoapp.viewmodel.TodoViewModel;

public class TodoDetailFragment extends Fragment {
    private TodoViewModel todoViewModel;
    private int todoId;
    private EditText editTitle;
    private EditText editDetail;
    private Button btnSelectDate;
    private Button btnUpdate;
    private Button btnDelete;

    public TodoDetailFragment() {
        // Required empty public constructor
    }

    public static TodoDetailFragment newInstance(int todoId) {
        TodoDetailFragment fragment = new TodoDetailFragment();
        Bundle args = new Bundle();
        args.putInt("todoId", todoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todoViewModel = new ViewModelProvider(requireActivity()).get(TodoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_detail, container, false);

        editTitle = view.findViewById(R.id.editTitle);
        editDetail = view.findViewById(R.id.editDetail);
        btnSelectDate = view.findViewById(R.id.btnSelectDate);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);

        btnUpdate.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String detail = editDetail.getText().toString().trim();
            String date = btnSelectDate.getText().toString().trim();
            todoViewModel.updateTodoTitleAndDetail(title, detail);

            Toast.makeText(getActivity(), "Todo updated successfully", Toast.LENGTH_SHORT).show();

            ((MainActivity)getActivity()).navigateToTodoList();
        });

        btnDelete.setOnClickListener(v -> {
            todoViewModel.delete();
            Toast.makeText(getActivity(), "Todo deleted successfully", Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).navigateToTodoList();
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadTodoDetails();
    }

    private void loadTodoDetails() {
        if (todoId != -1) {
            // Fetch the todo from the database
            Todo todo = todoViewModel.getTodoById(todoId);
            if (todo != null) {
                editTitle.setText(todo.getTitle());
                editDetail.setText(todo.getDetail());
            }
        }
    }

    private void updateTodo() {
        String title = editTitle.getText().toString();
        String detail = editDetail.getText().toString();

        if (todoId != -1) {
            Todo updatedTodo = new Todo(todoId, title, detail);
            todoViewModel.updateTodo(updatedTodo);
            Toast.makeText(requireContext(), "Todo updated", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteTodo() {
        if (todoId != -1) {
            todoViewModel.deleteTodoById(todoId);
            Toast.makeText(requireContext(), "Todo deleted", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        }
    }
}
