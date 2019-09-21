package com.scrpn.stackoverflowusers.ui.list

import com.scrpn.stackoverflowusers.RxSchedulersOverrideRule
import com.scrpn.stackoverflowusers.domain.model.User
import com.scrpn.stackoverflowusers.interactor.ApiInteractor
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.RuntimeException
import java.util.*

class UserListPresenterTest {
    @get:Rule
    var rxScheduleOverriderRule = RxSchedulersOverrideRule()

    @Mock
    lateinit var userListScreen: UserListScreen

    @Mock
    lateinit var apiInteractor: ApiInteractor

    lateinit var userListPresenter: UserListPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userListPresenter = UserListPresenter(apiInteractor)
    }

    @After
    fun clearMocks() {
        Mockito.framework().clearInlineMocks()
    }

    @Test
    fun loadsUsers() {
        val testUsers = listOf(
            User(
                userId = 1,
                displayName = "John Smith",
                creationDate = Date(),
                location = "London",
                reputation = 9000,
                profileImage = "http://sample.org/a.jpg",
                blocked = false,
                following = false
            ),
            User(
                userId = 2,
                displayName = "John Rambo",
                creationDate = Date(),
                location = "US",
                reputation = 19000,
                profileImage = "http://sample.org/c.jpg",
                blocked = false,
                following = false
            ),
            User(
                userId = 3,
                displayName = "John Hello",
                creationDate = Date(),
                location = "Singapore",
                reputation = 29000,
                profileImage = "http://sample.org/b.jpg",
                blocked = false,
                following = false
            )
        )

        Mockito.`when`(apiInteractor.getUsers()).thenReturn(Observable.just(testUsers))

        userListPresenter.attachScreen(userListScreen)
        userListPresenter.refreshItems()

        Mockito.verify(userListScreen).showLoading(true)
        Mockito.verify(userListScreen).onUsersLoaded(testUsers)
        Mockito.verify(userListScreen, Mockito.never()).onLoadFailed()
        Mockito.verify(userListScreen).showLoading(false)
    }

    @Test
    fun handeFailToLoad() {
        Mockito.`when`(apiInteractor.getUsers()).thenReturn(Observable.error(RuntimeException("error")))

        userListPresenter.attachScreen(userListScreen)
        userListPresenter.refreshItems()

        Mockito.verify(userListScreen).showLoading(true)
        Mockito.verify(userListScreen).onLoadFailed()
        Mockito.verify(userListScreen, Mockito.never()).onUsersLoaded(Mockito.anyList())
        Mockito.verify(userListScreen).showLoading(false)
    }
}