package com.example.a325.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a325.R;
import com.example.a325.acitvities.ClassifyActivity;
import com.example.a325.datas.CourseListData;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

public class ClassifyAdapter extends RecyclerView.Adapter<ClassifyAdapter.ViewHolder> {

    private List<CourseListData> listDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public ClassifyAdapter(Context context, List<CourseListData> list){
        listDatas = list;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_home_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            CourseListData data = listDatas.get(i);
            Picasso.with(mContext).load(R.drawable.course_local1).into(viewHolder.img);
            viewHolder.title.setText(data.getName()+"");
            viewHolder.numbers.setText(data.getNumbers()+"");
            if(data.getFinished() == 0){
                viewHolder.finished.setTextColor(mContext.getResources().getColor(R.color.course_update_text));
                String update = "更新至"+data.getMaxChapterSeq()+"-"+data.getMaxMediaSeq();
                viewHolder.finished.setText(update);
            }else {
                viewHolder.finished.setTextColor(mContext.getResources().getColor(R.color.course_second_text));
                viewHolder.finished.setText("更新完成");
            }
    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        TextView numbers;
        TextView finished;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            img = ButterKnife.findById(itemView, R.id.img);
            title = ButterKnife.findById(itemView,R.id.tv_title);
            numbers = ButterKnife.findById(itemView, R.id.tv_number);
            finished = ButterKnife.findById(itemView, R.id.tv_finished);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemClickListener != null){
                        itemClickListener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }

    }



    public ClassifyAdapter.OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
