package com.github.luthfipun.mediaplayer.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlaylistItem(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("playlist")
    val videoItems: List<VideoItem>
): Serializable