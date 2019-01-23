package com.italkbb.apklist;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangSiYe on 2018/12/10.
 * 测试更改
 */
public class AppsAdapter<T> extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(View v);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(View v, int position);
    }

    private List<T> mList = new ArrayList<>();

    public List<T> getmList() {
        return mList;
    }

    public AppsAdapter(List<T> datas) {
        mList.clear();
        mList.addAll(datas);
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemSelectedListener onItemSelectedListener;

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public void setData(List<T> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AppsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apps_recyclerview, parent, false);
        ViewHolder holder = new ViewHolder(view);

//        holder.tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.onItemClick(v);
//            }
//        });
//        holder.iv_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.onItemClick(v);
//            }
//        });
        holder.layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AppsAdapter.ViewHolder holder, final int position) {
        String content = ((MyAppInfo) mList.get(position)).getAppName();
        holder.tv_name.setText(content);
        String content1 = ((MyAppInfo) mList.get(position)).getPackageName();
        holder.tv_package.setText(content1);

        Drawable icon = ((MyAppInfo) mList.get(position)).getImage();
//        holder.tv.setTag(position);

        holder.iv_icon.setImageDrawable(icon);

        holder.layout_content.setTag(position);

        /** 设置Item的Selected事件 */
//        holder.tv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Log.e("RecyclerAdapter", "onFocusChange hasFocus:" + hasFocus + ",position:" + position);
//                if (hasFocus) {
//                    if (onItemSelectedListener != null) {
//                        onItemSelectedListener.onItemSelected(holder.itemView, position);
//                    }
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout_content;
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_package;

        public ViewHolder(View itemView) {
            super(itemView);
            layout_content = itemView.findViewById(R.id.layout_content);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_package = itemView.findViewById(R.id.tv_package);
        }
    }

}
