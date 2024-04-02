package com.example.todoapplication.domin.Room

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapplication.domin.dao.DAO
import com.example.todoapplication.domin.model.TodoModel


@Database(entities = [TodoModel::class], version = 2)
abstract class TodoROOM :RoomDatabase(){
    abstract fun dao():DAO

    companion object{
        @Volatile
        var INSTENCE:TodoROOM?=null


        fun getInstence(context: Context):TodoROOM{
            synchronized(this){
                var instence= INSTENCE
                if (instence==null){
                    instence= Room.databaseBuilder(
                        context.applicationContext,
                        TodoROOM::class.java,
                        "Tododb2"
                    ).build()
                }
                return instence
            }

        }
    }
}