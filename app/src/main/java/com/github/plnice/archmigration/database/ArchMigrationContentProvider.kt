package com.github.plnice.archmigration.database

import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import com.github.plnice.archmigration.database.ArchMigrationContract.Messages
import dagger.android.DaggerContentProvider
import javax.inject.Inject

class ArchMigrationContentProvider : DaggerContentProvider() {

    companion object {
        private const val AUTHORITY = "com.github.plnice.archmigration.provider"

        private const val PATH_MESSAGES = "messages"

        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH_MESSAGES")

        private const val MESSAGES = 1

        private val URI_MATCHER = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, PATH_MESSAGES, MESSAGES)
        }
    }

    @Inject
    lateinit var dbHelper: SQLiteOpenHelper

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        return when (URI_MATCHER.match(uri)) {
            MESSAGES -> dbHelper
                    .writableDatabase
                    .insert(Messages.TABLE_NAME, null, values)
                    .let { Uri.parse("content://$AUTHORITY/$PATH_MESSAGES/$it") }
                    .also { context.contentResolver.notifyChange(uri, null) }
            else -> throw IllegalArgumentException("Illegal uri: $uri")
        }
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        return when (URI_MATCHER.match(uri)) {
            MESSAGES -> dbHelper
                    .readableDatabase
                    .query(Messages.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
                    .apply { setNotificationUri(context.contentResolver, uri) }
            else -> throw IllegalArgumentException("Illegal uri: $uri")
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return when(URI_MATCHER.match(uri)) {
            MESSAGES -> dbHelper
                    .writableDatabase
                    .update(Messages.TABLE_NAME, values, selection, selectionArgs)
                    .also { context.contentResolver.notifyChange(uri, null) }
            else -> throw IllegalArgumentException("Illegal uri: $uri")
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return when(URI_MATCHER.match(uri)) {
            MESSAGES -> dbHelper
                    .writableDatabase
                    .delete(Messages.TABLE_NAME, selection, selectionArgs)
                    .also { context.contentResolver.notifyChange(uri, null) }
            else -> throw IllegalArgumentException("Illegal uri: $uri")
        }
    }

    override fun getType(uri: Uri): String {
        return when(URI_MATCHER.match(uri)) {
            MESSAGES -> "vnd.android.cursor.dir/vnd.$AUTHORITY.messages"
            else -> throw IllegalArgumentException("Illegal uri: $uri")
        }
    }

}
