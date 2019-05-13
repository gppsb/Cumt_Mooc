package com.example.a325;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a325.fragments.AllCourseFragment;
import com.example.a325.fragments.DownloadFragment;
import com.example.a325.fragments.HomeFragment;
import com.example.a325.fragments.MineFragment;
import com.example.a325.test.HttpConnection;
import com.example.a325.test.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.viewpager)
    ViewPager mviewpager;
    @Bind(R.id.tv_home)
    TextView mtvHome;
    @Bind(R.id.tab_home)
    RelativeLayout mtabHome;
    @Bind(R.id.tv_allcourse)
    TextView mtvAllcourse;
    @Bind(R.id.tab_allcourse)
    RelativeLayout mtabAllcourse;
    @Bind(R.id.tv_download)
    TextView mtvDownload;
    @Bind(R.id.tab_download)
    RelativeLayout mtabDownload;
    @Bind(R.id.tv_mine)
    TextView mtvMine;
    @Bind(R.id.tab_mine)
    RelativeLayout mtabMine;

    private List<Fragment> mFragments;
    private HomeFragment mHomeFragment;
    private AllCourseFragment mAllCourseFragment;
    private DownloadFragment mDownloadFragment;
    private MineFragment mMineFragment;

    public static final String TAG="MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupContent();
        setupTabClick();



    }



    private void setupContent(){
        initFragments();

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mviewpager.setAdapter(adapter);
        mviewpager.addOnPageChangeListener(mOnPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            clearTabBackgroundWithTextColor();
            switch (i){
                case 0:
                    mtabHome.setBackgroundResource(R.drawable.community_2);
                    mtvHome.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;
                case 1:
                    mtabAllcourse.setBackgroundResource(R.drawable.course_2);
                    mtvAllcourse.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;
                case 2:
                    mtabDownload.setBackgroundResource(R.drawable.download_2);
                    mtvDownload.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;
                case 3:
                    mtabMine.setBackgroundResource(R.drawable.mine_2);
                    mtvMine.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;
            }


        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private void clearTabBackgroundWithTextColor(){
        mtabAllcourse.setBackgroundResource(R.drawable.course_1);
        mtabDownload.setBackgroundResource(R.drawable.download_1);
        mtabHome.setBackgroundResource(R.drawable.community_1);
        mtabMine.setBackgroundResource(R.drawable.mine_1);

        mtvAllcourse.setTextColor(getResources().getColor(R.color.tab_unselected));
        mtvDownload.setTextColor(getResources().getColor(R.color.tab_unselected));
        mtvHome.setTextColor(getResources().getColor(R.color.tab_unselected));
        mtvMine.setTextColor(getResources().getColor(R.color.tab_unselected));

    }
    private void initFragments(){
        mFragments = new ArrayList<>();

        mAllCourseFragment = new AllCourseFragment();
        mHomeFragment = new HomeFragment();
        mDownloadFragment = new DownloadFragment();
        mMineFragment = new MineFragment();

        mFragments.add(mHomeFragment);
        mFragments.add(mAllCourseFragment);
        mFragments.add(mDownloadFragment);
        mFragments.add(mMineFragment);
    }
    private void setupTabClick(){
        mtabDownload.setOnClickListener(this);
        mtabHome.setOnClickListener(this);
        mtabAllcourse.setOnClickListener(this);
        mtabMine.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab_home:
                mviewpager.setCurrentItem(0);
                break;
            case R.id.tab_allcourse:
                mviewpager.setCurrentItem(1);
                break;
            case R.id.tab_download:
                mviewpager.setCurrentItem(2);
                break;
            case R.id.tab_mine:
                mviewpager.setCurrentItem(3);
                break;
        }
    }



    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }
}





//test网络功能
//    private void test() {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                User user = new User();
////                user.setId(editText_id.getText().toString());
////                user.setPassword(editText_password.getText().toString());
//
//                String address = "http://192.168.137.1:8080/ResponServlet";
//                HttpConnection.sendOkHttpRequest(address, user, new okhttp3.Callback(){
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.d(MainActivity.TAG,"连接失败");
//                    }
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String responseData = response.body().string();
//                        System.out.println("响应信息： " + responseData);
//                        parseJSONWithGSON(responseData);
//                        Log.d("2","链接成功");
//
//                        Looper.prepare();
//                        Toast.makeText(MainActivity.this, "succeed", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//                });
//
//            }
//        }).start();
//
//
//    }
//    public void parseJSONWithGSON(String jsonData){
//        Gson gson = new Gson();
//        User user = gson.fromJson(jsonData, User.class);
//        // Log.d(MainActivity.TAG,"no = " + user.getNo());
//        Log.d(MainActivity.TAG,"id = " + user.getId());
//        Log.d(MainActivity.TAG,"password = " + user.getPassword());
//    }
//
//    //test网络功能