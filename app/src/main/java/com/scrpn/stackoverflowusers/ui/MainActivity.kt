package com.scrpn.stackoverflowusers.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.scrpn.stackoverflowusers.AppComponent
import com.scrpn.stackoverflowusers.R
import com.scrpn.stackoverflowusers.ui.list.UserListFragment


class MainActivity : BaseActivity() {

    private var manager: ConnectivityManager? = null
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            runOnUiThread {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                if (currentFragment is UserListFragment) {
                    currentFragment.onConnectionAvailabilityChanged(true)
                }
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            runOnUiThread {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                if (currentFragment is UserListFragment) {
                    currentFragment.onConnectionAvailabilityChanged(false)
                }
            }
        }
    }

    override val layoutResource: Int
        get() = R.layout.activity_main

    override fun injectDependencies(injector: AppComponent) {
        injector.inject(this)
    }

    fun loadFragment(fragment: Fragment) {
        if (supportFragmentManager.isStateSaved) {
            return
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment, fragment.nameTag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(fragment.nameTag)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportFragmentManager.backStackEntryCount < 1) {
            loadFragment(UserListFragment())
        }

        manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            manager?.registerDefaultNetworkCallback(networkCallback)
        }
    }

    override fun onDestroy() {
        manager?.unregisterNetworkCallback(networkCallback)
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            pop()
        } else {
            this.finish()
        }
    }

    fun pop(): Boolean {
        if (supportFragmentManager.isStateSaved) {
            return false
        }

        return supportFragmentManager.popBackStackImmediate()
    }

    internal val Fragment.nameTag: String
        get() {
            return this::class.qualifiedName!!
        }
}
