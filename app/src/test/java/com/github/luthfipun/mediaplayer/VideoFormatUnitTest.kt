package com.github.luthfipun.mediaplayer

import com.github.luthfipun.mediaplayer.domain.util.VideoFormat
import com.github.luthfipun.mediaplayer.domain.util.setVideoFormat
import org.junit.Test
import org.junit.Assert

class VideoFormatUnitTest {
    @Test
    fun test_video_format(){

        val file1 = "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd"
        val file2 = "https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_ts/master.m3u8"
        val file3 = "https://playready.directtaps.net/smoothstreaming/SSWSS720H264/SuperSpeedway_720.ism/Manifest"
        val file4 = "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/video-137.mp4"
        val file5 = "https://storage.googleapis.com/exoplayer-test-media-1/flac/play.flac"

        Assert.assertEquals(setVideoFormat(file1), VideoFormat.DASH)
        Assert.assertEquals(setVideoFormat(file2), VideoFormat.HLS)
        Assert.assertEquals(setVideoFormat(file3), VideoFormat.SMOOTHSTREAMING)
        Assert.assertEquals(setVideoFormat(file4), VideoFormat.PROGRESSIVE)
        Assert.assertEquals(setVideoFormat(file5), VideoFormat.PROGRESSIVE)
    }
}