package com.app.work.demo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.app.work.demo.R
import com.app.work.demo.model.UserEntity
import kotlinx.android.synthetic.main.raw_users.view.*

class UsersAdapter(var mContext: Fragment, var list: List<UserEntity>) :
    RecyclerView.Adapter<UsersAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.raw_users, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, pos: Int) {
        val model = list[pos]
        holder.itemView.tvUserInfo.text = model.email + "\n" + model.phone + "\n" + model.gender
    }
}

