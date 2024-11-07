package com.example.startzplayassignment.ui.player

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.startzplayassignment.databinding.ActivityFullScreenVideoBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

class FullScreenVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullScreenVideoBinding
    private var exoPlayer: ExoPlayer? = null
    private val videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ExoPlayer
        exoPlayer = ExoPlayer.Builder(this).build().apply {
            binding.videoView.player = this
            val mediaItem = MediaItem.fromUri(videoUrl)

            // Add User-Agent and create media source
            val dataSourceFactory = DefaultHttpDataSource.Factory()
                .setUserAgent("ExoPlayer")
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaItem)

            setMediaSource(mediaSource)
            prepare()
            playWhenReady = true

            // Add error listener for troubleshooting
            addListener(object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    Log.e("FullScreenVideoActivity", "Player error: ${error.message}")
                }
            })
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
        }
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.playWhenReady = false
    }

    override fun onResume() {
        super.onResume()
        exoPlayer?.playWhenReady = true
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        exoPlayer = null
    }
}
