package com.shaadi.shaadi_assignment.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.shaadi.shaadi_assignment.R
import com.shaadi.shaadi_assignment.model.UserListModel
import com.shaadi.shaadi_assignment.viewmodel.UserViewModel
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : AppCompatActivity(), UserListAdapter.UserAdapterListener {
    override fun onStatusClicked(userListModel: UserListModel) {
        realm?.executeTransaction { realm ->

            realm.insertOrUpdate(userListModel)
        }
    }

    var realm: Realm? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        realm = Realm.getDefaultInstance()
        realm?.executeTransaction { realm ->

            var userListModels = realm?.where<UserListModel>()!!.findAll()
            if (userListModels.size > 0) {
                var userList = realm.copyFromRealm(userListModels)
                loadListData(userList)
            } else {
                val userViewModel = ViewModelProviders.of(this@UserListActivity).get(UserViewModel::class.java)
                userViewModel.loadUserList("10")?.observe(this, Observer { apiResponse ->
                    apiResponse?.let {
                        saveOnLocal(apiResponse?.results)
                        loadListData(apiResponse?.results)
                    } ?: run {
                        userList.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        txtNoResult.visibility = View.VISIBLE
                    }
                })
            }
        }

    }

    private fun saveOnLocal(results: List<UserListModel>?) {
        realm?.executeTransaction { realm ->
            realm.copyToRealm(results)
        }
    }

    private fun loadListData(results: List<UserListModel>?) {
        txtNoResult.visibility = View.GONE
        progressBar.visibility = View.GONE
        userList.visibility = View.VISIBLE
        val userListAdapter = UserListAdapter()
        userListAdapter.userListModels = results
        userListAdapter.activity = this
        userListAdapter.userAdapterListener = this
        userList.adapter = userListAdapter
        userList.layoutManager = GridLayoutManager(this, 2)
    }
}
