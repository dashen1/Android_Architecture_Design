package com.vtech.mobile.mvidemo_wallpaper.data.model

import android.os.Build
import com.squareup.moshi.Json

data class Res(
    @Json(name = "vertical")
    val vertical:List<Vertical>
)
