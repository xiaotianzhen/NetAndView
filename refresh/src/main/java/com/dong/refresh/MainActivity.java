package com.dong.refresh;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.dong.refresh.adapter.MyAdapter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MaterialRefreshLayout mRefreshLayout;
    private List<String> mList = new ArrayList<String>();
    private final static int START_NORMAL = 0;
    private final static int START_REFRESH = 1;
    private final static int START_MORE = 2;
    private MyAdapter adapter;
    private int m = 1;
    private int n = 1;
    private int state = START_NORMAL;

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initRefreshLayout();
        getData(1);
    }

    private void initRefreshLayout() {

        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
               refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (m>5) {
                            Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                        } else {
                            loadmore();
                        }
                        mRefreshLayout.finishRefreshLoadMore();
                    }
                });
            }
        });
    }

    private void getData(int m) {
        String str = "";

        for (n = 1; n <= m; n++) {
            for (int i = 1; i <= 20; i++) {
                str = "第" + i + "条";
                mList.add(str);
            }
        }
        showData();
    }

    private void showData() {

        switch (state) {
            case START_NORMAL:
                adapter = new MyAdapter(getApplicationContext(), mList);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getApplicationContext()));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                break;
            case START_REFRESH:
                adapter.addData(mList);
                mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;
            case START_MORE:
                adapter.addData(adapter.getData().size(), mList);
                mRecyclerView.scrollToPosition(mList.size());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }
    }

    public void refresh() {
        state = START_REFRESH;
        adapter.clearData(mList);
        getData(1);
    }

    public void loadmore() {
        state = START_MORE;
        getData(m + 1);
    }

}
