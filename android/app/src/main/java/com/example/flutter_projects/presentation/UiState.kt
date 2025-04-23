package com.example.flutter_projects.presentation

import com.example.flutter_projects.domain.User

sealed class UserUiState {
    data class Success(val savedItem: User) : UserUiState()
    data class Error(val message: String) : UserUiState()
    data object Loading : UserUiState()
}



