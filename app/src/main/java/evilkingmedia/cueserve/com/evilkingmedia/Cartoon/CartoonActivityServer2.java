package evilkingmedia.cueserve.com.evilkingmedia.Cartoon;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class CartoonActivityServer2 extends AppCompatActivity {

    private LinearLayout linearCategory;
    private RecyclerView recyclerView;
    private CartoonServer1Adapter mAdapter;
    private List<MoviesModel> movieList = new ArrayList<>();
    private List<MoviesModel> movieurlList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    ImageView ivNext, ivPrev, ivUp, ivDown;
    EditText etMoviename;
    Button btnMoviename;
    private int elementsize;
    Boolean isPrev, isNext, isSearch = false, isFilmDel = false;
    int corePoolSize = 60;
    String Currenturl;
    android.support.v7.widget.SearchView search;
    int i = 0;
    String Category = "";
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    private ArrayList<String> yearArrayList = new ArrayList<>();
    private ArrayList<String> durationArrayList = new ArrayList<>();
    private Button btnhome, btnCategory ;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    SearchView searchview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sports_server2);
        linearCategory = findViewById(R.id.categories);
        recyclerView = findViewById(R.id.recyclerview);
        btnhome = findViewById(R.id.btnhome);
        btnCategory = findViewById(R.id.btncategory);
        ivNext = findViewById(R.id.ivNext);
        ivPrev = findViewById(R.id.ivPrev);
        ivUp = findViewById(R.id.ivUp);
        ivDown = findViewById(R.id.ivDown);
        ivNext.setVisibility(View.GONE);
        ivPrev.setVisibility(View.GONE);
        isNext = false;


        btnMoviename = findViewById(R.id.btnMoviname);
        btnhome.setVisibility(View.GONE);
        btnCategory.setVisibility(View.GONE);

        searchview = findViewById(R.id.searchView);

        searchview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                searchview.setFocusable(true);
                searchview.setFocusableInTouchMode(true);
                return false;
            }
        });


      //  new prepareCartoonData(Constant.CARTOONURL2, "").execute();

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                new  NextPagedata().execute();
            }
        });

        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i--;
                new  PreviousPagedata().execute();
            }
        });
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

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    private class prepareCartoonData extends AsyncTask<String, Void, Void> {
        String movieUrl;
        String mainurl;

        public prepareCartoonData(String mainurl, String movieurl1Cinema) {
            this.movieUrl = movieurl1Cinema;
            this.mainurl = mainurl;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(CartoonActivityServer2.this);
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
                Document doc = Jsoup.connect(mainurl + "" + movieUrl).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.38 Safari/537.36")
                       .get();

                MoviesModel moviesurl = new MoviesModel();
                moviesurl.setCurrenturl(mainurl + "" + movieUrl);
                movieurlList.add(moviesurl);

                Elements data = doc.select("div#main_col").select("div[class=wpwm-items-ajax]").select("div[class=media3]");
                if(data !=null) {
                    Log.d("data size", data.size() + "");
                    for (int i = 0; i < data.size(); i++) {
                        String url = data.get(i).select("a").attr("href");
                        String title = data.get(i).select("div[class=title-film]").select("p").text();
                        String  imageurl = data.get(i).select("img").attr("src");

                        MoviesModel movie = new MoviesModel();
                        movie.setImage(imageurl);
                        movie.setUrl(url);
                        movie.setTitle(title);
                        movieList.add(movie);

                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }


            mAdapter = new CartoonServer1Adapter(movieList, CartoonActivityServer2.this);
            // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(CartoonActivityServer2.this, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.invalidate();
            recyclerView.setAdapter(mAdapter);
            //   ivNext.setVisibility(View.VISIBLE);


               /* try {
                    if (isNext == true) {
                        ivPrev.setVisibility(View.VISIBLE);
                    } else {
                        ivPrev.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    ivPrev.setVisibility(View.GONE);
                }*/
            if (i != 0) {

                ivPrev.setVisibility(View.VISIBLE);
            } else {
                ivPrev.setVisibility(View.GONE);
            }


        };
    }
    private class NextPagedata extends AsyncTask<String, Void, Void> {
        String NextPageUrl = null;
        String newurl = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(CartoonActivityServer2.this);
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
                //  isNext = true;
                String result;
                Document doc = null;
                for (int i = 0; i < movieurlList.size(); i++) {

                    newurl = movieurlList.get(i).getCurrenturl();
                }
                doc = Jsoup.connect(newurl).timeout(10000).get();
                Elements main = doc.select("div[class=container]");
                for (Element urls : main.select("div[class=pagination-wrapper]")) {
                    //perform your data extractions here.
                    for (Element urlss : urls.getElementsByTag("a")) {

                        NextPageUrl = urlss.attr("data-paginated");
                        Log.d("Urls", NextPageUrl);
                        MoviesModel moviesModel = new MoviesModel();
                        moviesModel.setUrl(NextPageUrl);
                        movieList.add(moviesModel);

                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            movieList.clear();
            ivPrev.setVisibility(View.VISIBLE);
            NextPageUrl = "https://streamingsports.me/" + NextPageUrl;
            new  prepareCartoonData(NextPageUrl,"").execute();
        }

        ;
    }


    private class PreviousPagedata extends AsyncTask<String, Void, Void> {
        String PrevPageUrl = null;
        String newurl = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(CartoonActivityServer2.this);
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
                //   isNext = true;
                String result;
                Document doc = null;
                for (int i = 0; i < movieurlList.size(); i++) {

                    newurl = movieurlList.get(i).getCurrenturl();
                }
                doc = Jsoup.connect(newurl).timeout(10000).get();
                movieurlList.clear();
                for (Element urls : doc.getElementsByClass("page_nav")) {
                    //perform your data extractions here.
                    for (Element urlss : urls.getElementsByTag("i")) {
                        for (Element nexturl : urlss.getElementsByTag("a")) {
                            result = urlss != null ? urlss.absUrl("href") : null;
                            Log.d("Urls", String.valueOf(nexturl));
                            PrevPageUrl = nexturl.attr("href");
                            Log.d("Urls", PrevPageUrl);
                            MoviesModel movieurl = new MoviesModel();
                            movieurl.setCurrenturl(PrevPageUrl);
                            movieList.add(movieurl);
                            break;
                        }
                        break;
                    }
                    break;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            movieList.clear();
            ivPrev.setVisibility(View.VISIBLE);
            PrevPageUrl = "https://streamingsports.me/" + PrevPageUrl;
            new  prepareCartoonData(PrevPageUrl,"").execute();
        }

        ;
    }

}
