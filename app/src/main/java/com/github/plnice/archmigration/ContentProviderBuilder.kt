package com.github.plnice.archmigration

import com.github.plnice.archmigration.database.ArchMigrationContentProvider
import com.github.plnice.archmigration.database.DatabaseModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ContentProviderBuilder {

    @ContributesAndroidInjector(modules = [DatabaseModule::class])
    abstract fun bindContentProvider(): ArchMigrationContentProvider

}
