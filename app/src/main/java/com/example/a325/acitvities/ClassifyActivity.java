package com.example.a325.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.a325.R;
import com.example.a325.adapters.ClassifyAdapter;
import com.example.a325.bases.BaseActivity;
import com.example.a325.datas.CourseListData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassifyActivity extends BaseActivity implements ClassifyAdapter.OnItemClickListener {


    @Bind(R.id.recycler)
    RecyclerView mrecycler;

    private List<CourseListData> mCourseDatas;
    private ClassifyAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_classify;
    }

    @Override
    protected void init() {
        initView();
        initLocalCourse();

    }

    private void initView(){
        mCourseDatas = new ArrayList<>();
        mAdapter = new ClassifyAdapter(this,mCourseDatas);
        mAdapter.setOnItemClickListener(this);
        mrecycler.setAdapter(mAdapter);

        mrecycler.setLayoutManager(new LinearLayoutManager(this));
        mrecycler.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);//去掉下滑阴影


    }

    private void initLocalCourse(){
        for(int i = 0; i < 7; i++){
            CourseListData data = new CourseListData();

            data.setName("课程"+i);
            data.setNumbers(i);
            data.setFinished(4);
            mCourseDatas.add(data);

        }
        mAdapter.notifyDataSetChanged();


    }


    @OnClick(R.id.iv_back) void Onclick(){
        finish();
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this,CoursePlayActivity.class);
        startActivity(intent);
    }
}
