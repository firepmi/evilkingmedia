package evilkingmedia.cueserve.com.evilkingmedia.Cartoon;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.SportsCategoryActivity;

public class CartoonCategoryActivity extends AppCompatActivity {
    LinearLayout rlMovies, rlMovies1, rlMovies2, rlMovies3, r1Movies4;
    TextView tvserver3, tvserver4;
    CardView moviesCardView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon_category);

        rlMovies = findViewById(R.id.rlMovies);
        rlMovies1 = findViewById(R.id.rlMovies1);
        rlMovies2 = findViewById(R.id.rlMovies2);
        rlMovies3 = findViewById(R.id.rlMovies3);


        rlMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent i = new Intent(CartoonCategoryActivity.this, CartoonActivityServer1.class);
                startActivity(i);
            }
        });
        rlMovies1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.openWVCapp(CartoonCategoryActivity.this,Constant.EVILKINGCARTOONURL);

            }
        });

    /*    rlMovies2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Intent i = new Intent(CartoonCategoryActivity.this, CartoonActivityServer3.class);
                startActivity(i);
            }
        });

        rlMovies3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent i = new Intent(CartoonCategoryActivity.this, CartoonActivityServer4.class);
                startActivity(i);
            }
        });*/
    }


}
