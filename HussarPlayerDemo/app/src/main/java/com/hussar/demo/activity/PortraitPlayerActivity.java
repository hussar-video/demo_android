package com.hussar.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hussar.core.HussarPlayer;
import com.hussar.core.IHussarPlayer;

import video.hussar.demo.R;

public class PortraitPlayerActivity extends Activity {

    private final static String TAG = "PortraitPlayerActivity";
    public final static String TAG_URL = "tag_url";

    private HussarPlayer mPlayer;
    private String path;

    /**
     * get screen width pixels
     *
     * @return
     */

    public static int getScreenWidth(Context context) {
        try {
            return context.getResources().getDisplayMetrics().widthPixels;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait_player);
        path = getIntent().getStringExtra(TAG_URL);

        mPlayer = (HussarPlayer) findViewById(R.id.player);
        mPlayer.setAutoFullScreen(true);
        mPlayer.getLayoutParams().height = getScreenWidth(this) * 9 / 16;
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
                Toast.makeText(PortraitPlayerActivity.this, "Error what:" + what + ",extra:" + extra, Toast.LENGTH_LONG).show();
                return false;
            }
        });
        mPlayer.setHussarViewListener(new IHussarPlayer.IHussarViewListener() {
            @Override
            public void onButtonClickEvent(IHussarPlayer.HussarPlayerButtonEvent buttonEvent) {
                switch (buttonEvent) {
                    case HussarPlayerButtonPause:
                        Log.d(TAG, "HussarPlayerButtonPause");
                        break;
                    case HussarPlayerButtonPlay:
                        Log.d(TAG, "HussarPlayerButtonPlay");
                        break;
                    case HussarPlayerButtonSeek:
                        Log.d(TAG, "HussarPlayerButtonSeek");
                        break;
                    case HussarPlayerButtonStop:
                        Log.d(TAG, "HussarPlayerButtonStop");
                        break;
                }
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
