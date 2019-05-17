package com.example.a325.acitvities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.a325.R;
import com.example.a325.adapters.ClassifyAdapter;
import com.example.a325.bases.BaseActivity;
import com.example.a325.datas.CourseListData;
import com.example.a325.fragments.HomeFragment;
import com.example.a325.utils.HttpRequest;
import com.example.a325.utils.HttpUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassifyActivity extends BaseActivity implements ClassifyAdapter.OnItemClickListener {


    @Bind(R.id.recycler)
    RecyclerView mrecycler;
    @Bind(R.id.tv_classify)
    TextView mtvClassify;

    private List<CourseListData> mCourseDatas;
    private ClassifyAdapter mAdapter;
    private int id;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_classify;
    }

    @Override
    protected void init() {
        initView();
      //  initLocalCourse();
        new CourseListAsyncTask().execute();

    }
    private class CourseListAsyncTask extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... voids) {
            String url = HttpUrl.getInstance().getCourseListUrl();
            return HttpRequest.getInstance().POST(url,null);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.e("CourseList",s);
            analysisCourseListJsonData(s);
        }
    }

    private void analysisCourseListJsonData(String s) {
        List<CourseListData> mCourseDatas2 = new ArrayList<>();
        Gson gson = new Gson();
        mCourseDatas2 = gson.fromJson(s,new TypeToken<List<CourseListData>>(){}.getType());
        if (id == 2){//公务员考试
            for(CourseListData data:mCourseDatas2){
                if((data.getId() > 6) && (data.getId() <11) ){
                    mCourseDatas.add(data);
                }
            }
        }
        else if(id == 1){//全部课程
            mCourseDatas.addAll(mCourseDatas2);
        }
        else if(id == 3){//财会类
            for(CourseListData data:mCourseDatas2){
                if(data.getId() > 10){
                    mCourseDatas.add(data);
                }
            }

        }
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mCourseDatas = new ArrayList<>();
        mAdapter = new ClassifyAdapter(this, mCourseDatas);
        mAdapter.setOnItemClickListener(this);
        mrecycler.setAdapter(mAdapter);

        mrecycler.setLayoutManager(new LinearLayoutManager(this));
        mrecycler.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);//去掉下滑阴影
        mtvClassify.setText(getIntent().getStringExtra("name"));

        id = getIntent().getIntExtra("id",0);
    }

    private void initLocalCourse() {
        for (int i = 0; i < 7; i++) {
            CourseListData data = new CourseListData();

            data.setName("课程" + i);
            data.setNumbers(i);
            data.setFinished(1);
            mCourseDatas.add(data);

        }
        mAdapter.notifyDataSetChanged();


    }


    @OnClick(R.id.iv_back)
    void Onclick() {
        finish();
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, CoursePlayActivity.class);
        int Id = mCourseDatas.get(position).getId();
        String title = mCourseDatas.get(position).getName();
        intent.putExtra("id",Id);
        intent.putExtra("title",title);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
