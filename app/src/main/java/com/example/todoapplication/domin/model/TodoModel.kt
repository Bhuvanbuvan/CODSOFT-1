package com.example.todoapplication.domin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("todotable")
data class TodoModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val Task:String,
    val checked:Boolean,
    val Like:Boolean,
    val Date:String
)
