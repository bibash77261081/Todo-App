package com.example.todoapp.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.todoapp.MainActivity;
import com.example.todoapp.R;
import com.example.todoapp.database.Todo;
import com.example.todoapp.viewmodel.TodoViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TodoDetailFragment extends Fragment {
    private TodoViewModel todoViewModel;
    private int todoId;
    private EditText editTitle;
    private EditText editDetail;
    private CheckBox checkboxCompleted;
    private Button btnSelectDate;
    private Button btnUpdate;
    private Button btnDelete;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private boolean status;

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
        setHasOptionsMenu(true);
        todoViewModel = new ViewModelProvider(requireActivity()).get(TodoViewModel.class);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_detail, container, false);

        editTitle = view.findViewById(R.id.editTitle);
        editDetail = view.findViewById(R.id.editDetail);
        btnSelectDate = view.findViewById(R.id.btnSelectDate);
        checkboxCompleted = view.findViewById(R.id.checkboxCompleted);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);

        btnSelectDate.setOnClickListener(v -> showDatePickerDialog());

        if (getArguments() != null) {
            todoId = getArguments().getInt("todoId", -1);
            Log.d("TodoDetailFragment", "Todo ID: " + todoId);
        }

        btnUpdate.setOnClickListener(v -> { updateTodo(); });
        btnDelete.setOnClickListener(v -> { deleteTodo(); });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadTodoDetails();
    }

    private void loadTodoDetails() {
        if (todoId != -1) {
            LiveData<Todo> todoLiveData = todoViewModel.getTodoById(todoId);
            todoLiveData.observe(getViewLifecycleOwner(), new Observer<Todo>() {
                @Override
                public void onChanged(Todo todo) {
                    todoLiveData.removeObserver(this);
                    if (todo != null) {
                        editTitle.setText(todo.getTitle());
                        editDetail.setText(todo.getDetail());
                        checkboxCompleted.setChecked(todo.isComplete());

                        String formattedDate = dateFormat.format(todo.getDate());
                        btnSelectDate.setText(formattedDate);
                    }
                }
            });
        }
    }

    private void updateTodo() {
        String title = editTitle.getText().toString().trim();
        String detail = editDetail.getText().toString().trim();
        Date date = calendar.getTime();

        if (checkboxCompleted.isChecked()){
            status = true;
        }
        else {
            status = false;
        }

        if (todoId != -1) {
            Todo updatedTodo = new Todo(todoId, title, detail, date, status);
            todoViewModel.update(updatedTodo);
            Toast.makeText(requireContext(), "Todo updated", Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).navigateToTodoList();
        }
    }

    private void deleteTodo() {
        if (todoId != -1) {
            todoViewModel.deleteById(todoId);
            Toast.makeText(requireContext(), "Todo deleted", Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).navigateToTodoList();
        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateButton();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDateButton() {
        String formattedDate = dateFormat.format(calendar.getTime());
        btnSelectDate.setText(formattedDate);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.back_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_back:
                requireActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("Todo Detail");
    }
}
