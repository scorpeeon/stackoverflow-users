package com.scrpn.stackoverflowusers.ui.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.scrpn.stackoverflowusers.AppComponent
import com.scrpn.stackoverflowusers.R
import com.scrpn.stackoverflowusers.domain.model.User
import com.scrpn.stackoverflowusers.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject

class UserDetailsFragment: BaseFragment(), UserDetailsScreen {

    companion object{
        const val ARG_USER_ID = "ARG_USER_ID"

        fun newInstance(userId: Long): UserDetailsFragment {
            val args = Bundle()
            args.putSerializable(ARG_USER_ID, userId)
            val fragment = UserDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var presenter: UserDetailsPresenter

    private var user: User? = null

    override val layoutResource: Int
        get() = R.layout.fragment_details

    override fun injectDependencies(injector: AppComponent) {
        injector.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_USER_ID)) {
                var userId = it.getLong(ARG_USER_ID)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            activity.setSupportActionBar(detailToolbar)
            activity.supportActionBar?.let() {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowHomeEnabled(true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.attachScreen(this)
    }

    override fun onPause() {
        presenter.detachScreen()
        super.onPause()
    }
}