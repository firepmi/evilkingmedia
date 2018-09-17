package evilkingmedia.cueserve.com.evilkingmedia.Cartoon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.SportsActivityCatServer1;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter.BindListSports1Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class CartoonActivitySubServer1 extends AppCompatActivity {

    private LinearLayout linearCategory;
    private RecyclerView recyclerView;
    private CartoonServer1SubAdapter mAdapter;
    private List<MoviesModel> movieList = new ArrayList<>();
    private List<MoviesModel> movieurlList = new ArrayList<>();
    private List<SportsModel> sportsModelList = new ArrayList<>();
    private ArrayList<String> urlArrayList = new ArrayList<>();
    private ArrayList<String> timeArrayList = new ArrayList<>();
    private List<SportsModel> sportsModelUrlList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private int pos;
    private ImageView ivNext,ivPrev, ivUp, ivDown;
    private LinearLayout ll_search,ll_categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_film);

        ivNext = findViewById(R.id.ivNext);
        ivPrev = findViewById(R.id.ivPrev);
        ivUp = findViewById(R.id.ivUp);
        ivDown = findViewById(R.id.ivDown);
        ll_search = findViewById(R.id.ll_search);
        ll_categories = findViewById(R.id.categories);

        ivPrev.setVisibility(View.GONE);
        ivUp.setVisibility(View.GONE);
        ivDown.setVisibility(View.GONE);
        ivNext.setVisibility(View.GONE);
        ll_search.setVisibility(View.GONE);
        ll_categories.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recyclerview);

        Bundle bundle = getIntent().getExtras();
        movieurlList = (List<MoviesModel>) bundle.getSerializable("urldata");




        mAdapter = new CartoonServer1SubAdapter(movieurlList, CartoonActivitySubServer1.this);
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(CartoonActivitySubServer1.this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.invalidate();
        recyclerView.setAdapter(mAdapter);

        ivUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollBy(0, -200);
            }
        });

        ivDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollBy(0, 200);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CartoonActivitySubServer1.this,CartoonActivityServer1.class);
        startActivity(intent);
    }
}

