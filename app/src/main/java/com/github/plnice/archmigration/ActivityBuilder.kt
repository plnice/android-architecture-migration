package com.github.plnice.archmigration

import com.github.plnice.archmigration.main.MainActivity
import com.github.plnice.archmigration.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    @ActivityScope
    abstract fun bindMainActivity(): MainActivity

}
