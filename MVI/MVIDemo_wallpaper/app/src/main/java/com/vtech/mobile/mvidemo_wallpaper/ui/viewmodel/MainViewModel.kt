package com.vtech.mobile.mvidemo_wallpaper.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtech.mobile.mvidemo_wallpaper.data.intent.MainIntent
import com.vtech.mobile.mvidemo_wallpaper.data.repository.MainRepository
import com.vtech.mobile.mvidemo_wallpaper.data.state.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repository: MainRepository) :ViewModel(){

    // Create intent Channel,unlimited capacity.
    val mainIntentChannel = Channel<MainIntent>(Channel.UNLIMITED)

    // Changeable state data flow.
    private val _state = MutableStateFlow<MainState>(MainState.Idle)

    // Observable state data flow.
    val state:StateFlow<MainState> get() = _state

    // 执行顺序 主构造函数 -》 init代码块 -》 此构造代码块
    init {
        viewModelScope.launch {
            // Collecting intent.
            mainIntentChannel.consumeAsFlow().collect{
                when(it){
                    // 获取想要的意图
                    is MainIntent.GetWallpaper -> getWallpaper()
                }
            }
        }
    }

    private fun getWallpaper() {
        viewModelScope.launch {
            // Change state to loading.
            _state.value = MainState.Loading
            // The state of Internet request.
            _state.value = try {
                MainState.Wallpapers(repository.getWallpaper())
            }catch (e:Exception){
                // Fail to request.
                MainState.Error(e.localizedMessage ?: "Unknown Error")
            }
        }
    }
}
