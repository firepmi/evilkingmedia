package evilkingmedia.cueserve.com.evilkingmedia.series;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListSeriesCat2Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class SeriesActivityCatServer2 extends AppCompatActivity {
    private LinearLayout linearCategory;
    private RecyclerView recyclerView;
    private BindListSeriesCat2Adapter mAdapter;
    private List<MoviesModel> movieList = new ArrayList<>();
    private List<MoviesModel> movieurlList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    ImageView ivNext, ivPrev, ivUp, ivDown;
    EditText etMoviename;
    String url;
    Button btnMoviename;
    private int elementsize;
    Boolean isPrev, isNext;
    int corePoolSize = 60;
    String Currenturl;
    android.support.v7.widget.SearchView search;
    int i = 0;
    String Category = "";
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    private ArrayList<String> yearArrayList = new ArrayList<>();
    private ArrayList<String> durationArrayList = new ArrayList<>();
    private Button btnhome, btncategory, btnfilmsub, btnfilmaz;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_series_server_sub2);

        //  setContentView(R.layout.gridview_list);

        recyclerView = findViewById(R.id.recyclerview);
        btnhome = findViewById(R.id.btnhome);
        btncategory = findViewById(R.id.btncategory);
        url = getIntent().getStringExtra("url");


        new prepareMovieData(url, "").execute();


        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SeriesActivityCatServer2.this, SeriesActivityServer2.class);
                startActivity(i);
            }
        });

        btncategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SeriesActivityCatServer2.this, SeriesActivityCategoryServer2.class);
                startActivity(i);
            }
        });


    }


    private class prepareMovieData extends AsyncTask<String, Void, Void> {
        String movieUrl;
        String mainurl;

        public prepareMovieData(String mainurl, String movieurl1Cinema) {
            this.movieUrl = movieurl1Cinema;
            this.mainurl = mainurl;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SeriesActivityCatServer2.this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            //Movie1
            try {
                // Connect to the web site
                Document doc = Jsoup.connect(mainurl + "/" + movieUrl).timeout(10000).get();

                Elements data = doc.select("ul[class=episodios]").first().getElementsByClass("episodiotitle");

                for (int i = 0; i < data.size(); i++) {

                    String urldata = data.get(i).getElementsByTag("a").attr("href");
                    String title = data.get(i).getElementsByTag("a").text();
                    MoviesModel movie = new MoviesModel();
                    movie.setTitle(title);
                    movie.setUrl(urldata);
                    movieList.add(movie);
                }


            } catch (IOException e1) {
                e1.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            mAdapter = new BindListSeriesCat2Adapter(movieList, SeriesActivityCatServer2.this);
            // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SeriesActivityCatServer2.this, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }

}

