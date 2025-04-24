package com.example.flutter_projects.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flutter_projects.domain.User
import com.example.flutter_projects.domain.UserDataCase
import com.example.flutter_projects.domain.UserItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyUserDataViewModel(private val useCase: UserDataCase) : ViewModel() {


    private val _userdata =
        MutableStateFlow<UserUiState>(UserUiState.Init)
    val userData = _userdata.asStateFlow()


    private val _userList =
        MutableStateFlow<UserListUiState>(UserListUiState.Loading)
    val userList = _userList.asStateFlow()



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


    fun getUser() {
        viewModelScope.launch {
            _userList.emit(UserListUiState.Loading)
            try {
                useCase.getUser().collect { user ->
                    _userList.emit(UserListUiState.Success(user as UserItem))
                }
            } catch (e: Exception) {
                _userList.emit(UserListUiState.Error(e.message.toString()))
            }

        }
    }

}
