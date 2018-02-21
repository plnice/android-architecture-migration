package com.github.plnice.archmigration.main.presenter

import com.github.plnice.archmigration.main.MainActivityMvp.View.MessageViewData
import com.github.plnice.archmigration.model.Message
import com.github.plnice.archmigration.utils.CurrentDateProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.util.*

@RunWith(RobolectricTestRunner::class)
class MessageViewDataConverterTest {

    private lateinit var dateTimeFormatter: DateTimeFormatter

    @Mock
    private lateinit var currentDateProvider: CurrentDateProvider

    private lateinit var messageViewDataConverter: MessageViewDataConverter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val context = RuntimeEnvironment.application
        dateTimeFormatter = DateTimeFormatter(context)
        messageViewDataConverter = MessageViewDataConverter(dateTimeFormatter, currentDateProvider)
    }

    @Test
    fun shouldConvertMessagesToViewData() {
        val date1 = Calendar.getInstance().apply { clear(); set(2018, 0, 2, 12, 34, 56) }.time
        val date2 = Calendar.getInstance().apply { clear(); set(2018, 2, 4, 21, 43, 6) }.time

        val messages = listOf(
                Message(1, date1, "Message 1"),
                Message(2, date2, "Message 2"))

        val viewData = listOf(
                MessageViewData(1, "January 2, 2018, 12:34 PM", "Message 1"),
                MessageViewData(2, "March 4, 2018, 9:43 PM", "Message 2"))

        with(messageViewDataConverter) {
            assertThat(messages.toViewData()).containsAllIn(viewData).inOrder()
        }
    }

    @Test
    fun shouldConvertMessageStringToMessage() {
        val messageString = "Message 1"
        val date = Calendar.getInstance().apply { clear(); set(18, 1, 2, 12, 34, 56) }.time

        `when`(currentDateProvider.getCurrentDate()).thenReturn(date)

        with(messageViewDataConverter) {
            assertThat(messageString.toMessage()).isEqualTo(Message(null, date, messageString))
        }
    }
}
