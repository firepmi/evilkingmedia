package evilkingmedia.cueserve.com.evilkingmedia.film;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListAdapterServer4;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class FilmActivityServer4 extends AppCompatActivity {
    private LinearLayout linearCategory, ll_right;
    private RelativeLayout rl_bottom;
    private RecyclerView recyclerView;
    private BindListAdapterServer4 mAdapter;
    private List<MoviesModel> movieList = new ArrayList<>();
    private List<MoviesModel> movieurlList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private ImageView ivNext, ivPrev, ivUp, ivDown;
    private EditText etMoviename;
    private Button btnMoviename;
    private TextView txt_nodata;
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
    private Button btnhome, btnsubita;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_film_server4);

        //  setContentView(R.layout.gridview_list);
        linearCategory = findViewById(R.id.categories);
        recyclerView = findViewById(R.id.recyclerview);
      //  btnhome = findViewById(R.id.btnhome);
     //   btnsubita = findViewById(R.id.btnsubita);
        ivNext = findViewById(R.id.ivNext);
        ivPrev = findViewById(R.id.ivPrev);
        ivUp = findViewById(R.id.ivUp);
        ivDown = findViewById(R.id.ivDown);
        ivNext.setVisibility(View.GONE);
        ivPrev.setVisibility(View.GONE);
        isNext = false;

        etMoviename = findViewById(R.id.etMoviname);
        btnMoviename = findViewById(R.id.btnMoviname);
        rl_bottom = findViewById(R.id.rl_bottom);
        ll_right = findViewById(R.id.ll_right);
        txt_nodata = findViewById(R.id.txt_nodata);

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
                new searchdata(Constant.MOVIESERACH4).execute();

            }
        });


        new prepareMovieData(Constant.MOVIEURL4, "").execute();

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                new NextPagedata().execute();

            }
        });

        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i--;
                new PreviousPagedata().execute();
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


      /*  btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new prepareMovieData(Constant.MOVIEURL4, "").execute();
                movieList.clear();
                mAdapter.notifyDataSetChanged();
                Category = "";
                isNext = false;
                isMovieita = false;
                i = 0;
                etMoviename.setText("");
            }
        });

        btnsubita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new prepareMovieDataita(Constant.MOVIEURL4_SUBITA, "").execute();
                movieList.clear();
                mAdapter.notifyDataSetChanged();
                Category = "";
                isNext = false;
                isMovieita = true;
                i = 0;
                etMoviename.setText("");
            }
        });*/
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
            try {
                mProgressDialog = new ProgressDialog(FilmActivityServer4.this);
                mProgressDialog.setTitle("");
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(String... strings) {

            //Movie1
            try {
                // Connect to the web site
                Document doc = Jsoup.connect(mainurl + "" + movieUrl).timeout(10000).get();
                movieurlList.clear();
                MoviesModel moviesurl = new MoviesModel();


                Elements data = doc.select("div[class=leftC]");
                if (data != null ) {

                    Elements data1 = data.select("div[class=filmcontent]").select("div[class=moviefilm]");
                    moviesurl.setCurrenturl(mainurl + "" + movieUrl);
                    movieurlList.add(moviesurl);
                    if (data1 != null && !data1.isEmpty()) {
                        ViewVisibility(View.VISIBLE, View.GONE);

                     //   Elements data2 = data1.select("#archive-content").first().select("article");
                        Log.d("data size", data1.size() + "");

                        if (data1 != null) {
                            for (int i = 0; i < data1.size(); i++) {

                                String urldata = data1.get(i).select("a").attr("href");
                                String imagedata = data1.get(i).select("a").select("img").attr("src");
                                String title =data1.get(i).select("div[class=movief]").select("a").text();
                                MoviesModel movie = new MoviesModel();
                                movie.setImage(imagedata);
                                movie.setUrl(urldata);
                                movie.setTitle(title);
                                movieList.add(movie);
                            }
                        }
                    } else {
                        ViewVisibility(View.GONE, View.VISIBLE);
                    }
                }
                else {
                    ViewVisibility(View.GONE, View.VISIBLE);
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

            if (!movieUrl.isEmpty()) {
                mAdapter = new BindListAdapterServer4(movieList, FilmActivityServer4.this);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilmActivityServer4.this, 3);
                recyclerView.setLayoutManager(mLayoutManager);
                //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.invalidate();
                recyclerView.setAdapter(mAdapter);
             //   ivNext.setVisibility(View.VISIBLE);


                try {
                    if (isNext == true) {
                        ivPrev.setVisibility(View.VISIBLE);
                    } else {
                        ivPrev.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    ivPrev.setVisibility(View.GONE);
                }
                if (i != 0) {

                    ivPrev.setVisibility(View.VISIBLE);
                } else {
                    ivPrev.setVisibility(View.GONE);
                }
            } else {
                mAdapter = new BindListAdapterServer4(movieList, FilmActivityServer4.this);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilmActivityServer4.this, 3);
                recyclerView.setLayoutManager(mLayoutManager);
                //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
              //  ivNext.setVisibility(View.VISIBLE);


                try {
                    if (isNext == true) {
                        ivPrev.setVisibility(View.VISIBLE);
                    } else {
                        ivPrev.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    ivPrev.setVisibility(View.GONE);
                }
                if (i != 0) {

                    ivPrev.setVisibility(View.VISIBLE);
                } else {
                    ivPrev.setVisibility(View.GONE);
                }

            }


        }

    }

    private void ViewVisibility(final int status, final int visible) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                ivNext.setVisibility(status);
                ivPrev.setVisibility(status);
                ivUp.setVisibility(status);
                ivDown.setVisibility(status);
                txt_nodata.setVisibility(visible);
            }
        });
    }


    private class NextPagedata extends AsyncTask<String, Void, Void> {
        String NextPageUrl = null;
        String newurl = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(FilmActivityServer4.this);
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
                isNext = true;
                String result;
                Document doc = null;
                for (int i = 0; i < movieurlList.size(); i++) {

                    newurl = movieurlList.get(i).getCurrenturl();

                }
                doc = Jsoup.connect(newurl).timeout(10000).get();
                movieurlList.clear();

                for (Element urls : doc.getElementsByClass("wp-pagenavi")) {
                    //perform your data extractions here.
                    for (Element urlss : urls.getElementsByClass("nextpostslink")) {
                        for (Element nexturl : urlss.getElementsByTag("a")) {
                            result = urlss != null ? urlss.absUrl("href") : null;
                            Log.d("Urls", String.valueOf(nexturl));
                            NextPageUrl = nexturl.attr("href");
                            Log.d("Urls", NextPageUrl);
                            MoviesModel movieurl = new MoviesModel();
                            movieurl.setCurrenturl(NextPageUrl);
                            movieurlList.add(movieurl);
                        }
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
            if (isSearch == true) {
                /*String SpilString = NextPageUrl;
                String[] separated = NextPageUrl.split("\\?");
                for (String item : separated) {
                    System.out.println("item = " + item);
                }
                NextPageUrl = separated[0];*/
                isNextSearch = true;
                new searchdata(NextPageUrl).execute();

            } else if (isMovieita == true) {
                new prepareMovieDataita(NextPageUrl, "").execute();
            } else {
                new prepareMovieData(NextPageUrl, "").execute();
            }

        }

        ;
    }


    private class PreviousPagedata extends AsyncTask<String, Void, Void> {
        String PrevPageUrl = null;
        String newurl = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(FilmActivityServer4.this);
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
                isNext = true;
                String result;
                Document doc = null;

                for (int i = 0; i < movieurlList.size(); i++) {

                    newurl = movieurlList.get(i).getCurrenturl();

                }
                doc = Jsoup.connect(newurl).timeout(10000).get();
                movieurlList.clear();
                for (Element urls : doc.getElementsByClass("wp-pagenavi")) {
                    //perform your data extractions here.
                    for (Element urlss : urls.getElementsByClass("previouspostslink")) {
                        for (Element nexturl : urlss.getElementsByTag("a")) {
                            result = urlss != null ? urlss.absUrl("href") : null;
                            Log.d("Urls", String.valueOf(nexturl));
                            PrevPageUrl = nexturl.attr("href");
                            Log.d("Urls", PrevPageUrl);
                            MoviesModel movieurl = new MoviesModel();
                            movieurl.setCurrenturl(PrevPageUrl);
                            movieurlList.add(movieurl);
                        }
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
            if (isSearch == true) {
               /* String[] separated = PrevPageUrl.split("\\?");
                for (String item : separated) {
                    System.out.println("item = " + item);
                }
                PrevPageUrl = separated[0];*/
                new searchdata(PrevPageUrl).execute();
            } else if (isMovieita == true) {
                new prepareMovieDataita(PrevPageUrl, "").execute();
            } else {
                new prepareMovieData(PrevPageUrl, "").execute();
            }

        }

        ;
    }

    private class searchdata extends AsyncTask<String, Void, Void> {
        String mainurl = null;
        String newurl = null;

        public searchdata(String mainurl) {
            this.mainurl = mainurl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(FilmActivityServer4.this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            //Movie1
            try {

                if (isNextSearch == false) {
                    Connection.Response loginPageResponse =
                            Jsoup.connect(mainurl)
                                    .referrer(Constant.MOVIEURL4)
                                    .userAgent("Mozilla/5.0")
                                    .timeout(10 * 1000)
                                    .followRedirects(true)
                                    .execute();

                    System.out.println("Fetched login page");

                    //get the cookies from the response, which we will post to the action URL
                    Map<String, String> mapLoginPageCookies = loginPageResponse.cookies();

                    //lets make data map containing all the parameters and its values found in the form
                    Map<String, String> mapParams = new HashMap<String, String>();
                    mapParams.put("s", etMoviename.getText().toString().trim());


                    //URL found in form's action attribute
                    String strActionURL = mainurl;

                    Connection.Response responsePostLogin = Jsoup.connect(strActionURL)
                            //referrer will be the login page's URL
                            .referrer(mainurl)
                            //user agent
                            .userAgent("Mozilla/5.0")
                            //connect and read time out
                            .timeout(10 * 1000)
                            //post parameters
                            .data(mapParams)
                            //cookies received from login page
                            .cookies(mapLoginPageCookies)
                            //many websites redirects the user after login, so follow them
                            .followRedirects(true)
                            .execute();

                    MoviesModel moviesurl = new MoviesModel();
                    String searchquery = etMoviename.getText().toString().trim();
                    moviesurl.setCurrenturl(mainurl + "?s=" + searchquery);
                    movieurlList.add(moviesurl);

                    System.out.println("HTTP Status Code: " + responsePostLogin.statusCode());

                    //parse the document from response
                    Document doc = responsePostLogin.parse();


                    movieList.clear();

                    Elements data = doc.select("div#home_cont");
                    if (data != null ) {
                        Elements data1 = data.select("div[class=home_tall_box]");

                        if (data1 != null && !data1.isEmpty()) {
                            ViewVisibility(View.VISIBLE, View.GONE);

                            //   Elements data2 = data1.select("#archive-content").first().select("article");
                            Log.d("data size", data1.size() + "");

                            if (data1 != null) {
                                for (int i = 0; i < data1.size(); i++) {

                                    String urldata = data1.get(i).select("a").attr("href");
                                    String imagedata = data1.get(i).select("a").select("img").attr("src");
                                    String title =data1.get(i).select("h3").text();
                                    MoviesModel movie = new MoviesModel();
                                    movie.setImage(imagedata);
                                    movie.setUrl(urldata);
                                    movie.setTitle(title);
                                    movieList.add(movie);
                                }
                            }
                        } else {
                            ViewVisibility(View.GONE, View.VISIBLE);
                        }
                    }
                    else {
                        ViewVisibility(View.GONE, View.VISIBLE);
                    }

                    Map<String, String> mapLoggedInCookies = responsePostLogin.cookies();
                } else {

                    Document document = Jsoup.connect(mainurl).ignoreContentType(true).ignoreHttpErrors(true).timeout(10000).get();

                    movieList.clear();

                    Elements data = document.getElementsByClass("result-item");

                    for (int i = 0; i < data.size(); i++) {
                        String image = data.get(i).getElementsByClass("thumbnail animation-2").first().select("img[src~=(?i)\\.(png|jpe?g|gif)]").attr("src");
                        String title = data.get(i).getElementsByClass("thumbnail animation-2").first().select("img[src~=(?i)\\.(png|jpe?g|gif)]").attr("alt");
                        String url = data.get(i).getElementsByTag("a").attr("href");
                        MoviesModel movie = new MoviesModel();
                        movie.setImage(image);
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

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            mAdapter = new BindListAdapterServer4(movieList, FilmActivityServer4.this);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilmActivityServer4.this, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.invalidate();
            recyclerView.setAdapter(mAdapter);

            try {
                if (isNext == true) {
                    ivPrev.setVisibility(View.VISIBLE);
                } else {
                    ivPrev.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                ivPrev.setVisibility(View.GONE);
            }
            if (i != 0) {

                ivPrev.setVisibility(View.VISIBLE);
            } else {
                ivPrev.setVisibility(View.GONE);
            }
        }


    }

    private class prepareMovieDataita extends AsyncTask<String, Void, Void> {
        String movieUrl;
        String mainurl;

        public prepareMovieDataita(String mainurl, String movieurl1Cinema) {
            this.movieUrl = movieurl1Cinema;
            this.mainurl = mainurl;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(FilmActivityServer4.this);
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
                Document doc = Jsoup.connect(mainurl + "" + movieUrl).ignoreContentType(true).ignoreHttpErrors(true).timeout(10000).get();

                MoviesModel moviesurl = new MoviesModel();
                moviesurl.setCurrenturl(mainurl + "" + movieUrl);
                movieurlList.add(moviesurl);

                Element data = doc.getElementById("dt_contenedor");
                if (data != null) {
                    Element data1 = data.getElementById("contenedor");
                    if (data1 != null) {

                        Elements data2 = data1.getElementsByClass("items").first().select("article");
                        Log.d("data size", data2.size() + "");
                        if (data2 != null) {
                            for (int i = 0; i < data2.size(); i++) {
                                Elements imageurl = data2.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                                String imagedata = imageurl.get(i).attr("src");
                                String title = imageurl.get(i).attr("alt");
                                String urldata = data2.get(i).getElementsByClass("poster").first().getElementsByTag("a").attr("href");
                                MoviesModel movie = new MoviesModel();
                                movie.setImage(imagedata);
                                movie.setUrl(urldata);
                                movie.setTitle(title);
                                movieList.add(movie);
                            }
                        }
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

            if (!movieUrl.isEmpty()) {
                mAdapter = new BindListAdapterServer4(movieList, FilmActivityServer4.this);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilmActivityServer4.this, 3);
                recyclerView.setLayoutManager(mLayoutManager);
                //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.invalidate();
                recyclerView.setAdapter(mAdapter);
             //   ivNext.setVisibility(View.VISIBLE);


                try {
                    if (isNext == true) {
                        ivPrev.setVisibility(View.VISIBLE);
                    } else {
                        ivPrev.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    ivPrev.setVisibility(View.GONE);
                }
                if (i != 0) {

                    ivPrev.setVisibility(View.VISIBLE);
                } else {
                    ivPrev.setVisibility(View.GONE);
                }
            } else {
                mAdapter = new BindListAdapterServer4(movieList, FilmActivityServer4.this);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilmActivityServer4.this, 3);
                recyclerView.setLayoutManager(mLayoutManager);
                //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                ivNext.setVisibility(View.VISIBLE);


                try {
                    if (isNext == true) {
                        ivPrev.setVisibility(View.VISIBLE);
                    } else {
                        ivPrev.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    ivPrev.setVisibility(View.GONE);
                }
                if (i != 0) {

                    ivPrev.setVisibility(View.VISIBLE);
                } else {
                    ivPrev.setVisibility(View.GONE);
                }

            }


        }

    }


}


