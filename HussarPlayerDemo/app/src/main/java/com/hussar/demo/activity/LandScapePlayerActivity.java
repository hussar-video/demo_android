package com.hussar.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.hussar.core.HussarPlayer;
import com.hussar.core.IHussarPlayer;

import video.hussar.demo.R;

public class LandScapePlayerActivity extends Activity {

    public final static String TAG_URL = "tag_url";

    private HussarPlayer mPlayer;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landscape_player);
        path = getIntent().getStringExtra(TAG_URL);

        mPlayer = (HussarPlayer) findViewById(R.id.player);
        mPlayer.lockToLandScape();
        mPlayer.setHussarPlayerListener(new IHussarPlayer.IHussarPlayerListener() {
            @Override
            public void onPrepared() {
            }

            @Override
            public void onRenderStart() {
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onPause() {
            }

            @Override
            public void onBufferingStart(IHussarPlayer.HussarBufferType type) {
            }

            @Override
            public void onBuffering(int percent) {
            }

            @Override
            public void onBufferingEnd() {
            }

            @Override
            public void onSeekComplete(int position) {
            }

            @Override
            public void onCompletion() {
                mPlayer.showReplayView();
            }

            @Override
            public boolean onError(int what, int extra) {
                Toast.makeText(LandScapePlayerActivity.this, "Error what:" + what + ",extra:" + extra, Toast.LENGTH_LONG).show();
                return false;
            }
        });
        mPlayer.initWithURL(path);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPlayer != null) {
            mPlayer.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mPlayer.onPause();
        }
    }
}
