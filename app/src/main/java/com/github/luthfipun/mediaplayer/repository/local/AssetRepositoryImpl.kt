package com.github.luthfipun.mediaplayer.repository.local

import android.content.Context
import com.github.luthfipun.mediaplayer.domain.util.VideoType
import com.github.luthfipun.mediaplayer.domain.util.VideoTypeFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import men.ngopi.zain.jsonloaderlibrary.JSONLoader
import men.ngopi.zain.jsonloaderlibrary.StringLoaderListener
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AssetRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context
) : AssetRepository {

    override suspend fun getJSONString(videoType: VideoType): Result<String> {
        val fileName = when(videoType){
            VideoType.DashHls -> VideoTypeFile.DASH_HSL.getFileName()
            VideoType.Misc -> VideoTypeFile.MISC.getFileName()
            VideoType.Playlist -> VideoTypeFile.PLAYLIST.getFileName()
            VideoType.WithAds -> VideoTypeFile.WITH_ADS.getFileName()
        }

        return withContext(Dispatchers.IO){
            suspendCoroutine { continuation ->
                JSONLoader.with(context)
                    .fileName(fileName)
                    .get(object : StringLoaderListener {
                        override fun onResponse(response: String?) {
                            if (!response.isNullOrEmpty()){
                                continuation.resume(Result.success(response))
                            }else{
                                continuation.resumeWithException(Exception("data is empty!"))
                            }
                        }

                        override fun onFailure(error: IOException?) {
                            continuation.resumeWithException(Exception(error?.message))
                        }
                    })
            }
        }
    }
}