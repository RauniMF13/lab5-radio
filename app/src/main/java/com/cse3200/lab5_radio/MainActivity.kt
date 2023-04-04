package com.cse3200.lab5_radio

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.cse3200.lab5_radio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null
    private val mediaPlayerLiveData = MutableLiveData<MediaPlayer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayerLiveData.value = mediaPlayer

        val layoutManager: LayoutManager = LinearLayoutManager(this)

        binding.recyclerView.layoutManager = layoutManager

        val adapter = StationListAdapter(stationBank, mediaPlayerLiveData, this, binding)

        binding.recyclerView.adapter = adapter

        binding.startButton.setOnClickListener {
            unpauseMusic()
        }

        binding.stopButton.setOnClickListener {
            pauseMusic()
        }
    }

    private fun unpauseMusic() {
        mediaPlayerLiveData.observe(this) { mediaPlayer ->
            this.mediaPlayer = mediaPlayer
        }
        if (mediaPlayerLiveData.value != null && !(mediaPlayerLiveData.value!!.isPlaying)) {
            mediaPlayerLiveData.value!!.start()
        } else if (mediaPlayerLiveData.value == null){
            Toast.makeText(this, "Choose a station first!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pauseMusic() {
        mediaPlayerLiveData.observe(this) { mediaPlayer ->
            this.mediaPlayer = mediaPlayer
        }
        if (mediaPlayerLiveData.value != null && mediaPlayerLiveData.value!!.isPlaying) {
            mediaPlayerLiveData.value!!.pause()
        }
    }

    private val stationBank = listOf(
        Station(R.string.station_one_name, R.string.station_one_url, R.drawable.station_one),
        Station(R.string.station_two_name, R.string.station_two_url, R.drawable.station_two),
        Station(R.string.station_three_name, R.string.station_three_url, R.drawable.station_three),
        Station(R.string.station_four_name, R.string.station_four_url, R.drawable.station_four),
        Station(R.string.station_five_name, R.string.station_five_url, R.drawable.station_five),
        Station(R.string.station_six_name, R.string.station_six_url, R.drawable.station_six),
        Station(R.string.station_seven_name, R.string.station_seven_url, R.drawable.station_seven),
        Station(R.string.station_eight_name, R.string.station_eight_url, R.drawable.station_eight),
        Station(R.string.station_nine_name, R.string.station_nine_url, R.drawable.station_nine),
        Station(R.string.station_ten_name, R.string.station_ten_url, R.drawable.station_ten)
    )
}