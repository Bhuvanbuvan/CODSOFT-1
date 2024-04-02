package com.example.todoapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.domin.model.TodoModel
import com.example.todoapplication.repository.TodoRepository
import kotlinx.coroutines.launch

class TaskViewModel(var repository: TodoRepository):ViewModel() {
    fun insertTask(todoModel: TodoModel){
        viewModelScope.launch {
            repository.insertTask(todoModel)
        }
    }

    fun getTasks()=repository.getTask()

    fun getselected()=repository.getSelectedTask()

    fun updateTask(todoModel: TodoModel){
        viewModelScope.launch {
            repository.updatestask(todoModel)
        }
    }

    fun deleteTask(todoModel: TodoModel){
        viewModelScope.launch {
            repository.deleteTask(todoModel)
        }
    }

    fun updateTitle(title:String,id:Int){
        viewModelScope.launch {
            repository.updateTitle(title,id)
        }
    }
}