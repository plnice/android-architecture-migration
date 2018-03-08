package com.github.plnice.archmigration.main.model

import android.arch.lifecycle.ViewModel
import com.github.plnice.archmigration.main.MainActivityMvp
import com.github.plnice.archmigration.main.MainActivityMvp.Model.ModelState
import com.github.plnice.archmigration.main.MainActivityMvp.Model.ModelState.Loaded
import com.github.plnice.archmigration.main.MainActivityMvp.Model.ModelState.Loading
import com.github.plnice.archmigration.model.Message
import com.github.plnice.archmigration.repositories.MessagesRepository
import com.github.plnice.archmigration.utils.TypedViewModelFactory
import io.reactivex.Flowable
import javax.inject.Inject

class MainActivityModel(private val messagesRepository: MessagesRepository) : MainActivityMvp.Model, ViewModel() {

    private val stateFlowable: Flowable<ModelState> by lazy {
        messagesRepository
                .getMessages()
                .map<ModelState> { Loaded(it) }
                .startWith(Loading)
    }

    override fun getState(): Flowable<ModelState> {
        return stateFlowable
    }

    override fun storeMessage(message: Message) {
        messagesRepository.storeMessage(message)
    }

    override fun deleteMessage(id: Long) {
        messagesRepository.deleteMessage(id)
    }

}

class MainActivityModelFactory
@Inject constructor(private val messagesRepository: MessagesRepository) : TypedViewModelFactory<MainActivityModel> {
    override fun create(): MainActivityModel = MainActivityModel(messagesRepository)
}
