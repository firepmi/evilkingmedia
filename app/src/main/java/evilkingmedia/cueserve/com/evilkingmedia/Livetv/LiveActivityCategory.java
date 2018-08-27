package evilkingmedia.cueserve.com.evilkingmedia.Livetv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.MusicAdapterServer1;
import evilkingmedia.cueserve.com.evilkingmedia.film.FilmActivity;
import evilkingmedia.cueserve.com.evilkingmedia.film.FilmActivityServer2;
import evilkingmedia.cueserve.com.evilkingmedia.film.FilmActivityServer3;
import evilkingmedia.cueserve.com.evilkingmedia.film.FilmActivityServer4;
import evilkingmedia.cueserve.com.evilkingmedia.film.FilmCategoryActivity;

public class LiveActivityCategory extends AppCompatActivity {
    LinearLayout rlMovies, rlMovies1, rlMovies2, rlMovies3, r1Movies4,rlMovies5, rlMovies6, rlMovies7, rlMovies8, r1Movies9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_category);

        rlMovies = findViewById(R.id.rlMovies);
        rlMovies1 = findViewById(R.id.rlMovies1);
        rlMovies2 = findViewById(R.id.rlMovies2);
        rlMovies3 = findViewById(R.id.rlMovies3);
        r1Movies4 = findViewById(R.id.rlMovies4);
        rlMovies5 = findViewById(R.id.rlMovies5);
        rlMovies6 = findViewById(R.id.rlMovies6);
        rlMovies7 = findViewById(R.id.rlMovies7);
        rlMovies8 = findViewById(R.id.rlMovies8);
        r1Movies9 = findViewById(R.id.rlMovies9);



        rlMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LiveActivityCategory.this, LiveActivityServer1.class);
                i.putExtra("url", Constant.LIVETV1);
                startActivity(i);
            }
        });
        rlMovies1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LiveActivityCategory.this, LiveActivityServer1.class);
                i.putExtra("url", Constant.LIVETV2);
                startActivity(i);
            }
        });

        rlMovies2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LiveActivityCategory.this, LiveActivityServer1.class);
                i.putExtra("url", Constant.LIVETV3);
                startActivity(i);
            }
        });

        rlMovies3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LiveActivityCategory.this,LiveActivityServer1.class);
                i.putExtra("url", Constant.LIVETV4);
                startActivity(i);
            }
        });
        r1Movies4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LiveActivityCategory.this, LiveActivityServer1.class);
                i.putExtra("url", Constant.LIVETV5);
                startActivity(i);
            }
        });

        rlMovies5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LiveActivityCategory.this, LiveActivityServer1.class);
                i.putExtra("url", Constant.LIVETV6);
                startActivity(i);
            }
        });
        rlMovies6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LiveActivityCategory.this, LiveActivityServer1.class);
                i.putExtra("url", Constant.LIVETV7);
                startActivity(i);
            }
        });

        rlMovies7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LiveActivityCategory.this, LiveActivityServer1.class);
                i.putExtra("url", Constant.LIVETV8);
                startActivity(i);
            }
        });

        rlMovies8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LiveActivityCategory.this,LiveActivityServer1.class);
                i.putExtra("url", Constant.LIVETV9);
                startActivity(i);
            }
        });

        r1Movies9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LiveActivityCategory.this,LiveActivityServer1.class);
                i.putExtra("url", Constant.LIVETV10);
                startActivity(i);
            }
        });

    }
}

