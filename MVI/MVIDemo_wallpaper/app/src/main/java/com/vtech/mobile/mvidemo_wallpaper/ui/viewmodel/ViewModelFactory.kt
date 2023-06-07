package com.vtech.mobile.mvidemo_wallpaper.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vtech.mobile.mvidemo_wallpaper.data.repository.MainRepository
import com.vtech.mobile.mvidemo_wallpaper.network.ApiService

class ViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // 判断 MainViewModel 是不是 modelClass 的父类或接口
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiService)) as T
        }
        throw IllegalArgumentException("UnKnown class")
    }
}