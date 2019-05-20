package com.example.a325.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a325.R;
import com.example.a325.acitvities.CoursePlayActivity;
import com.example.a325.adapters.HomeAdapter;
import com.example.a325.datas.CourseListData;
import com.example.a325.utils.HttpRequest;
import com.example.a325.utils.HttpUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment implements ListView.OnItemClickListener {


    @Bind(R.id.tv_cons)
    TextView mtvCons;
    @Bind(R.id.tv_clear)
    TextView mtvClear;
    @Bind(R.id.likeListView)
    ListView likeListView;
    /*@Bind(R.id.progressbar)
    ProgressBar mprogressbar;*/

    private HomeAdapter mAdapter;
    private List<CourseListData> likeCourses;
    private boolean isLogin = false;
    private String uname;
    final private String TAG = "DownloadFragment";

    public DownloadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        ButterKnife.bind(this, view);

        init();
        getDiveceSize();



        return view;
    }

    private void init() {
        likeCourses = new ArrayList<>();
        mAdapter = new HomeAdapter(getActivity(),likeCourses);
        likeListView.setAdapter(mAdapter);
        likeListView.setOnItemClickListener(this);


        uname = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).getString("user"," ");

        if (!uname.equals(" ")){//如果登陆了，加载喜爱的课程
            Log.e(TAG,"目前登录的用户："+uname);
            new AllLikedAsyncTask().execute();
        }


    }

    private class AllLikedAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String url = HttpUrl.getInstance().getAllLikeUrl();

            Map<String, String> params = HttpUrl.getInstance().getAllLikeParams(uname);

            return HttpRequest.getInstance().POST(url,params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Log.e(TAG,"网络获取的数据为"+s);

            analysisLikeJsonData(s);


        }
    }

    private void analysisLikeJsonData(String s) {
        List<CourseListData> datas = new ArrayList<>();
        Gson gson = new Gson();
        datas = gson.fromJson(s,new TypeToken<List<CourseListData>>(){}.getType());
        likeCourses.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }


    /**
     *
     *
     *
     */
    private void getDiveceSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        long totalBlocks;
        long availableBlocks;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
            availableBlocks = stat.getAvailableBlocks();
        }

        String occupyText = formatSize(blockSize * (totalBlocks - availableBlocks));
        String availableText = formatSize(blockSize * availableBlocks);

        String text = occupyText + "/" + availableText;
        mtvCons.setText(text);

       /* mprogressbar.setMax((int) totalBlocks);
        mprogressbar.setProgress((int) (totalBlocks-availableBlocks));*/

    }

    private String formatSize(long size) {
        return Formatter.formatFileSize(getActivity(), size);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //点击事件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), CoursePlayActivity.class);
        int Id = likeCourses.get(i).getId();
        String title = likeCourses.get(i).getName();
        intent.putExtra("id",Id);
        intent.putExtra("title",title);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
    }
}
