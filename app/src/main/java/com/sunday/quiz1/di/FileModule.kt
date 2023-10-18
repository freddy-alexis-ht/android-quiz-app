package com.sunday.quiz1.di

import android.content.Context
import com.sunday.quiz1.data.source.file.FileImpl
import com.sunday.quiz1.data.source.file.IFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FileModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): IFile {
        return FileImpl(context)
    }
}