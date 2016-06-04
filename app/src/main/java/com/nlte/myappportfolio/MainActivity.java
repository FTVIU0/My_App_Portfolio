package com.nlte.myappportfolio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nlte.myappportfolio.utils.ToastShowUtil;

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
                ToastShowUtil.show(this, "This button will launch my popular_movies app!");
                break;
            case R.id.btn_stock_hawk:
                ToastShowUtil.show(this, "This button will launch my stock_hawk app!");
                break;
            case R.id.btn_build_bigger:
                ToastShowUtil.show(this, "This button will launch my build_bigger app!");
                break;
            case R.id.btn_make_your_app_material:
                ToastShowUtil.show(this, "This button will launch my make_your_app_material app!");
                break;
            case R.id.btn_go_ubiquitous:
                ToastShowUtil.show(this, "This button will launch my go_ubiquitous app!");
                break;
            case R.id.btn_capstone:
                ToastShowUtil.show(this, "This button will launch my capstone app!");
                break;

        }
    }
}
