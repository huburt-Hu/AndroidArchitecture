package com.huburt.app.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;

import com.huburt.app.R;
import com.huburt.app.ijkplayer.IMediaController;
import com.huburt.app.ijkplayer.IjkMediaController;
import com.huburt.app.ijkplayer.IjkVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class TestIjkplayerActivity extends AppCompatActivity {

    @BindView(R.id.ijk_video_view)
    IjkVideoView ijkVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ijkplayer);
        ButterKnife.bind(this);

        String mVideoPath = "http://storage.gzstv.net/uploads/media/huangmeiyan/jr05-09.mp4";

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        IMediaController mediaController = new IjkMediaController(this, false);
        ijkVideoView.setMediaController(mediaController);

        ijkVideoView.setVideoPath(mVideoPath);

        ijkVideoView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ijkVideoView.stopPlayback();
        ijkVideoView.release(true);
        IjkMediaPlayer.native_profileEnd();
    }
}
