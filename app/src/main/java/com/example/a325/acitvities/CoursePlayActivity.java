package com.example.a325.acitvities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.a325.R;
import com.example.a325.bases.BaseActivity;
import com.example.a325.datas.MediaData;
import com.example.a325.fragments.Courseplay_CommentFragment;
import com.example.a325.fragments.Courseplay_IntroFragment;
import com.example.a325.fragments.Courseplay_ListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class CoursePlayActivity extends BaseActivity {


    @Bind(R.id.tv_title)
    TextView mtvTitle;
    @Bind(R.id.tablayout)
    TabLayout mtablayout;
    @Bind(R.id.viewpager)
    ViewPager mviewpager;

    @Bind(R.id.videoView)
    VideoView mvideoView;


    private int mId;//课程编号
    private String mTitle;//课程名称
    private MediaData mMediaDate;//课程信息
    private String[] mTitles = {"课程简介", "课程大纲", "测试"};//TableLayout
    private List<Fragment> mFragments;//存放三个碎片
    //三个碎片
    private Courseplay_IntroFragment mIntroFragment;
    private Courseplay_ListFragment mListFragment;
    private Courseplay_CommentFragment mCommentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_play;
    }


    protected void init() {
        //mId = getIntent().getIntExtra("id",0);
        //mTitle = getIntent().getIntExtra("title");

        mtvTitle.setText("课程x");//本地
        //setVideoView
        Vitamio.isInitialized(this);
        mvideoView.setVideoPath("http://192.168.137.1:8080/vid/video.mp4");
        setupViewPager();


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

    @OnClick(R.id.iv_back)
    void Onclick() {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
