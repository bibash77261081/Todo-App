package com.example.todoapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.todoapp.R;
import com.example.todoapp.viewmodel.TodoViewModel;

public class TodoDetailFragment extends Fragment {
    private TodoViewModel todoViewModel;
    private EditText editTitle;
    private EditText editDetail;
    private EditText editDate;
    private Button btnUpdate;
    private Button btnDelete;

    public TodoDetailFragment() {
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
        View view = inflater.inflate(R.layout.fragment_todo_detail, container, false);

        editTitle = view.findViewById(R.id.editTitle);
        editDetail = view.findViewById(R.id.editDetail);
        editDate = view.findViewById(R.id.editDate);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString().trim();
                String detail = editDetail.getText().toString().trim();
                String date = editDate.getText().toString().trim();
                todoViewModel.updateTodoTitleAndDetail(title, detail);
                Navigation.findNavController(v).navigateUp();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoViewModel.delete();
                Navigation.findNavController(v).navigateUp();
            }
        });

        return view;
    }
}
