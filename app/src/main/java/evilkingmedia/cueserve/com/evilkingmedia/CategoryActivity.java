package evilkingmedia.cueserve.com.evilkingmedia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import evilkingmedia.cueserve.com.evilkingmedia.Cartoon.CartoonActivityServer1;
import evilkingmedia.cueserve.com.evilkingmedia.Cartoon.CartoonCategoryActivity;
import evilkingmedia.cueserve.com.evilkingmedia.EPG.EPGActivity;
import evilkingmedia.cueserve.com.evilkingmedia.Livetv.LiveActivityCategory;
import evilkingmedia.cueserve.com.evilkingmedia.Meteo.MeteoActivity;
import evilkingmedia.cueserve.com.evilkingmedia.Musica.MusicActivityCategory;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.SportsCategoryActivity;
import evilkingmedia.cueserve.com.evilkingmedia.film.FilmCategoryActivity;
import evilkingmedia.cueserve.com.evilkingmedia.series.SeriesCategoryActivity;

public class CategoryActivity extends AppCompatActivity {

    private TextView txtemail, txtBottom, txt_dt_time, txtlogout, txtsetting;
    private LinearLayout rlMovies, rlCartoon, rlSports, rlMusic, rlMeteo, rlepg, rllive, rlebook;
    private ImageView download,wvc,dns;

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
        rlCartoon = findViewById(R.id.rlCartoon);
        rlSports = findViewById(R.id.rlSports);
        rlepg = findViewById(R.id.rlepg);
        rlMusic = findViewById(R.id.rlMusic);
        rlMeteo = findViewById(R.id.rlMeteo);
        rllive = findViewById(R.id.rllive);
        rlebook = findViewById(R.id.rlebook);
        txt_dt_time = findViewById(R.id.dt_time);
        txtemail = findViewById(R.id.txtemail);
        txtlogout = findViewById(R.id.txtlogout);
        txtsetting = findViewById(R.id.txtsetting);
        download = findViewById(R.id.download);
        wvc = findViewById(R.id.wvc);
        dns = findViewById(R.id.dns);

        wvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.WVC_playstore)));
            }
        });

        dns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.androidaba.com/dnset-cambia-dns-su-android-senza-avere-il-root/")));
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.evilkingmedia.com/download/")));
            }
        });

        txtemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","evilkodi@libero.it", null));
              //  emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support");
             //   emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        txtsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=co.wuffy.player")));
            }
        });

        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                final Date date = new Date();
                final DateFormat df = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                CategoryActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        txt_dt_time.setText(df.format(date));
                    }
                });


            }
        }, 0, 1000);




        rlMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, FilmCategoryActivity.class);
                startActivity(i);
            }
        });

        rlCartoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, CartoonCategoryActivity.class);
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
               Constant.playInWuffy(CategoryActivity.this,Constant.METEOURL );
              /* Intent i =new Intent(CategoryActivity.this, MeteoActivity.class);
               startActivity(i);*/
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
