package com.example.mylib_readonlinenovels.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylib_readonlinenovels.R
import com.example.mylib_readonlinenovels.data.entity.Book

class BookAdapter(var list: List<Book>): RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    private lateinit var dialog: Dialog

    fun setDialog(dialog: Dialog){
        this.dialog = dialog
    }

    interface Dialog{
        fun onClick(position: Int)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var title: TextView
        var writer: TextView
        var pages: TextView
        var synopsis: TextView
        init{
            title = view.findViewById(R.id.title)
            writer = view.findViewById(R.id.writer)
            pages = view.findViewById(R.id.pages)
            synopsis = view.findViewById(R.id.synopsis)
            view.setOnClickListener {
                dialog.onClick(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_book, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.writer.text = list[position].writer
        holder.pages.text = list[position].pages.toString()
        holder.synopsis.text = list[position].synopsis
    }
}