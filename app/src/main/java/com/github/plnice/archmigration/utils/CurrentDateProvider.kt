package com.github.plnice.archmigration.utils

import java.util.*
import javax.inject.Inject

interface CurrentDateProvider {
    fun getCurrentDate(): Date
}

class CalendarCurrentDateProvider
@Inject constructor() : CurrentDateProvider {

    override fun getCurrentDate(): Date {
        return Calendar.getInstance().time
    }
}
