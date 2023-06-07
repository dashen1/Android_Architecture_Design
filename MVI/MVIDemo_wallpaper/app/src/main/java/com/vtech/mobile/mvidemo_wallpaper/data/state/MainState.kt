package com.vtech.mobile.mvidemo_wallpaper.data.state

import com.vtech.mobile.mvidemo_wallpaper.data.model.Wallpaper

sealed class MainState{
    object Idle:MainState()

    object Loading:MainState()

    data class Wallpapers(val wallpaper: Wallpaper):MainState()

    data class Error(val error:String):MainState()
}
