package com.example.android.lab01_2;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adjustForOrientation();
    }

    private void adjustForOrientation() {
//        View headline = (View) findViewById(R.id.headline_fragment);
//        View news = (View) findViewById(R.id.news_fragment);
//        int orientation = getResources().getConfiguration().orientation;
//        // Vertical
//        if (orientation == 1) {
//            // Headline fragment
//            headline.getLayoutParams().width = ActionBar.LayoutParams.WRAP_CONTENT;
//            headline.getLayoutParams().height = ActionBar.LayoutParams.MATCH_PARENT;
//
//            // News fragment
//            news.getLayoutParams().width = ActionBar.LayoutParams.WRAP_CONTENT;
//            news.getLayoutParams().height = ActionBar.LayoutParams.MATCH_PARENT;
//            news.setLayoutParams(RelativeLayout.RIGHT_OF, R.id.headline_fragment);
//        }
//        Log.i("Orientation_debugging", "current orientation: " + orientation);
    }
}
