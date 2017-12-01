package com.example.nayan.musicplayer;

import android.widget.MediaController;

import com.google.android.exoplayer2.SimpleExoPlayer;

/**
 * Created by Nayan on 12/1/2017.
 */

public class PlayerControl implements MediaController.MediaPlayerControl {

    private SimpleExoPlayer exoPlayer;

    public PlayerControl(SimpleExoPlayer exoPlayer) {
        this.exoPlayer = exoPlayer;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    public int getAudioSessionId() {
        throw new UnsupportedOperationException();
    }

    public int getBufferPercentage() {
        return this.exoPlayer.getBufferedPercentage();
    }

    public int getCurrentPosition() {
        return this.exoPlayer.getDuration() == -1L ? 0 : (int) this.exoPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return this.exoPlayer.getDuration() == -1L ? 0 : (int) this.exoPlayer.getDuration();
    }

    public boolean isPlaying() {
        return this.exoPlayer.getPlayWhenReady();
    }

    public void start() {
        this.exoPlayer.setPlayWhenReady(true);
    }

    public void stop() {
        exoPlayer.stop();
    }

    public void pause() {
        this.exoPlayer.setPlayWhenReady(false);
    }

    public void seekTo(int timeMillis) {
        long seekPosition = this.exoPlayer.getDuration() == -1L ? 0L : (long) Math.min(Math.max(0, timeMillis), this.getDuration());
        this.exoPlayer.seekTo(seekPosition);
    }
}
