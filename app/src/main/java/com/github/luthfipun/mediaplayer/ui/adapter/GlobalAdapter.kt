package com.github.luthfipun.mediaplayer.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.luthfipun.mediaplayer.databinding.ItemViewBinding
import com.github.luthfipun.mediaplayer.domain.model.*

class GlobalAdapter: RecyclerView.Adapter<GlobalAdapter.ViewHolder>() {

    private var globalData: GlobalData? = null
    var globalAdapterListener: GlobalAdapterListener? = null

    interface GlobalAdapterListener{
        fun playlistData(playlist: Playlist)
        fun videoData(video: Video)
        fun videoItemData(videoItem: VideoItem)
        fun playlistItemData(playlistItem: PlaylistItem)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(globalData: GlobalData){
        this.globalData = globalData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(globalData){
            is GlobalData.PlaylistData -> holder.bindPlaylist((globalData as GlobalData.PlaylistData).playlists[position], globalAdapterListener)
            is GlobalData.VideoData -> holder.bindVideo((globalData as GlobalData.VideoData).videos[position], globalAdapterListener)
            is GlobalData.VideoItemData -> holder.bindVideItem((globalData as GlobalData.VideoItemData).videoItem[position], globalAdapterListener)
            is GlobalData.PlaylistItemData -> holder.bindPlaylistItem((globalData as GlobalData.PlaylistItemData).playlistItem[position], globalAdapterListener)
            else -> {}
        }
    }

    override fun getItemCount(): Int {
        return when(globalData){
            is GlobalData.PlaylistData -> (globalData as GlobalData.PlaylistData).playlists.size
            is GlobalData.VideoData -> (globalData as GlobalData.VideoData).videos.size
            is GlobalData.VideoItemData -> (globalData as GlobalData.VideoItemData).videoItem.size
            is GlobalData.PlaylistItemData -> (globalData as GlobalData.PlaylistItemData).playlistItem.size
            null -> 0
        }
    }

    class ViewHolder(
        private val itemViewBinding: ItemViewBinding
    ): RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bindPlaylist(playlist: Playlist, globalAdapterListener: GlobalAdapterListener?) {
            itemViewBinding.txtTitle.text = playlist.name
            itemViewBinding.container.setOnClickListener {
                globalAdapterListener?.playlistData(playlist)
            }
        }

        fun bindVideo(video: Video, globalAdapterListener: GlobalAdapterListener?) {
            itemViewBinding.txtTitle.text = video.name
            itemViewBinding.container.setOnClickListener {
                globalAdapterListener?.videoData(video)
            }
        }

        fun bindVideItem(videoItem: VideoItem, globalAdapterListener: GlobalAdapterListener?) {
            itemViewBinding.txtTitle.text = videoItem.name
            itemViewBinding.container.setOnClickListener {
                globalAdapterListener?.videoItemData(videoItem)
            }
        }

        fun bindPlaylistItem(
            playlistItem: PlaylistItem,
            globalAdapterListener: GlobalAdapterListener?
        ) {
            itemViewBinding.txtTitle.text = playlistItem.name
            itemViewBinding.container.setOnClickListener {
                globalAdapterListener?.playlistItemData(playlistItem)
            }
        }

    }
}