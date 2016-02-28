package com.bookmarketer.nw.bookmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    private Button mUploadBtn;
    private Button mActiveBtn;
    private Button mlogOutBtn;
    private Button mChatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mUploadBtn = (Button) findViewById(R.id.uploadBtn);
        mActiveBtn = (Button) findViewById(R.id.activeBtn);
        mlogOutBtn = (Button) findViewById(R.id.logoutBtn);
        mChatBtn = (Button) findViewById(R.id.chatBtn);
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
        mChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
