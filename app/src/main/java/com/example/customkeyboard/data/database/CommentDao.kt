package com.example.customkeyboard.data.database

import androidx.room.*
import com.example.customkeyboard.data.model.Comment
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments ORDER BY createdAt DESC")
    fun getAllComments(): Flow<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: Comment)

    @Update
    suspend fun updateComment(comment: Comment)

    @Delete
    suspend fun deleteComment(comment: Comment)

    @Query("SELECT * FROM comments ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomComment(): Comment?
}
