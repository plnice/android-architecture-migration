package com.github.plnice.archmigration.repositories

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.github.plnice.archmigration.model.Message
import com.github.plnice.archmigration.roomdatabase.ArchMigrationRoomDatabase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.text.SimpleDateFormat
import java.util.*

@RunWith(RobolectricTestRunner::class)
class RoomMessagesRepositoryTest {

    private lateinit var dateFormat: SimpleDateFormat
    private lateinit var messagesRepository: MessagesRepository

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

        val roomDatabase = Room
                .inMemoryDatabaseBuilder(
                        RuntimeEnvironment.application,
                        ArchMigrationRoomDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        messagesRepository = RoomMessagesRepository(roomDatabase)
    }

    @Test
    fun shouldStoreMessage() {
        val date = Calendar.getInstance().apply { clear(); set(2018, 0, 2, 12, 34, 56) }.time
        val message = Message(null, date, "Message content")
        val storedMessage = message.copy(id = 1)

        messagesRepository
                .storeMessage(message)

        messagesRepository
                .getMessages()
                .distinctUntilChanged()
                .test()
                .assertValue(listOf(storedMessage))
    }

    @Test
    fun shouldDeleteMessage() {
        val date = Calendar.getInstance().apply { clear(); set(2018, 0, 2, 12, 34, 56) }.time
        val message = Message(null, date, "Message content")
        val storedMessage = message.copy(id = 1)

        messagesRepository
                .storeMessage(message)

        messagesRepository
                .deleteMessage(storedMessage.id!!)

        messagesRepository
                .getMessages()
                .distinctUntilChanged()
                .test()
                .assertValue(emptyList())
    }
}
