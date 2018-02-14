package com.github.plnice.archmigration.main.view

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.airbnb.epoxy.EpoxyTouchHelper
import com.github.plnice.archmigration.R
import com.github.plnice.archmigration.main.MainActivity
import com.github.plnice.archmigration.main.MainActivityMvp
import com.github.plnice.archmigration.main.MainActivityMvp.View.ViewState
import com.github.plnice.archmigration.main.view.epoxy.MainEpoxyController
import com.github.plnice.archmigration.main.view.epoxy.MessageEpoxyModel
import com.github.plnice.archmigration.utils.SchedulersProvider
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivityView
@Inject constructor(private val activity: MainActivity,
                    private val schedulersProvider: SchedulersProvider) : MainActivityMvp.View {

    private lateinit var controller: MainEpoxyController

    private val messageSwipedOutSubject = PublishSubject.create<Long>()

    override fun onCreate() {
        with(activity) {
            setContentView(R.layout.activity_main)
            setSupportActionBar(toolbar)

            controller = MainEpoxyController()

            recycler_view.layoutManager = LinearLayoutManager(this).apply {
                stackFromEnd = true
            }
            recycler_view.adapter = controller.adapter

            EpoxyTouchHelper
                    .initSwiping(recycler_view)
                    .leftAndRight()
                    .withTarget(MessageEpoxyModel::class.java)
                    .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<MessageEpoxyModel>() {
                        override fun onSwipeCompleted(model: MessageEpoxyModel, itemView: View, position: Int, direction: Int) {
                            messageSwipedOutSubject.onNext(model.id())
                        }
                    })
        }
    }

    override fun setViewState(viewState: ViewState) {
        controller.setData(viewState)
    }

    override fun getSendButtonClicks(): Flowable<String> {
        return RxView
                .clicks(activity.send_button)
                .debounce(300, TimeUnit.MILLISECONDS, schedulersProvider.computation)
                .map { activity.edit_text.text.toString() }
                .toFlowable(BackpressureStrategy.LATEST)
    }

    override fun onMessageSwipedOut(): Flowable<Long> {
        return messageSwipedOutSubject.toFlowable(BackpressureStrategy.LATEST)
    }

    override fun clearEditText() {
        activity.edit_text.text = null
    }

}
