package com.github.luthfipun.mediaplayer.domain.util

import android.net.Uri
import com.github.luthfipun.mediaplayer.domain.model.VideoItem
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ext.ima.ImaServerSideAdInsertionUriBuilder
import com.google.android.exoplayer2.util.MimeTypes


fun setMediaSourceItems(mediaItems: List<VideoItem>): List<MediaItem> {
    return mediaItems.map { videoItem -> setMediaSource(videoItem) }
}

fun setMediaSource(videoItem: VideoItem): MediaItem {

    // setting clipping configuration
    val clippingConfiguration = MediaItem.ClippingConfiguration.Builder()
    videoItem.clipStartPositionMs?.let { clippingConfiguration.setStartPositionMs(it) }
    videoItem.clipEndPositionMs?.let { clippingConfiguration.setEndPositionMs(it) }

    // setting mediaItem configuration
    val mediaItem = MediaItem.Builder()
        .setUri(Uri.parse(videoItem.uri))

    if (videoItem.adTagUri != null){
        // setting ads configuration
        val adsConfiguration = MediaItem.AdsConfiguration.Builder(Uri.parse(videoItem.adTagUri)).build()
        mediaItem.setAdsConfiguration(adsConfiguration)
    }

    if (videoItem.drmScheme != null){
        // setting drm license configuration
        val drmConfiguration = MediaItem.DrmConfiguration.Builder(setDrmSchemeType(videoItem.drmScheme!!))
            .setLicenseUri(videoItem.drmLicenseUri)
            .build()
        mediaItem.setDrmConfiguration(drmConfiguration)
    }

    mediaItem.setClippingConfiguration(clippingConfiguration.build())

    if (videoItem.adTagUri?.startsWith("ssai://") == true){
        return MediaItem.fromUri(ImaServerSideAdInsertionUriBuilder().setAssetKey(videoItem.adTagUri).build())
    }

    return when(setVideoFormat(videoItem.uri!!)){
        VideoFormat.DASH -> mediaItem.setMimeType(MimeTypes.APPLICATION_MPD).build()
        VideoFormat.HLS -> mediaItem.setMimeType(MimeTypes.APPLICATION_M3U8).build()
        VideoFormat.PROGRESSIVE -> mediaItem.setMimeType(MimeTypes.BASE_TYPE_APPLICATION).build()
        VideoFormat.RTSP -> mediaItem.setMimeType(MimeTypes.APPLICATION_RTSP).build()
        VideoFormat.SMOOTHSTREAMING -> mediaItem.setMimeType(MimeTypes.APPLICATION_SS).build()
    }
}
