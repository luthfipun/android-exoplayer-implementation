package com.github.luthfipun.mediaplayer

import com.github.luthfipun.mediaplayer.domain.model.*
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_global_data(){

        val videoData = mutableListOf<Video>()
        videoData.add(Video("test", listOf()))

        val playlistData = mutableListOf<Playlist>()
        playlistData.add(Playlist("test", listOf()))

        val videoItemData = mutableListOf<VideoItem>()
        videoItemData.add(VideoItem())

        when(val globalData = setGlobalData(GlobalData.VideoData(videoData))){
            is GlobalData.PlaylistData -> {
                assertEquals(globalData.playlists, playlistData)
            }
            is GlobalData.VideoData -> {
                assertEquals(globalData.videos, videoData)
            }
            is GlobalData.VideoItemData -> {
                assertEquals(globalData.videoItem, videoItemData)
            }
        }
    }
}