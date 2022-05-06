package com.github.luthfipun.mediaplayer.domain.util


enum class VideoTypeFile {
    DASH_HSL {
        override fun getFileName() = "dash_hls.json"
    },
    MISC {
        override fun getFileName() = "misc.json"
    },
    PLAYLIST {
        override fun getFileName() = "playlists.json"
    },
    WITH_ADS {
        override fun getFileName() = "with_ads.json"
    };

    abstract fun getFileName(): String
}