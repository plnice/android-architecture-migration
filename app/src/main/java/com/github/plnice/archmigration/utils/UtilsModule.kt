package com.github.plnice.archmigration.utils

import dagger.Binds
import dagger.Module

@Module
abstract class UtilsModule {

    @Binds
    abstract fun provideCurrentDateProvider(calendarCurrentDateProvider: CalendarCurrentDateProvider): CurrentDateProvider

}
