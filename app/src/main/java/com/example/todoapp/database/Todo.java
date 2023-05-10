package com.example.todoapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "todos")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String detail;
    private Date date;
    @ColumnInfo(name = "is_complete")
    private boolean isComplete;


    @Ignore
    public Todo(int id, String title, String detail, Date date, boolean isComplete) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.date = date;
        this.isComplete = isComplete;
    }

    public Todo(String title, String detail, Date date, boolean isComplete) {
        this.title = title;
        this.detail = detail;
        this.date = date;
        this.isComplete = isComplete;
    }

    @Ignore
    public Todo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", date=" + date +
                ", isComplete=" + isComplete +
                '}';
    }
}