package com.example.todoapp.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapp.MainActivity;
import com.example.todoapp.R;
import com.example.todoapp.database.Todo;
import com.example.todoapp.viewmodel.TodoViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTodoFragment extends Fragment {

    private EditText editTextTitle;
    private EditText editTextDetail;
    private Button buttonAdd;
    private Button buttonSelectDate;

    private TodoViewModel todoViewModel;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    MainActivity activity;
    public AddTodoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        todoViewModel = new ViewModelProvider(requireActivity()).get(TodoViewModel.class);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        activity = (MainActivity) requireActivity();
//        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_todo, container, false);

        editTextTitle = view.findViewById(R.id.edit_text_title);
        editTextDetail = view.findViewById(R.id.edit_text_detail);
        buttonAdd = view.findViewById(R.id.button_add);
        buttonSelectDate = view.findViewById(R.id.button_select_date);

        buttonSelectDate.setOnClickListener(v -> showDatePickerDialog());

        buttonAdd.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString().trim();
            String detail = editTextDetail.getText().toString().trim();
            Date date = calendar.getTime();

            // Validate input
            if (title.isEmpty()) {
                editTextTitle.setError("Title is required");
                editTextTitle.requestFocus();
                return;
            }

            else if (detail.isEmpty()) {
                editTextDetail.setError("Detail is required");
                editTextDetail.requestFocus();
                return;
            }

            else if (date == null) {
                Toast.makeText(requireContext(), "Please select a date", Toast.LENGTH_SHORT).show();
                return;
            }

            else {
                Todo todo = new Todo();
                todo.setTitle(title);
                todo.setDetail(detail);
                todo.setDate(date);
                todo.setComplete(false);

                todoViewModel.insert(todo);

                Toast.makeText(getActivity(), "Todo added successfully", Toast.LENGTH_SHORT).show();

                activity.displayTodoListFragment();
            }
        });

        return view;
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
        buttonSelectDate.setText(formattedDate);
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
//                NavHostFragment.findNavController(this).navigateUp();
                activity.displayTodoListFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}