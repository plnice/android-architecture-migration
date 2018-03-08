package com.github.plnice.archmigration.repositories

import com.github.plnice.archmigration.roomdatabase.RoomDatabaseModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RoomDatabaseModule::class])
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideMessagesRepository(roomMessagesRepository: RoomMessagesRepository): MessagesRepository =
            roomMessagesRepository

}
