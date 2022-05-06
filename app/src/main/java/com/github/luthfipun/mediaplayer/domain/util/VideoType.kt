package com.github.luthfipun.mediaplayer.domain.util

import java.io.Serializable

sealed class VideoType: Serializable {
    object DashHls: VideoType()
    object Misc: VideoType()
    object Playlist: VideoType()
    object WithAds: VideoType()
}