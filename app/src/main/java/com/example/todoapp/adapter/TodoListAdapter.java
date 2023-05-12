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
    private SimpleDateFormat inputDateFormat;

    private SimpleDateFormat dateFormat;

    private OnTodoItemClickListener itemClickListener;

    public TodoListAdapter(OnTodoItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        todos = new ArrayList<>();
        inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateFormat = new SimpleDateFormat("EE dd MMM yyyy", Locale.getDefault());
    }

    public TodoListAdapter(List<Todo> todos) {
        this.todos = todos;
        inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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
        private TextView txtDay;
        private TextView txtDate;
        private TextView txtMonth;
        private TextView txtStatus;

        TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDetail = itemView.findViewById(R.id.txtDetail);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtMonth = itemView.findViewById(R.id.txtMonth);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            itemView.setOnClickListener(this);
        }

        void bind(Todo todo) {
            txtTitle.setText(todo.getTitle());
            txtDetail.setText(todo.getDetail());

            txtStatus.setText(todo.isComplete() ? "COMPLETED" : "INCOMPLETE");

            Date date = todo.getDate();
            if (date != null) {
                String formattedDate = dateFormat.format(date);

                String[] item = formattedDate.split(" ");
                String day = item[0];
                String dd = item[1];
                String month = item[2];

                txtDay.setText(day);
                txtDate.setText(dd);
                txtMonth.setText(month);

            } else {
                // Get current date
                Calendar currentCalendar = Calendar.getInstance();
                Date currentDate = currentCalendar.getTime();

                todo.setDate(currentDate);
                String formattedDate = dateFormat.format(currentDate);

                String[] item = formattedDate.split(" ");
                String day = item[0];
                String dd = item[1];
                String month = item[2];

                txtDay.setText(day);
                txtDate.setText(dd);
                txtMonth.setText(month);
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