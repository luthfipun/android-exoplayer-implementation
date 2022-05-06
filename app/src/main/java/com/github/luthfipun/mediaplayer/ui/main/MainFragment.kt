package com.github.luthfipun.mediaplayer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.luthfipun.mediaplayer.databinding.FragmentMainBinding
import com.github.luthfipun.mediaplayer.domain.model.*
import com.github.luthfipun.mediaplayer.domain.util.VideoType
import com.github.luthfipun.mediaplayer.domain.util.setAlert
import com.github.luthfipun.mediaplayer.ui.adapter.GlobalAdapter
import com.github.luthfipun.mediaplayer.ui.more.MoreActivity

class MainFragment: Fragment(), GlobalAdapter.GlobalAdapterListener {

    private var sourceVideoType: VideoType = VideoType.DashHls

    companion object{
        private const val SOURCE_TYPE = "SOURCE_TYPE"
        fun sourceType(sourceType: VideoType) = MainFragment().apply {
            arguments = bundleOf(SOURCE_TYPE to sourceType)
        }
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    private val globalAdapter = GlobalAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        initView()
        initBundle()
    }

    private fun subscribeObservers() {
        viewModel.videoData.observe(viewLifecycleOwner) { dataState ->
            dataState.onSuccess { videos ->
                displayVideoData(videos)
            }

            dataState.onFailure {
                context?.setAlert(it.message)
            }
        }

        viewModel.playlistData.observe(viewLifecycleOwner) { dataState ->
            dataState.onSuccess { playLists ->
                displayPlaylistData(playLists)
            }

            dataState.onFailure {
                context?.setAlert(it.message)
            }
        }
    }

    private fun displayPlaylistData(playLists: PlayLists) {
        val globalDataSet = GlobalData.PlaylistData(playLists.playlists)
        globalAdapter.setData(globalDataSet)
    }

    private fun displayVideoData(videos: Videos) {
        val globalDataSet = GlobalData.VideoData(videos.videos)
        globalAdapter.setData(globalDataSet)
    }

    private fun initView() {
        binding.recyclerView.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = globalAdapter
        }
        globalAdapter.globalAdapterListener = this
    }

    private fun initBundle() {
        sourceVideoType = arguments?.get(SOURCE_TYPE) as VideoType
        when(sourceVideoType){
            VideoType.DashHls -> viewModel.setStateEvent(MainStateEvent.GetDashHlsEvent)
            VideoType.Misc -> viewModel.setStateEvent(MainStateEvent.GetMiscEvent)
            VideoType.Playlist -> viewModel.setStateEvent(MainStateEvent.GetPlaylistEvent)
            VideoType.WithAds -> viewModel.setStateEvent(MainStateEvent.GetWithAdsEvent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun playlistData(playlist: Playlist) {
        context?.let { startActivity(MoreActivity.createIntent(it, playlist)) }
    }

    override fun videoData(video: Video) {
        context?.let { startActivity(MoreActivity.createIntent(it, video)) }
    }

    override fun videoItemData(videoItem: VideoItem) {}
    override fun playlistItemData(playlistItem: PlaylistItem) {}
}