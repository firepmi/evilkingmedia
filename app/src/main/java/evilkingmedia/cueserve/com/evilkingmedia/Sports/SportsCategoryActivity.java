package evilkingmedia.cueserve.com.evilkingmedia.Sports;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import evilkingmedia.cueserve.com.evilkingmedia.R;

public class SportsCategoryActivity extends AppCompatActivity {

    LinearLayout rlSports, rlSports1, r2Sports2, r3Sports3, r4Movies4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_category);

        rlSports = findViewById(R.id.rlMovies);
        rlSports1 = findViewById(R.id.rlMovies1);
        r2Sports2 = findViewById(R.id.rlMovies2);
        r3Sports3 = findViewById(R.id.rlMovies3);
        r4Movies4 = findViewById(R.id.rlMovies4);

        rlSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SportsCategoryActivity.this, SportsActivityServer1.class);
                startActivity(i);
            }
        });

        rlSports1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SportsCategoryActivity.this, SportsActivityServer2.class);
                startActivity(i);
            }
        });

        r2Sports2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SportsCategoryActivity.this, SportsActivityServer3.class);
                startActivity(i);
            }
        });

        r3Sports3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SportsCategoryActivity.this, SportsActivityServer4.class);
                startActivity(i);
            }
        });

        r4Movies4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SportsCategoryActivity.this, SportsActivityServer5.class);
                startActivity(i);
            }
        });


    }
}
