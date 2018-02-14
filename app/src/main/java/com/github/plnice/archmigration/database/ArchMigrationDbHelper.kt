package com.github.plnice.archmigration.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.github.plnice.archmigration.database.ArchMigrationContract.Messages
import javax.inject.Inject

class ArchMigrationDbHelper
@Inject constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "arch_migration"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE =
                "CREATE TABLE ${Messages.TABLE_NAME} (" +
                        "${Messages.COLUMN_ID} INTEGER PRIMARY KEY," +
                        "${Messages.COLUMN_CREATED_AT} DATETIME DEFAULT (datetime('now','localtime'))," +
                        "${Messages.COLUMN_MESSAGE} TEXT NOT NULL" +
                        ")"

        private const val SQL_DELETE =
                "DROP TABLE IF EXISTS ${Messages.TABLE_NAME}"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO properly handle data migration
        db.execSQL(SQL_DELETE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

}
