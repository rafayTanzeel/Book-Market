package com.bookmarketer.nw.bookmarket;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class BarcodeScanner extends AppCompatActivity implements View.OnClickListener {

    private static final String ISBN = "https://www.googleapis.com/books/v1/volumes";
    private final String SERVER_URL = "http://rafaytanzeel.com/bookMarket/bookMethods.php";
    private String TokenId;
    private TextView titleText;
    private TextView authorText;
    private TextView summaryText;
    private String isbn_code;
    public RequestPackage d;
    private String publisher;
    private String bookURL;
    private Button submit;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    private String jsonData="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        titleText = (TextView) findViewById(R.id.titleText);
        authorText = (TextView) findViewById(R.id.authorText);
        summaryText = (TextView) findViewById(R.id.summaryText);

        TokenId = getIntent().getStringExtra("TOKEN_ID");
        submit = (Button) findViewById(R.id.submit_book);
        submit.setOnClickListener(this);
        findViewById(R.id.read_barcode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
            intent.putExtra(BarcodeCaptureActivity.UseFlash, false);
            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        } else if (v.getId() == R.id.submit_book) {

            PostBooksData bookDatePost = new PostBooksData();
            bookDatePost.execute(d);


                Toast.makeText(this, "Book Posted Successfully", Toast.LENGTH_LONG).show();


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    isbn_code = barcode.displayValue;


                    if (isOnline()) {
                        RequestPackage p = new RequestPackage();
                        p.setMethod("GET");
                        p.setUri(ISBN);
                        p.setParam("q", "isbn:" + barcode.displayValue);

                        ExtractBookData bookInfo = new ExtractBookData();
                        bookInfo.execute(p);

                        d = new RequestPackage();
                        d.setMethod("POST");
                        d.setUri(SERVER_URL);


                    } else {
                        Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_LONG).show();
                    }


//                    barcodeValue.setText(barcode.displayValue);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    // statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                //  statusMessage.setText(String.format(getString(R.string.barcode_error),
                //         CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    class ExtractBookData extends AsyncTask<RequestPackage, String, BookInfo> {

        @Override
        protected BookInfo doInBackground(RequestPackage... params) {
            String jsonData = httpManager.getData(params[0]);
            BookInfo bookInfo = ParseJson.parse(jsonData);
            return bookInfo;
        }

        @Override
        protected void onPostExecute(BookInfo s) {

            findViewById(R.id.read_barcode).setVisibility(View.INVISIBLE);


            titleText.setText(s.getTitle());
            authorText.setText(s.getAuthors());
            summaryText.setText(s.getDesc());
            publisher = s.getPublisher();
            bookURL = s.getsThumb();


            d.setParam("tokenId", TokenId);
            d.setParam("isbn", isbn_code);
            d.setParam("title", titleText.getText().toString());
            d.setParam("author", authorText.getText().toString());
            d.setParam("publisher", publisher);
            d.setParam("bookUrl", bookURL);
            d.setParam("price",((EditText)findViewById(R.id.price)).getText().toString());
            d.setParam("method", "upload");
            submit.setVisibility(View.VISIBLE);
            submit.setEnabled(true);
        }
    }


    class PostBooksData extends AsyncTask<RequestPackage, Void, Void> {

        @Override
        protected Void doInBackground(RequestPackage... params) {
            Log.d("hi", "hi");
            jsonData = httpManager.getData(params[0]);
            Log.d("JSON", jsonData);
            return null;
        }
    }


}
