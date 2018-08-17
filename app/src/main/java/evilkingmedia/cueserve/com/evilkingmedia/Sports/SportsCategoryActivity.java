package evilkingmedia.cueserve.com.evilkingmedia.Sports;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import evilkingmedia.cueserve.com.evilkingmedia.R;

public class SportsCategoryActivity extends AppCompatActivity {

    LinearLayout rlSports, rlSports1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_category);

        rlSports = findViewById(R.id.rlMovies);
        rlSports1 = findViewById(R.id.rlMovies1);

        rlSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SportsCategoryActivity.this, SportsActivityServer1.class);
                startActivity(i);
            }
        });


    }
}
