package com.github.plnice.archmigration.roomdatabase

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class RoomDatabaseModule {

    @Provides
    fun provideDatabase(context: Context): ArchMigrationRoomDatabase = Room
            .databaseBuilder(
                    context.applicationContext,
                    ArchMigrationRoomDatabase::class.java,
                    "arch_migration_room")
            .build()

}
