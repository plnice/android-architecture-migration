package com.github.plnice.archmigration.main

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import com.github.plnice.archmigration.ActivityScope
import com.github.plnice.archmigration.main.MainActivityModule.MainActivityProvidesModule
import com.github.plnice.archmigration.main.model.MainActivityModel
import com.github.plnice.archmigration.main.model.MainActivityModelFactory
import com.github.plnice.archmigration.main.presenter.MainActivityPresenter
import com.github.plnice.archmigration.main.utils.Idler
import com.github.plnice.archmigration.main.utils.MainActivityIdler
import com.github.plnice.archmigration.main.view.MainActivityView
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [MainActivityProvidesModule::class])
abstract class MainActivityModule {

    @Binds
    abstract fun provideFragmentActivity(mainActivity: MainActivity): FragmentActivity

    @Binds
    @ActivityScope
    abstract fun provideView(view: MainActivityView): MainActivityMvp.View

    @Binds
    @ActivityScope
    abstract fun providePresenter(presenter: MainActivityPresenter): MainActivityMvp.Presenter

    @Binds
    @ActivityScope
    abstract fun provideIdler(idler: MainActivityIdler): Idler

    @Module
    class MainActivityProvidesModule {

        @Provides
        @ActivityScope
        fun provideModel(activity: FragmentActivity, factory: MainActivityModelFactory): MainActivityMvp.Model =
                ViewModelProviders.of(activity, factory).get(MainActivityModel::class.java)

    }
}
