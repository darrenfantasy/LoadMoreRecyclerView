package com.fantasy.loadmorerecyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fantasy.loadmorerecyclerview.BaseLoadMoreAdapter;

/**
 * Created by darrenfantasy on 2018/3/15.
 */

public class TestLoadMoreAdapter extends BaseLoadMoreAdapter {

    @Override
    public RecyclerView.ViewHolder subOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.test_item, null);
        TestViewHolder testViewHolder = new TestViewHolder(view);
        return testViewHolder;
    }

    @Override
    public void subOnBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TestViewHolder testViewHolder = (TestViewHolder) holder;
        testViewHolder.testTextView.setText("darrenfantasy:" + position);
    }

    class TestViewHolder extends RecyclerView.ViewHolder {
        private TextView testTextView;

        public TestViewHolder(View itemView) {
            super(itemView);
            this.testTextView = itemView.findViewById(R.id.test_item_tv);
        }
    }
}
