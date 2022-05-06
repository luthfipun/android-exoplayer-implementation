package com.github.luthfipun.mediaplayer.repository

import com.github.luthfipun.mediaplayer.domain.model.PlayLists
import com.github.luthfipun.mediaplayer.domain.model.Videos
import com.github.luthfipun.mediaplayer.domain.util.VideoType
import com.github.luthfipun.mediaplayer.repository.local.AssetRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val assetRepository: AssetRepository
) : MainRepository{

    override suspend fun getDashHlsData() = flow<Result<Videos>> {

        val getJsonString = assetRepository.getJSONString(VideoType.DashHls)

        if (getJsonString.isSuccess){
            getJsonString.onSuccess { jsonString ->
                val videoData = Gson().fromJson(jsonString, Videos::class.java)
                emit(Result.success(videoData))
            }
        }else{
            getJsonString.onFailure { exception ->
                emit(Result.failure(exception))
            }
        }
    }

    override suspend fun getMiscData() = flow<Result<Videos>> {

        val getJsonString = assetRepository.getJSONString(VideoType.Misc)

        if (getJsonString.isSuccess){
            getJsonString.onSuccess { jsonString ->
                val videoData = Gson().fromJson(jsonString, Videos::class.java)
                emit(Result.success(videoData))
            }
        }else{
            getJsonString.onFailure { exception ->
                emit(Result.failure(exception))
            }
        }
    }

    override suspend fun getPlaylistData() = flow<Result<PlayLists>> {

        val getJsonString = assetRepository.getJSONString(VideoType.Playlist)

        if (getJsonString.isSuccess){
            getJsonString.onSuccess { jsonString ->
                val playlistData = Gson().fromJson(jsonString, PlayLists::class.java)
                emit(Result.success(playlistData))
            }
        }else{
            getJsonString.onFailure { exception ->
                emit(Result.failure(exception))
            }
        }
    }

    override suspend fun getWithAdsData() = flow<Result<Videos>> {

        val getJsonString = assetRepository.getJSONString(VideoType.WithAds)

        if (getJsonString.isSuccess){
            getJsonString.onSuccess { jsonString ->
                val videoData = Gson().fromJson(jsonString, Videos::class.java)
                emit(Result.success(videoData))
            }
        }else{
            getJsonString.onFailure { exception ->
                emit(Result.failure(exception))
            }
        }
    }
}