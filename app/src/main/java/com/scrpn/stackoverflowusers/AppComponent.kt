package com.scrpn.stackoverflowusers

import android.app.Application


import com.scrpn.stackoverflowusers.interactor.InteractorModule
import com.scrpn.stackoverflowusers.interactor.NetworkInteractor
import com.scrpn.stackoverflowusers.network.NetworkModule
import com.scrpn.stackoverflowusers.ui.MainActivity
import com.scrpn.stackoverflowusers.ui.details.UserDetailsFragment
import com.scrpn.stackoverflowusers.ui.list.UserListFragment

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, InteractorModule::class])
interface AppComponent {

    fun inject(application: StackOverflowUsersApplication)

    fun inject(networkInteractor: NetworkInteractor)
    fun inject(mainActivity: MainActivity)
    fun inject(userListFragment: UserListFragment)
    fun inject(userDetailsFragment: UserDetailsFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
