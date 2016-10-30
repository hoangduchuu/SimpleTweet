package com.example.hoang.Tweet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoang.Tweet.R;
import com.example.hoang.Tweet.api.RestApplication;
import com.example.hoang.Tweet.api.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class PostTweetActivity extends AppCompatActivity {
    private RestClient restClient;

    @BindView(R.id.edtStatus)
    EditText edtStatus;
    @BindView(R.id.btnPost)
    Button btnPostStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_tweet);

        ButterKnife.bind(this);
        getSupportActionBar().setTitle(getString(R.string.post_title));
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        restClient = RestApplication.getRestClient();

        clickPost();
    }



    private void clickPost() {
        btnPostStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postAction();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itPosts:
                postAction();
                startActivity(new Intent(getApplicationContext(), TimelineActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void postAction() {
        if (edtStatus.getText().length() > 140){
            Toast.makeText(this, getString(R.string.to_long), Toast.LENGTH_SHORT).show();
        }else{
            restClient.postTweet(edtStatus.getText().toString(),
                    new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                        }
                    });
            Toast.makeText(getApplicationContext(), "Posted on Twitter", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), TimelineActivity.class));
        }


    }

}

