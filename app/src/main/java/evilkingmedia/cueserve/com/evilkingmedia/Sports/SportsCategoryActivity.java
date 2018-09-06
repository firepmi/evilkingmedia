package evilkingmedia.cueserve.com.evilkingmedia.Sports;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;

public class SportsCategoryActivity extends AppCompatActivity {

    LinearLayout rlSports, rlSports1, r2Sports2, r3Sports3, rlEpg, rlDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_category);

        rlSports = findViewById(R.id.rlMovies);
        rlSports1 = findViewById(R.id.rlMovies1);
        r2Sports2 = findViewById(R.id.rlMovies2);
        r3Sports3 = findViewById(R.id.rlMovies3);
        rlEpg = findViewById(R.id.rlEpg);
        rlDoc = findViewById(R.id.rlSportByDoc);

        rlSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SportsCategoryActivity.this, SportsActivityServer5.class);
                startActivity(i);
            }
        });

        rlSports1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SportsCategoryActivity.this, SportsActivityServer1.class);
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
                Intent i = new Intent(SportsCategoryActivity.this, SportsActivityServer2.class);
                startActivity(i);
            }
        });

        rlEpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SportsCategoryActivity.this,WebViewActivitySports3.class);
                i.putExtra("url",Constant.SPORTSEPGURL);
                startActivity(i);
            }
        });

        rlDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SportsCategoryActivity.this,SportsActivityByDoc.class);
                i.putExtra("url",Constant.SPORTSEPGURL);
                startActivity(i);
            }
        });


    }
}
