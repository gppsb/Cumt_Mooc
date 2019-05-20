package com.example.a325.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a325.R;
import com.example.a325.acitvities.ClassifyActivity;
import com.example.a325.acitvities.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    @Bind(R.id.iv_header)
    ImageView ivHeader;

    private View mineView;
    private final int SUBACTIVITY1 = 1;//resqudeCode请求码
    final String TAG = "InMine";


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
        ifLogin();

    }


    private void ifLogin() {         //判断是否登录
        String user = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).getString("user", " ");
        Log.e(TAG, user);
        if (user != " ") {
            ivHeader.setImageResource(R.drawable.img_1);
            tvName.setText(user);
        } else {
            ivHeader.setImageResource(R.drawable.course_default_bg);
            tvName.setText("请点击头像登录");
        }
    }

    private void setupClick() {
        tabFollow.setOnClickListener(this);
        tabDownload.setOnClickListener(this);
        //  ivHeader.setOnClickListener(this);

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


    @OnClick(R.id.iv_header)
    public void onClick() {
        Log.e(TAG, "点动了");
        String user = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).getString("user", " ");
        if (user.equals(" ")) {//未登录
            Log.e(TAG, "user为空");
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent, SUBACTIVITY1);
        } else {//已登录
            //clear
            Log.e(TAG, "view被点击，登出");
            SharedPreferences sp = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.commit();
            ivHeader.setImageResource(R.drawable.course_default_bg);
            tvName.setText("请点击头像登陆");
            //clear;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String uname = data.getExtras().getString("uname");
        ivHeader.setImageResource(R.drawable.img_1);
        tvName.setText(uname);
    }
}
