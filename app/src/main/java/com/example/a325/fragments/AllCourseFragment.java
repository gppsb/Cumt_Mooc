package com.example.a325.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a325.R;
import com.example.a325.acitvities.ClassifyActivity;
import com.example.a325.adapters.AllCourseAdapter;
import com.example.a325.datas.AllCourseData;
import com.example.a325.utils.HttpRequest;
import com.example.a325.utils.HttpUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllCourseFragment extends Fragment implements View.OnClickListener, AllCourseAdapter.OnItemClickListener{

    @Bind(R.id.iv_classify)
    ImageView mivClassify;
    @Bind(R.id.iv_search)
    ImageView mivSearch;
    @Bind(R.id.recycler)
    RecyclerView mrecycler;

    private List<AllCourseData> listDatas; //课程分类数据列表
    private AllCourseAdapter mAdapter;   //RecyclerView装载器

    public AllCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_course, container, false);
        ButterKnife.bind(this, view);

        init();
        initLocalClassify();
        //new ClassifyAsyncTask().execute();

        return view;
    }

    private void init(){
        listDatas = new ArrayList<>();
        mAdapter = new AllCourseAdapter(getActivity(),listDatas);
        mAdapter.setOnItemClickListener(this);
        mrecycler.setAdapter(mAdapter);

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {//设置RecyclerView的列数
            @Override
            public int getSpanSize(int i) {
                return mAdapter.isSection(i) ? layoutManager.getSpanCount():1;
            }
        });


        mrecycler.setLayoutManager(layoutManager); //设置布局（RecycleView必要操作）
        mrecycler.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);//去掉下滑阴影

    }

    private void initLocalClassify(){
        //第一行
        AllCourseData con = new AllCourseData();con.setId(1);con.setName("全部课程");con.setNumbers(99);listDatas.add(con);
        //第二行，升学择业tit
        AllCourseData tit = new AllCourseData();tit.setId(101);tit.setName("升学择业");tit.setNumbers(50);tit.setTitle(true);listDatas.add(tit);
        //第三行，升学择业具体
        AllCourseData con1 = new AllCourseData();con1.setId(2);con1.setName("公务员考试");con1.setNumbers(4);;listDatas.add(con1);
        AllCourseData con2 = new AllCourseData();con2.setId(3);con2.setName("金融财会考试");con2.setNumbers(6);listDatas.add(con2);
        //计算机类
        AllCourseData tit1 = new AllCourseData();tit1.setId(102);tit1.setName("计算机");tit1.setNumbers(70);tit1.setTitle(true);listDatas.add(tit1);
        AllCourseData con3 = new AllCourseData();con3.setId(111);con3.setName("前沿技术");con3.setNumbers(15);;listDatas.add(con3);
        AllCourseData con4 = new AllCourseData();con4.setId(4);con4.setName("软件设计");con4.setNumbers(14);;listDatas.add(con4);
        AllCourseData con5 = new AllCourseData();con5.setId(5);con5.setName("计算机基础");con5.setNumbers(15);;listDatas.add(con5);
        AllCourseData con6 = new AllCourseData();con6.setId(6);con6.setName("网络技术");con6.setNumbers(16);;listDatas.add(con6);
        AllCourseData con7 = new AllCourseData();con7.setId(7);con7.setName("硬件系统");con7.setNumbers(15);;listDatas.add(con7);
        AllCourseData con8 = new AllCourseData();con8.setId(8);con8.setName("程序设计");con8.setNumbers(18);;listDatas.add(con8);
        AllCourseData con9 = new AllCourseData();con9.setId(9);con9.setName("数据结构");con9.setNumbers(5);;listDatas.add(con9);
        //外语类
        AllCourseData tit2 = new AllCourseData();tit2.setId(103);tit2.setName("外语");tit2.setNumbers(22);tit2.setTitle(true);listDatas.add(tit2);
        AllCourseData con10 = new AllCourseData();con10.setId(10);con10.setName("英语");con10.setNumbers(15);;listDatas.add(con10);
        AllCourseData con11 = new AllCourseData();con11.setId(11);con11.setName("俄语");con11.setNumbers(15);;listDatas.add(con11);
        AllCourseData con12 = new AllCourseData();con12.setId(12);con12.setName("法语");con12.setNumbers(12);;listDatas.add(con12);
        AllCourseData con13 = new AllCourseData();con13.setId(13);con13.setName("德语");con13.setNumbers(15);;listDatas.add(con13);
        AllCourseData con14 = new AllCourseData();con14.setId(14);con14.setName("意大利语");con14.setNumbers(15);;listDatas.add(con14);
        //工学
        AllCourseData tit3 = new AllCourseData();tit3.setId(104);tit3.setName("工学");tit3.setNumbers(29);tit3.setTitle(true);listDatas.add(tit3);
        AllCourseData con15 = new AllCourseData();con15.setId(15);con15.setName("电气信息");con15.setNumbers(15);;listDatas.add(con15);
        AllCourseData con16 = new AllCourseData();con16.setId(16);con16.setName("土木工程");con16.setNumbers(16);;listDatas.add(con16);
        AllCourseData con17 = new AllCourseData();con17.setId(17);con17.setName("机械工程");con17.setNumbers(17);;listDatas.add(con17);
















        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), ClassifyActivity.class);
        AllCourseData data = listDatas.get(position);
        intent.putExtra("name",data.getName());
        intent.putExtra("id",data.getId());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private class ClassifyAsyncTask extends AsyncTask<Void,Void,String> {


        @Override
        protected String doInBackground(Void... voids) {
            String url = HttpUrl.getInstance().getClassifyCourseUrl();
            return HttpRequest.getInstance().POST(url,null);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            analysisJsonData(s);

        }
    }

    private void analysisJsonData(String s) {
    }
}
