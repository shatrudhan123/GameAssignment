package com.example.userprofileassignment.utils


import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.userprofileassignment.R

class ProgressOverlay(private val activity: Activity) {

    private var overlayView: View? = null
    private var backgroundView: View? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    fun show(message: String = "Please wait...") {
        mainHandler.post {
            if (activity.isFinishing || activity.isDestroyed) return@post

            val rootView = activity.findViewById<ViewGroup>(android.R.id.content)
            if (overlayView == null) {
                overlayView = LayoutInflater.from(activity)
                    .inflate(R.layout.layout_progress_overlay, rootView, false)
                rootView.addView(overlayView)
            }

            overlayView?.bringToFront()

            backgroundView = overlayView?.findViewById(R.id.fadeBackground)
            val textView = overlayView?.findViewById<TextView>(R.id.progressText)
            textView?.text = message

            overlayView?.visibility = View.VISIBLE

            backgroundView?.alpha = 0f
            backgroundView?.animate()?.alpha(1f)?.setDuration(300)?.start()
        }
    }

    fun hide() {
        mainHandler.post {
            if (activity.isFinishing || activity.isDestroyed) return@post

            overlayView?.let { overlay ->
                backgroundView = overlay.findViewById(R.id.fadeBackground)
                backgroundView?.animate()?.alpha(0f)?.setDuration(300)
                    ?.withEndAction {
                        overlay.visibility = View.GONE
                    }?.start()
            }
        }
    }
}
