package com.github.luthfipun.mediaplayer

import com.github.luthfipun.mediaplayer.domain.model.*
import org.junit.Assert
import org.junit.Test

class DataUnitTest {

    @Test
    fun test_global_data(){
        val videoData = mutableListOf<Video>()
        videoData.add(Video("test", listOf()))

        val playlistData = mutableListOf<Playlist>()
        playlistData.add(Playlist("test", listOf()))

        val videoItemData = mutableListOf<VideoItem>()
        videoItemData.add(VideoItem())

        val playlistItemData = mutableListOf<PlaylistItem>()
        playlistItemData.add(PlaylistItem("test", videoItemData))

        when(val globalData = setGlobalData(GlobalData.VideoData(videoData))){
            is GlobalData.PlaylistData -> {
                Assert.assertEquals(globalData.playlists, playlistData)
            }
            is GlobalData.VideoData -> {
                Assert.assertEquals(globalData.videos, videoData)
            }
            is GlobalData.VideoItemData -> {
                Assert.assertEquals(globalData.videoItem, videoItemData)
            }
            is GlobalData.PlaylistItemData -> {
                Assert.assertEquals(globalData.playlistItem, playlistItemData)
            }
        }
    }
}