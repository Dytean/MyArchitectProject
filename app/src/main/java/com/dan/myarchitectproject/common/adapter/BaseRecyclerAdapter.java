package com.dan.myarchitectproject.common.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Dan
 * Date: 2017/3/29 下午4:20
 * Description:
 * PackageName: com.example.dan.myapplication.tools
 * Copyright: 杭州安存网络科技有限公司
 **/

public abstract class BaseRecyclerAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private List<T> data;
    private int layoutResId;

    public BaseRecyclerAdapter(int layoutResId, List<T> data){
        this.data = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0){
            this.layoutResId = layoutResId;
        }else {
            throw new NullPointerException("请设置Item资源Id");
        }
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId,parent,false);
        return (VH) new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        bindData(holder,data.get(position));
    }

    protected abstract void bindData(VH holder, T data);

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder{
        private SparseArray<View> mViews;
        public BaseViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
        }

        public <T extends View> T findViewById(int viewId){
            View view = mViews.get(viewId);
            if (view == null){
                view = itemView.findViewById(viewId);
                mViews.put(viewId,view);
            }
            return (T)view;
        }

        public BaseViewHolder setText(int viewId, CharSequence s){
            TextView view = findViewById(viewId);
            view.setText(s);
            return this;
        }

        public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener){
            View view = findViewById(viewId);
            view.setOnClickListener(listener);
            return this;
        }

    }
}
