package com.github.plnice.archmigration.utils

import android.content.Context
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Inject

class RxLoaderManager
@Inject constructor(private val context: Context,
                    private val loaderManager: LoaderManager) {

    fun <T> createLoaderFlowable(id: Int, onCreate: (Context) -> Loader<T>): Flowable<T> {
        return Flowable.create({ emitter ->
            loaderManager.initLoader(id, Bundle.EMPTY, object : LoaderManager.LoaderCallbacks<T> {
                override fun onCreateLoader(id: Int, args: Bundle?): Loader<T> {
                    return onCreate(context.applicationContext)
                }

                override fun onLoadFinished(loader: Loader<T>, data: T) {
                    emitter.onNext(data)
                }

                override fun onLoaderReset(loader: Loader<T>) {
                }
            })
        }, BackpressureStrategy.LATEST)
    }

}
