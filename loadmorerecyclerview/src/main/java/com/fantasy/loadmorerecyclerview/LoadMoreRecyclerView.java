package com.fantasy.loadmorerecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


/**
 * Created by darrenfantasy on 2018/1/24.
 */

public class LoadMoreRecyclerView extends RecyclerView {
    private LayoutManager mLayoutManager;
    private OnScrollListener mOnScrollListener;
    private LoadMoreListener mLoadMoreListener;
    private boolean mIsLoadingMore = false;
    private boolean mIsHasMore = true;

    public LoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {
        mOnScrollListener = new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (null != mLoadMoreListener && mIsHasMore && !mIsLoadingMore && dy >= 0) {
                    int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                    int totalItemCount = mLayoutManager.getItemCount();
                    if (lastVisibleItem == totalItemCount - 1) {
                        setIsLoadingMore(true);
                        mLoadMoreListener.onLoadMore();
                    }
                }
            }
        };
        this.addOnScrollListener(mOnScrollListener);
    }


    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        mLayoutManager = layout;
    }

    /**
     * 设置加载更多的监听
     *
     * @param listener
     */
    public void setLoadMoreListener(LoadMoreListener listener) {
        mLoadMoreListener = listener;
    }

    /**
     * 设置正在加载更多
     *
     * @param loadingMore
     */
    public void setIsLoadingMore(boolean loadingMore) {
        this.mIsLoadingMore = loadingMore;
    }


    public void setIsHasMore(boolean isHasMore) {
        this.mIsHasMore = isHasMore;
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }
}
