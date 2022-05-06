package com.github.luthfipun.mediaplayer.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Playlist(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("samples")
    val playlistItems: List<PlaylistItem>
): Serializable