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
import android.widget.Toast;

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
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListAdapterServer3;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class FilmActivityServer3 extends AppCompatActivity {
    private LinearLayout linearCategory;
    private RecyclerView recyclerView;
    private BindListAdapterServer3 mAdapter;
    private List<MoviesModel> movieList = new ArrayList<>();
    private List<MoviesModel> movieurlList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    ImageView ivNext, ivPrev, ivUp, ivDown;
    EditText etMoviename;
    Button btnMoviename;
    private int elementsize;
    Boolean isPrev, isNext, isSearch = false;
    int corePoolSize = 60;
    String Currenturl;
    android.support.v7.widget.SearchView search;
    int i = 0;
    String Category = "";
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    private ArrayList<String> yearArrayList = new ArrayList<>();
    private ArrayList<String> durationArrayList = new ArrayList<>();
    private Button btnhome, btnhdstream;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_film_server3);

        //  setContentView(R.layout.gridview_list);
        linearCategory = findViewById(R.id.categories);
        recyclerView = findViewById(R.id.recyclerview);
        btnhome = findViewById(R.id.btnhome);
        btnhdstream = findViewById(R.id.btnhdstream);
        ivNext = findViewById(R.id.ivNext);
        ivPrev = findViewById(R.id.ivPrev);
        ivUp = findViewById(R.id.ivUp);
        ivDown = findViewById(R.id.ivDown);
        ivNext.setVisibility(View.GONE);
        ivPrev.setVisibility(View.GONE);
        isNext = false;

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
                new searchdata(Constant.MOVIEURL3).execute();

            }
        });


        new prepareMovieData(Constant.MOVIEURL3, "").execute();

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

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new prepareMovieData(Constant.MOVIEURL3, "").execute();
                movieList.clear();
                mAdapter.notifyDataSetChanged();
                Category = "";
                isNext = false;
                i = 0;
                etMoviename.setText("");

            }
        });

        btnhdstream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new prepareMovieData(Constant.MOVIEURL3_HD, "").execute();
                movieList.clear();
                mAdapter.notifyDataSetChanged();
                Category = "";
                isNext = false;
                i = 0;
                etMoviename.setText("");

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
            mProgressDialog = new ProgressDialog(FilmActivityServer3.this);
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
                Document doc = Jsoup.connect(mainurl + "" + movieUrl).timeout(10000).get();

                MoviesModel moviesurl = new MoviesModel();
                moviesurl.setCurrenturl(mainurl + "" + movieUrl);
                movieurlList.add(moviesurl);

                Elements data = doc.getElementsByClass("container-fluid").select("div[class=span12 filmbox]");
                Log.d("size", data.size() + "");
                if(data!=null) {
                    for (int i = 0; i < data.size(); i++) {
                        MoviesModel movie = new MoviesModel();
                        Elements bodydata = data.get(i).select("div[class=span4]").first().getElementsByTag("a");
                        if(bodydata != null) {
                            String movieurl = bodydata.attr("href");
                            movie.setUrl(movieurl);
                        }
                        Elements bodydata1 = data.get(i).select("div[class=span4]").first().getElementsByTag("p").first().select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                        if(bodydata1 != null) {
                            String imageurl = bodydata1.attr("src");
                            movie.setImage(imageurl);
                        }
                        Elements bodydata2 = data.get(i).select("div[class=span8]").first().getElementsByTag("a").first().select("h1");
                        if(bodydata2 != null) {
                            String title = bodydata2.text();
                            movie.setTitle(title);
                        }
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

            if (!movieUrl.isEmpty()) {
                mAdapter = new BindListAdapterServer3(movieList, FilmActivityServer3.this);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilmActivityServer3.this, 3);
                recyclerView.setLayoutManager(mLayoutManager);
                //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.invalidate();
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
            } else {
                mAdapter = new BindListAdapterServer3(movieList, FilmActivityServer3.this);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilmActivityServer3.this, 3);
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

        View.OnClickListener onClickButton = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Button b = (Button) v;
                if (b.getText().toString().equalsIgnoreCase("Home")) {
                    Toast.makeText(FilmActivityServer3.this, b.getText().toString(), Toast.LENGTH_LONG).show();

                    // bindData(Constant.MOVIEURL1_CINEMA);
                } else if (b.getText().toString().equalsIgnoreCase("Film Al Cinema")) {
                    Toast.makeText(FilmActivityServer3.this, b.getText().toString(), Toast.LENGTH_LONG).show();
                    movieList.clear();

                    mAdapter.notifyDataSetChanged();
                    Category = Constant.MOVIEURL1_CINEMA;
                    isNext = false;
                    i = 0;
                    new prepareMovieData(Constant.MOVIEURL1, Constant.MOVIEURL1_CINEMA).execute();

                    //bindData(Constant.MOVIEURL1_SUB);
                } else if (b.getText().toString().equalsIgnoreCase("Film Sub ITA")) {
                    Toast.makeText(FilmActivityServer3.this, b.getText().toString(), Toast.LENGTH_LONG).show();
                    new prepareMovieData(Constant.MOVIEURL1, Constant.MOVIEURL1_SUB).execute();
                    movieList.clear();
                    mAdapter.notifyDataSetChanged();
                    Category = Constant.MOVIEURL1_SUB;
                    isNext = false;
                    i = 0;
                } else if (b.getText().toString().equalsIgnoreCase("Attori Consigliati")) {
                    Toast.makeText(FilmActivityServer3.this, b.getText().toString(), Toast.LENGTH_LONG).show();
                } else if (b.getText().toString().equalsIgnoreCase("Lista Film A/Z")) {
                    Toast.makeText(FilmActivityServer3.this, b.getText().toString(), Toast.LENGTH_LONG).show();
                    new prepareMovieData(Constant.MOVIEURL1, Constant.MOVIEURL1_ATOZ).execute();
                    movieList.clear();
                    mAdapter.notifyDataSetChanged();
                    Category = Constant.MOVIEURL1_ATOZ;
                    isNext = false;
                    i = 0;
                } else if (b.getText().toString().equalsIgnoreCase("Consigli")) {
                    Toast.makeText(FilmActivityServer3.this, b.getText().toString(), Toast.LENGTH_LONG).show();
                } else if (b.getText().toString().equalsIgnoreCase("Richiedi film +2100")) {
                    Toast.makeText(FilmActivityServer3.this, b.getText().toString(), Toast.LENGTH_LONG).show();
                } else if (b.getText().toString().equalsIgnoreCase("IL Film si Blocca?/Download Film?")) {
                    Toast.makeText(FilmActivityServer3.this, b.getText().toString(), Toast.LENGTH_LONG).show();
                }
            }
        };
    }


    public void bindData(String strUrl) {
        try {
            Document doc = Jsoup.connect(Constant.MOVIEURL1 + "/" + strUrl).timeout(10000).get();
            Elements title = doc.select("#dle-content p[class=h4]");
            int titleSize = title.size();
            Elements images = doc.select("#dle-content img[src~=(?i)\\.(png|jpe?g|gif)]");
            elementsize = images.size();
            Elements rating = doc.select("#dle-content span[class=ml-imdb]");
            int ratingSize = rating.size();
            Elements mElementYear = doc.select("#dle-content span[class=ml-label]");
            int yearSize = mElementYear.size();
            Elements mElementurl = doc.select("#dle-content a[href]");
            for (int j = 0; j < yearSize; j++) {
                if (mElementYear.get(j).text().contains("min")) {
                    durationArrayList.add(mElementYear.get(j).text());
                } else {
                    yearArrayList.add(mElementYear.get(j).text());
                }

            }
            for (int i = 0; i < elementsize; i++) {
                Elements mElementImage = doc.select("#dle-content img[src~=(?i)\\.(png|jpe?g|gif)]");
                String image = mElementImage.get(i).attr("src");
                Log.d("image", image);
                MoviesModel movie = new MoviesModel();
                movie.setImage(image);

                //For Movie Title
                Elements mElementTitle = doc.select("#dle-content p[class=h4]");
                String movieTitle = mElementTitle.get(i).text();
                Log.d("movieTitle", movieTitle);
                movie.setTitle(movieTitle);

                //For Rating
                Elements mElementRating = doc.select("#dle-content span[class=ml-imdb]");
                String movieRating = mElementRating.get(i).text();
                movie.setRating(movieRating);

                //For year
                movie.setYear(yearArrayList.get(i));

                //For duration
                movie.setDuration(durationArrayList.get(i));

                //For Url
                Elements mElementH2 = doc.select("#dle-content h2");
                Elements mElementUrl = mElementH2.get(i).getElementsByTag("a");
                String url = mElementUrl.attr("href");
                Log.d("href", url);
                movie.setUrl(url);
                movieList.add(movie);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class NextPagedata extends AsyncTask<String, Void, Void> {
        String NextPageUrl = null;
        String newurl = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(FilmActivityServer3.this);
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
                for (Element urls : doc.select("#wp_page_numbers > ul")) {
                    //perform your data extractions here.
                    for (Element urlss : urls.getElementsByTag("li")) {
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
                String SpilString =NextPageUrl;
                String[] separated = NextPageUrl.split("\\?");
                for (String item : separated)
                {
                    System.out.println("item = " + item);
                }
                NextPageUrl=separated[0];
                new searchdata(NextPageUrl).execute();

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
            mProgressDialog = new ProgressDialog(FilmActivityServer3.this);
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
                for (Element urls : doc.select("#wp_page_numbers > ul")) {
                    //perform your data extractions here.
                    for (Element urlss : urls.getElementsByTag("li")) {
                        for (Element nexturl : urlss.getElementsByTag("a")) {
                            result = urlss != null ? urlss.absUrl("href") : null;
                            Log.d("Urls", String.valueOf(nexturl));
                            PrevPageUrl = nexturl.attr("href");
                            Log.d("Urls", PrevPageUrl);
                            MoviesModel movieurl = new MoviesModel();
                            movieurl.setCurrenturl(PrevPageUrl);
                            movieurlList.add(movieurl);
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
            if(isSearch == true){
                String[] separated = PrevPageUrl.split("\\?");
                for (String item : separated)
                {
                    System.out.println("item = " + item);
                }
                PrevPageUrl=separated[0];
                new searchdata(PrevPageUrl).execute();
            }
            else{
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
            mProgressDialog = new ProgressDialog(FilmActivityServer3.this);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            //Movie1
            try {
                Connection.Response loginPageResponse =
                        Jsoup.connect(mainurl)
                                .referrer(Constant.MOVIEURL3)
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
                Document document = responsePostLogin.parse();

                System.out.print(document);
                movieList.clear();

                Elements data = document.getElementsByClass("container-fluid").select("div[class=span12 filmbox]");
                Log.d("size", data.size() + "");
                for (int i = 0; i < data.size(); i++) {
                    Elements bodydata = data.get(i).select("div[class=span4]").first().getElementsByTag("a");
                    String movieurl = bodydata.attr("href");
                    Elements bodydata1 = data.get(i).select("div[class=span4]").first().getElementsByTag("p").first().select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                    String imageurl = bodydata1.attr("src");
                    Elements bodydata2 = data.get(i).select("div[class=span8]").first().getElementsByTag("a").first().select("h1");
                    String title=bodydata2.text();
                    MoviesModel movie = new MoviesModel();
                    movie.setImage(imageurl);
                    movie.setUrl(movieurl);
                    movie.setTitle(title);
                    movieList.add(movie);

               /* Elements data = document.getElementsByClass("container main").first().select("ul[class=posts]").first().getElementsByTag("li");

                Log.d("data size", data.size() + "");
                for (int i = 0; i < data.size(); i++) {

                    MoviesModel movie = new MoviesModel();

                    Elements image = data.select("li");
                    Elements gettag = image.get(i).getElementsByTag("a");
                    String imageurl = gettag.attr("data-thumbnail");
                    String movieurl = gettag.attr("href");
                    Log.d("movieurl", movieurl);
                    Log.d("image data", imageurl);
                    movie.setImage(imageurl);
                    movie.setUrl(movieurl);

                    Elements name = image.get(i).select("div[class=title]");
                    String title = name.text();
                    Log.d("title", title);
                    movie.setTitle(title);
                    movieList.add(movie);*/
                }


                Map<String, String> mapLoggedInCookies = responsePostLogin.cookies();


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

            mAdapter = new BindListAdapterServer3(movieList, FilmActivityServer3.this);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilmActivityServer3.this, 3);
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

        ;
    }


}


