package com.github.plnice.archmigration.main.presenter

import com.github.plnice.archmigration.main.MainActivityMvp
import com.github.plnice.archmigration.main.MainActivityMvp.Model.ModelState
import com.github.plnice.archmigration.model.Message
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class TestModel : MainActivityMvp.Model {

    var stateFlowable: Flowable<ModelState> = Flowable.empty()

    val messageStored: PublishSubject<Message> = PublishSubject.create()
    val messageDeleted: PublishSubject<Long> = PublishSubject.create()

    override fun getState() = stateFlowable

    override fun storeMessage(message: Message) {
        messageStored.onNext(message)
    }

    override fun deleteMessage(id: Long) {
        messageDeleted.onNext(id)
    }
}
