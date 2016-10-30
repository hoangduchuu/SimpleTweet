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
    private Button btnPostStatus;
    private RestClient restClient;

    @BindView(R.id.edtStatus)
    EditText edtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_tweet);

        ButterKnife.bind(this);
        getSupportActionBar().setTitle("what happen now?");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        restClient = RestApplication.getRestClient();


        btnPostStatus = (Button)findViewById(R.id.btnPost);

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.miCompose:

                return true;
            case R.id.miProfile:
                postAction();
                Intent i = new Intent(PostTweetActivity.this, TimelineActivity.class);
                startActivity(i);
                Toast.makeText(this, "click miProfile", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void postAction() {
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

//    private void limitText() {
//        edtStatus.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Toast.makeText(PostTweetActivity.this, "before", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Toast.makeText(PostTweetActivity.this, "On", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                int n = Integer.parseInt(s.toString());
//                if (n > 100){
//                    s.replace(0,s.length(),"100");
//                }
//            }
//        });
//    }

}

