package com.shaadi.shaadi_assignment.service

import com.shaadi.shaadi_assignment.model.UserApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api/")
    fun getUserList(@Query("results")  results:String): Call<UserApiResponse>
}