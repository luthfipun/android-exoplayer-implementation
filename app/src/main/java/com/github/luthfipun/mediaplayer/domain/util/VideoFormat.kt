package com.github.luthfipun.mediaplayer.domain.util

sealed class VideoFormat {
    object DASH: VideoFormat()
    object HLS: VideoFormat()
    object SMOOTHSTREAMING: VideoFormat()
    object PROGRESSIVE: VideoFormat()
    object RTSP: VideoFormat() // not yet used for now
}

fun setVideoFormat(fileName: String): VideoFormat{
    return when {
        fileName.endsWith(".mpd", true) -> VideoFormat.DASH
        fileName.endsWith(".m3u8", true) -> VideoFormat.HLS
        fileName.endsWith("/Manifest", true) -> VideoFormat.SMOOTHSTREAMING
        else -> VideoFormat.PROGRESSIVE
    }
}