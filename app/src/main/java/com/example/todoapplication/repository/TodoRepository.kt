package com.example.todoapplication.repository

import androidx.room.Dao
import com.example.todoapplication.domin.dao.DAO
import com.example.todoapplication.domin.model.TodoModel

class TodoRepository(private var dao: DAO) {

    suspend fun insertTask(todoModel: TodoModel){
        dao.insertTask(todoModel)
    }

     fun getTask()=dao.getAllTask()

    fun getSelectedTask()=dao.getChechedTask()


    suspend fun updatestask(todoModel: TodoModel){
        dao.updateTask(todoModel)
    }

    suspend fun deleteTask(todoModel: TodoModel){
        dao.deleteTask(todoModel)
    }

    suspend fun updateTitle(title:String,id:Int){
        dao.updatetitle(title,id)
    }
}