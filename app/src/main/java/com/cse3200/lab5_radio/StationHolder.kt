package com.cse3200.lab5_radio

import android.content.Context
import android.content.res.Resources
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.cse3200.lab5_radio.databinding.ActivityMainBinding
import com.cse3200.lab5_radio.databinding.StationItemBinding

class StationHolder (
    val binding: StationItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(station : Station) {
        binding.textView.setText(station.stationName)
        binding.thumbnailImage.setImageResource(station.imageResId)
    }
}

class StationListAdapter(
    private val stations : List<Station>,
    private var mediaPlayerLiveData: MutableLiveData<MediaPlayer>?,
    val context : Context,
    private var mainBinding: ActivityMainBinding
) : RecyclerView.Adapter<StationHolder>() {

    private var selectedRadioPosition = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StationHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StationItemBinding.inflate(inflater, parent, false)
        return StationHolder(binding)
    }

    override fun onBindViewHolder(
        holder: StationHolder,
        position: Int) {
        var mediaPlayer : MediaPlayer? = null
        val station = stations[position]
        holder.bind(station)

        holder.binding.button.setOnClickListener {
            if (selectedRadioPosition != holder.adapterPosition) {
                if (mediaPlayerLiveData?.value != null && mediaPlayerLiveData!!.value!!.isPlaying) {
                    mediaPlayerLiveData!!.value!!.stop()
                    mediaPlayerLiveData!!.value!!.release()
                    mediaPlayerLiveData!!.value = null
                }

                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )

                mediaPlayer!!.setDataSource(context.resources.getString(station.stationUrl))
                mediaPlayer!!.setOnPreparedListener {
                    it.start()
                }
                mediaPlayer!!.prepareAsync()
                mediaPlayerLiveData?.value = mediaPlayer
                selectedRadioPosition = holder.adapterPosition
                mainBinding.stationName.text = context.resources.getString(station.stationName)
            }
        }

    }

    override fun getItemCount() = stations.size
}