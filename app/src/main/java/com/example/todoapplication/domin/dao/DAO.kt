package com.example.todoapplication.domin.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapplication.domin.model.TodoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {
    @Insert
    suspend fun insertTask(todoModel: TodoModel)

    @Query("SELECT * FROM todotable ORDER BY ID DESC")
    fun getAllTask():Flow<List<TodoModel>>

    @Query("SELECT * FROM TODOTABLE WHERE checked == 1")
    fun getChechedTask():Flow<List<TodoModel>>

    @Update
    suspend fun updateTask(todoModel: TodoModel)

    @Delete
    suspend fun deleteTask(todoModel: TodoModel)

    @Query("UPDATE TODOTABLE SET Task=:todotitle WHERE ID=:todoId")
    suspend fun updatetitle(todotitle: String,todoId:Int)
}