package com.github.plnice.archmigration.utils

import android.app.Activity
import com.github.plnice.archmigration.main.MainActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import javax.inject.Provider

fun <T : Activity> createFakeActivityInjector(block: T.() -> Unit): DispatchingAndroidInjector<Activity> {
    val injector = AndroidInjector<T> { it.block() }
    val factory = AndroidInjector.Factory<T> { injector }

    val clazz = MainActivity::class.java as Class<out Activity>
    val provider = Provider<AndroidInjector.Factory<out Activity>> { factory }

    return DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(mapOf(clazz to provider))
}
