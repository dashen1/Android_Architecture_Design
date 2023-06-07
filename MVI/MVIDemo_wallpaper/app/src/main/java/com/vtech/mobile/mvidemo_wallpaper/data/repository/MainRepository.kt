package com.vtech.mobile.mvidemo_wallpaper.data.repository

import com.vtech.mobile.mvidemo_wallpaper.network.ApiService

class MainRepository(private val apiService: ApiService) {

    suspend fun getWallpaper() = apiService.getWallPaper()
}