package com.example.a325.acitvities;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.a325.R;
import com.example.a325.bases.BaseActivity;
import com.example.a325.datas.MediaData;
import com.example.a325.fragments.Courseplay_CommentFragment;
import com.example.a325.fragments.Courseplay_IntroFragment;
import com.example.a325.fragments.Courseplay_ListFragment;
import com.example.a325.utils.HttpRequest;
import com.example.a325.utils.HttpUrl;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class CoursePlayActivity extends BaseActivity {

    private static final int VIDEO_PALY = 0;

    private static final int VIDEO_BG = 1;

    private static final int SEC = 1000;//一秒钟时间

    private static final int HIDE_TIME = 3500;//隐藏控制栏时间


    @Bind(R.id.tv_title)
    TextView mtvTitle;
    @Bind(R.id.tablayout)
    TabLayout mtablayout;
    @Bind(R.id.viewpager)
    ViewPager mviewpager;

    @Bind(R.id.videoView)
    VideoView mvideoView;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.progress_start)
    ProgressBar progressStart;
    @Bind(R.id.play_control)
    ImageView playControl;
    @Bind(R.id.seekbar)
    SeekBar seekbar;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.iv_fullscreen)
    ImageView ivFullscreen;
    @Bind(R.id.video_bg)
    RelativeLayout videoBg;


    private int mId;//课程编号
    private String mTitle;//课程名称
    private String[] mTitles = {"课程简介", "课程大纲", "测试"};//TableLayout
    private List<Fragment> mFragments;
    private Courseplay_IntroFragment mIntroFragment;
    private Courseplay_ListFragment mListFragment;
    private Courseplay_CommentFragment mCommentFragment;

    private MediaController mMediaController;
    private MediaData mMediaData;

    //总时长字符串
    private String mTotalDurationStr;
    //总时长
    private long mTotalDuration;
    //开始时长
    private long startDuration = 0;
    //是否在滑动拖块
    private boolean mIsSlide = false;
    //是否播放结束
    private boolean mIsPlayEnd = false;

    /**
     * 设置时间控制
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == VIDEO_PALY) {
                startDuration += SEC;
                //设置时间显示
                if (startDuration >= mTotalDuration) {
                    startDuration = mTotalDuration;
                }
                tvTime.setText(sec2time(startDuration) + "/" + mTotalDurationStr);
                //设置SeekBar的进度
                if (!mIsSlide) {
                    int progress = (int) ((startDuration * 1.0 / mTotalDuration) * 1000);
                    seekbar.setProgress(progress);
//                    mSeekBar.setSecondaryProgress(progress+100);
                }
                //继续轮回
                mHandler.sendEmptyMessageDelayed(VIDEO_PALY, SEC);
            }
            if (msg.what == VIDEO_BG) {//隐藏控制栏
                videoBg.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_play;
    }

    @Override
    protected void init() {
        mId = getIntent().getIntExtra("id", 0);
        mTitle = getIntent().getStringExtra("title");

        mtvTitle.setText(mTitle);
        //setVideoView
        setupViewPager();
        Vitamio.isInitialized(this);

        new MediaAsyncTask().execute();
        setupSeekBarChanged();
    }

    private class MediaAsyncTask extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... voids) {
            String url = HttpUrl.getInstance().getMediaInfo();

            Map<String, String> params = HttpUrl.getInstance().getMediaInfoParams(mId+"");
            Log.e("id","course id is"+mId);

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("MediaData",s);

            analysisJsonData(s);
        }
    }

    private void analysisJsonData(String s) {
        Gson gson = new Gson();
        mMediaData = gson.fromJson(s,MediaData.class);
        Log.e("MeidaUrl",mMediaData.getMediaUrl());
        setupPlay();
    }

    private void setupPlay() {
        if (mMediaData == null) return;
        String MediaURL = mMediaData.getMediaUrl();
        mvideoView.setVideoPath(MediaURL.trim());

        // mvideoView.setVideoPath("http://192.168.137.1:8080/vid/video.mp4");
        setupPlayListener();
    }

    private void setupPlayListener() {
        //预处理
        mvideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //start加载条隐藏
                progressStart.setVisibility(View.GONE);
                //暂停播放设置为播放
                playControl.setImageResource(R.drawable.video_pause);
                mTotalDuration = mp.getDuration();
                mTotalDurationStr = sec2time(mTotalDuration);
                startVideo();
            }
        });
        //缓冲
        mvideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what){
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        if (mvideoView.isPlaying()) {
                            mvideoView.pause();
                        }
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        mvideoView.start();
                        break;
                }
                return true;
            }
        });
        //完成
        //视频播放完成
        mvideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //暂停播放
                pauseVideo();

                mIsPlayEnd = true;
            }
        });
        mvideoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                debug("buffer-->"+mp.getBufferProgress());
            }
        });


    }
    private void setupSeekBarChanged() {
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //开始滑动拖块，不隐藏
                mHandler.removeMessages(VIDEO_BG);
                //将值设置为真，此时不会自动改变滑动位置
                mIsSlide = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //滑动完后设置为假
                mIsSlide = false;

                startDuration = (long) ((seekBar.getProgress()*1.0 / 1000) * mTotalDuration);
                tvTime.setText(sec2time(startDuration) + "/" + mTotalDurationStr);
                mvideoView.seekTo(startDuration);

                if (mIsPlayEnd) {
                    mIsPlayEnd = false;
                    startVideo();
                } else {
                    mHandler.sendEmptyMessageDelayed(VIDEO_BG, HIDE_TIME);
                }
            }
        });
    }

    private void pauseVideo() {
        playControl.setImageResource(R.drawable.video_play);
        mvideoView.pause();
        mHandler.removeMessages(VIDEO_PALY);
        mHandler.removeMessages(VIDEO_BG);
        //显示控制栏
        videoBg.setVisibility(View.VISIBLE);
    }

    private void startVideo() {
        playControl.setImageResource(R.drawable.video_pause);
        mvideoView.start();
        mHandler.sendEmptyMessageDelayed(VIDEO_PALY, SEC);
        if (videoBg.getVisibility() == View.VISIBLE && mvideoView.isPlaying()) {
            mHandler.sendEmptyMessageDelayed(VIDEO_BG, HIDE_TIME);
        }



    }


    //视频播放控制按钮
    @OnClick(R.id.play_control) void setupPlayClick() {
        if (mvideoView != null) {
            if (mvideoView.isPlaying()) {
                pauseVideo();
            } else {
                startVideo();
            }
        }
    }


    //全屏键
    @OnClick(R.id.iv_fullscreen) void fullScreen() {

    }

    //显示视频控制器
    @OnClick(R.id.main_view) void showVideoControl() {
        if (videoBg.getVisibility() == View.GONE) {
            videoBg.setVisibility(View.VISIBLE);
            if (mvideoView.isPlaying()) {
                //显示之后过3秒隐藏
                mHandler.sendEmptyMessageDelayed(VIDEO_BG, HIDE_TIME);
            }
        } else {
            videoBg.setVisibility(View.GONE);
        }
    }

    //返回键
    @OnClick(R.id.iv_back) void onBack() {
        finish();
    }


    private String sec2time(long time) {
        //初始化Formatter的转换格式。
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        String hms = formatter.format(time);

        return hms;
    }

    private void setupViewPager() {
        addFragments();

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }


        };

        mviewpager.setAdapter(adapter);
        mtablayout.setupWithViewPager(mviewpager);


    }

    private void addFragments() {
        mFragments = new ArrayList<>();

        mIntroFragment = new Courseplay_IntroFragment();
        mListFragment = new Courseplay_ListFragment();
        mCommentFragment = new Courseplay_CommentFragment();

        mFragments.add(mIntroFragment);
        mFragments.add(mListFragment);
        mFragments.add(mCommentFragment);
    }
}
