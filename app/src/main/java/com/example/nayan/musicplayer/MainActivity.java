package com.example.nayan.musicplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    private ArrayList<MMp3> mMp3ArrayList;
    private Gson gson;
    private int aut0;
    private SimpleExoPlayer player2;
    private PlayerControl playerControl2;
    private BandwidthMeter bandwidthMeter;
    private TrackSelection.Factory videoTrackSelectionFactory;
    private TrackSelector trackSelector;
    private AudioManager audioManager;
    private TextView tvDuration, tvTotalDuration;
    private SeekBar seek;
    private Button btnPrevious, btnPause, btnNext;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new Gson();
        tvDuration = (TextView) findViewById(R.id.tvDuration);
        tvTotalDuration = (TextView) findViewById(R.id.tvTotalDuration);
        seek = (SeekBar) findViewById(R.id.seek);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnPause.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        handler = new Handler();
        init();

        getSound();

    }

    private void getSound() {
//        Log.e("Items__________", "method " + category.getCategoryId() + "");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("http://www.radhooni.com/content/match_game/v1/sound.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("Items__________", " response " + response.toString());
                try {

                    mMp3ArrayList = new ArrayList<>(Arrays.asList(gson.fromJson(response.getJSONArray("items").toString(), MMp3[].class)));
                    Log.e("Items__________", "size" + mMp3ArrayList.size());
//                    autoPlaying();
                    playSongwithExoPlayerSecondSong(mMp3ArrayList.get(0).getSounds().get(aut0).getFile());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("Items__________", "failer");
            }
        });

    }

    private void init() {
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        bandwidthMeter = new DefaultBandwidthMeter();
        videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);


        player2 = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
        playerControl2 = new PlayerControl(player2);
    }

    private void autoPlaying() {
        Log.e("start", " playing " + aut0);
        if (mMp3ArrayList != null && mMp3ArrayList.size() > 0) {
            if (mMp3ArrayList.size() < aut0)
                return;
            else {
                playSongwithExoPlayerSecondSong(mMp3ArrayList.get(0).getSounds().get(aut0).getFile());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        aut0++;
                        autoPlaying();

                    }
                }, playerControl2.getDuration());
            }
        }
    }


    public void playSongwithExoPlayerSecondSong(String url) {

        Log.e("STAG", "playSongwithExoPlayerSecondSong method");

        // DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        ExtractorMediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null);

        player2.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                Log.e("player", " time" );
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.e("player", " onTracksChanged" );
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.e("player", " onLoadingChanged" );

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.e("player", " " + playbackState);
                if (playbackState == SimpleExoPlayer.STATE_READY) {
                    tvTotalDuration.setText("/"+String.format("%02d:%02d ", TimeUnit.MILLISECONDS.toMinutes((long) playerControl2.getDuration()), TimeUnit.MILLISECONDS.toSeconds((long) playerControl2.getDuration()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) playerControl2.getDuration()))));
                }
                if (playbackState == SimpleExoPlayer.STATE_ENDED) {

                    if (mMp3ArrayList != null && mMp3ArrayList.size() > 0) {
                        if (mMp3ArrayList.size() < aut0)
                            return;
                        else {
                            playSongwithExoPlayerSecondSong(mMp3ArrayList.get(0).getSounds().get(aut0).getFile());
                            aut0++;
                        }
                    }
                    Log.e("player", " ended");
                }

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity() {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        });
        player2.prepare(mediaSource);
        playerControl2.start();
        updateTimeUI();

        handler.removeCallbacks(moveSeekBarThread);
        handler.postDelayed(moveSeekBarThread, 100);
    }

    private void updateTimeUI() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//
                tvDuration.setText(String.format("%02d:%02d ", TimeUnit.MILLISECONDS.toMinutes((long) playerControl2.getCurrentPosition()), TimeUnit.MILLISECONDS.toSeconds((long) playerControl2.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) playerControl2.getCurrentPosition()))));
//                txtDuration2.setText(playerControl.getCurrentPosition()/1000 + "s");
                updateTimeUI();
            }
        }, 1000);

    }

    private Runnable moveSeekBarThread = new Runnable() {
        public void run() {
            if (playerControl2.isPlaying()) {

                int mediaPos_new = playerControl2.getCurrentPosition();
                int mediaMax_new = playerControl2.getDuration();
                seek.setMax(mediaMax_new);
                seek.setProgress(mediaPos_new);

                seek.postDelayed(this, 100); //Looping the thread after 0.1 second
            }

        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNext)
            playSongwithExoPlayerSecondSong(mMp3ArrayList.get(0).getSounds().get(aut0 + 1).getFile());
        if (v.getId() == R.id.btnPrevious)
            playSongwithExoPlayerSecondSong(mMp3ArrayList.get(0).getSounds().get(aut0 - 1).getFile());
        if (v.getId() == R.id.btnPause) {
            if (playerControl2.isPlaying())
                playerControl2.pause();
        }


    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.e("onCompletion", "");
        if (mMp3ArrayList != null && mMp3ArrayList.size() > 0) {
            if (mMp3ArrayList.size() < aut0)
                return;
            else {
                playSongwithExoPlayerSecondSong(mMp3ArrayList.get(0).getSounds().get(aut0).getFile());
                aut0++;
            }
        }
    }
}
