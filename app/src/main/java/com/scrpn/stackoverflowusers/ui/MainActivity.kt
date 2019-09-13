package com.scrpn.stackoverflowusers.ui

import android.view.MenuItem

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.ListFragment

import com.scrpn.stackoverflowusers.AppComponent
import com.scrpn.stackoverflowusers.R
import com.scrpn.stackoverflowusers.ui.list.UserListFragment

class MainActivity : BaseActivity() {
    override val layoutResource: Int
        get() = R.layout.activity_main

    override fun injectDependencies(injector: AppComponent) {
        injector.inject(this)
    }

    override fun onStart() {
        super.onStart()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, UserListFragment())
            .commit()
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
