package com.example.startzplayassignment.ui.player

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.startzplayassignment.R
import com.example.startzplayassignment.databinding.ActivityFullScreenVideoBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

class FullScreenVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullScreenVideoBinding
    private var exoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exoPlayer = ExoPlayer.Builder(this).build()
        val videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"

        val mediaItem = MediaItem.fromUri(videoUrl)

        val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(mediaItem)
        exoPlayer!!.setMediaSource(mediaSource)
        exoPlayer!!.prepare()
        binding.videoView.player = exoPlayer

        exoPlayer!!.playWhenReady = true
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        exoPlayer?.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        exoPlayer = null
    }
}
