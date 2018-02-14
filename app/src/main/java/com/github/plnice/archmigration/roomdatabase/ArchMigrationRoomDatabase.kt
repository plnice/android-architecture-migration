package com.github.plnice.archmigration.roomdatabase

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.github.plnice.archmigration.model.Message

@Database(entities = [Message::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArchMigrationRoomDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}
