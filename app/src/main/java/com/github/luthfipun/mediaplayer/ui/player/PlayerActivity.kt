package com.github.luthfipun.mediaplayer.ui.player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex
import com.github.luthfipun.mediaplayer.databinding.ActivityPlayerBinding
import com.github.luthfipun.mediaplayer.domain.model.PlaylistItem
import com.github.luthfipun.mediaplayer.domain.model.VideoItem
import com.github.luthfipun.mediaplayer.domain.util.setAlert
import com.github.luthfipun.mediaplayer.domain.util.setMediaSourceItems
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.ext.ima.ImaServerSideAdInsertionMediaSource
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ads.AdsLoader
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource.HttpDataSourceException
import com.google.android.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException


class PlayerActivity : AppCompatActivity(), Player.Listener {

    companion object{
        private const val VIDEO_ITEM = "VIDEO_ITEM"
        private const val PLAYLIST_ITEM = "PLAYLIST_ITEM"

        fun createIntent(context: Context, videoItem: VideoItem): Intent{
            return Intent(context, PlayerActivity::class.java).apply {
                putExtra(VIDEO_ITEM, videoItem)
            }
        }

        fun createIntent(context: Context, playlistItem: PlaylistItem): Intent{
            return Intent(context, PlayerActivity::class.java).apply {
                putExtra(PLAYLIST_ITEM, playlistItem)
            }
        }
    }

    private lateinit var binding: ActivityPlayerBinding
    private var exoPlayer: ExoPlayer? = null
    private var clientSideAdsLoader: AdsLoader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        MultiDex.install(this)

        setContentView(binding.root)
        initIntent()
    }

    private fun initIntent() {
        if (intent.hasExtra(VIDEO_ITEM)){
            prepareVideoItem((intent.getSerializableExtra(VIDEO_ITEM) as VideoItem))
        }else{
            preparePlaylistItem((intent.getSerializableExtra(PLAYLIST_ITEM) as PlaylistItem))
        }
    }

    private fun preparePlaylistItem(playlistItem: PlaylistItem) {
        setupPlayer(playlistItem.videoItems)
    }

    private fun prepareVideoItem(videoItem: VideoItem) {
        setupPlayer(listOf(videoItem))
    }

    private fun setupPlayer(mediaItems: List<VideoItem>){

        val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(this)
        val mediaSourceFactory = DefaultMediaSourceFactory(dataSourceFactory)
        val adsLoader: ImaServerSideAdInsertionMediaSource.AdsLoader = ImaServerSideAdInsertionMediaSource.AdsLoader
            .Builder(this, binding.exoPlayerView)
            .build()
        val adsMediaSourceFactory: ImaServerSideAdInsertionMediaSource.Factory = ImaServerSideAdInsertionMediaSource.Factory(adsLoader, mediaSourceFactory)

        mediaSourceFactory.setAdsLoaderProvider{ setClientSideAdsLoader() }
        mediaSourceFactory.setAdViewProvider(binding.exoPlayerView)
        mediaSourceFactory.setServerSideAdInsertionMediaSourceFactory(adsMediaSourceFactory)

        exoPlayer = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .setTrackSelector(DefaultTrackSelector(this))
            .build()

        binding.exoPlayerView.player = exoPlayer
        exoPlayer?.setMediaItems(setMediaSourceItems(mediaItems))
        exoPlayer?.playWhenReady = true
        exoPlayer?.prepare()
        exoPlayer?.addListener(this)
    }

    private fun setClientSideAdsLoader(): AdsLoader {
        if (clientSideAdsLoader == null){
            clientSideAdsLoader = ImaAdsLoader.Builder(this).build()
        }
        clientSideAdsLoader?.setPlayer(exoPlayer)
        return clientSideAdsLoader as AdsLoader
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)

        val cause: Throwable = error.cause!!

        setAlert(error.message)

        if (cause is HttpDataSourceException) {
            if (cause is InvalidResponseCodeException) {
                setAlert(cause.message)
            } else {
                setAlert("player error!")
            }
        }
    }

    private fun releasePlayer(){
        clientSideAdsLoader?.setPlayer(null)
        binding.exoPlayerView.player = null
        exoPlayer?.release()
        exoPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        clientSideAdsLoader?.release()
    }

    override fun onStart() {
        super.onStart()
        binding.exoPlayerView.onResume()
    }

    override fun onResume() {
        super.onResume()
        if (exoPlayer != null){
            exoPlayer?.play()
        }
    }

    override fun onPause() {
        super.onPause()
        if (exoPlayer != null){
            exoPlayer?.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        if (exoPlayer != null){
            exoPlayer?.release()
            releasePlayer()
        }
    }
}