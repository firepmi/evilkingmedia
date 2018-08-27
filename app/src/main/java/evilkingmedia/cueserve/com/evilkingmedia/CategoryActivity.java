package evilkingmedia.cueserve.com.evilkingmedia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import evilkingmedia.cueserve.com.evilkingmedia.EPG.EPGActivity;
import evilkingmedia.cueserve.com.evilkingmedia.Ebbok.EbbokActivity;
import evilkingmedia.cueserve.com.evilkingmedia.Livetv.LiveActivityCategory;
import evilkingmedia.cueserve.com.evilkingmedia.Meteo.MeteoActivity;
import evilkingmedia.cueserve.com.evilkingmedia.Musica.MusicActivityCategory;
import evilkingmedia.cueserve.com.evilkingmedia.film.FilmCategoryActivity;
import evilkingmedia.cueserve.com.evilkingmedia.series.SeriesCategoryActivity;

public class CategoryActivity extends AppCompatActivity {

    private TextView txtBottom;
    private LinearLayout rlMovies, rlSeries, rlSports, rlMusic, rlMeteo, rlepg, rllive, rlebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        txtBottom = findViewById(R.id.txt);
        String styledText = "Sito Ufficiale: evilkingmedia.com - Assistenza Web: androidaba.com - Assistenza Telegram:<font color='blue'> https://t.me/evilkingmedia</font> ";
        txtBottom.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
        rlMovies = findViewById(R.id.rlMovies);
        rlSeries = findViewById(R.id.rlSeries);
        rlSports = findViewById(R.id.rlSports);
        rlepg = findViewById(R.id.rlepg);
        rlMusic = findViewById(R.id.rlMusic);
        rlMeteo = findViewById(R.id.rlMeteo);
        rllive = findViewById(R.id.rllive);
        rlebook = findViewById(R.id.rlebook);

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

       /* rlSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, SportsCategoryActivity.class);
                startActivity(i);
            }
        });*/

        rlepg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, EPGActivity.class);
                startActivity(i);
            }
        });

        rlMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, MusicActivityCategory.class);
                startActivity(i);
            }
        });

       rlMeteo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i =new Intent(CategoryActivity.this, MeteoActivity.class);
               startActivity(i);
           }
       });

        rllive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, LiveActivityCategory.class);
                startActivity(i);
            }
        });

        rlebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/0BzG97RrFLXbOSmh1Uk1lTS1uWFU"));
                startActivity(browserIntent);
            }
        });
    }
}
