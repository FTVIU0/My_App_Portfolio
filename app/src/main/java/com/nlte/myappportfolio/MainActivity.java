package com.nlte.myappportfolio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnPopularMmovies;
    private Button btnStockHawk;
    private Button btnBuildBigger;
    private Button btnMakeYourAppMaterial;
    private Button btnGoUbiquitous;
    private Button btnCapstone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPopularMmovies = (Button) findViewById(R.id.btn_popular_movies);
        btnStockHawk = (Button) findViewById(R.id.btn_stock_hawk);
        btnBuildBigger = (Button) findViewById(R.id.btn_build_bigger);
        btnMakeYourAppMaterial = (Button) findViewById(R.id.btn_make_your_app_material);
        btnGoUbiquitous = (Button) findViewById(R.id.btn_go_ubiquitous);
        btnCapstone = (Button) findViewById(R.id.btn_capstone);

        btnPopularMmovies.setOnClickListener(this);
        btnStockHawk.setOnClickListener(this);
        btnBuildBigger.setOnClickListener(this);
        btnMakeYourAppMaterial.setOnClickListener(this);
        btnGoUbiquitous.setOnClickListener(this);
        btnCapstone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_popular_movies:
                Toast.makeText(this, "This button will launch my popular_movies app!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_stock_hawk:
                Toast.makeText(this, "This button will launch my stock_hawk app!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_build_bigger:
                Toast.makeText(this, "This button will launch my build_bigger app!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_make_your_app_material:
                Toast.makeText(this, "This button will launch my make_your_app_material app!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_go_ubiquitous:
                Toast.makeText(this, "This button will launch my go_ubiquitousapp!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_capstone:
                Toast.makeText(this, "This button will launch my capstone app!", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
