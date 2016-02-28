package com.bookmarketer.nw.bookmarket;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainMenu extends AppCompatActivity {

    private Button mUploadBtn;
    private Button mActiveBtn;
    private Button mlogOutBtn;
    private Button mChatBtn;
    private EditText mSearchText;
    private final String SERVER_URL="http://rafaytanzeel.com/bookMarket/bookMethods.php";

    private Button mSearchSubmitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mUploadBtn = (Button) findViewById(R.id.uploadBtn);
        mActiveBtn = (Button) findViewById(R.id.activeBtn);
        mlogOutBtn = (Button) findViewById(R.id.logoutBtn);
        mChatBtn = (Button) findViewById(R.id.chatBtn);
        mSearchText = (EditText) findViewById(R.id.searchText);
        mSearchSubmitBtn = (Button) findViewById(R.id.searchSubmitBtn);
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
        mSearchSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestPackage p =new RequestPackage();
                p.setMethod("POST");
                p.setUri(SERVER_URL);
                p.setParam("method", "search");
                p.setParam("title", mSearchText.getText().toString());
                sendSearchPost userDatePost=new sendSearchPost();
                userDatePost.execute(p);
            }
        });
    }
}
class sendSearchPost extends AsyncTask<RequestPackage, Void, Void> {

    @Override
    protected Void doInBackground(RequestPackage... params) {
        String jsonData = httpManager.getData(params[0]);
        Log.d("JSON", jsonData);
        return null;
    }

}