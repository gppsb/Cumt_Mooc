package com.example.a325.acitvities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.a325.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClassifyActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.iv_back)
    ImageView mivBack;
    @Bind(R.id.iv_search)
    ImageView mivSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        ButterKnife.bind(this);

        setupClick();
    }

    private void setupClick(){
        mivBack.setOnClickListener(this);
        mivSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.iv_search:
                    break;

            }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_none,R.anim.slide_out_left);
    }
}
