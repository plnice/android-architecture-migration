package com.github.plnice.archmigration.repositories

import android.support.v4.app.FragmentActivity
import com.github.plnice.archmigration.model.Message
import com.github.plnice.archmigration.utils.RxLoaderManager
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import java.text.SimpleDateFormat
import java.util.*

@RunWith(RobolectricTestRunner::class)
class ContentProviderMessagesRepositoryTest {

    private lateinit var dateFormat: SimpleDateFormat
    private lateinit var messagesRepository: MessagesRepository

    @Before
    fun setUp() {
        val activity = Robolectric.setupActivity(FragmentActivity::class.java)
        val loaderManager = activity.supportLoaderManager
        val contentResolver = activity.contentResolver
        val rxLoaderManager = RxLoaderManager(activity, loaderManager)

        dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        messagesRepository = ContentProviderMessagesRepository(rxLoaderManager, contentResolver)
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
