package com.example.customkeyboard.ui.comments

import android.app.Application
import androidx.lifecycle.*
import com.example.customkeyboard.data.database.AppDatabase
import com.example.customkeyboard.data.model.Comment
import com.example.customkeyboard.data.repository.CommentRepository
import kotlinx.coroutines.launch

class CommentListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CommentRepository
    val allComments: LiveData<List<Comment>>

    init {
        val commentDao = AppDatabase.getDatabase(application).commentDao()
        repository = CommentRepository(commentDao)
        allComments = repository.allComments.asLiveData()
    }

    fun insert(text: String) = viewModelScope.launch {
        repository.insert(Comment(text = text))
    }

    fun update(comment: Comment) = viewModelScope.launch {
        repository.update(comment)
    }

    fun delete(comment: Comment) = viewModelScope.launch {
        repository.delete(comment)
    }
}
