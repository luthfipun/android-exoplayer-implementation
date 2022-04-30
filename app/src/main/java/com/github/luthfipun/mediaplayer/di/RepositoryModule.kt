package com.github.luthfipun.mediaplayer.di

import com.github.luthfipun.mediaplayer.repository.MainRepository
import com.github.luthfipun.mediaplayer.repository.MainRepositoryImpl
import com.github.luthfipun.mediaplayer.repository.local.AssetRepository
import com.github.luthfipun.mediaplayer.repository.local.AssetRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository =
        mainRepositoryImpl

    @Singleton
    @Provides
    fun provideAssetRepository(assetRepositoryImpl: AssetRepositoryImpl): AssetRepository =
        assetRepositoryImpl
}