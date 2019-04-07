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
import android.widget.ListView;

import com.example.a325.R;
/*import com.example.a325.acitvities.CoursePlayActivity;*/
import com.example.a325.acitvities.CoursePlayActivity;
import com.example.a325.adapters.HomeAdapter;
import com.example.a325.datas.CourseListData;
import com.example.a325.utils.HttpRequest;
import com.example.a325.utils.HttpUrl;
import com.recker.flybanner.FlyBanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener , ListView.OnItemClickListener{



    @Bind(R.id.iv_study_latest)
    ImageView mivStudyLatest;
    @Bind(R.id.iv_search)
    ImageView mivSearch;
    @Bind(R.id.iv_scan)
    ImageView mivScan;
    @Bind(R.id.listview)
    ListView mlistview;


    private View mHeaderView;
    private HomeAdapter mAdapter;
    private List<CourseListData> mCourseDatas;
    private FlyBanner mBanner;


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
        setupClick();

       // new CourseListAsyncTask().execute();    //课程列表数据载入

        initLocalBanner();
        initLocalCourse();


        return view;
    }

    private void init(){
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_header,null);
        mlistview.addHeaderView(mHeaderView);
        mCourseDatas = new ArrayList<>();
        mAdapter = new HomeAdapter(getActivity(),mCourseDatas);
        mlistview.setAdapter(mAdapter);
        mlistview.setOnItemClickListener(this);

        mBanner = ButterKnife.findById(mHeaderView,R.id.banner);

    }
    private void setupClick(){

    }

    private void initLocalBanner(){
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.course_local);
        images.add(R.drawable.course_local1);
        images.add(R.drawable.course_local2);
        mBanner.setImages(images);
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


    /*private class CourseListAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String url = HttpUrl.getInstance().getCourseListUrl();
            Map<String, String> params = HttpUrl.getInstance().getCourseListParams(1);

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            analysisCourseListJsonData(s);

        }
    }
    private void analysisCourseListJsonData(String s) {
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("errorCode");

            if (errorCode == 1000) {
                JSONArray array = object.getJSONArray("data");

                for (int i = 0; i< array.length(); i++) {
                    CourseListData data = new CourseListData();
                    object = array.getJSONObject(i);

                    data.setId(object.getInt("id"));
                    data.setName(object.getString("name"));
                    data.setPic(object.getString("pic"));
                    data.setDesc(object.getString("desc"));
                    data.setIsLearned(object.getInt("is_learned"));
                    data.setCompanyId(object.getInt("company_id"));
                    data.setNumbers(object.getInt("numbers"));
                    data.setUpdateTime(object.getLong("update_time"));
                    data.setCoursetype(object.getInt("coursetype"));
                    data.setDuration(object.getLong("duration"));
                    data.setFinished(object.getInt("finished"));
                    data.setIsFollow(object.getInt("is_follow"));
                    data.setMaxChapterSeq(object.getInt("max_chapter_seq"));
                    data.setMaxMediaSeq(object.getInt("max_media_seq"));
                    data.setLastTime(object.getLong("last_time"));
                    data.setChapterSeq(object.getInt("chapter_seq"));
                    data.setMediaSeq(object.getInt("media_seq"));

//                    debug(data.toString());
                    mCourseDatas.add(data);
                }

                mAdapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/


    @Override
    public void onClick(View view) {


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getActivity(), CoursePlayActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
    }
}
