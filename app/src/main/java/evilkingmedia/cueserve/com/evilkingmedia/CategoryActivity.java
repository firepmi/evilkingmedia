package evilkingmedia.cueserve.com.evilkingmedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import evilkingmedia.cueserve.com.evilkingmedia.Meteo.MeteoActivity;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.SportsCategoryActivity;
import evilkingmedia.cueserve.com.evilkingmedia.film.FilmCategoryActivity;
import evilkingmedia.cueserve.com.evilkingmedia.series.SeriesCategoryActivity;

public class CategoryActivity extends AppCompatActivity {

    private TextView txtBottom;
    private LinearLayout rlMovies, rlSeries, rlSports, rlMusic, rlMeteo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        txtBottom = findViewById(R.id.txt);
        String styledText = "<font color='blue'>androidaba.com:</font> Pocket TV: plu di 1300 canali TV da tutto il mondo - OLA TV per Android: canali TV da tutto il mondo - MEGA Official 3.3.8";
        txtBottom.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
        rlMovies = findViewById(R.id.rlMovies);
        rlSeries = findViewById(R.id.rlSeries);
        rlSports = findViewById(R.id.rlSports);
        rlMusic = findViewById(R.id.rlMusic);
        rlMeteo = findViewById(R.id.rlMeteo);


        rlMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, FilmCategoryActivity.class);
                startActivity(i);
            }
        });

        rlSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, SeriesCategoryActivity.class);
                startActivity(i);
            }
        });

        rlSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, SportsCategoryActivity.class);
                startActivity(i);
            }
        });

       /* rlMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, MusicaActivityServer1.class);
                startActivity(i);
            }
        });*/

       rlMeteo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i =new Intent(CategoryActivity.this, MeteoActivity.class);
               startActivity(i);
           }
       });
    }
}
