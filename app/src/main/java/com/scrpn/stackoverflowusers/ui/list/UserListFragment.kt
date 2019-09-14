package com.scrpn.stackoverflowusers.ui.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.scrpn.stackoverflowusers.AppComponent
import com.scrpn.stackoverflowusers.R
import com.scrpn.stackoverflowusers.network.model.User
import com.scrpn.stackoverflowusers.ui.BaseFragment
import com.scrpn.stackoverflowusers.ui.details.UserDetailsFragment
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.item_list.*
import javax.inject.Inject

class UserListFragment: BaseFragment(), UserListScreen, OnUserSelectedListener {

    private val TAG = UserListFragment::class.java.simpleName

    @Inject
    lateinit var presenter: UserListPresenter

    override fun injectDependencies(injector: AppComponent) {
        injector.inject(this)
    }

    override val layoutResource: Int
        get() = R.layout.fragment_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        toolbar.title = getString(R.string.app_name)
    }

    override fun onResume() {
        super.onResume()
        presenter.attachScreen(this)
        presenter.refreshItems()
    }

    override fun onPause() {
        presenter.detachScreen()
        super.onPause()
    }

    private fun setupRecyclerView() {
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorAccent
            )
        )
        swipeRefreshLayout.setOnRefreshListener { presenter.refreshItems() }
    }

    override fun onUserSelected(userId: Long) {
        navigateToFragment(UserDetailsFragment.newInstance(userId))
    }

    override fun showLoading(loading: Boolean) {
        swipeRefreshLayout.isRefreshing = loading
    }

    override fun onUsersLoaded(users: List<User>?) {
        viewFlipper.displayedChild = 1
        itemListRecyclerView.adapter = UserRecyclerViewAdapter(requireContext(), users ?: ArrayList(), this)
    }

    override fun onLoadFailed() {
        viewFlipper.displayedChild = 0
        Toast.makeText(requireContext(), R.string.error_loading_users, Toast.LENGTH_LONG).show()
        Log.e(TAG, "Loading list failed")
    }

    override fun onConnectionAvailabilityChanged(available: Boolean) {
        if (available) {
            presenter.refreshItems()
        }
    }
}