package kz.tech.testhackernewsclient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import javax.inject.Inject;

import kz.tech.testhackernewsclient.adapters.AdapterStories;
import kz.tech.testhackernewsclient.db.DaoBest;
import kz.tech.testhackernewsclient.db.DaoNew;
import kz.tech.testhackernewsclient.db.DaoTop;
import kz.tech.testhackernewsclient.models.Model;
import kz.tech.testhackernewsclient.mvp.IView;
import kz.tech.testhackernewsclient.tools.NetworkStatus;

public class MainActivity extends AppCompatActivity implements IView {
    private static final String TAG = "TAG";
    private MainPresenter presenter;
    private RecyclerView recyclerView;
    private AdapterStories adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    //  Var for ScrollListener
    private LinearLayoutManager linearLayoutManager;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    //  Var check status net
    private boolean isScroll = false;
    private boolean isNetwork = false;
    @Inject
    DaoNew daoNew;
    @Inject
    DaoTop daoTop;
    @Inject
    DaoBest daoBest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  init views
        initViews();
        //  init dagger
        App.app().getComponent().injectsActivity(this);
        //  init presenter
        presenter = new MainPresenter(this, daoNew, daoTop, daoBest);
        presenter.onStartStorage();

        if (NetworkStatus.isNetworkConnected(this)) {
            isNetwork = true;
        } else {
            isNetwork = false;
            onToastMsg("No internet connection");
        }

    }
    private void initViews() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("New Stories"));
        tabLayout.addTab(tabLayout.newTab().setText("Top Stories"));
        tabLayout.addTab(tabLayout.newTab().setText("Best Stories"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: presenter.getNewStories(); break;
                    case 1: presenter.getTopStories(); break;
                    case 2: presenter.getBestStories(); break;
                }
                Log.e(TAG, " select");
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.e(TAG, " unselected");
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e(TAG, " reselected");
            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout)this.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if (isNetwork) {
                            swipeRefreshLayout.setRefreshing(false);
                            isScroll = true;
                            previousTotal = 0;
                            //           UPDATE
                            presenter.updateStories(tabLayout.getSelectedTabPosition()+1);
                            Log.e(TAG, " TRUE");
                        } else {
                            Log.e(TAG, " FALSE");
                        }
                    }
                }
        );
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScroll) {
                    visibleItemCount = recyclerView.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                    // Handling the infinite scroll
                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                        //           UPDATE

                        loading = true;
                    }
                }
            }
        });
        adapter = new AdapterStories(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterStories.OnItemClickListener() {
            @Override
            public void onItemClick(String url) {
                startActivity(new Intent(MainActivity.this, WebActivity.class).putExtra("url", url));
            }

            @Override
            public void onCommentClick(String id) {

            }
        });
    }
    @Override
    public void setStory(Model m) {
        adapter.updateDate(m);
    }
    @Override
    public void setStories(List<Model> list) {
        adapter.updateDates(list);
    }
    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroyView();
        }
    }
}
