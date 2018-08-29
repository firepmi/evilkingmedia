package evilkingmedia.cueserve.com.evilkingmedia.series;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.SportsActivityCatServer1;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter.BindListSports1Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListSeries4Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListSeries4SubAdapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class SeriesActivityCatServer4 extends AppCompatActivity {
    private ImageView ivNext, ivPrev, ivUp, ivDown;
    private EditText etMoviename;
    private LinearLayout llsearch;
    private Button btnMoviename;
    private LinearLayout linearCategory;
    private Button btnhome, btncategory;
    private BindListSeries4SubAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<MoviesModel> movieList = new ArrayList<MoviesModel>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_film);
        Bundle bundle = getIntent().getExtras();
        movieList = (List<MoviesModel>) bundle.getSerializable("data");
        linearCategory = findViewById(R.id.categories);
        recyclerView = findViewById(R.id.recyclerview);
        btnhome = findViewById(R.id.btnhome);
        btncategory = findViewById(R.id.btncategory);
        llsearch = findViewById(R.id.ll_search);

        ivNext = findViewById(R.id.ivNext);
        ivPrev = findViewById(R.id.ivPrev);
        ivUp = findViewById(R.id.ivUp);
        ivDown = findViewById(R.id.ivDown);
        ivNext.setVisibility(View.GONE);
        ivPrev.setVisibility(View.GONE);
        linearCategory.setVisibility(View.GONE);
        llsearch.setVisibility(View.GONE);
        mAdapter = new BindListSeries4SubAdapter(movieList, SeriesActivityCatServer4.this);
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SeriesActivityCatServer4.this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }
    }
