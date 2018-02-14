package com.github.plnice.archmigration

import android.app.Application
import android.content.Context
import com.github.plnice.archmigration.utils.DefaultSchedulersProvider
import com.github.plnice.archmigration.utils.SchedulersProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun provideContext(application: Application): Context

    @Binds
    abstract fun provideSchedulersProvider(defaultSchedulersProvider: DefaultSchedulersProvider): SchedulersProvider

}
