package com.example.infinitscroll.di

import android.content.Context
import androidx.room.Room
import com.example.infinitscroll.data.local.GithubDatabase
import com.example.infinitscroll.util.Constants.GITHUB_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext
        context: Context
    ): GithubDatabase {
        return Room.databaseBuilder(
            context,
            GithubDatabase::class.java,
            GITHUB_DATABASE
        ).build()
    }
}