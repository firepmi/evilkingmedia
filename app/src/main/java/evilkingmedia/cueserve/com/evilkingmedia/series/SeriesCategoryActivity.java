package evilkingmedia.cueserve.com.evilkingmedia.series;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.film.FilmActivityServer4;

public class SeriesCategoryActivity extends AppCompatActivity {

    LinearLayout rlMovies, rlMovies1, rlMovies2, rlMovies3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_category);

        rlMovies = findViewById(R.id.rlMovies);
        rlMovies1 = findViewById(R.id.rlMovies1);
        rlMovies2 = findViewById(R.id.rlMovies2);
        rlMovies3 = findViewById(R.id.rlMovies3);

        rlMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeriesCategoryActivity.this, SeriesActivityServer1.class);
                startActivity(i);
            }
        });
        rlMovies1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeriesCategoryActivity.this, SeriesActivityServer2.class);
                startActivity(i);
            }
        });

        rlMovies2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeriesCategoryActivity.this, SeriesActivityServer3.class);
                startActivity(i);
            }
        });

        rlMovies3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeriesCategoryActivity.this, SeriesActivityServer4.class);
                startActivity(i);
            }
        });
    }
}
