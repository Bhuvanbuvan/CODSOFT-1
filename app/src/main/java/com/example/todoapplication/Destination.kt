package com.example.todoapplication

sealed class Destination(val rout: String) {
    object home:Destination("first")
    object second:Destination("second")
}