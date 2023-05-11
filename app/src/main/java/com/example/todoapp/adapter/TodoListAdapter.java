package com.example.todoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.database.Todo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {
    private List<Todo> todos;
    private SimpleDateFormat dateFormat;

    private OnTodoItemClickListener itemClickListener;

    public TodoListAdapter(OnTodoItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        todos = new ArrayList<>();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    public TodoListAdapter(List<Todo> todos) {
        this.todos = todos;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    public TodoListAdapter() {
        this.todos = new ArrayList<>();
    }

    public void setTodos(List<Todo> todos) {
        if (todos != null) {
            this.todos = todos;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.bind(todo);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtTitle;
        private TextView txtDetail;
        private TextView txtDate;
        private TextView txtStatus;

        TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDetail = itemView.findViewById(R.id.txtDetail);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            itemView.setOnClickListener(this);
        }

        void bind(Todo todo) {
            txtTitle.setText(todo.getTitle());
            txtDetail.setText(todo.getDetail());

            if (todo.isComplete() == true){
                txtStatus.setText("Completed");
            }
            else {
                txtStatus.setText("Incomplete");
            }

            Date date = todo.getDate();
            if (date != null) {
                String formattedDate = dateFormat.format(date);
                txtDate.setText(formattedDate);
            } else {
                // Get the current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;  // January is represented as 0
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Display the current date
                String currentDate = day + "/" + month + "/" + year;
                txtDate.setText(currentDate);
            }

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Todo todo = todos.get(position);
                itemClickListener.onTodoItemClick(todo);
            }
        }
    }

    public interface OnTodoItemClickListener {
        void onTodoItemClick(Todo todo);
    }
}