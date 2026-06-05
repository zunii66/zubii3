package com.example.customkeyboard.ui.comments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.customkeyboard.R
import com.example.customkeyboard.data.model.Comment

class CommentAdapter(
    private val onEdit: (Comment) -> Unit,
    private val onDelete: (Comment) -> Unit
) : ListAdapter<Comment, CommentAdapter.CommentViewHolder>(CommentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view, onEdit, onDelete)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CommentViewHolder(
        itemView: View,
        private val onEdit: (Comment) -> Unit,
        private val onDelete: (Comment) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.comment_text)
        private val editButton: ImageButton = itemView.findViewById(R.id.btn_edit)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.btn_delete)

        fun bind(comment: Comment) {
            textView.text = comment.text
            editButton.setOnClickListener { onEdit(comment) }
            deleteButton.setOnClickListener { onDelete(comment) }
        }
    }

    class CommentDiffCallback : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }
}
