package com.github.luthfipun.mediaplayer.repository

import com.github.luthfipun.mediaplayer.repository.local.AssetRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val assetRepository: AssetRepository
) : MainRepository