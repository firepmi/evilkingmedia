package evilkingmedia.cueserve.com.evilkingmedia.Sports;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.Livetv.LiveActivityCategory;
import evilkingmedia.cueserve.com.evilkingmedia.Musica.MusicActivityCategory;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.film.FilmCategoryActivity;

public class SportsCategoryActivity extends AppCompatActivity {

    LinearLayout rlSports, rlSports1, r2Sports2, r3Sports3, llSportsServer1, rlDoc, rlEvilking,r2Sports6,r2Sports7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_category);

        rlSports = findViewById(R.id.rlMovies);
        rlSports1 = findViewById(R.id.rlMovies1);
        r2Sports2 = findViewById(R.id.rlMovies2);
        r3Sports3 = findViewById(R.id.rlMovies3);
        r2Sports6 = findViewById(R.id.rlSportServer6);
        r2Sports7 = findViewById(R.id.rlSportServer7);
        llSportsServer1 = findViewById(R.id.llSportsServer1);
        rlDoc = findViewById(R.id.rlSportByDoc);
        rlEvilking = findViewById(R.id.rlEvilking);

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

        llSportsServer1.setOnClickListener(new View.OnClickListener() {
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
                Constant.playInWuffy(SportsCategoryActivity.this,Constant.SPORTSBYDOCURL);
              /*  Intent i = new Intent(SportsCategoryActivity.this,SportsActivityByDoc.class);
                i.putExtra("url",Constant.SPORTSEPGURL);
                startActivity(i);*/
            }
        });

        rlEvilking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.openWVCapp(SportsCategoryActivity.this,Constant.EVILKINGSPORTSURL);
            }
        });
        r2Sports6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SportsCategoryActivity.this,WebViewActivitySports3.class);
                i.putExtra("url",Constant.SPORTSURL6);
                startActivity(i);
            }
        });

        r2Sports7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SportsCategoryActivity.this,WebViewActivitySports3.class);
                i.putExtra("url",Constant.SPORTSURL7);
                startActivity(i);
            }
        });

    }


}
