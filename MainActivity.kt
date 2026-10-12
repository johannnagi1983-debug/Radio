package de.radioplayer.prototype

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.*
import android.media.AudioAttributes
import android.media.MediaPlayer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : Activity() {
    private var player: MediaPlayer? = null
    private var currentUrl: String? = null
    private lateinit var status: TextView
    private val stations = listOf(
        Station("sunshine live", "http://stream.sunshine-live.de/live/mp3-192"),
        Station("Radio BOB!", "https://streams.radiobob.de/bob-live/mp3-192/streams.radiobob.de/"),
        Station("Absolut Rock", "https://edge12.live-sm.absolutradio.de/absolut-rock/stream/mp3")
    )
    data class Station(val name: String, val url: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        window.navigationBarColor = Color.BLACK
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 18, 18, 18)
            setBackgroundColor(Color.rgb(8, 8, 8))
        }
        val clock = TextView(this).apply {
            textSize = 28f; setTextColor(Color.WHITE); gravity = Gravity.CENTER
            text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            setPadding(0, 8, 0, 22)
        }
        root.addView(clock)
        val title = TextView(this).apply {
            text = "RADIOPLAYER"; textSize = 16f; setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER; setPadding(0, 0, 0, 14)
        }
        root.addView(title)
        status = TextView(this).apply {
            text = "Sender auswählen"; textSize = 14f; setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER; setPadding(0, 0, 0, 12)
        }
        root.addView(status)
        val list = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        stations.forEach { station ->
            val button = Button(this).apply {
                text = station.name
                textSize = 16f
                setTextColor(Color.WHITE)
                setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(35,35,35)))
                setOnClickListener { playStation(station) }
            }
            list.addView(button, LinearLayout.LayoutParams(-1, 64).apply { bottomMargin = 10 })
        }
        root.addView(list)
        val stop = Button(this).apply {
            text = "STOPP"
            setTextColor(Color.WHITE)
            setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(55,55,55)))
            setOnClickListener { stopPlayback() }
        }
        root.addView(stop, LinearLayout.LayoutParams(-1, 56))
        val note = TextView(this).apply {
            text = "Prototyp · Favoriten und Kachelansicht folgen in weiteren Ausbaustufen"
            textSize = 12f; setTextColor(Color.GRAY); gravity = Gravity.CENTER
            setPadding(0, 20, 0, 0)
        }
        root.addView(note)
        setContentView(ScrollView(this).apply { addView(root) })
    }

    private fun playStation(station: Station) {
        stopPlayback()
        status.text = "Verbinde mit ${station.name} …"
        try {
            player = MediaPlayer().apply {
                setAudioAttributes(AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
                setDataSource(station.url)
                setOnPreparedListener { it.start(); status.text = "Läuft: ${station.name}" }
                setOnErrorListener { _, _, _ -> status.text = "Stream konnte nicht abgespielt werden"; true }
                prepareAsync()
            }
            currentUrl = station.url
        } catch (e: Exception) {
            status.text = "Fehler beim Starten des Streams"
        }
    }

    private fun stopPlayback() {
        try { player?.stop() } catch (_: Exception) {}
        player?.release()
        player = null
        currentUrl = null
        if (::status.isInitialized) status.text = "Wiedergabe gestoppt"
    }

    override fun onDestroy() { stopPlayback(); super.onDestroy() }
}
