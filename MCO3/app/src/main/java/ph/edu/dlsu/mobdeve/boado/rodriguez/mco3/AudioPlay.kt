package ph.edu.dlsu.mobdeve.boado.rodriguez.mco3



import android.content.Context
import android.media.MediaPlayer

object AudioPlay {
    var mediaPlayer: MediaPlayer? = null
    var lastResource: Int? = null

    fun playAudio(c: Context, id: Int, isLooping: Boolean = true) {
        createMediaPlayer(c, id)

        mediaPlayer?.let {
            it.isLooping = isLooping

            if (!it.isPlaying) {
                it.start()
            }
        }
    }

    private fun createMediaPlayer(c: Context, id: Int) {
        // in case it's already playing something
        mediaPlayer?.stop()

        mediaPlayer = MediaPlayer.create(c, id)
        lastResource = id
    }

    // usually used inside the Activity's onResume method
    fun continuePlaying(c: Context, specificResource: Int? = null) {
        specificResource?.let {
            if (lastResource != specificResource) {
                createMediaPlayer(c, specificResource)
            }
        }

        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
            }
        }
    }

    fun pauseAudio() {
        mediaPlayer?.pause()
    }

    fun stopAudio(){
        mediaPlayer?.stop()
    }

}