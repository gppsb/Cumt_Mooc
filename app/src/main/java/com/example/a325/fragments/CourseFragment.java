package com.example.a325.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.a325.R;
import com.example.a325.acitvities.ClassifyActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends Fragment implements View.OnClickListener {


    @Bind(R.id.iv_classify)
    ImageView mivClassify;
    @Bind(R.id.iv_study_latest)
    ImageView mivStudyLatest;
    @Bind(R.id.iv_search)
    ImageView mivSearch;
    @Bind(R.id.iv_scan)
    ImageView mivScan;
    @Bind(R.id.listview)
    ListView mlistview;

    public CourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        ButterKnife.bind(this, view);

        setupClick();
        return view;

    }

    private void setupClick(){
        mivClassify.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_classify:
                Intent intent1 = new Intent(getActivity(),ClassifyActivity.class);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_none);
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
