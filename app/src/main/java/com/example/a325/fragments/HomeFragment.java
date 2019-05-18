package com.example.a325.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.a325.R;
/*import com.example.a325.acitvities.CoursePlayActivity;*/
import com.example.a325.acitvities.ClassifyActivity;
import com.example.a325.acitvities.CoursePlayActivity;
import com.example.a325.adapters.HomeAdapter;
import com.example.a325.datas.BannerData;
import com.example.a325.datas.CourseListData;
import com.example.a325.utils.HttpRequest;
import com.example.a325.utils.HttpUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener , ListView.OnItemClickListener
    ,FlyBanner.OnItemClickListener{



    @Bind(R.id.iv_study_latest)
    ImageView mivStudyLatest;
    @Bind(R.id.iv_search)
    ImageView mivSearch;
    @Bind(R.id.iv_scan)
    ImageView mivScan;
    @Bind(R.id.listview)
    ListView mlistview;

    private LinearLayout mTab_One;//公考
    private LinearLayout mTab_Two;//财会考试


    private View mHeaderView;
    private HomeAdapter mAdapter;
    private List<CourseListData> mCourseDatas;
    private FlyBanner mBanner;
    private List<BannerData> mBannerDatas;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);


        init();
        new BannerAsyncTask().execute();
        new CourseListAsyncTask().execute();

        return view;
    }

    private void init(){
        initView();
        setupClick();

    }

    private void initView(){
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_header,null);
        mlistview.addHeaderView(mHeaderView);
        mCourseDatas = new ArrayList<>();
        mAdapter = new HomeAdapter(getActivity(),mCourseDatas);
        mlistview.setAdapter(mAdapter);
        mlistview.setOnItemClickListener(this);

        mBanner = ButterKnife.findById(mHeaderView,R.id.banner);
        mBanner.setOnItemClickListener(this);

        mTab_One = ButterKnife.findById(mHeaderView,R.id.tab_one);
        mTab_Two = ButterKnife.findById(mHeaderView, R.id.tab_two);

    }
    private void setupClick(){
        mTab_One.setOnClickListener(this);
        mTab_Two.setOnClickListener(this);
    }
    private class CourseListAsyncTask extends AsyncTask<Void,Void,String>{
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



    private class BannerAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String url = HttpUrl.getInstance().getBannerUrl();

            return HttpRequest.getInstance().POST(url,null);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // Log.e("banner",s);
            analysisBannnerJsonData(s);

        }
    }

    private void analysisBannnerJsonData(String s) {
        mBannerDatas = new ArrayList<>();
        Gson gson = new Gson();
        mBannerDatas =   gson.fromJson(s ,new TypeToken<List<BannerData>>(){}.getType());
        setBanner();
    }

    private void analysisCourseListJsonData(String s) {
        List<CourseListData> mCourseDatas2 = new ArrayList<>();
        Gson gson = new Gson();
        mCourseDatas2 = gson.fromJson(s,new TypeToken<List<CourseListData>>(){}.getType());
        mCourseDatas.addAll(mCourseDatas2);
        mAdapter.notifyDataSetChanged();
    }

    private void setBanner() {
        List<String> imgs = new ArrayList<String>();
        for(BannerData data : mBannerDatas){
            imgs.add(data.getPic());
        }
        mBanner.setImagesUrl(imgs);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab_one:
                Intent intent1 = new Intent(getActivity(), ClassifyActivity.class);
                intent1.putExtra("id",2);
                intent1.putExtra("name","公务员考试");
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);

                break;
            case R.id.tab_two:
                Intent intent2 = new Intent(getActivity(), ClassifyActivity.class);
                intent2.putExtra("id",3);
                intent2.putExtra("name","金融财会考试");
                startActivity(intent2);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);

                break;
        }

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     *
     * listView点击事件
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getActivity(), CoursePlayActivity.class);
            int Id = mCourseDatas.get(i-1).getId();
            String title = mCourseDatas.get(i-1).getName();
            intent.putExtra("id",Id);
            intent.putExtra("title",title);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
    }

    /**
     *
     * flyBanner点击事件
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), CoursePlayActivity.class);
        int Id = mBannerDatas.get(position).getId();
        String title = mBannerDatas.get(position).getName();
        intent.putExtra("id",Id);
        intent.putExtra("title",title);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
    }


}
