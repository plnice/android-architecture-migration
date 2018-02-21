package com.github.plnice.archmigration.utils

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposables
import java.util.concurrent.TimeUnit

object InstantScheduler : Scheduler() {

    override fun createWorker() = object : Worker() {

        override fun schedule(run: Runnable, delay: Long, unit: TimeUnit) = run.run().let { Disposables.empty() }

        override fun isDisposed() = false

        override fun dispose() = Unit
    }
}
