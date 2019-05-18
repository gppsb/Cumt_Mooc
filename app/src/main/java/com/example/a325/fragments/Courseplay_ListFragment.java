package com.example.a325.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a325.R;
import com.example.a325.adapters.cpAdapter;
import com.example.a325.bases.BaseFragment;
import com.example.a325.datas.CpData;
import com.example.a325.utils.HttpRequest;
import com.example.a325.utils.HttpUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vov.vitamio.widget.VideoView;


/**
 * 249360
 * A simple {@link Fragment} subclass.
 */
public class Courseplay_ListFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.listview)
    ListView mListView;
    private VideoView videoView;
    private cpAdapter mAdapter;
    private List<CpData> listDatas = new ArrayList<>();
    private int mCurrentPosition = 1;//当前播放视频的位置
    private int mId;


    @Override

    protected int getLayoutId() {
        return R.layout.fragment_courseplay__list;
    }

    @Override
    protected void init() {
        mId = getActivity().getIntent().getIntExtra("id", 0);


        mListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        mAdapter = new cpAdapter(getActivity(), listDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        // initLocalCpData();
        new CpAsyncTask().execute();

    }

    private void initLocalCpData() {
        CpData data1 = new CpData();
        CpData data2 = new CpData();

        //  ChapterSeq-MediaSeq-Name,duration(时间),isSelected,isTitle
        // ChapterName//章节名
        data1.setMediaId(1);
        data1.setTitle(true);
        data1.setChapterName("第一章");
        listDatas.add(data1);

        data2.setChapterSeq(1);
        data2.setMediaSeq(1);
        data2.setName("第一章第一节");
        data2.setDuration(1001);
        data2.setSeleted(true);
        data2.setTitle(false);
        listDatas.add(data2);

        mAdapter.notifyDataSetChanged();


    }

    private class CpAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String url = HttpUrl.getInstance().getCpInfo();
            Map<String, String> params = HttpUrl.getInstance().getCpInfoParams(mId + "");

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            analysisJsonData(s);
        }
    }

    private void analysisJsonData(String s) {
        List<CpData> listData2 = new ArrayList<>();
        Gson gson = new Gson();
        listData2 = gson.fromJson(s, new TypeToken<List<CpData>>() {
        }.getType());
        listDatas.addAll(listData2);
        listData2.get(1).setSeleted(true);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CpData data = listDatas.get(i);
        mCurrentPosition = i;
        if (!data.isTitle()) {
            clearListSelected();
            data.setSeleted(true);
            mAdapter.notifyDataSetChanged();
            videoView = getActivity().findViewById(R.id.videoView);
            videoView.setVideoPath(data.getMediaUrl());
            //if (mListener != null) {
            //  mListener.onPlayVideo(data.getMediaUrl());
            // }
        }
    }

    //public void setPlayVideoListener(PlayVideoListener listener) {
    //  mListener = listener;
    // }
    private void clearListSelected() {
        for (CpData data : listDatas)
            data.setSeleted(false);
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


}
