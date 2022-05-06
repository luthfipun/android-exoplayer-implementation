package com.github.luthfipun.mediaplayer.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.luthfipun.mediaplayer.R
import com.github.luthfipun.mediaplayer.databinding.ActivityMainBinding
import com.github.luthfipun.mediaplayer.domain.util.VideoType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setup bottom navigation
        setupBottomNavSelected()
        setupBottomNavReSelected()

        // set default navigation view
        binding.bottomView.selectedItemId = R.id.nav_dash_hls
    }

    private fun setupBottomNavSelected(){
        binding.bottomView.setOnItemSelectedListener { menuItem ->
            setActionBarTitle(menuItem.title.toString())
            return@setOnItemSelectedListener when(menuItem.itemId){
                R.id.nav_dash_hls -> {
                    setFragment(MainFragment.sourceType(VideoType.DashHls))
                    true
                }
                R.id.nav_ads -> {
                    setFragment(MainFragment.sourceType(VideoType.WithAds))
                    true
                }
                R.id.nav_playlists -> {
                    setFragment(MainFragment.sourceType(VideoType.Playlist))
                    true
                }
                R.id.nav_misc -> {
                    setFragment(MainFragment.sourceType(VideoType.Misc))
                    true
                }
                else -> false
            }
        }
    }

    private fun setupBottomNavReSelected(){
        binding.bottomView.setOnItemReselectedListener { menuItem ->
            setActionBarTitle(menuItem.title.toString())
            when(menuItem.itemId){
                R.id.nav_dash_hls -> setFragment(MainFragment.sourceType(VideoType.DashHls))
                R.id.nav_ads -> setFragment(MainFragment.sourceType(VideoType.WithAds))
                R.id.nav_playlists -> setFragment(MainFragment.sourceType(VideoType.Playlist))
                R.id.nav_misc -> setFragment(MainFragment.sourceType(VideoType.Misc))
            }
        }
    }

    private fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title ?: getString(R.string.app_name)
    }

    private fun setFragment(fragment: Fragment){
        val frag = supportFragmentManager.beginTransaction()
        frag.replace(binding.container.id, fragment)
        frag.commit()
    }
}