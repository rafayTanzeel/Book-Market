package com.bookmarketer.nw.bookmarket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.microsoft.windowsazure.mobileservices.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SignIn extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatus;
    private ProgressDialog mProgressDialog;
    private Button signIn;
    private Button signOut;
    private Button disconnect;
    private Button proceed;
    private ImageView imageView;
    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mClient = new MobileServiceClient(
                "https://nwhacks001.azure-mobile.net/",
                "ZkpGveeHyUvgfFWWttjOZslavbAzTJ60",
                this
        );

        mStatus = (TextView) findViewById(R.id.status);
        signIn = (Button) findViewById(R.id.sign_in_button);
        signOut = (Button) findViewById(R.id.sign_out_button);
        disconnect = (Button) findViewById(R.id.disconnect_button);
        proceed = (Button) findViewById(R.id.nextPage);
        imageView = (ImageView) findViewById(R.id.imageView);

        signIn.setOnClickListener(this);
        signOut.setOnClickListener(this);
        disconnect.setOnClickListener(this);
        proceed.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                }
        );
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                }
        );
    }


    private void updateUI(boolean signedIn) {
        if (signedIn) {
            signIn.setVisibility(View.GONE);
            signOut.setVisibility(View.VISIBLE);
            disconnect.setVisibility(View.VISIBLE);
            proceed.setVisibility(View.VISIBLE);
        } else {
            mStatus.setText("Signed In");
            signIn.setVisibility(View.VISIBLE);
            signOut.setVisibility(View.GONE);
            disconnect.setVisibility(View.GONE);
            proceed.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            mStatus.setText(account.getDisplayName() + "is Signed In");
            
            DownLoadImage image=new DownLoadImage();
            image.execute(account.getPhotoUrl().toString());
            updateUI(true);
        } else {
            updateUI(false);
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast toast = new Toast(this);
        toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
            case R.id.nextPage:
                goToNextPage();
                break;
        }
    }


    private void goToNextPage() {
        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
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

    class DownLoadImage extends AsyncTask<String, Void, Bitmap> {

        Bitmap bitmap;
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                InputStream in=(InputStream) new URL(params[0]).getContent();
                bitmap = BitmapFactory.decodeStream(in);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

}
