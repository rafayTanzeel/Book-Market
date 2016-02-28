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
import android.widget.ListView;

import com.bookmarketer.nw.bookmarket.Adapters.SearchAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainMenu extends AppCompatActivity {

    private Button mUploadBtn;
    private Button mActiveBtn;
    private Button mlogOutBtn;
    private Button mChatBtn;
    public ListView mList;
    private EditText mSearchText;
    private String jsonScript;
    private final String SERVER_URL="http://rafaytanzeel.com/bookMarket/bookMethods.php";


    private Button mSearchSubmitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mList  = (ListView) findViewById(R.id.listView);
        mUploadBtn = (Button) findViewById(R.id.uploadBtn);
        mActiveBtn = (Button) findViewById(R.id.activeBtn);
        mlogOutBtn = (Button) findViewById(R.id.logoutBtn);
        mChatBtn = (Button) findViewById(R.id.chatBtn);
        mSearchText = (EditText) findViewById(R.id.searchText);
        mSearchSubmitBtn = (Button) findViewById(R.id.searchSubmitBtn);
        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BarcodeScanner.class);
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

    class sendSearchPost extends AsyncTask<RequestPackage, Void, Void> {

        private String jsonData;
        private JSONObject json;

        @Override
        protected Void doInBackground(RequestPackage... params) {
            jsonData = httpManager.getData(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String stuff;
            try {
                json = new JSONObject(jsonData);
                JSONArray temp = json.getJSONArray("content");
                stuff =  temp.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
       //     Log.d("shoot me ",);
            updateUI();

        }
    }
    public void updateUI(){
        Log.d("shoot me ","fdsfdsf");

        SearchAdapter adapter = new SearchAdapter(getApplicationContext());
        Log.d("shoot me ","dfdf");

        mList.setAdapter(adapter);
        Log.d("shoot me ", "violenntly");


    }
}
