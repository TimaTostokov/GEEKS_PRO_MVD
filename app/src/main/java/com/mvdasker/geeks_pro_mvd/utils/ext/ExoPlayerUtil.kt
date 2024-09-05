package com.mvdasker.geeks_pro_mvd.utils.ext

import android.content.Context
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

object ExoPlayerUtil {
    fun initializePlayer(context: Context, mediaUrl: String): ExoPlayer {

//    /* @OptIn(UnstableApi::class)
//         fun initializePlayer(context: Context, mediaUrl: String): ExoPlayer {
//
//             val dataSourceFactory = DefaultHttpDataSource.Factory()
//
//
//             val mediaSource: MediaSource = when {
//                 mediaUrl.endsWith(".m3u8") -> {
//                     // HLS источник для потоков HLS.
//                     HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(mediaUrl))
//                 }
//                 mediaUrl.endsWith(".mp4") -> {
//                     // ProgressiveMediaSource для MP4.
//                     ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(mediaUrl))
//                 }
//                 else -> {
//
//                     Log.e("ExoPlayerUtil", "Unsupported media format: ${mediaUrl}")
//                     return ExoPlayer.Builder(context).build()
//                 }
//             }
//
//             return ExoPlayer.Builder(context).build().apply {
//                 Log.d("ExoPlayerUtil", "URL: $mediaUrl")
//
//                 setMediaSource(mediaSource)
//                 trackSelectionParameters = trackSelectionParameters
//                     .buildUpon()
//                     .setMaxVideoSizeSd()
//                     .build()
//                 playWhenReady = true
//                 prepare()*/

        return ExoPlayer.Builder(context).build().apply {
            Log.d("shamal", mediaUrl)
            val mediaItemBuilder = MediaItem.Builder().setUri(mediaUrl)

            val mimeType = when {
                mediaUrl.endsWith(".mpd") -> MimeTypes.APPLICATION_MPD
                mediaUrl.endsWith(".mp4") -> MimeTypes.VIDEO_MP4
                else -> "NULL"
            }
            mediaItemBuilder.setMimeType(mimeType)

            val mediaItem = mediaItemBuilder.build()

            setMediaItem(mediaItem)
            trackSelectionParameters = trackSelectionParameters
                .buildUpon()
                .setMaxVideoSizeSd()
                .build()
            playWhenReady = true
            prepare()

            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_BUFFERING -> Log.d("ExoPlayer", "Буферизация...")
                        Player.STATE_READY -> Log.d("ExoPlayer", "Готов к воспроизведению")
                        Player.STATE_ENDED -> Log.d("ExoPlayer", "Воспроизведение завершено")
                        Player.STATE_IDLE -> Log.d("ExoPlayer", "Плеер бездействует")
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    Log.e("ExoPlayer", "Ошибка воспроизведения: ${error.message}")
                }
            })
        }
    }

    fun releasePlayer(exoPlayer: ExoPlayer?) {
        exoPlayer?.release()
    }

}