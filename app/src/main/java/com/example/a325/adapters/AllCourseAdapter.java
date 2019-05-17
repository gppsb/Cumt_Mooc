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
import com.example.a325.datas.AllCourseData;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

public class AllCourseAdapter extends RecyclerView.Adapter<AllCourseAdapter.ViewHolder> {


    private List<AllCourseData> listDatas;
    private LayoutInflater inflater;
    private Context mContext;

    public AllCourseAdapter(Context context, List<AllCourseData> list){
        listDatas = list;
        inflater = LayoutInflater.from(context);
        mContext = context;
    }
    @NonNull
    @Override
    public AllCourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType == 0){//title
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_allcourse_item_title, viewGroup, false);
            view.setTag(0);
            return new ViewHolder(view);
        }else {//content
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_allcourse_item_content, viewGroup,false);
            view.setTag(1);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AllCourseAdapter.ViewHolder viewHolder, int position) {
        AllCourseData data = listDatas.get(position);
        if(isSection(position)){
            viewHolder.title.setText(data.getName());
        }else {
            Picasso.with(mContext).load(R.drawable.allcourse).into(viewHolder.image);
            viewHolder.name.setText(data.getName()+"");
            viewHolder.number.setText(data.getNumbers()+"");

        }

    }

    public boolean isSection(int position) {
        if (listDatas.get(position).isTitle())
            return true;

        return false;
    }

    @Override
    public int getItemCount() {
        return listDatas.size();    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        TextView name;
        TextView number;
        ImageView image;
        public ViewHolder(final View itemView) {
            super(itemView);
            if((int) itemView.getTag() == 0){//title
                title = ButterKnife.findById(itemView,R.id.tv_title);
            }else {//context
                name = ButterKnife.findById(itemView,R.id.tv_name);
                number = ButterKnife.findById(itemView,R.id.tv_number);
                image = ButterKnife.findById(itemView,R.id.image);
            }

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

    @Override
    public int getItemViewType(int position) {
        if (isSection(position))
            return 0;

        return 1;
    }

    public OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
