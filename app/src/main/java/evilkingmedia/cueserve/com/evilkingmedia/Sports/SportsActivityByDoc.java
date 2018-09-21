package evilkingmedia.cueserve.com.evilkingmedia.Sports;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListSeriesEpisod3Adapter;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.MeteoAdapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.MeteoModel;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;
import evilkingmedia.cueserve.com.evilkingmedia.series.SeriesActivityServer3;

public class SportsActivityByDoc extends AppCompatActivity {
    private LinearLayout linearCategory,llsearch;
    private RecyclerView recyclerView;
    private MeteoAdapter mAdapter;
    private BindListSeriesEpisod3Adapter mAdapter2;
    private List<MoviesModel> movieList = new ArrayList<>();
    private List<MoviesModel> movieurlList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    ImageView ivNext, ivPrev, ivUp, ivDown;
    EditText etMoviename;
    Button btnMoviename;
    String Pageurl, url = null;
    ArrayList<String> arrayList = new ArrayList<>();
    private int elementsize;
    Boolean isPrev, isNext, isSearch = false, isNextSearch = false, isMovieita = false;
    int corePoolSize = 60;
    String Currenturl;
    android.support.v7.widget.SearchView search;
    int i = 0;
    String Category = "";
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    private ArrayList<String> yearArrayList = new ArrayList<>();
    private ArrayList<String> durationArrayList = new ArrayList<>();
    private Button btnhome, btncategory;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    ArrayList<String> allURls = new ArrayList<String>();
    ArrayList<String> allURl = new ArrayList<String>();
    ArrayList<String> alltitle = new ArrayList<String>();
    private List<MeteoModel> meteolist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_series_server3);
        //  setContentView(R.layout.gridview_list);
        linearCategory = findViewById(R.id.categories);
        llsearch = findViewById(R.id.llsearch);
        recyclerView = findViewById(R.id.recyclerview);
        ivNext = findViewById(R.id.ivNext);
        ivPrev = findViewById(R.id.ivPrev);
        ivUp = findViewById(R.id.ivUp);
        ivDown = findViewById(R.id.ivDown);
        ivNext.setVisibility(View.GONE);
        ivPrev.setVisibility(View.GONE);
        llsearch.setVisibility(View.GONE);
        isNext = false;
        url = getIntent().getStringExtra("url");
        etMoviename = findViewById(R.id.etMoviname);
        btnMoviename = findViewById(R.id.btnMoviname);

        etMoviename.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                etMoviename.setFocusableInTouchMode(true);
                etMoviename.setFocusable(true);
                return false;
            }
        });

        btnMoviename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSearch = true;
                //     new searchdata(Constant.SERIESURL3).execute();

            }
        });
        new  prepareMovieData(Constant.SPORTSURL6, "").execute();

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


    private class prepareMovieData extends AsyncTask<String, Void, Void> {
        String movieUrl;
        String mainurl;
        String title;
        String ptitle;
        public prepareMovieData(String mainurl, String movieurl1Cinema) {
            this.movieUrl = movieurl1Cinema;
            this.mainurl = mainurl;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             mProgressDialog = new ProgressDialog(SportsActivityByDoc.this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {

                Document doc = Jsoup.connect(Constant.SPORTSURL6).timeout(10000).get();
                Elements data = doc.select("div[class=page-content]").select("div[class=container]").select("div[class=wrapper]").select("div#mainbodycont").select("table");
                Elements data1= data.get(1).select("tbody").select("tr").select("td");
                Elements data2= data1.get(0).select("d1[class=tabs]").select("dd");
                Elements sports = data2.get(0).select("div[class=videolistdivintabs]");

                for(int i=0;i<sports.size();i++)
                {
                    Elements all = sports.get(i).select("table").select("tr");
                    Elements all_data = all.get(1).select("td");
                    Elements td = all.get(1).select("tr");
                    Elements tr = td.get(0).select("td");
                    String url = tr.get(0).select("a").attr("href");
                    String img = tr.get(0).select("a").select("img").attr("src");
                    String title = tr.get(1).select("a").text();


                    MeteoModel meteo = new MeteoModel();
                    meteo.setTitle(title);
                    meteo.setUrl(url);
                    meteo.setImage(img);
                    meteolist.add(meteo);
                }
    /*            URL urls = new URL(mainurl);
                BufferedReader in = new BufferedReader(new InputStreamReader(urls
                        .openStream()));
                String str;
                while ((str = in.readLine()) != null) {

                    allURls.add(str);


                }
                String url = null;

                for (int i = 0; i < allURls.size(); i++) {

                    if (allURls.get(i).toString().contains(".mp4")|| allURls.get(i).toString().contains(".m3u8")) {

                        allURl.add(allURls.get(i).toString());
                    }
                    else{
                        if(allURls.get(i).toString().contains("#EXTINF"))
                        {
                            String[] separated = allURls.get(i).toString().split(",");
                            for (String item : separated) {
                                System.out.println("item = " + item);

                                title = separated[1];


                            }
                            alltitle.add(title);
                        }

                    }

                }

                for (int i = 0; i < allURl.size(); i++) {
                    MeteoModel meteo = new MeteoModel();
                    ptitle = alltitle.get(i).toString();
                    meteo.setTitle(ptitle);
                    meteo.setUrl(allURl.get(i));
                    meteolist.add(meteo);
                }

                //meteo.setUrl(videourl);

                Log.e("data", allURls + "");
                in.close();*/

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            mAdapter = new MeteoAdapter(meteolist, SportsActivityByDoc.this);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SportsActivityByDoc.this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.invalidate();
            recyclerView.setAdapter(mAdapter);
        }
    }


}

