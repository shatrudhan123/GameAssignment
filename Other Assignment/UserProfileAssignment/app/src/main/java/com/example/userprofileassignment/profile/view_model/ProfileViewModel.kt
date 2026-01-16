package com.example.userprofileassignment.profile.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gis_app.feature.constant_data.ApiData
import com.example.userprofileassignment.profile.RetrofitClient
import com.example.userprofileassignment.profile.model.UserProfile
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    private val _profile = MutableLiveData<UserProfile>()
    val profile: LiveData<UserProfile> = _profile


    fun fetchProfile() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getProfile(ApiData.USER_PROFILE)
                _profile.value = response

            } catch (e: Exception) {
                Log.e(TAG, "API error", e)
            }
        }
    }

}
