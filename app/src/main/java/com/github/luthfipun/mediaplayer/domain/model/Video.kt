package com.github.luthfipun.mediaplayer.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Video(
    @SerializedName("name")
    val name: String,
    @SerializedName("samples")
    val videoItems: List<VideoItem>
): Serializable