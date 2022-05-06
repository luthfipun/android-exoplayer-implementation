package com.github.luthfipun.mediaplayer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.luthfipun.mediaplayer.domain.model.PlayLists
import com.github.luthfipun.mediaplayer.domain.model.Videos
import com.github.luthfipun.mediaplayer.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel(){

    private val _videoData = MutableLiveData<Result<Videos>>()
    val videoData: LiveData<Result<Videos>> = _videoData

    private val _playlistData = MutableLiveData<Result<PlayLists>>()
    val playlistData: LiveData<Result<PlayLists>> = _playlistData

    fun setStateEvent(mainStateEvent: MainStateEvent){
        viewModelScope.launch {
            when(mainStateEvent){
                MainStateEvent.GetDashHlsEvent -> {
                    mainRepository.getDashHlsData()
                        .onEach { dataState ->
                            _videoData.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                MainStateEvent.GetMiscEvent -> {
                    mainRepository.getMiscData()
                        .onEach { dataState ->
                            _videoData.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                MainStateEvent.GetPlaylistEvent -> {
                    mainRepository.getPlaylistData()
                        .onEach { dataState ->
                            _playlistData.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                MainStateEvent.GetWithAdsEvent -> {
                    mainRepository.getWithAdsData()
                        .onEach { dataState ->
                            _videoData.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class MainStateEvent{
    object GetDashHlsEvent: MainStateEvent()
    object GetWithAdsEvent: MainStateEvent()
    object GetPlaylistEvent: MainStateEvent()
    object GetMiscEvent: MainStateEvent()
}