package evilkingmedia.cueserve.com.evilkingmedia.Sports;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter.BindListSports1Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class SportsActivityCatServer1 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BindListSports1Adapter mAdapter;
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
        sportsModelUrlList = (List<SportsModel>) bundle.getSerializable("urldata");
        pos = getIntent().getIntExtra("position",0);

        for(int i=0;i<sportsModelUrlList.size();i++)
        {
            int category = Integer.parseInt(sportsModelUrlList.get(i).getCategory());
            if(category == pos)
            {

                SportsModel sportsModel = new SportsModel();
                sportsModel.setDate(sportsModelUrlList.get(i).getChannel());
                sportsModel.setUrl(sportsModelUrlList.get(i).getUrl());
                sportsModel.setTeam1(sportsModelUrlList.get(i).getTeam1());
                sportsModelList.add(sportsModel);
            }
        }


        mAdapter = new BindListSports1Adapter(sportsModelList, SportsActivityCatServer1.this,sportsModelUrlList);
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SportsActivityCatServer1.this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.invalidate();
        recyclerView.setAdapter(mAdapter);

    }
}
