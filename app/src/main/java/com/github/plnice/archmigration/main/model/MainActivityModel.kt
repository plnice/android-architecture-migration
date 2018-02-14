package com.github.plnice.archmigration.main.model

import com.github.plnice.archmigration.main.MainActivityMvp
import com.github.plnice.archmigration.main.MainActivityMvp.Model.ModelState
import com.github.plnice.archmigration.main.MainActivityMvp.Model.ModelState.Loaded
import com.github.plnice.archmigration.main.MainActivityMvp.Model.ModelState.Loading
import com.github.plnice.archmigration.model.Message
import com.github.plnice.archmigration.repositories.MessagesRepository
import io.reactivex.Flowable
import javax.inject.Inject

class MainActivityModel
@Inject constructor(private val messagesRepository: MessagesRepository) : MainActivityMvp.Model {

    override fun getState(): Flowable<ModelState> {
        return messagesRepository
                .getMessages()
                .map<ModelState> { Loaded(it) }
                .startWith(Loading)
    }

    override fun storeMessage(message: Message) {
        messagesRepository.storeMessage(message)
    }

    override fun deleteMessage(id: Long) {
        messagesRepository.deleteMessage(id)
    }

}
