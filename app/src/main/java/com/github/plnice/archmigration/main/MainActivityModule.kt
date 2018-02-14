package com.github.plnice.archmigration.main

import android.support.v4.app.FragmentActivity
import com.github.plnice.archmigration.ActivityScope
import com.github.plnice.archmigration.main.model.MainActivityModel
import com.github.plnice.archmigration.main.presenter.MainActivityPresenter
import com.github.plnice.archmigration.main.view.MainActivityView
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun provideFragmentActivity(mainActivity: MainActivity): FragmentActivity

    @Binds
    @ActivityScope
    abstract fun provideModel(model: MainActivityModel): MainActivityMvp.Model

    @Binds
    @ActivityScope
    abstract fun provideView(view: MainActivityView): MainActivityMvp.View

    @Binds
    @ActivityScope
    abstract fun providePresenter(presenter: MainActivityPresenter): MainActivityMvp.Presenter

}
