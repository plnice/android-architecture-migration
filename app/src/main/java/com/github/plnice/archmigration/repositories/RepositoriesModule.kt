package com.github.plnice.archmigration.repositories

import android.content.ContentResolver
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v4.app.LoaderManager
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    fun provideLoaderManager(activity: FragmentActivity): LoaderManager = activity.supportLoaderManager

    @Provides
    fun provideContentResolver(context: Context): ContentResolver = context.contentResolver

    @Provides
    fun provideMessagesRepository(contentProviderMessagesRepository: ContentProviderMessagesRepository): MessagesRepository =
            contentProviderMessagesRepository

}
