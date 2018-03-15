package com.fantasy.loadmorerecyclerview;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by darrenfantasy on 2018/1/25.
 */

public abstract class BaseLoadMoreAdapter extends RecyclerView.Adapter {
    private final int TYPE_LOAD_MORE_ITEM = 999;
    private final int HEAD_ITEM = 998;
    private List<Object> mTotalDataList;
    private boolean mIshasMore;

    public void addDataList(List<? extends Object> dataList) {
        if (this.mTotalDataList == null)
            this.mTotalDataList = new ArrayList<Object>();
        this.mTotalDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public List<Object> getTotalDataList() {
        return mTotalDataList;
    }

    public void setIsHasMore(boolean isHasMore) {
        this.mIshasMore = isHasMore;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOAD_MORE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more_foot_view, null);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(40, parent.getContext())));
            return new FootViewHolder(itemView);
        } else {
            return this.subOnCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HEAD_ITEM) {

        } else if (getItemViewType(position) == TYPE_LOAD_MORE_ITEM) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            if (mIshasMore) {
                footViewHolder.footText.setVisibility(View.INVISIBLE);
                footViewHolder.loadingView.setVisibility(View.VISIBLE);
            } else {
                footViewHolder.loadingView.setVisibility(View.INVISIBLE);
                footViewHolder.footText.setVisibility(View.VISIBLE);
            }
        } else {
            this.subOnBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_ITEM;
        } else if (this.mTotalDataList.size() + 1 == position) {
            return TYPE_LOAD_MORE_ITEM;
        } else {
            return subGetItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        if (null == mTotalDataList)
            return 1;
        return mTotalDataList.size() + 2;
    }

    private int dip2px(float dpValue, Context context) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
        return (int) px;
    }

    public abstract RecyclerView.ViewHolder subOnCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void subOnBindViewHolder(RecyclerView.ViewHolder holder, int position);

    public int subGetItemViewType(int position) {
        return 0;
    }

    class FootViewHolder extends RecyclerView.ViewHolder {
        public TextView footText;
        public ImageView loadingView;

        public FootViewHolder(View itemView) {
            super(itemView);
            loadingView = itemView.findViewById(R.id.foot_loading);
            footText = itemView.findViewById(R.id.foot_text);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        public View headView;

        public HeadViewHolder(View itemView) {
            super(itemView);
            headView = itemView;
        }
    }
}
