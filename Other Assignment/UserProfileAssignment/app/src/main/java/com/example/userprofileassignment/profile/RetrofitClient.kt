package com.example.userprofileassignment.profile

import com.example.gis_app.feature.constant_data.ApiData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {


    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(ApiData.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
