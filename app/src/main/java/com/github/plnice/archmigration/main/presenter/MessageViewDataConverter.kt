package com.github.plnice.archmigration.main.presenter

import com.github.plnice.archmigration.main.MainActivityMvp.View.MessageViewData
import com.github.plnice.archmigration.model.Message
import com.github.plnice.archmigration.utils.CurrentDateProvider
import javax.inject.Inject

class MessageViewDataConverter
@Inject constructor(private val dateTimeFormatter: DateTimeFormatter,
                    private val currentDateProvider: CurrentDateProvider) {

    fun List<Message>.toViewData(): List<MessageViewData> {
        return map { it.toViewData() }
    }

    private fun Message.toViewData(): MessageViewData {
        return MessageViewData(
                id!!,
                dateTimeFormatter.formatDateTime(createdAt.time),
                message)
    }

    fun String.toMessage(): Message {
        return Message(null, currentDateProvider.getCurrentDate(), this)
    }

}
