package com.example.frc5987scoutingapp.ui

class UserViewModel(private val dao: UserDao) : ViewModel() {
    // Collects the Flow from Room and converts it to a Compose State
    val userList = dao.getAllUsers().collectAsState(initial = emptyList())
}