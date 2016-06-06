package com.nlte.myappportfolio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MoviesSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_summary);
        if (savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .add(R.id.movies_content, new MoviesSummaryFragment())
                    .commit();
        }
    }
}
