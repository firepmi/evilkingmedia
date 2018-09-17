package evilkingmedia.cueserve.com.evilkingmedia.film;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.series.SeriesActivityServer2;
import evilkingmedia.cueserve.com.evilkingmedia.series.SeriesActivityServer3;
import evilkingmedia.cueserve.com.evilkingmedia.series.SeriesActivityServer4;

public class FilmCategoryActivity extends AppCompatActivity {

    LinearLayout rlMovies, rlMovies1, rlMovies2, rlMovies3,rlMovies4, r1Series1, r1Series2, r1Series3, r1Series4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_category);

        rlMovies = findViewById(R.id.rlMovies);
        rlMovies1 = findViewById(R.id.rlMovies1);
        rlMovies2 = findViewById(R.id.rlMovies2);
        rlMovies3 = findViewById(R.id.rlMovies3);
        rlMovies4 = findViewById(R.id.rlMovies4);
        r1Series1 = findViewById(R.id.rlSeries1);
        r1Series2 = findViewById(R.id.rlSeries2);
        r1Series3 = findViewById(R.id.rlSeries3);
        r1Series4 = findViewById(R.id.rlSeries4);



        rlMovies1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FilmCategoryActivity.this, FilmActivityServer2.class);
                startActivity(i);
            }
        });

        rlMovies2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FilmCategoryActivity.this, FilmActivityServer3.class);
                startActivity(i);
            }
        });

        rlMovies3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(FilmCategoryActivity.this,FilmActivity.class);
                startActivity(i);
            }
        });

        r1Series1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FilmCategoryActivity.this, SeriesActivityServer4.class);
                startActivity(i);
            }
        });

        r1Series2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FilmCategoryActivity.this, SeriesActivityServer2.class);
                startActivity(i);
            }
        });

        r1Series3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(FilmCategoryActivity.this,SeriesActivityServer3.class);
                startActivity(i);
            }
        });

        rlMovies4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.alertDialogWVC(FilmCategoryActivity.this,Constant.EVILKINGMOVIEURL);


            }
        });

        r1Series4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.alertDialogWVC(FilmCategoryActivity.this,Constant.EVILKINGSERIESURL);
            }
        });
    }
}
