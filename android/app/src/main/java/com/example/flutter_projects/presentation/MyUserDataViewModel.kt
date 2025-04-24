package com.example.flutter_projects.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flutter_projects.domain.User
import com.example.flutter_projects.domain.UserDataCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyUserDataViewModel(private val useCase: UserDataCase) : ViewModel() {


    private val _userdata =
        MutableStateFlow<UserUiState>(UserUiState.Init)
    val userData = _userdata.asStateFlow()


    fun save(user: User) {
        viewModelScope.launch {
            _userdata.emit(UserUiState.Loading)
            try {
                useCase.invoke(user).collect { user ->
                    _userdata.emit(UserUiState.Success(user as User))
                }
            } catch (e: Exception) {
                _userdata.emit(UserUiState.Error(e.message.toString()))
            }

        }
    }

}
