package com.example.a325.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a325.R;
import com.example.a325.adapters.ClassifyAdapter;
import com.example.a325.datas.ClassifyData;
import com.example.a325.datas.CourseListData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllCourseFragment extends Fragment implements View.OnClickListener, ClassifyAdapter.OnItemClickListener{

    @Bind(R.id.iv_classify)
    ImageView mivClassify;
    @Bind(R.id.iv_search)
    ImageView mivSearch;
    @Bind(R.id.recycler)
    RecyclerView mrecycler;

    private List<ClassifyData> listDatas; //课程分类数据列表
    private ClassifyAdapter mAdapter;   //RecyclerView装载器

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

        return view;
    }

    private void init(){
        listDatas = new ArrayList<>();
        mAdapter = new ClassifyAdapter(getActivity(),listDatas);
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
        ClassifyData tit = new ClassifyData();
        ClassifyData con = new ClassifyData();
        for(int i =0; i<3; i++){
            tit.setName("title"+i);
            tit.setNumbers(0);
            tit.setTitle(true);
            listDatas.add(tit);
            for(int i2 =0; i2<5; i2++){
                con.setName("con"+i2);
                con.setNumbers(i2);
                con.setPic("");
                listDatas.add(con);
            }
        }

        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
