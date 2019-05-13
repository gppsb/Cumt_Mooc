package com.example.a325.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.a325.R;
import com.example.a325.acitvities.ClassifyActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.tab_follow)
    LinearLayout tabFollow;
    @Bind(R.id.tab_download)
    LinearLayout tabDownload;
    @Bind(R.id.tab_massage)
    LinearLayout tabMassage;
    @Bind(R.id.tab_personalInfo)
    LinearLayout tabPersonalInfo;
    @Bind(R.id.tab_note)
    LinearLayout tabNote;
    @Bind(R.id.tab_setting)
    LinearLayout tabSetting;

    private View mineView;




    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);

        initView();
        setupClick();

        return view;
    }

    private void initView() {
        mineView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mine_content, null);



    }

    private void setupClick() {
        tabFollow.setOnClickListener(this);
        tabDownload.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_follow:
                Intent intent1 = new Intent(getActivity(), ClassifyActivity.class);
                startActivity(intent1);
                break;
            case R.id.tab_download:
                Intent intent2 = new Intent(getActivity(), ClassifyActivity.class);
                startActivity(intent2);
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
