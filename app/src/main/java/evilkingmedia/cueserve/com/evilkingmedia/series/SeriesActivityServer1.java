package evilkingmedia.cueserve.com.evilkingmedia.series;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListSeriesAdapter;


public class SeriesActivityServer1 extends AppCompatActivity{

    ArrayList<String> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BindListSeriesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_film);
        recyclerView = findViewById(R.id.recyclerview);
        arrayList.add("Serie-tv-streaming");
        arrayList.add("Serie TV Americane");
        arrayList.add("Serie TV Italiane");
        arrayList.add("Avventura");
        arrayList.add("Azione");
        arrayList.add("Biografico");
        arrayList.add("Classici");
        arrayList.add("Comico");
        arrayList.add("Commedia");
        arrayList.add("Demenziale");
        arrayList.add("Drama");
        arrayList.add("Fantascienza");
        arrayList.add("Fantasy");
        arrayList.add("Giallo");
        arrayList.add("Guerra");
        arrayList.add("Horror");
        arrayList.add("Legal Drama");
        arrayList.add("Medical Drama");
        arrayList.add("Musical");
        arrayList.add("Noir");
        arrayList.add("Poliziesco");
        arrayList.add("Prison Drama");
        arrayList.add("Sentimentale");
        arrayList.add("Storico");
        arrayList.add("Teen Drama");
        arrayList.add("Thriller");
        arrayList.add("Western");

        mAdapter = new BindListSeriesAdapter(arrayList, SeriesActivityServer1.this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SeriesActivityServer1.this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.invalidate();
        recyclerView.setAdapter(mAdapter);
    }

}
