package evilkingmedia.cueserve.com.evilkingmedia.Musica;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import evilkingmedia.cueserve.com.evilkingmedia.R;

public class MusicActivityCategory extends AppCompatActivity {
    LinearLayout rlMovies, rlMovies1, rlMovies2, rlMovies3, r1Movies4;
    TextView tvserver3, tvserver4;
    CardView moviesCardView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_category);

        rlMovies = findViewById(R.id.rlMovies);
        rlMovies1 = findViewById(R.id.rlMovies1);
        rlMovies2 = findViewById(R.id.rlMovies2);
        rlMovies3 = findViewById(R.id.rlMovies3);
        r1Movies4 = findViewById(R.id.rlMovies4);
        tvserver3 = findViewById(R.id.tvserver3);
        tvserver4 = findViewById(R.id.tvserver4);
        moviesCardView1 = findViewById(R.id.moviesCardView1);

        moviesCardView1.setVisibility(View.GONE);

        tvserver3.setText("Server 2");
        tvserver4.setText("Server 3");
        rlMovies1.setVisibility(View.GONE);


        r1Movies4.setVisibility(View.GONE);

        rlMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MusicActivityCategory.this, MusicaActivityServer1.class);
                startActivity(i);
            }
        });
        rlMovies1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MusicActivityCategory.this, MusicaActivityServer2.class);
                startActivity(i);
            }
        });

        rlMovies2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MusicActivityCategory.this, MusicaActivityServer3.class);
                startActivity(i);
            }
        });

        rlMovies3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MusicActivityCategory.this, MusicaActivityServer4.class);
                startActivity(i);
            }
        });
    }
}

