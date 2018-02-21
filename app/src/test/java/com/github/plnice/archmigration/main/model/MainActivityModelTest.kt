package com.github.plnice.archmigration.main.model

import com.github.plnice.archmigration.main.MainActivityMvp.Model.ModelState.Loaded
import com.github.plnice.archmigration.main.MainActivityMvp.Model.ModelState.Loading
import com.github.plnice.archmigration.model.Message
import com.github.plnice.archmigration.repositories.MessagesRepository
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MainActivityModelTest {

    @Mock
    private lateinit var messagesRepository: MessagesRepository

    private lateinit var mainActivityModel: MainActivityModel

    @Before
    fun setUp() {
        mainActivityModel = MainActivityModel(messagesRepository)
    }

    @Test
    fun shouldReturnCorrectStateFlowable() {
        val messagesLists = listOf(
                emptyList(),
                listOf(Message(1, Date(), "Message 1")),
                listOf(Message(1, Date(), "Message 1"), Message(2, Date(), "Message 2")))

        `when`(messagesRepository.getMessages()).thenReturn(Flowable.fromIterable(messagesLists))

        mainActivityModel
                .getState()
                .distinctUntilChanged()
                .test()
                .assertValueSequence(listOf(Loading) + messagesLists.map { Loaded(it) })
    }

    @Test
    fun shouldCallRepositoryToStoreMessage() {
        val message = Message(null, Date(), "Message content")

        mainActivityModel.storeMessage(message)

        verify(messagesRepository).storeMessage(message)
    }

    @Test
    fun shouldCallRepositoryToDeleteMessage() {
        val id = 1L

        mainActivityModel.deleteMessage(id)

        verify(messagesRepository).deleteMessage(id)
    }
}
