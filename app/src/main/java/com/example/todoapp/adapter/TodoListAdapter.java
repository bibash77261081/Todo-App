package com.example.todoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.database.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {
    private List<Todo> todos;
    private OnTodoItemClickListener itemClickListener;

    public TodoListAdapter(OnTodoItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        todos = new ArrayList<>();
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
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

        TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDetail = itemView.findViewById(R.id.txtDetail);
            txtDate = itemView.findViewById(R.id.txtDate);
            itemView.setOnClickListener(this);
        }

        void bind(Todo todo) {
            txtTitle.setText(todo.getTitle());
            txtDetail.setText(todo.getDetail());
            txtDate.setText((CharSequence) todo.getDate());
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