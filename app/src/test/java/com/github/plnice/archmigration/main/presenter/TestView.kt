package com.github.plnice.archmigration.main.presenter

import com.github.plnice.archmigration.main.MainActivityMvp
import com.github.plnice.archmigration.main.MainActivityMvp.View.ViewState
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class TestView : MainActivityMvp.View {

    val viewStates: PublishSubject<ViewState> = PublishSubject.create()
    val sendButtonClicks: PublishSubject<String> = PublishSubject.create()
    val messageSwipedOut: PublishSubject<Long> = PublishSubject.create()
    val clearedEditText: PublishSubject<Unit> = PublishSubject.create()

    override fun onCreate() {
    }

    override fun setViewState(viewState: ViewState) {
        viewStates.onNext(viewState)
    }

    override fun getSendButtonClicks(): Flowable<String> {
        return sendButtonClicks.toFlowable(BackpressureStrategy.LATEST)
    }

    override fun onMessageSwipedOut(): Flowable<Long> {
        return messageSwipedOut.toFlowable(BackpressureStrategy.LATEST)
    }

    override fun clearEditText() {
        clearedEditText.onNext(Unit)
    }
}
