package com.example.util

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.AudioTrack
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import java.util.Locale
import kotlin.concurrent.thread
import kotlin.math.sin

class AudioHelper(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    private var toneGen: ToneGenerator? = null
    private val vibrator: Vibrator? = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

    init {
        try {
            tts = TextToSpeech(context, this)
            toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 80)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale("ms", "MY"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fallback to English or default locale
                tts?.setLanguage(Locale.getDefault())
            }
            tts?.setPitch(1.1f) // Slightly cheerful child-friendly pitch
            tts?.setSpeechRate(0.85f) // Clear, slightly slower tempo for kids to follow
            isTtsReady = true
        }
    }

    fun speak(text: String, isSlow: Boolean = false) {
        if (!isTtsReady || text.isBlank()) return
        tts?.stop()
        tts?.setSpeechRate(if (isSlow) 0.7f else 0.85f)
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "cilik_speech_${System.currentTimeMillis()}")
    }

    fun stopSpeech() {
        tts?.stop()
    }

    fun playCorrectSound() {
        try {
            toneGen?.startTone(ToneGenerator.TONE_PROP_BEEP2, 250)
            vibrate(80)
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun playWrongSound() {
        try {
            toneGen?.startTone(ToneGenerator.TONE_PROP_NACK, 250)
            vibrate(150)
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun playStarChime() {
        thread {
            try {
                toneGen?.startTone(ToneGenerator.TONE_DTMF_D, 150)
                Thread.sleep(120)
                toneGen?.startTone(ToneGenerator.TONE_DTMF_0, 200)
                vibrate(50)
            } catch (e: Exception) {
                // Ignore
            }
        }
    }

    fun playCheerFanfare() {
        thread {
            try {
                val tones = listOf(
                    ToneGenerator.TONE_DTMF_1,
                    ToneGenerator.TONE_DTMF_3,
                    ToneGenerator.TONE_DTMF_5,
                    ToneGenerator.TONE_DTMF_8
                )
                for (t in tones) {
                    toneGen?.startTone(t, 160)
                    Thread.sleep(140)
                }
                vibrate(200)
            } catch (e: Exception) {
                // Ignore
            }
        }
    }

    fun playWaterSplashSound() {
        thread {
            try {
                // Play soft double bubbling tones for wuduk water representation
                toneGen?.startTone(ToneGenerator.TONE_SUP_RINGTONE, 200)
                Thread.sleep(100)
                toneGen?.startTone(ToneGenerator.TONE_PROP_PROMPT, 150)
                vibrate(40)
            } catch (e: Exception) {
                // Ignore
            }
        }
    }

    private fun vibrate(durationMs: Long) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(durationMs)
            }
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun release() {
        try {
            tts?.stop()
            tts?.shutdown()
            toneGen?.release()
        } catch (e: Exception) {
            // Ignore
        }
    }
}
