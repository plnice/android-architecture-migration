package com.github.plnice.archmigration.main

import android.arch.lifecycle.LifecycleObserver
import com.github.plnice.archmigration.main.utils.Idler
import com.github.plnice.archmigration.model.Message
import io.reactivex.Flowable

interface MainActivityMvp {

    interface Model {
        fun getState(): Flowable<ModelState>
        fun storeMessage(message: Message)
        fun deleteMessage(id: Long)

        sealed class ModelState {
            object Loading : ModelState()
            data class Loaded(val messages: List<Message>) : ModelState()
        }
    }

    interface Presenter : LifecycleObserver {
        fun getIdler(): Idler
    }

    interface View : LifecycleObserver {
        fun setViewState(viewState: ViewState)

        fun getSendButtonClicks(): Flowable<String>
        fun onMessageSwipedOut(): Flowable<Long>

        fun clearEditText()

        sealed class ViewState {
            object Loading : ViewState()
            data class Loaded(val messages: List<MessageViewData>) : ViewState()
        }

        data class MessageViewData(val id: Long, val createdAt: String, val message: String)
    }

}
