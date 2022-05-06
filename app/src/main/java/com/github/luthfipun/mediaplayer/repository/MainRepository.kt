package com.github.luthfipun.mediaplayer.repository

import com.github.luthfipun.mediaplayer.domain.model.PlayLists
import com.github.luthfipun.mediaplayer.domain.model.Videos
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getDashHlsData(): Flow<Result<Videos>>
    suspend fun getMiscData(): Flow<Result<Videos>>
    suspend fun getPlaylistData(): Flow<Result<PlayLists>>
    suspend fun getWithAdsData(): Flow<Result<Videos>>
}