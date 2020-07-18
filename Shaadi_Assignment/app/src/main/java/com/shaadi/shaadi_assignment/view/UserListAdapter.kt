package com.shaadi.shaadi_assignment.view

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.shaadi.shaadi_assignment.R
import com.shaadi.shaadi_assignment.model.UserListModel
import kotlinx.android.synthetic.main.user_list_item.view.*

class UserListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var userListModels: List<UserListModel>? = null
    var activity: Activity? = null
    var userAdapterListener: UserAdapterListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, null)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {

        return userListModels!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder)
            holder.bindItems(userListModels!![position])
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(userListModel: UserListModel) {
            Glide.with(activity).load(userListModel.picture?.large).into(itemView.imgUser)
            itemView.txtUser.text = userListModel.name?.first + " " + userListModel.name?.last
            if (userListModel.userStatus == -1) {
                itemView.imgAccept.visibility = View.VISIBLE
                itemView.imgReject.visibility = View.VISIBLE
                itemView.txtStatus.visibility = View.GONE
                itemView.imgReject.setOnClickListener(View.OnClickListener { view ->
                    userListModel.userStatus = 0
                    notifyDataSetChanged()
                    showToast(activity?.resources?.getString(R.string.member_rejected))
                    userAdapterListener?.onStatusClicked(userListModel)
                })
                itemView.imgAccept.setOnClickListener(View.OnClickListener { view ->
                    userListModel.userStatus = 1
                    notifyDataSetChanged()
                    showToast(activity?.resources?.getString(R.string.member_accepted))
                    userAdapterListener?.onStatusClicked(userListModel)
                })
            } else if (userListModel.userStatus == 1) {
                itemView.imgAccept.visibility = View.INVISIBLE
                itemView.imgReject.visibility = View.INVISIBLE
                itemView.txtStatus.visibility = View.VISIBLE
                itemView.txtStatus.setTextColor(activity?.resources!!.getColor(R.color.red))
                itemView.txtStatus.text = activity?.resources?.getString(R.string.member_accepted)
            } else {
                itemView.imgAccept.visibility = View.INVISIBLE
                itemView.imgReject.visibility = View.INVISIBLE
                itemView.txtStatus.visibility = View.VISIBLE
                itemView.txtStatus.setTextColor(activity?.resources!!.getColor(R.color.grey))
                itemView.txtStatus.text = activity?.resources?.getString(R.string.member_rejected)
            }
        }
    }
    fun showToast(txtToast:String?){
        val toast=Toast.makeText(
            activity,
            txtToast,
            Toast.LENGTH_LONG
        )
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()

    }

    interface UserAdapterListener {
        fun onStatusClicked(userListModel: UserListModel)
    }
}