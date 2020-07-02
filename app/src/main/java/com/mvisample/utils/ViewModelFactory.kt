package com.mvisample.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvisample.data.api.ApiHelper
import com.mvisample.data.repository.MainRepository
import com.mvisample.ui.main.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ViewModelFactory(private val apiHelper: ApiHelper, private val networkHelper: NetworkHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper), networkHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

