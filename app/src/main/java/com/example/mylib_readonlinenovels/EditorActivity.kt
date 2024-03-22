package com.example.mylib_readonlinenovels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mylib_readonlinenovels.data.AppDatabase
import com.example.mylib_readonlinenovels.data.entity.Book

class EditorActivity : AppCompatActivity() {
    private lateinit var titleEditText: EditText
    private lateinit var writerEditText: EditText
    private lateinit var pagesEditText: EditText
    private lateinit var synopsisEditText: EditText
    private lateinit var btnsave: Button
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        titleEditText = findViewById(R.id.title)
        writerEditText = findViewById(R.id.writer)
        pagesEditText = findViewById(R.id.pages)
        synopsisEditText = findViewById(R.id.synopsis)
        btnsave = findViewById(R.id.btnsave)

        database = AppDatabase.getInstance(applicationContext)

        val intent = intent.extras
        if (intent != null){
            val id = intent.getInt("id", 0)
            val book = database.bookDao().get(id)

            titleEditText.setText(book.title)
            writerEditText.setText(book.writer)
            pagesEditText.setText(book.pages.toString())
            synopsisEditText.setText(book.synopsis)
            finish()
        }

        val titleText = titleEditText.text.toString()
        val writerText = writerEditText.text.toString()
        val pagesText = pagesEditText.text.toString().toIntOrNull()
        val synopsisText = synopsisEditText.text.toString()

        if (titleText.isNotEmpty() && writerText.isNotEmpty() && pagesText != null && synopsisText.isNotEmpty()) {
            if (intent != null) {
                // Edit data
                val bookEdit = Book().apply {
                    this.id = id
                    this.title = titleText
                    this.writer = writerText
                    this.pages = pagesText
                    this.synopsis = synopsisText
                }

                database.bookDao().update(bookEdit)
            } else {
                // Buat data baru
                val book = Book().apply {
                    this.title = titleText
                    this.writer = writerText
                    this.pages = pagesText
                    this.synopsis = synopsisText
                }

                database.bookDao().insertAll(book)
            }
            finish()
        } else {
            Toast.makeText(applicationContext, "All fields are required", Toast.LENGTH_SHORT).show()
        }
    }
}