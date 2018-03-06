package com.github.plnice.archmigration.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.plnice.archmigration.main.utils.Idler
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var view: MainActivityMvp.View

    @Inject
    lateinit var presenter: MainActivityMvp.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        with(lifecycle) {
            addObserver(view)
            addObserver(presenter)
        }
    }

    fun getIdler(): Idler {
        return presenter.getIdler()
    }

}
