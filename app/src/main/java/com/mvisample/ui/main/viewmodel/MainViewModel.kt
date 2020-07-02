package com.mvisample.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvisample.data.repository.MainRepository
import com.mvisample.ui.main.intent.MainIntent
import com.mvisample.ui.main.viewstate.MainState
import com.mvisample.utils.NetworkHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it) {
                    is MainIntent.FetchUser -> fetchUsers()
                }
            }
        }
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getUsers().let { users ->
                    if (users.isSuccessful) {
                        users.body()?.let {
                            _state.value = MainState.Users(it)
                        }
                    } else _state.value = MainState.Error(users.errorBody().toString())
                }
            } else _state.value = MainState.Error("No internet connection")
        }
    }
}