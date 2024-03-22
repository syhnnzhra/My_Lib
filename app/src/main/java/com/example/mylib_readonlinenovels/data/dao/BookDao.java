package com.example.mylib_readonlinenovels.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mylib_readonlinenovels.data.entity.Book;

import java.util.List;

import kotlin.jvm.JvmSuppressWildcards;

@Dao
public interface BookDao {
    @Query("Select * from book")
    @JvmSuppressWildcards
    List<Book> getAll();

    @Insert
    void insertAll(Book... books);

    @Delete
    void delete(Book books);

    @Query("SELECT * FROM book WHERE id = :id")
    Book get(int id);

    @Update
    void update(Book book);
}
