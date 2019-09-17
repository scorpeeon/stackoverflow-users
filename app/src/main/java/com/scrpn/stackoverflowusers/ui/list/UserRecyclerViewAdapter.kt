package com.scrpn.stackoverflowusers.ui.list

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout


import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.scrpn.stackoverflowusers.R
import com.scrpn.stackoverflowusers.domain.model.User
import kotlinx.android.synthetic.main.item_list_content.view.*
import android.view.*
import androidx.appcompat.widget.PopupMenu




class UserRecyclerViewAdapter internal constructor(
    private val context: Context,
    private val listener: OnUserSelectedListener,
    private var users: List<User> = ArrayList()
) : RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener = View.OnClickListener { view ->
        val user = view.tag as User
        listener.onUserClicked(user.userId)
    }

    private val onLongClickListener = View.OnLongClickListener { view ->
        val user = view.tag as User
        val popup = PopupMenu(context, view)

        if (user.blocked) {
            popup.menu.add(context.getString(R.string.unblock))
                .setOnMenuItemClickListener {
                    listener.onSetUserBlocked(user.userId, false)
                    true
                }
        } else {
            popup.menu.add(context.getString(R.string.block))
                .setOnMenuItemClickListener {
                    listener.onSetUserBlocked(user.userId, true)
                    true
                }
        }

        if (user.following) {
            popup.menu.add(context.getString(R.string.unfollow))
                .setOnMenuItemClickListener {
                    listener.onSetUserFollowed(user.userId, false)
                    true
                }
        } else {
            popup.menu.add(context.getString(R.string.follow))
                .setOnMenuItemClickListener {
                    listener.onSetUserFollowed(user.userId, true)
                    true
                }
        }
        popup.show()
        true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        Glide.with(context).load(user.profileImage).into(holder.imageView)
        //holder.layout.isEnabled = !user.blocked
        holder.layout.alpha = if (!user.blocked) 1.0f else 0.5f
        holder.titleTextView.text = user.displayName
        holder.subtitleTextView.text = context.getString(R.string.reputation, user.reputation.toString())
        holder.followingImageView.setImageDrawable(
            context.getDrawable(
                if (user.following) android.R.drawable.btn_star_big_on
                else android.R.drawable.btn_star_big_off
            )
        )

        holder.itemView.tag = users[position]
        holder.itemView.setOnClickListener(onClickListener)
        holder.itemView.setOnLongClickListener(onLongClickListener)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun updateItems(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layout: ConstraintLayout = view.layout
        val imageView: ImageView = view.image
        val titleTextView: TextView = view.title
        val subtitleTextView: TextView = view.subtitle
        val followingImageView: ImageView = view.followingImage

    }
}
