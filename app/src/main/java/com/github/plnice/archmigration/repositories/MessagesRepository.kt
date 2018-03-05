package com.github.plnice.archmigration.repositories

import com.github.plnice.archmigration.model.Message
import com.github.plnice.archmigration.roomdatabase.ArchMigrationRoomDatabase
import io.reactivex.Flowable
import javax.inject.Inject

interface MessagesRepository {
    fun getMessages(): Flowable<List<Message>>
    fun storeMessage(message: Message)
    fun deleteMessage(id: Long)
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
