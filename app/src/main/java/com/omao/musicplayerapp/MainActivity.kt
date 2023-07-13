package com.omao.musicplayerapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

        var seekBar = findViewById<SeekBar>(R.id.seek_bar)

        // Media Player
        var mediaPlayer = MediaPlayer.create(
            this,
            R.raw.mungumkuu
        )

        seekBar.isClickable = false

    }
}