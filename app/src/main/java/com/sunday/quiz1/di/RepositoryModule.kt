package com.sunday.quiz1.di

import com.sunday.quiz1.data.repository.RepositoryImpl
import com.sunday.quiz1.data.source.provider.IProvider
import com.sunday.quiz1.data.source.provider.ProviderImpl
import com.sunday.quiz1.domain.repository.IRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(
        repositoryImpl: RepositoryImpl
    ): IRepository

    @Binds
    abstract fun provideRepository2(
        repositoryImpl: ProviderImpl
    ): IProvider
}