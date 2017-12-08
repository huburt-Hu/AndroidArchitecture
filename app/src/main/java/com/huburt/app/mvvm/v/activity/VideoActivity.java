package com.huburt.app.mvvm.v.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huburt.app.R;
import com.huburt.app.base.BaseActivity;
import com.huburt.app.entity.PlayUrl;
import com.huburt.app.mvvm.vm.VideoViewModel;
import com.huburt.app.widget.AppBarLayoutScrollHelper;
import com.huburt.architecture.imageloader.ImageLoaderManager;
import com.huburt.architecture.mvvm.ActionObserver;
import com.huburt.architecture.util.DensityUtils;
import com.huburt.architecture.widget.listener.AppBarStateChangeListener;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZUserAction;
import cn.jzvd.JZUserActionStandard;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import timber.log.Timber;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/4.
 */

public class VideoActivity extends BaseActivity {

    public static final String PARAM_AID = "param_aid";
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.tab_layout)
//    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tv_av)
    TextView tvAv;
    @BindView(R.id.tv_play_immediately)
    TextView tvPlay;
    @BindView(R.id.video_player)
    JZVideoPlayerStandard videoPlayer;
    @BindView(R.id.rl_video_tip)
    ViewGroup videoTip;
    @BindView(R.id.bili_anim)
    ImageView ivBiliAnim;


    private int appbarHeight;
    private String aid;

    private boolean play;

    public static void start(Context context, String aid) {
        Intent starter = new Intent(context, VideoActivity.class);
        starter.putExtra(PARAM_AID, aid);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            aid = extras.getString(PARAM_AID);
            Timber.i("aid:" + aid);
        }

        initAppBar();

        videoPlayer.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "title");
        ImageLoaderManager.getInstance().load(videoPlayer.thumbImageView, "http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png");

        videoPlayer.setJzUserAction(new JZUserAction() {
            @Override
            public void onEvent(int type, Object url, int screen, Object... objects) {
                switch (type) {
                    case JZUserAction.ON_CLICK_START_ICON:
                        AppBarLayoutScrollHelper.disableScroll(appbar);
                        Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_CLICK_START_ERROR:
                        Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_CLICK_START_AUTO_COMPLETE:
                        AppBarLayoutScrollHelper.disableScroll(appbar);
                        Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_CLICK_PAUSE:
                        AppBarLayoutScrollHelper.restoreScroll(appbar);
                        Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_CLICK_RESUME:
                        AppBarLayoutScrollHelper.restoreScroll(appbar);
                        Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_SEEK_POSITION:
                        Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_AUTO_COMPLETE:
                        Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_ENTER_FULLSCREEN:
                        Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_QUIT_FULLSCREEN:
                        Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_ENTER_TINYSCREEN:
                        Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_QUIT_TINYSCREEN:
                        Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                        Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                        Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserActionStandard.ON_CLICK_START_THUMB:
                        Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserActionStandard.ON_CLICK_BLANK:
                        Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    default:
                        Log.i("USER_EVENT", "unknow");
                        break;
                }

            }
        });

        VideoViewModel viewModel = ViewModelProviders.of(this).get(VideoViewModel.class);
        viewModel.getVideoLiveData().observe(this, new ActionObserver<PlayUrl>() {

            @Override
            public void onAction(int id, Object... args) {
                if (id == 1) {
                    tvAv.setText("加载视频错误");
                    Toast.makeText(VideoActivity.this, "加载视频错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChanged(@Nullable PlayUrl playUrl) {
                if (playUrl != null) {
                    Map<String, PlayUrl.DurlBean> durl = playUrl.getDurl();
                    if (durl != null && durl.size() > 0) {
                        videoPlayer.setUp(durl.get("0").getUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "title");
                        ImageLoaderManager.getInstance().load(videoPlayer.thumbImageView, playUrl.getImg());
                    }
                }
            }
        });

        viewModel.getVideo(aid);
    }

    private void initAppBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        appbar.post(new Runnable() {
            @Override
            public void run() {
                appbarHeight = appbar.getHeight();
                Timber.i("appbarHeight:" + appbarHeight);
            }
        });

        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {
                if (state == State.EXPANDED) {
                    tvAv.setVisibility(View.VISIBLE);
                    tvPlay.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    tvAv.setVisibility(View.GONE);
                    tvPlay.setVisibility(View.VISIBLE);
                }
            }
        });
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > (appbarHeight / 3)) {
                    fab.hide();
                } else if (!play) {
                    fab.show();
                }
            }
        });

        fab.setOnClickListener(v -> {
            play = true;
            ObjectAnimator animator = ObjectAnimator.ofFloat(fab, "translationY", 0, -DensityUtils.dip2px(this, 60));
            animator.setDuration(100);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    fab.setVisibility(View.GONE);
                    showCircleAnim();
                }
            });
            animator.start();
        });

        tvPlay.setOnClickListener(v -> {
            appbar.setExpanded(true, true);
            tvPlay.setVisibility(View.GONE);
        });

    }

    private void showCircleAnim() {
        videoTip.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(videoTip,
                    videoPlayer.getWidth() - DensityUtils.dip2px(this, 30),
                    videoPlayer.getHeight() - DensityUtils.dip2px(this, 60),
                    0, ((float) Math.hypot(videoPlayer.getWidth(), videoPlayer.getHeight())));
            animator.setDuration(200);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ivBiliAnim.setImageResource(R.drawable.anim_video_loading);
                    AnimationDrawable cheersAnim = (AnimationDrawable) ivBiliAnim.getDrawable();
                    cheersAnim.selectDrawable(0);
                    cheersAnim.start();
                    videoPlayer.startButton.performClick();

//                    AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
//                    int scrollFlags = layoutParams.getScrollFlags();
//                    Timber.i("scrollFlags: %d", scrollFlags);
//                    int i = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP;
//                    Timber.i("flag | : %d", i);
//                    layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
//                    collapsingToolbarLayout.setLayoutParams(layoutParams);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ivBiliAnim.setImageResource(0);
                            videoTip.setVisibility(View.GONE);
                        }
                    }, 1000);
                }
            });
            animator.start();
        } else {
            videoPlayer.startButton.performClick();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressedSupport() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressedSupport();
    }
}
