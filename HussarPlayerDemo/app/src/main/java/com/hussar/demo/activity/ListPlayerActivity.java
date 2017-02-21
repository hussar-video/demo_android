package com.hussar.demo.activity;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hussar.core.HussarPlayer;
import com.hussar.core.IHussarPlayer;

import java.util.ArrayList;
import java.util.List;

import video.hussar.demo.R;

public class ListPlayerActivity extends Activity {
    private final static String TAG = "ListPlayerActivity";

    public final static String TAG_URL = "tag_url";
    private HussarPlayer mPlayer;

    private List<String> mVideoSources = new ArrayList<>();

    private String path;

    private ListView mListView;

    private int mFocusPos = 0;
    private ViewGroup mFocusItemView;
    private boolean initPlay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_player);
        initData();
        initView();
    }

    private void initData() {
        path = getIntent().getStringExtra(TAG_URL);
        for (int i = 0; i < 10; i++) {
            mVideoSources.add(path);
        }
    }

    private void initView() {
        initPlayer();
        mListView = (ListView) findViewById(R.id.lvPlayers);
        mListView.setAdapter(new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return mVideoSources.size();
            }

            @Override
            public Object getItem(int position) {
                return mVideoSources.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                FrameLayout itemView = (FrameLayout) convertView.inflate(ListPlayerActivity.this, R.layout.item_playerlist_starter, null);
//                itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(ListPlayerActivity.this) * 9 / 16));
                return itemView;
            }

            @Override
            public int getItemViewType(int position) {
                return position;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                if (mVideoSources.size() <= 0) {
                    return true;
                }
                return false;
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int currentPos = getFirstValidPosition(view, scrollState);
                if (scrollState == SCROLL_STATE_IDLE && !mVideoSources.isEmpty() && mPlayer != null && currentPos >= 0 && currentPos != mFocusPos) {
                    Log.d(TAG, "onScrollStateChanged " + view.getFirstVisiblePosition() + "," + view.getLastVisiblePosition() + ",scrollState:" + scrollState);
                    mPlayer.stop();
                    removeView(mFocusItemView, mPlayer);

                    mFocusPos = currentPos;
                    mFocusItemView = (ViewGroup) view.getChildAt(mFocusPos - view.getFirstVisiblePosition());
                    initPlayer();

                    addFullView(mFocusItemView, mPlayer);
                    mPlayer.initWithURL(mVideoSources.get(mFocusPos));
                }
//                LogUtil.d(TAG, "onScrollStateChanged " + view.getFirstVisiblePosition() + "," + view.getLastVisiblePosition() + ",scrollState:" + scrollState);
//                LogUtil.d(TAG, "onScrollStateChanged AbsListView length:" + view.getChildCount() + ",firstChild Top:" + view.getChildAt(0).getTop());
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                LogUtil.d(TAG, "onScroll firstVisibleItem:" + firstVisibleItem + ",visibleItemCount:" + visibleItemCount + ",totalItemCount:" + totalItemCount);
            }
        });

        mListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (initPlay) {
                    addFullView((ViewGroup) mListView.getChildAt(0), mPlayer);
                    mPlayer.initWithURL(mVideoSources.get(mFocusPos));
                    initPlay = false;
                }
            }
        });
    }

    private int getFirstValidPosition(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            int childCount = view.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = view.getChildAt(i);
                if (child.getTop() - view.getPaddingTop() >= 0 && child.getBottom() <= view.getHeight() + view.getPaddingTop()) {
                    return view.getFirstVisiblePosition() + i;
                }
            }
        }
        return -1;
    }

    public static void addFullView(ViewGroup parant, View view) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        if (parant != null && view != null) {
            if (isViewExsit(parant, view)) {
                removeView(parant, view);
            }
            parant.addView(view, params);
        }
    }

    public static void removeView(ViewGroup parant, View view) {
        if (parant != null && view != null) {
            Log.d(TAG, "removeView parant:" + parant + ",view:" + view);
            parant.removeView(view);
        }
    }

    public static boolean isViewExsit(ViewGroup parant, View view) {
        if (parant == null || view == null) {
            return false;
        }
        if (parant.indexOfChild(view) >= 0) {
            return true;
        }
        return false;
    }

    private void initPlayer() {
        mPlayer = new HussarPlayer(this);
        mPlayer.setAutoPlay(true);
        mPlayer.setUISync(true);
        mPlayer.setControllerEnable(false);
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
            }

            @Override
            public boolean onError(int what, int extra) {
                Toast.makeText(ListPlayerActivity.this, "Error what:" + what + ",extra:" + extra, Toast.LENGTH_LONG).show();
                return false;
            }
        });

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
        if (!mVideoSources.isEmpty()) {
            // need to call this method from list view handler in order to have list filled previously
        }
        if (mPlayer != null) {
            mPlayer.play();
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
