package com.github.luthfipun.mediaplayer.domain.model

import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("data")
    val videos: List<Video>
)

data class PlayLists(
    @SerializedName("data")
    val playlists: List<Playlist>
)

sealed class GlobalData{
    data class VideoData(var videos: List<Video>): GlobalData()
    data class PlaylistData(var playlists: List<Playlist>): GlobalData()
    data class PlaylistItemData(var playlistItem: List<PlaylistItem>): GlobalData()
    data class VideoItemData(var videoItem: List<VideoItem>): GlobalData()
}

fun setGlobalData(globalData: GlobalData) = globalData