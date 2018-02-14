package com.github.plnice.archmigration.main.presenter

import android.content.Context
import android.text.format.DateUtils
import javax.inject.Inject

class DateTimeFormatter
@Inject constructor(private val context: Context) {

    fun formatDateTime(millis: Long): String {
        return DateUtils.formatDateTime(
                context,
                millis,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME)
    }

}
