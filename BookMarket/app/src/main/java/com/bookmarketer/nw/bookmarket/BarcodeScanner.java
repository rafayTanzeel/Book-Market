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
import android.widget.CompoundButton;
import android.widget.*;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class BarcodeScanner extends AppCompatActivity implements View.OnClickListener {

    // use a compound button so either checkbox or switch widgets work.

    private static final String ISBN= "https://www.googleapis.com/books/v1/volumes";
    private TextView titleText;
    private TextView authorText;
    private TextView summaryText;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        titleText = (TextView) findViewById(R.id.titleText);
        authorText = (TextView) findViewById(R.id.authorText);
        summaryText = (TextView) findViewById(R.id.summaryText);


        findViewById(R.id.read_barcode).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
            intent.putExtra(BarcodeCaptureActivity.UseFlash, true);

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }

    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);


                    if(isOnline()) {
                        RequestPackage p =new RequestPackage();
                        p.setMethod("GET");
                        p.setUri(ISBN);
                        p.setParam("q","isbn:"+"0747532699"/*barcode.displayValue*/);
                       // ?q=isbn:

                        ExtractBookData bookInfo = new ExtractBookData();
                        bookInfo.execute(p);
                    }
                    else{
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
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo= cm.getActiveNetworkInfo();

        if(netInfo!=null && netInfo.isConnectedOrConnecting()){
            return true;
        }
        else{
            return false;
        }
    }

    class ExtractBookData extends AsyncTask<RequestPackage, String, BookInfo>{

        @Override
        protected BookInfo doInBackground(RequestPackage... params) {
            String jsonData = httpManager.getData(params[0]);
            BookInfo bookInfo = ParseJson.parse(jsonData);
            return bookInfo;
        }

        @Override
        protected void onPostExecute(BookInfo s) {

            titleText.setText(s.getTitle());
            authorText.setText(s.getAuthors());
            summaryText.setText(s.getDesc());

        }
    }


}
