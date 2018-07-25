package evilkingmedia.cueserve.com.evilkingmedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import evilkingmedia.cueserve.com.evilkingmedia.film.FilmActivity;

public class CategoryActivity extends AppCompatActivity {

    private Button btnmovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        btnmovie = findViewById(R.id.btnmovie);

        btnmovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryActivity.this, FilmActivity.class);
                startActivity(i);
            }
        });
    }
}
