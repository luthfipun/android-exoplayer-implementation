package com.github.luthfipun.mediaplayer.ui.more

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.luthfipun.mediaplayer.R
import com.github.luthfipun.mediaplayer.databinding.ActivityMoreBinding
import com.github.luthfipun.mediaplayer.domain.model.*
import com.github.luthfipun.mediaplayer.ui.adapter.GlobalAdapter
import com.github.luthfipun.mediaplayer.ui.player.PlayerActivity

class MoreActivity : AppCompatActivity(), GlobalAdapter.GlobalAdapterListener {

    companion object{
        private const val NO_PLAYLIST = "NO_PLAYLIST"
        private const val WITH_PLAYLIST = "WITH_PLAYLIST"

        fun createIntent(context: Context, video: Video): Intent{
            return Intent(context, MoreActivity::class.java).apply {
                putExtra(NO_PLAYLIST, video)
            }
        }

        fun createIntent(context: Context, playlist: Playlist): Intent{
            return Intent(context, MoreActivity::class.java).apply {
                putExtra(WITH_PLAYLIST, playlist)
            }
        }
    }

    private val globalAdapter = GlobalAdapter()
    private lateinit var binding: ActivityMoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initIntent()
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

    private fun initIntent() {
        if (intent.hasExtra(NO_PLAYLIST)){
            displayVideoData((intent.getSerializableExtra(NO_PLAYLIST) as Video))
        }else{
            displayPlaylistData((intent.getSerializableExtra(WITH_PLAYLIST) as Playlist))
        }
    }

    private fun displayPlaylistData(playlist: Playlist) {
        setActionBarTitle(playlist.name)
        val globalDataSet = GlobalData.PlaylistItemData(playlist.playlistItems)
        globalAdapter.setData(globalDataSet)
    }

    private fun displayVideoData(video: Video) {
        setActionBarTitle(video.name)
        val globalDataSet = GlobalData.VideoItemData(video.videoItems)
        globalAdapter.setData(globalDataSet)
    }

    private fun setActionBarTitle(title: String?){
        supportActionBar?.apply {
            this.title = title ?: getString(R.string.app_name)
            this.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun playlistData(playlist: Playlist) {}

    override fun videoData(video: Video) {}

    override fun videoItemData(videoItem: VideoItem) {
        startActivity(PlayerActivity.createIntent(this, videoItem))
    }

    override fun playlistItemData(playlistItem: PlaylistItem) {
        startActivity(PlayerActivity.createIntent(this, playlistItem))
    }
}