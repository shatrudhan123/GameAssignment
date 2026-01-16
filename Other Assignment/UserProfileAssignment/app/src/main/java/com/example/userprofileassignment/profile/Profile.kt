package com.example.userprofileassignment.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.userprofileassignment.R
import com.example.userprofileassignment.profile.view_model.ProfileViewModel
import com.example.userprofileassignment.utils.ProgressOverlay
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.OnBackPressedCallback

class Profile : ComponentActivity() {

    private lateinit var ivAvatar: ImageView

    private lateinit var tvName: TextView
    private lateinit var tvUsername: TextView
    private lateinit var loadingOverlay: ProgressOverlay
    private lateinit var tvLocation: TextView
    private lateinit var tvFollowers: TextView
    private lateinit var tvFollowing: TextView
    private lateinit var iv_instagram: ImageView
    private lateinit var iv_website: ImageView
    private lateinit var iv_facebook: ImageView
    private var doubleBackToExitPressedOnce = false
    companion object {
        private const val TAG = "UserProfileData"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }
        loadingOverlay = ProgressOverlay(this)
        init()
        loadingOverlay.show("Please wait...")
        Log.d(TAG, "API call started")
        val viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        viewModel.profile.observe(this) { profile ->
            Log.d(TAG, "Profile loaded successfully")
            Log.d(TAG, "Observed profile: $profile")
            loadingOverlay.hide()
            tvName.text = profile.name
            tvUsername.text = "${profile.username}"
            tvLocation.text = "${profile.location.city}, ${profile.location.country}"
            tvFollowers.text = "${profile.followers} Followers"
            tvFollowing.text = "${profile.following} Following"

            Glide.with(this)
                .load(profile.avatar)
                .circleCrop()
                .into(ivAvatar)
        }

        viewModel.fetchProfile()
        iv_website.setOnClickListener {
            val websiteUrl = "https://www.immortal-technologies.com/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(intent)
        }

        iv_instagram.setOnClickListener {

            val instagramUsername = "its_shatrudhan_rajput"

            val appUri = Uri.parse("http://instagram.com/_u/$instagramUsername")
            val webUri = Uri.parse("https://www.instagram.com/$instagramUsername/")

            try {
                val intent = Intent(Intent.ACTION_VIEW, appUri)
                intent.setPackage("com.instagram.android")
                startActivity(intent)
            } catch (e: Exception) {
                val intent = Intent(Intent.ACTION_VIEW, webUri)
                startActivity(intent)
            }
        }
        iv_facebook.setOnClickListener {

            val facebookProfileId = "shatrudhanrajput123@gmail.com"
            val appUri = Uri.parse("fb://profile/$facebookProfileId")
            val webUri = Uri.parse("https://www.facebook.com/$facebookProfileId")

            try {
                val intent = Intent(Intent.ACTION_VIEW, appUri)
                intent.setPackage("com.facebook.katana")
                startActivity(intent)
            } catch (e: Exception) {
                val intent = Intent(Intent.ACTION_VIEW, webUri)
                startActivity(intent)
            }
        }




    }

    private fun init() {
        ivAvatar = findViewById(R.id.iv_avatar)

        tvName = findViewById(R.id.tv_name)
        tvUsername = findViewById(R.id.tv_username)
        tvLocation = findViewById(R.id.tv_location)
        tvFollowers = findViewById(R.id.tv_followers)
        tvFollowing = findViewById(R.id.tv_following)
        iv_instagram = findViewById(R.id.iv_instagram)
        iv_website = findViewById(R.id.iv_website)
        iv_facebook = findViewById(R.id.iv_facebook)

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (doubleBackToExitPressedOnce) {
                        finish()
                        return
                    }

                    doubleBackToExitPressedOnce = true
                    Toast.makeText(
                        this@Profile,
                        "Press back again to exit",
                        Toast.LENGTH_SHORT
                    ).show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        doubleBackToExitPressedOnce = false
                    }, 2000)
                }
            }
        )
    }
    override fun onDestroy() {
        super.onDestroy()
        if (::loadingOverlay.isInitialized) {
            loadingOverlay.hide()
        }
    }

}