package com.example.userprofileassignment.profile

import com.example.userprofileassignment.profile.model.UserProfile
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {


@GET
suspend fun getProfile(
    @Url fullUrl: String
): UserProfile

}
