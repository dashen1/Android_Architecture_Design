package com.vtech.mobile.mvidemo_wallpaper.network

import com.vtech.mobile.mvidemo_wallpaper.data.model.Wallpaper
import retrofit2.http.GET

interface ApiService {

    /**
     * Fetch Wallpaper
     */

    @GET("v1/vertical/vertical?limit=30&skip=180&adult=false&first=0&order=hot")
    suspend fun getWallPaper(): Wallpaper
}