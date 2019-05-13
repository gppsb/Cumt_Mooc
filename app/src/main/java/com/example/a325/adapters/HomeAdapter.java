package com.example.a325.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a325.R;
import com.example.a325.datas.CourseListData;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

public class HomeAdapter extends BaseAdapter {

    private List<CourseListData> listDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public HomeAdapter(Context context,List<CourseListData> list){
        mContext = context;
        listDatas = list;
        inflater =LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return listDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CourseListData data = listDatas.get(i);
        ViewHolder holder = null;

        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.fragment_home_item,null);

            holder.img = ButterKnife.findById(view, R.id.img);
            holder.title = ButterKnife.findById(view, R.id.tv_title);
            holder.numbers = ButterKnife.findById(view, R.id.tv_number);
            holder.finished = ButterKnife.findById(view, R.id.tv_finished);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Picasso.with(mContext).load(data.getPic()).placeholder(R.drawable.course_default_bg).into(holder.img);


        holder.title.setText(data.getName()+" ");
        holder.numbers.setText(data.getNumbers()+"");
        if (data.getFinished() == 0){
            holder.finished.setTextColor(mContext.getResources().getColor(R.color.course_update_text));
            String update = "更新至"+data.getMaxChapterSeq()+"-"+data.getMaxMediaSeq();
            holder.finished.setText(update);
        }else {
            holder.finished.setTextColor(mContext.getResources().getColor(R.color.course_second_text));
            holder.finished.setText("更新完成");
        }
        return view;


    }

    private static class ViewHolder{
        ImageView img;
        TextView title;
        TextView numbers;
        TextView finished;
    }
}
