package com.github.plnice.archmigration.repositories

import com.github.plnice.archmigration.roomdatabase.RoomDatabaseModule
import dagger.Module
import dagger.Provides

@Module(includes = [RoomDatabaseModule::class])
class RepositoriesModule {

    @Provides
    fun provideMessagesRepository(roomMessagesRepository: RoomMessagesRepository): MessagesRepository =
            roomMessagesRepository

}
