package com.foxconn.alan.AlanLauncher.ui;

import android.app.Activity;
import android.os.Bundle;

import com.foxconn.alan.AlanLauncher.R;

public class DetailsActivity extends Activity {

    public static final String MOVIE = "Movie";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
}
