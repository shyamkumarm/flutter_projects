package com.example.flutter_projects.presentation

import com.example.flutter_projects.domain.User
import com.example.flutter_projects.domain.UserItem

sealed class UserUiState {
    data class Success(val savedItem: User?) : UserUiState()
    data class Error(val message: String) : UserUiState()
    data object Loading : UserUiState()
    data object Init : UserUiState()
}

sealed class UserListUiState {
    data class Success(val savedItem: UserItem?) : UserListUiState()
    data class Error(val message: String) : UserListUiState()
    data object Loading : UserListUiState()
}




