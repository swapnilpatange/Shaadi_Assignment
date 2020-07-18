package com.shaadi.shaadi_assignment.service

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.shaadi.shaadi_assignment.model.UserApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    val liveUserResponse: MutableLiveData<UserApiResponse> = MutableLiveData()
    companion object Factory {
        var gson = GsonBuilder().setLenient().create()
        fun create(): ApiInterface {
            Log.e("retrofit","create")
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://randomuser.me/")
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
    fun loadUserList(results: String): MutableLiveData<UserApiResponse>? {
        val retrofitCall  = create().getUserList(results)
        retrofitCall.enqueue(object : Callback<UserApiResponse> {
            override fun onFailure(call: Call<UserApiResponse>, t: Throwable?) {
                liveUserResponse?.value=null
                Log.e("on Failure :", "retrofit error")
            }
            override fun onResponse(call: Call<UserApiResponse>, response: retrofit2.Response<UserApiResponse>) {
                val list  = response.body()
                liveUserResponse?.value = list

            }
        })
        return liveUserResponse
    }
}