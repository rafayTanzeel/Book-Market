package com.bookmarketer.nw.bookmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    private Button mUploadBtn;
    private Button mActiveBtn;
    private Button mlogOutBtn;
    private Button mChatBtn;
    private String TokenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mUploadBtn = (Button) findViewById(R.id.uploadBtn);
        mActiveBtn = (Button) findViewById(R.id.activeBtn);
        mlogOutBtn = (Button) findViewById(R.id.logoutBtn);
        mChatBtn = (Button) findViewById(R.id.chatBtn);

        mUploadBtn.setOnClickListener(this);
        mActiveBtn.setOnClickListener(this);
        mlogOutBtn.setOnClickListener(this);
        mChatBtn.setOnClickListener(this);

        TokenId = getIntent().getStringExtra("TOKEN_ID");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.uploadBtn:
                goToBarcodePage();
                break;
            case R.id.activeBtn:
                goToActiveSales();
                break;
            case R.id.logoutBtn:
                logoutOutNow();
                break;
            case R.id.chatBtn:
                goToChat();
                break;
        }
    }

    private void goToBarcodePage(){
        Intent i = new Intent(this, BarcodeScanner.class);
        i.putExtra("TOKEN_ID", TokenId);
        startActivity(i);
        finish();
    }

    private void goToActiveSales(){
        Intent i = new Intent(this, ActiveActivity.class);
        startActivity(i);
        finish();
    }

    private void logoutOutNow(){
        Intent i = new Intent(this, SignIn.class);
        startActivity(i);
        finish();
    }

    private void goToChat(){
        Intent i = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(i);
        finish();
    }



}
