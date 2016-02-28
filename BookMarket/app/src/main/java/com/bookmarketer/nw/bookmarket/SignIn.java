package com.bookmarketer.nw.bookmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    

    public void move(View view){
        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
    }
}
