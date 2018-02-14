package com.github.plnice.archmigration.repositories

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.support.v4.content.CursorLoader
import androidx.database.getLong
import androidx.database.getString
import com.github.plnice.archmigration.database.ArchMigrationContentProvider
import com.github.plnice.archmigration.database.ArchMigrationContract.Messages
import com.github.plnice.archmigration.model.Message
import com.github.plnice.archmigration.roomdatabase.ArchMigrationRoomDatabase
import com.github.plnice.archmigration.utils.RxLoaderManager
import com.github.plnice.archmigration.utils.toSequence
import io.reactivex.Flowable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

interface MessagesRepository {
    fun getMessages(): Flowable<List<Message>>
    fun storeMessage(message: Message)
    fun deleteMessage(id: Long)
}

class ContentProviderMessagesRepository
@Inject constructor(private val rxLoaderManager: RxLoaderManager,
                    private val contentResolver: ContentResolver) : MessagesRepository {

    companion object {
        private const val LOADER_MESSAGES = 0

        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    }

    override fun getMessages(): Flowable<List<Message>> {
        return rxLoaderManager
                .createLoaderFlowable(LOADER_MESSAGES) { context ->
                    CursorLoader(context).apply { uri = ArchMigrationContentProvider.CONTENT_URI }
                }
                .map {
                    it.toSequence { it.toMessage() }.toList()
                }
    }

    override fun storeMessage(message: Message) {
        contentResolver.insert(
                ArchMigrationContentProvider.CONTENT_URI,
                message.toContentValues())
    }

    override fun deleteMessage(id: Long) {
        contentResolver.delete(
                ArchMigrationContentProvider.CONTENT_URI,
                "${Messages.COLUMN_ID} = ?",
                arrayOf("$id")
        )
    }

    private fun Cursor.toMessage() = with(this) {
        Message(getLong(Messages.COLUMN_ID),
                getString(Messages.COLUMN_CREATED_AT).let { dateFormat.parse(it) },
                getString(Messages.COLUMN_MESSAGE))
    }

    private fun Message.toContentValues() = ContentValues().apply {
        put(Messages.COLUMN_CREATED_AT, dateFormat.format(createdAt))
        put(Messages.COLUMN_MESSAGE, message)
    }
}

class RoomMessagesRepository
@Inject constructor(private val database: ArchMigrationRoomDatabase) : MessagesRepository {

    override fun getMessages(): Flowable<List<Message>> {
        return database.messageDao().getAll()
    }

    override fun storeMessage(message: Message) {
        database.messageDao().insert(message)
    }

    override fun deleteMessage(id: Long) {
        database.messageDao().delete(Message(id))
    }
}
