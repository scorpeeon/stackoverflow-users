package com.scrpn.stackoverflowusers.interactor

import com.scrpn.stackoverflowusers.RxSchedulersOverrideRule
import com.scrpn.stackoverflowusers.domain.InMemoryDataSource
import com.scrpn.stackoverflowusers.network.NetworkDataSource
import com.scrpn.stackoverflowusers.network.model.User
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

class ApiInteractorTest {
    @get:Rule
    var rxScheduleOverriderRule = RxSchedulersOverrideRule()

    @Mock
    lateinit var networkDataSource: NetworkDataSource

    @Mock
    lateinit var inMemoryDataSource: InMemoryDataSource

    lateinit var apiInteractor : ApiInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        apiInteractor = ApiInteractor(networkDataSource, inMemoryDataSource)
    }

    @After
    fun clearMocks() {
        Mockito.framework().clearInlineMocks()
    }

    @Test
    fun loadsUsers() {

        Mockito.`when`(networkDataSource.getUsers()).thenReturn(Observable.just(networkUsers))
        Mockito.`when`(inMemoryDataSource.users).thenReturn(domainUsers)

        apiInteractor.getUsers().test()
            .assertNoErrors()
            .awaitCount(1)
            .assertValue(domainUsers)
            .assertComplete()

        Mockito.verify(networkDataSource).getUsers()
        Mockito.verify(inMemoryDataSource, Mockito.atLeast(1)).users
    }

    @Test
    fun failToLoadUsers() {

        val error = RuntimeException("error")
        Mockito.`when`(networkDataSource.getUsers()).thenReturn(Observable.error(error))
        Mockito.`when`(inMemoryDataSource.users).thenReturn(domainUsers)

        apiInteractor.getUsers().test()
            .assertError(error)
            .awaitCount(1)

        Mockito.verify(networkDataSource).getUsers()
        Mockito.verify(inMemoryDataSource, Mockito.never()).users
    }

    @Test
    fun getLocalUsers() {

        Mockito.`when`(inMemoryDataSource.users).thenReturn(domainUsers)

        apiInteractor.getLocalUsers()

        Mockito.verify(inMemoryDataSource).users
    }

    private val networkUsers = listOf(
        User(
            userId = 1,
            displayName = "John Smith",
            creationDate = Date(),
            location = "London",
            reputation = 9000,
            profileImage = "http://sample.org/a.jpg"
        ),
        User(
            userId = 2,
            displayName = "John Rambo",
            creationDate = Date(),
            location = "US",
            reputation = 19000,
            profileImage = "http://sample.org/c.jpg"
        ),
        User(
            userId = 3,
            displayName = "John Hello",
            creationDate = Date(),
            location = "Singapore",
            reputation = 29000,
            profileImage = "http://sample.org/b.jpg"
        ))

    private val domainUsers = listOf(
        com.scrpn.stackoverflowusers.domain.model.User(
            userId = networkUsers[0].userId,
            displayName = networkUsers[0].displayName,
            creationDate = networkUsers[0].creationDate,
            location = networkUsers[0].location,
            reputation = networkUsers[0].reputation,
            profileImage = networkUsers[0].profileImage,
            blocked = false,
            following = false
        ),
        com.scrpn.stackoverflowusers.domain.model.User(
            userId = networkUsers[1].userId,
            displayName = networkUsers[1].displayName,
            creationDate = networkUsers[1].creationDate,
            location = networkUsers[1].location,
            reputation = networkUsers[1].reputation,
            profileImage = networkUsers[1].profileImage,
            blocked = false,
            following = true
        ),
        com.scrpn.stackoverflowusers.domain.model.User(
            userId = networkUsers[2].userId,
            displayName = networkUsers[2].displayName,
            creationDate = networkUsers[2].creationDate,
            location = networkUsers[2].location,
            reputation = networkUsers[2].reputation,
            profileImage = networkUsers[2].profileImage,
            blocked = true,
            following = false
        )
    )
}