package com.github.luthfipun.mediaplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.luthfipun.mediaplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set default navigation view
        binding.bottomView.selectedItemId = R.id.nav_dash_hls
        setActionBarTitle(getString(R.string.nav_dash_hls))

        // setup bottom navigation
        setupBottomNavSelected()
        setupBottomNavReSelected()
    }

    private fun setupBottomNavSelected(){
        binding.bottomView.setOnItemSelectedListener { menuItem ->
            setActionBarTitle(menuItem.title.toString())
            return@setOnItemSelectedListener when(menuItem.itemId){
                R.id.nav_dash_hls -> {
                    true
                }
                R.id.nav_ads -> {
                    true
                }
                R.id.nav_playlists -> {
                    true
                }
                R.id.nav_misc -> {
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
                R.id.nav_dash_hls -> {

                }
                R.id.nav_ads -> {

                }
                R.id.nav_playlists -> {

                }
                R.id.nav_misc -> {

                }
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