package com.example.hoang.Tweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.hoang.Tweet.R;
import com.example.hoang.Tweet.adapter.TweetAdapter;
import com.example.hoang.Tweet.api.RestApplication;
import com.example.hoang.Tweet.api.RestClient;
import com.example.hoang.Tweet.model.Tweet;
import com.example.hoang.Tweet.util.EndlessRecyclerViewScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    @BindView(R.id.rView)
    RecyclerView rView;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.pbLoarMore)
    ProgressBar progressBar;

    private RestClient restClient;
    private TweetAdapter adapter;
    private ArrayList<Tweet> tweets;
    private EndlessRecyclerViewScrollListener scrollListener;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        tweets = new ArrayList<>();
        adapter = new TweetAdapter(this, tweets);
        rView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(linearLayoutManager);
        restClient = RestApplication.getRestClient();
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getPost(15,1);
        loadmore();
        pullRefresh();
    }

    private void loadmore() {
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("PAGES", String.valueOf(page));
                progressBar.setVisibility(View.VISIBLE);
                getPost(15, page);
            }
        };
        rView.addOnScrollListener(scrollListener);
    }

    private void pullRefresh() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tweets.clear();
                getPost(15,0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miCompose:
                Intent i = new Intent(getApplicationContext(), PostTweetActivity.class);
                startActivity(i);
                return true;
            case  R.id.itLogout:
                restClient.clearAccessToken();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getPost(int count, int curentPage) {
        restClient.getTimeline(count, curentPage, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUGER", response.toString());
                tweets.addAll(Tweet.fromJsonArray(response));
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                Log.d("DEBUGER", response.toString());
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("DEBUGER", errorResponse.toString());
            }
        });
    }
}

