package com.example.a325.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a325.R;
import com.example.a325.adapters.HomeAdapter;
import com.example.a325.bases.BaseFragment;
import com.example.a325.datas.CourseListData;
import com.example.a325.utils.HttpRequest;
import com.example.a325.utils.HttpUrl;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Courseplay_IntroFragment extends BaseFragment {

    @Bind(R.id.listview)
    ListView mListView;

    private View mHeaderView;

    private TextView mTvTitle;
    private ImageView mIvIsFinish;
    private TextView mTvContent;

    private List<CourseListData> listDatas = new ArrayList<>();
    private HomeAdapter mAdapter;

    private int mId;






    @Override
    protected int getLayoutId() {
        return R.layout.fragment_courseplay__intro;
    }

    @Override
    protected void init() {

        mListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);

        listDatas.clear();
        mAdapter = new HomeAdapter(getActivity(), listDatas);
        mListView.setAdapter(mAdapter);
        setupHeaderView();

        new HeaderAsyncTask().execute();


    }

    private void setupHeaderView() {
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_courseplay_intro_header, null);

        mTvTitle = ButterKnife.findById(mHeaderView,R.id.tv_title);
        mIvIsFinish = ButterKnife.findById(mHeaderView,R.id.iv_finish);
        mTvContent = ButterKnife.findById(mHeaderView,R.id.tv_content);

        mListView.addHeaderView(mHeaderView);

        mId = getActivity().getIntent().getIntExtra("id",0);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class HeaderAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String url = HttpUrl.getInstance().getCourseIntro();
            Map<String, String> params = HttpUrl.getInstance().getCourseIntroParams(mId+"");

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // Log.e("CourseIntro",s);
            analysisHeaderJsonData(s);

        }
    }

    private void analysisHeaderJsonData(String s) {
        CourseListData intro = new CourseListData();
        Gson gson = new Gson();
        intro = gson.fromJson(s,CourseListData.class);

        mTvTitle.setText(intro.getName());
        mTvContent.setText(intro.getDesc());
        if(intro.getFinished() == 1){
            mIvIsFinish.setVisibility(View.GONE);
        }

    }

}
