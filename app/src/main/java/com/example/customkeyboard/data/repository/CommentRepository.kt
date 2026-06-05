package com.example.customkeyboard.data.repository

import com.example.customkeyboard.data.database.CommentDao
import com.example.customkeyboard.data.model.Comment
import kotlinx.coroutines.flow.Flow

class CommentRepository(private val commentDao: CommentDao) {
    val allComments: Flow<List<Comment>> = commentDao.getAllComments()

    suspend fun insert(comment: Comment) {
        commentDao.insertComment(comment)
    }

    suspend fun update(comment: Comment) {
        commentDao.updateComment(comment)
    }

    suspend fun delete(comment: Comment) {
        commentDao.deleteComment(comment)
    }

    suspend fun getRandomComment(): Comment? {
        return commentDao.getRandomComment()
    }
}
