package com.github.plnice.archmigration.main.view.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.github.plnice.archmigration.main.MainActivityMvp.View.MessageViewData
import com.github.plnice.archmigration.main.MainActivityMvp.View.ViewState
import com.github.plnice.archmigration.main.MainActivityMvp.View.ViewState.Loaded
import com.github.plnice.archmigration.main.MainActivityMvp.View.ViewState.Loading

class MainEpoxyController : TypedEpoxyController<ViewState>() {

    override fun buildModels(data: ViewState) {
        when (data) {
            Loading -> buildLoadingStateModels()
            is Loaded -> buildLoadedStateModels(data.messages)
        }
    }

    private fun buildLoadingStateModels() {
        loading { id("loading") }
    }

    private fun buildLoadedStateModels(messages: List<MessageViewData>) {
        when {
            messages.isEmpty() -> emptyState { id("empty state") }
            else -> {
                messages.forEach {
                    message {
                        id(it.id)
                        createdAt(it.createdAt)
                        message(it.message)
                    }
                }
                emptySpace {
                    id("empty_space")
                }
            }
        }
    }
}
