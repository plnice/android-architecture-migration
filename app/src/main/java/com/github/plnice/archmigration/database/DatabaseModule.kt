package com.github.plnice.archmigration.database

import android.database.sqlite.SQLiteOpenHelper
import dagger.Binds
import dagger.Module

@Module
abstract class DatabaseModule {

    @Binds
    abstract fun provideSQLiteOpenHelper(archMigrationDbHelper: ArchMigrationDbHelper): SQLiteOpenHelper

}
