package com.github.luthfipun.mediaplayer.repository.local

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AssetRepositoryImpl @Inject constructor(
    private val applicationContext: ApplicationContext
) : AssetRepository