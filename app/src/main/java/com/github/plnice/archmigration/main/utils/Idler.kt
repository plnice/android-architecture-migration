package com.github.plnice.archmigration.main.utils

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.idling.CountingIdlingResource
import javax.inject.Inject

interface Idler {
    fun register(idlingRegistry: IdlingRegistry)
    fun unregister(idlingRegistry: IdlingRegistry)
    fun idleNow()
    fun increment()
    fun decrement()
}

class MainActivityIdler
@Inject constructor() : Idler {

    private val idlingResource = CountingIdlingResource("MainActivityIdlingResource")

    override fun register(idlingRegistry: IdlingRegistry) {
        idlingRegistry.register(idlingResource)
    }

    override fun unregister(idlingRegistry: IdlingRegistry) {
        idlingRegistry.unregister(idlingResource)
    }

    override fun idleNow() {
        while (!idlingResource.isIdleNow) {
            synchronized(idlingResource) {
                while (!idlingResource.isIdleNow) {
                    idlingResource.decrement()
                }
            }
        }
    }

    override fun increment() {
        idlingResource.increment()
    }

    override fun decrement() {
        idlingResource.decrement()
    }
}
