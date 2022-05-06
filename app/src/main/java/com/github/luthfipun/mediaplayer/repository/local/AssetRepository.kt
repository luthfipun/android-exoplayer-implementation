package com.github.luthfipun.mediaplayer.repository.local

import com.github.luthfipun.mediaplayer.domain.util.VideoType

interface AssetRepository {
    suspend fun getJSONString(videoType: VideoType): Result<String>
}