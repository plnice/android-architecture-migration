package com.github.plnice.archmigration

import android.app.Activity
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector

class ArchMigrationApplication : DaggerApplication() {

    var overriddenActivityInjector: DispatchingAndroidInjector<Activity>? = null

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return overriddenActivityInjector ?: super.activityInjector()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .apply { inject(this@ArchMigrationApplication) }
    }

}
