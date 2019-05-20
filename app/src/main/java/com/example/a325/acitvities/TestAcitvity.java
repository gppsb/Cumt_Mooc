package com.example.a325.acitvities;

import android.os.Bundle;

import com.example.a325.R;
import com.example.a325.bases.BaseActivity;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestAcitvity extends BaseActivity {
    @Bind(R.id.test)
    FlyBanner test;

    @Override
    protected int getLayoutId() {
        return R.layout.test;
    }

    @Override
    protected void init() {
        List<String> imgs = new ArrayList<String>();
        String img = "http://192.168.137.1:8080/pic/banner/banner1.png";
        String img2 = "http://192.168.137.1:8080/pic/banner/banner2.png";
        String img3 = "http://192.168.137.1:8080/pic/banner/banner1.png";
        imgs.add(img);imgs.add(img2);imgs.add(img3);
        test.setImagesUrl(imgs);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
