package com.github.plnice.archmigration.main

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.github.plnice.archmigration.ArchMigrationApplication
import com.github.plnice.archmigration.R
import com.github.plnice.archmigration.database.ArchMigrationContentProvider
import com.github.plnice.archmigration.main.model.MainActivityModel
import com.github.plnice.archmigration.main.presenter.DateTimeFormatter
import com.github.plnice.archmigration.main.presenter.MainActivityPresenter
import com.github.plnice.archmigration.main.presenter.MessageViewDataConverter
import com.github.plnice.archmigration.main.utils.MainActivityIdler
import com.github.plnice.archmigration.main.view.MainActivityView
import com.github.plnice.archmigration.repositories.ContentProviderMessagesRepository
import com.github.plnice.archmigration.utils.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers.anyOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityRule = object : ActivityTestRule<MainActivity>(MainActivity::class.java) {

        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            val application = InstrumentationRegistry.getTargetContext().applicationContext as ArchMigrationApplication
            application.overriddenActivityInjector = createFakeActivityInjector<MainActivity> { makeInjects() }
        }
    }

    private fun MainActivity.makeInjects() {
        val rxLoaderManager = RxLoaderManager(this, supportLoaderManager)
        val model = MainActivityModel(ContentProviderMessagesRepository(rxLoaderManager, contentResolver))
        val messageViewDataConverter = MessageViewDataConverter(DateTimeFormatter(this), CalendarCurrentDateProvider())
        view = MainActivityView(this, InstantSchedulersProvider())
        presenter = MainActivityPresenter(model, view, messageViewDataConverter, DefaultSchedulersProvider(), MainActivityIdler())
    }

    private val idlingRegistry: IdlingRegistry = IdlingRegistry.getInstance()

    @Before
    fun clearContentProvider() {
        activityRule.activity.contentResolver.delete(
                ArchMigrationContentProvider.CONTENT_URI, null, null)
    }

    @Before
    fun registerIdlingResource() {
        activityRule.activity.getIdler().register(idlingRegistry)
    }

    @After
    fun unregisterIdlingResource() {
        activityRule.activity.getIdler().unregister(idlingRegistry)
    }

    @Test
    fun shouldAddNewMessage() {
        val editText = withId(R.id.edit_text)
        val sendButton = withId(R.id.send_button)
        val editControls = editText to sendButton
        val item = childAtPosition(withId(R.id.recycler_view), 0)

        val newMessage = "Never give up!"

        item.checkIsValidEmptyState()
        editControls.addNewMessage(newMessage)
        item.checkIsValidMessage(newMessage)

        editText.checkEditTextCleared()
    }

    @Test
    fun shouldRemoveMessageAndDisplayEmptyStateWhenSwipedOut() {
        val editText = withId(R.id.edit_text)
        val sendButton = withId(R.id.send_button)
        val editControls = editText to sendButton
        val item = childAtPosition(withId(R.id.recycler_view), 0)

        val newMessage = "Never give up!"

        item.checkIsValidEmptyState()
        editControls.addNewMessage(newMessage)
        item.checkIsValidMessage(newMessage)

        onView(item).perform(swipeRight())
        item.checkIsValidEmptyState()
    }

    private fun Pair<Matcher<View>, Matcher<View>>.addNewMessage(newMessage: String) {
        val (editText, sendButton) = this

        onView(editText)
                .check(matches(isDisplayed()))
                .check(matches(withHint(R.string.hint)))
                .perform(typeText(newMessage))

        onView(sendButton)
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.send)))
                .perform(click())
    }

    private fun Matcher<View>.checkIsValidMessage(expectedMessage: String) {
        val createdAt = childWithId(this, R.id.created_at)
        val message = childWithId(this, R.id.message)

        onView(createdAt)
                .check(ViewAssertions.matches(isDisplayed()))
                .check(ViewAssertions.matches(anyOf(acceptableDateTimeTexts())))

        onView(message)
                .check(ViewAssertions.matches(isDisplayed()))
                .check(ViewAssertions.matches(withText(expectedMessage)))
    }

    private fun Matcher<View>.checkEditTextCleared() {
        onView(this)
                .check(ViewAssertions.matches(isDisplayed()))
                .check(ViewAssertions.matches(withText("")))
    }

    private fun Matcher<View>.checkIsValidEmptyState() {
        val icon = childWithId(this, R.id.empty_state_icon)
        val text = childWithId(this, R.id.empty_state_text)

        onView(icon)
                .check(ViewAssertions.matches(isDisplayed()))
                .check(ViewAssertions.matches(withDrawable(R.drawable.ic_message_black_48dp, tint = R.color.secondary_text)))

        onView(text)
                .check(ViewAssertions.matches(isDisplayed()))
                .check(ViewAssertions.matches(withText(R.string.empty_state)))
    }

    private fun acceptableDateTimeTexts(): List<Matcher<View>> {
        val currentCalendar = Calendar.getInstance()
        val acceptableDates = listOf(
                (currentCalendar.clone() as Calendar).apply { add(Calendar.MINUTE, -1) },
                currentCalendar)
        val formatter = DateTimeFormatter(activityRule.activity)

        return acceptableDates.map { withText(formatter.formatDateTime(it.timeInMillis)) }
    }
}
