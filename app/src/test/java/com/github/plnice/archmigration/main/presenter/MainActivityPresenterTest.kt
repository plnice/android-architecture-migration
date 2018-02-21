package com.github.plnice.archmigration.main.presenter

import com.github.plnice.archmigration.main.MainActivityMvp.Model.ModelState
import com.github.plnice.archmigration.main.MainActivityMvp.View.MessageViewData
import com.github.plnice.archmigration.main.MainActivityMvp.View.ViewState
import com.github.plnice.archmigration.model.Message
import com.github.plnice.archmigration.utils.CurrentDateProvider
import com.github.plnice.archmigration.utils.InstantSchedulersProvider
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MainActivityPresenterTest {

    @Mock
    private lateinit var dateTimeFormatter: DateTimeFormatter

    @Mock
    private lateinit var currentDateProvider: CurrentDateProvider

    private lateinit var model: TestModel
    private lateinit var view: TestView
    private lateinit var presenter: MainActivityPresenter

    @Before
    fun setUp() {
        val messageViewDataConverter = MessageViewDataConverter(dateTimeFormatter, currentDateProvider)

        model = TestModel()
        view = TestView()
        presenter = MainActivityPresenter(model, view, messageViewDataConverter, InstantSchedulersProvider())
    }

    @Test
    fun shouldPassCorrectViewStateToView() {
        val date = Date()
        val formattedDate = "2018-01-01 12:00"
        `when`(dateTimeFormatter.formatDateTime(date.time)).thenReturn(formattedDate)

        val modelStates = listOf(
                ModelState.Loading,
                ModelState.Loaded(emptyList()),
                ModelState.Loaded(listOf(Message(1, date, "Message 1"))))

        val expectedViewStates = listOf(
                ViewState.Loading,
                ViewState.Loaded(emptyList()),
                ViewState.Loaded(listOf(MessageViewData(1, formattedDate, "Message 1"))))

        model.stateFlowable = Flowable.fromIterable(modelStates)

        val stateObserver = view.viewStates.test()

        presenter.onStart()
        presenter.onStop()

        stateObserver.assertValueSequence(expectedViewStates)
    }

    @Test
    fun shouldStoreMessageAndClearEditTextOnSendButtonClick() {
        val date = Date()
        `when`(currentDateProvider.getCurrentDate()).thenReturn(date)

        val messageString = "Message content"

        val messageStoredObserver = model.messageStored.test()
        val clearedEditTextObserver = view.clearedEditText.test()

        presenter.onStart()
        view.sendButtonClicks.onNext(messageString)
        presenter.onStop()

        messageStoredObserver.assertValue(Message(null, date, messageString))
        clearedEditTextObserver.assertValue(Unit)
    }

    @Test
    fun shouldDeleteMessageWhenSwipedOut() {
        val id = 1L

        val messageDeletedObserver = model.messageDeleted.test()

        presenter.onStart()
        view.messageSwipedOut.onNext(id)
        presenter.onStop()

        messageDeletedObserver.assertValue(id)
    }
}
