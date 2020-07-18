package com.shaadi.shaadi_assignment.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shaadi.shaadi_assignment.model.UserApiResponse
import com.shaadi.shaadi_assignment.service.RetrofitService

class UserViewModel: ViewModel() {

    private val mService  = RetrofitService()
    fun loadUserList(results: String): MutableLiveData<UserApiResponse>? {
        return mService.loadUserList(results)
    }
}