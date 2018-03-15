package com.fantasy.loadmorerecyclerviewdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.fantasy.loadmorerecyclerview.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LoadMoreRecyclerView mRecyclerView;
    private TestLoadMoreAdapter mBaseLoadMoreAdapter;
    private int mPage = 1;
    private TestHandler mTestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPage++;
                getDatas(mPage);
            }
        });
        mBaseLoadMoreAdapter = new TestLoadMoreAdapter();
        mRecyclerView.setAdapter(mBaseLoadMoreAdapter);
        mTestHandler = new TestHandler();
        getDatas(1);
    }

    private void getDatas(final int page) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List list = new ArrayList();
                for (int i = page * 7; i < page * 7 + 7; i++) {
                    list.add(i);
                }
                Message message = Message.obtain();
                message.what = 1;
                message.obj = list;
                mTestHandler.sendMessage(message);
                if (page<7){
                    mBaseLoadMoreAdapter.setIsHasMore(true);
                    mRecyclerView.setIsHasMore(true);
                }else {
                    mBaseLoadMoreAdapter.setIsHasMore(false);
                    mRecyclerView.setIsHasMore(false);
                }
                mRecyclerView.setIsLoadingMore(false);

            }
        }.start();
    }

    class TestHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                List list = (List) msg.obj;
                mBaseLoadMoreAdapter.addDataList(list);
            }
        }
    }
}
