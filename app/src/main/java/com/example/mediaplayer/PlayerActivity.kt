package com.example.mediaplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class PlayerActivity : AppCompatActivity() {

    private lateinit var play: Button
    private var mediaPlayer = MediaPlayer()
    private var playerState = STATE_DEFAULT

    var url = "http://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview112/v4/ac/c7/d1/acc7d13f-6634-495f-caf6-491eccb505e8/mzaf_4002676889906514534.plus.aac.p.m4a"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        play = findViewById(R.id.playButton)

        preparePlayer()

        play.setOnClickListener {
            playbackControl()
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun preparePlayer() {
        try {
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                play.isEnabled = true
                playerState = STATE_PREPARED
            }
            mediaPlayer.setOnCompletionListener {
                play.text = "PLAY"
                playerState = STATE_PREPARED
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error playing audio file", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun startPlayer() {
        try {
            mediaPlayer.start()
            play.text = "PAUSE"
            playerState = STATE_PLAYING
        } catch (e: Exception) {
            Toast.makeText(this, "Error playing audio file", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun pausePlayer() {
        try {
            mediaPlayer.pause()
            play.text = "PLAY"
            playerState = STATE_PAUSED
        } catch (e: Exception) {
            Toast.makeText(this, "Error pausing audio file", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun playbackControl() {
        when(playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }
}