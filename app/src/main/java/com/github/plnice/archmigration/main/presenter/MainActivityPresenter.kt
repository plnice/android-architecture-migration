package com.github.plnice.archmigration.main.presenter

import com.github.plnice.archmigration.main.MainActivityMvp
import com.github.plnice.archmigration.main.MainActivityMvp.Model.ModelState
import com.github.plnice.archmigration.main.MainActivityMvp.View.ViewState
import com.github.plnice.archmigration.utils.SchedulersProvider
import com.github.plnice.archmigration.utils.plusAssign
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivityPresenter
@Inject constructor(private val model: MainActivityMvp.Model,
                    private val view: MainActivityMvp.View,
                    private val messageViewDataConverter: MessageViewDataConverter,
                    private val schedulersProvider: SchedulersProvider) : MainActivityMvp.Presenter {

    private val composite = CompositeDisposable()

    override fun onStart() {

        composite += model
                .getState()
                .distinctUntilChanged()
                .map { modelStateToViewState(it) }
                .observeOn(schedulersProvider.main)
                .subscribe {
                    view.setViewState(it)
                }

        composite += view
                .getSendButtonClicks()
                .filter { it.isNotBlank() }
                .map { with(messageViewDataConverter) { it.toMessage() } }
                .observeOn(schedulersProvider.io)
                .subscribe {
                    model.storeMessage(it)
                    view.clearEditText()
                }

        composite += view
                .onMessageSwipedOut()
                .observeOn(schedulersProvider.io)
                .subscribe {
                    model.deleteMessage(it)
                }
    }

    private fun modelStateToViewState(modelState: ModelState): ViewState {
        return when (modelState) {
            ModelState.Loading -> ViewState.Loading
            is ModelState.Loaded -> ViewState.Loaded(with(messageViewDataConverter) { modelState.messages.toViewData() })
        }
    }

    override fun onStop() {
        composite.clear()
    }

}