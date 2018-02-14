package com.github.plnice.archmigration

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class ArchMigrationApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .apply { inject(this@ArchMigrationApplication) }
    }

}
