package com.example.mylib_readonlinenovels

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.ClipDrawable.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylib_readonlinenovels.adapter.BookAdapter
import com.example.mylib_readonlinenovels.data.AppDatabase
import com.example.mylib_readonlinenovels.data.entity.Book
import com.example.mylib_readonlinenovels.data.dao.BookDao
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private var list = mutableListOf<Book>()
    private lateinit var adapter: BookAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        fab = findViewById(R.id.fab)

        database = AppDatabase.getInstance(this)

        adapter = BookAdapter(list)
        adapter.setDialog(object :  BookAdapter.Dialog {
            override fun onClick(position: Int) {
                // make dialog view
                val bookId = list[position].id
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle(list[position].title)
                dialog.setItems(R.array.items_option, DialogInterface.OnClickListener { dialog, which ->
                    if (which == 0) {
                        //edit
                        val intent = Intent(this@MainActivity, EditorActivity::class.java)
                        intent.putExtra("id", bookId)
                        startActivity(intent)
                    } else if (which == 1) {
                        //delete
                        database.bookDao().delete(list[position])
                        getData()
                    } else {
                        // cancel
                        dialog.dismiss()
                    }
                })

                //show dialog
                val dialogView = dialog.create()
                dialogView.show()
            }

        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, RecyclerView.VERTICAL))

        fab.setOnClickListener{
            startActivity(Intent(this, EditorActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getData(){
        list.clear()
        list.addAll(database.bookDao().getAll())
        adapter.notifyDataSetChanged()
    }
}