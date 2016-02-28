package com.bookmarketer.nw.bookmarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ChatActivity extends AppCompatActivity {

    private Button mUploadBtn;
    private Button mActiveBtn;
    private Button mlogOutBtn;
    private Button mSearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mUploadBtn = (Button) findViewById(R.id.uploadBtn);
        mActiveBtn = (Button) findViewById(R.id.activeBtn);
        mlogOutBtn = (Button) findViewById(R.id.logoutBtn);
        mSearchBtn = (Button) findViewById(R.id.searchBtn);
        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(i);
                finish();
            }
        });
        mActiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActiveActivity.class);
                startActivity(i);
                finish();
            }
        });
        mlogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(i);
                finish();
            }
        });
    }
}
