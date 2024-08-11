package com.papaguycodes.recorder

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var outputFile: String? = null
    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        outputFile = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"

        val btnRecord = findViewById<Button>(R.id.btnRecord)
        val btnStop = findViewById<Button>(R.id.btnStop)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnSummarize = findViewById<Button>(R.id.btnSummarize)
        val summaryTextView = findViewById<TextView>(R.id.summaryTextView)
        val switchDarkMode = findViewById<Switch>(R.id.switchDarkMode)

        btnRecord.setOnClickListener {
            startRecording()
            isRecording = true
        }

        btnStop.setOnClickListener {
            if (isRecording) {
                stopRecording()
                isRecording = false
            } else {
                stopPlayback()
            }
        }

        btnPlay.setOnClickListener {
            startPlayback()
        }

        btnSummarize.setOnClickListener {
            // Placeholder for summary feature
            summaryTextView.text = "This is where the summary would appear."
        }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setTheme(R.style.Theme_Recorder_Dark)
            } else {
                setTheme(R.style.Theme_Recorder)
            }
            recreate()
        }
    }

    private fun startRecording() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(outputFile)

            try {
                prepare()
                start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }

    private fun startPlayback() {
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(outputFile)
                prepare()
                start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun stopPlayback() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaRecorder?.release()
        mediaRecorder = null
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
