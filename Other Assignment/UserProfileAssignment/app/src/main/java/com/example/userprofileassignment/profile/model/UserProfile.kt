package com.example.userprofileassignment.profile.model

data class UserProfile(
    val name: String,
    val username: String,
    val avatar: String,
    val location: Location,
    val followers: Int,
    val following: Int,
    val website: String,
    val social: Social
)

data class Location(
    val city: String,
    val country: String
)

data class Social(
    val instagram: String,
    val facebook: String,
    val website: String
)
