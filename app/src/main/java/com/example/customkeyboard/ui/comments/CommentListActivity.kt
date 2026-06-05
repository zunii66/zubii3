package com.example.customkeyboard.ui.comments

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customkeyboard.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CommentListActivity : AppCompatActivity() {

    private lateinit var viewModel: CommentListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CommentAdapter(
            onEdit = { comment -> showEditDialog(comment) },
            onDelete = { comment -> viewModel.delete(comment) }
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(CommentListViewModel::class.java)
        viewModel.allComments.observe(this) { comments ->
            comments?.let { adapter.submitList(it) }
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val editText = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("Add Comment")
            .setView(editText)
            .setPositiveButton("Add") { _, _ ->
                val text = editText.text.toString()
                if (text.isNotBlank()) {
                    viewModel.insert(text)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditDialog(comment: com.example.customkeyboard.data.model.Comment) {
        val editText = EditText(this)
        editText.setText(comment.text)
        AlertDialog.Builder(this)
            .setTitle("Edit Comment")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val text = editText.text.toString()
                if (text.isNotBlank()) {
                    viewModel.update(comment.copy(text = text))
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
