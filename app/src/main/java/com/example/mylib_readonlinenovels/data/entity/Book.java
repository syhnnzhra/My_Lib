package com.example.mylib_readonlinenovels.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Book {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String writer;
    public int pages;
    public String synopsis;
}
