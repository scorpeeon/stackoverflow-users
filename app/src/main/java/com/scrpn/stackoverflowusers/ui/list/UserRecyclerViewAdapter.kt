package com.scrpn.stackoverflowusers.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.scrpn.stackoverflowusers.R
import com.scrpn.stackoverflowusers.network.model.User
import kotlinx.android.synthetic.main.item_list_content.view.*

class UserRecyclerViewAdapter internal constructor(
    private val context: Context,
    private val users: List<User>,
    private val listener: OnUserSelectedListener
) : RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener = View.OnClickListener { view ->
        val (_, userId) = view.tag as User
        listener.onUserSelected(userId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        Glide.with(context).load(user.profileImage).into(holder.imageView)
        holder.titleTextView.text = user.displayName
        holder.subtitleTextView.text = context.getString(R.string.reputation, user.reputation.toString())

        holder.itemView.tag = users[position]
        holder.itemView.setOnClickListener(mOnClickListener)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.image
        val titleTextView: TextView = view.title
        val subtitleTextView: TextView = view.subtitle

    }
}
