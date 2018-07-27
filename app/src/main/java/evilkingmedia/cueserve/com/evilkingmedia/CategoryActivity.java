package evilkingmedia.cueserve.com.evilkingmedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import evilkingmedia.cueserve.com.evilkingmedia.film.FilmActivity;

public class CategoryActivity extends AppCompatActivity {

    private TextView txtUSer;
    private LinearLayout rlMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        txtUSer = findViewById(R.id.txtuser);

        rlMovies = findViewById(R.id.rlMovies);

        rlMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, FilmActivity.class);
                startActivity(i);
            }
        });
    }
}
