package com.omao.musicplayerapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    // Variables
    var startTime = 0.0
    var finalTime = 0.0
    var forwardTime = 10000
    var backwardTime = 10000
    var oneTimeOnly = 0

    // Handler
    var handler: Handler = Handler()

    // Media Player
    var mediaPlayer = MediaPlayer()
    lateinit var timeTxt: TextView
    lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        val playButton: Button = findViewById(R.id.play_btn)
        val pauseButton: Button = findViewById(R.id.pause_btn)
        val forwardButton: Button = findViewById(R.id.forward_btn)
        val backButton: Button = findViewById(R.id.back_btn)

        val titleTxt: TextView = findViewById(R.id.song_title)
        timeTxt = findViewById(R.id.time_left)

        seekBar = findViewById<SeekBar>(R.id.seek_bar)

        // Media Player
        mediaPlayer = MediaPlayer.create(
            this,
            R.raw.mungumkuu
        )
        seekBar.isClickable = false

        // Adding functionalities for the buttons
        playButton.setOnClickListener() {
            mediaPlayer.start()

            finalTime = mediaPlayer.duration.toDouble()
            startTime = mediaPlayer.currentPosition.toDouble()
            if (oneTimeOnly == 0 ) {
                seekBar.max = finalTime.toInt()
                oneTimeOnly = 1
            }
            timeTxt.text = startTime.toString()
            seekBar.progress = startTime.toInt()

            handler.postDelayed(UpdateSongTime, 1000)
        }

        // Setting the music title
        titleTxt.text = ""+ resources.getResourceEntryName(R.raw.mungumkuu)

        // Stop Button
        pauseButton.setOnClickListener() {
            mediaPlayer.pause()
        }

        // Forward Button
        forwardButton.setOnClickListener() {
            var temp = startTime
            if ((temp + forwardTime) <= finalTime) {
                startTime = startTime + forwardTime
                mediaPlayer.seekTo(startTime.toInt())
            }else {
                Toast.makeText(this,
                "Can't jump forward", Toast.LENGTH_LONG).show()
            }
        }

        // Back Button
        backButton.setOnClickListener() {
            var temp = startTime
            if ((temp - backwardTime) > 0) {
                startTime = startTime - backwardTime
                mediaPlayer.seekTo(startTime.toInt())
            }else {
                Toast.makeText(this,
                    "Can't jump backward",
                    Toast.LENGTH_LONG).show()
            }
        }

    }

    // Creating Runnable
    val UpdateSongTime: Runnable = object : Runnable {
        override fun run() {
            startTime = mediaPlayer.currentPosition.toDouble()
            timeTxt.text = "" +
                    String.format(
                        "%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                        TimeUnit.MILLISECONDS.toSeconds(
                            startTime.toLong()
                            -TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(
                                    startTime.toLong()
                                )
                            )
                        )
                    )

            seekBar.progress = startTime.toInt()
            handler.postDelayed(this, 100)

        }
    }

}