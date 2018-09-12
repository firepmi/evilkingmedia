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
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListAdapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class FilmActivity extends AppCompatActivity {
    private LinearLayout linearCategory;
    private RecyclerView recyclerView;
    private BindListAdapter mAdapter;
    private List<MoviesModel> movieList = new ArrayList<>();
    private List<MoviesModel> movieurlList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    ImageView ivNext, ivPrev, ivUp, ivDown;
    EditText etMoviename;
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
    private Button btnhome, btnfilmal, btnfilmsub, btnfilmaz;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
         setContentView(R.layout.activity_film);

      //  setContentView(R.layout.gridview_list);
        linearCategory = findViewById(R.id.categories);
        recyclerView = findViewById(R.id.recyclerview);
        btnhome = findViewById(R.id.btnhome);
        btnfilmal = findViewById(R.id.btnfilmal);
        btnfilmsub = findViewById(R.id.btnfilmsub);
        btnfilmaz = findViewById(R.id.btnfilmaz);
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
                new searchdata().execute();

            }
        });

       /* recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });*/

        /* search = findViewById(R.id.search);*/

       /* search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });*/

        new prepareMovieData(Constant.MOVIEURL1, "").execute();

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
                new prepareMovieData(Constant.MOVIEURL1, "").execute();
                movieList.clear();
                mAdapter.notifyDataSetChanged();
                Category = "";
                isNext = false;
                i = 0;
                etMoviename.setText("");

            }
        });

        btnfilmal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                movieList.clear();

                mAdapter.notifyDataSetChanged();
                Category = Constant.MOVIEURL1_CINEMA;
                isNext = false;
                i = 0;
                new prepareMovieData(Constant.MOVIEURL1, Constant.MOVIEURL1_CINEMA).execute();
                etMoviename.setText("");

            }
        });

        btnfilmsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new prepareMovieData(Constant.MOVIEURL1, Constant.MOVIEURL1_SUB).execute();
                movieList.clear();
                mAdapter.notifyDataSetChanged();
                Category = Constant.MOVIEURL1_SUB;
                isNext = false;
                i = 0;
                etMoviename.setText("");

            }
        });

        btnfilmaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new prepareMovieData(Constant.MOVIEURL1, Constant.MOVIEURL1_ATOZ).execute();
                movieList.clear();
                mAdapter.notifyDataSetChanged();
                Category = Constant.MOVIEURL1_ATOZ;
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
            mProgressDialog = new ProgressDialog(FilmActivity.this);
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

                try {
                    MoviesModel moviesurl = new MoviesModel();
                    moviesurl.setCurrenturl(mainurl + "" + movieUrl);
                    movieurlList.add(moviesurl);
                    //For Categories
                    Elements categoryUl = doc.select("#main_container div.menu-menu-1-container > ul");
                    Log.e("categoryUl", "" + categoryUl.size());
                    Elements category = categoryUl.select("li");
                    Log.e("category", "" + category.size());

                    if (Category == Constant.MOVIEURL1_ATOZ) {
                        String movieTitle = null;
                        Elements trdata = doc.select("tr[class^=mlnew]");

                        Log.e("table size", trdata.size() + "");

                        for (int i = 1; i < trdata.size(); i++) { //first row is the col names so skip it.
                      /*  Element row = rows.get(i);
                        Elements cols = row.select("td");*/
                            Log.e("data", trdata + "");

                        }
                        Elements image = doc.getElementsByClass("mlnh-thumb");
                        for (int i = 1; i < image.size(); i++) {
                            String imagesrcatoz = image.get(i).getElementsByTag("img").attr("src");
                            Log.e("imagename", imagesrcatoz);
                            MoviesModel movie = new MoviesModel();
                            /*  movie.setImage(imagesrcatoz);*/
                            movieTitle = image.get(i).getElementsByTag("img").attr("title");
                            Log.d("movieTitle", movieTitle);
                            Elements year = doc.getElementsByClass("mlnh-3");
                            String movieyear = year.get(i).text();
                            Log.e("year", movieyear);
                            String urls = image.get(i).getElementsByTag("a").attr("href");
                            Log.e("data url", urls);
                            movie.setUrl(urls);
                            movie.setTitle(movieTitle);
                            movie.setYear(movieyear);
                            movieList.add(movie);
                        }


                        Log.d("movielist", movieList + "");


                    } else {
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            if(mProgressDialog!=null) {
                mProgressDialog.dismiss();
            }

            if(!movieUrl.isEmpty())
            {
                mAdapter = new BindListAdapter(movieList, FilmActivity.this);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilmActivity.this, 3);
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
            }
            else {
                mAdapter = new BindListAdapter(movieList, FilmActivity.this);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilmActivity.this, 3);
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
                Button b = (Button)v;
                if(b.getText().toString().equalsIgnoreCase("Home")){
                    Toast.makeText(FilmActivity.this,b.getText().toString(),Toast.LENGTH_LONG).show();

                   // bindData(Constant.MOVIEURL1_CINEMA);
                }
                else if(b.getText().toString().equalsIgnoreCase("Film Al Cinema")){
                    Toast.makeText(FilmActivity.this,b.getText().toString(),Toast.LENGTH_LONG).show();
                    movieList.clear();

                    mAdapter.notifyDataSetChanged();
                    Category = Constant.MOVIEURL1_CINEMA;
                    isNext = false;
                    i = 0;
                    new prepareMovieData(Constant.MOVIEURL1, Constant.MOVIEURL1_CINEMA).execute();

                    //bindData(Constant.MOVIEURL1_SUB);
                }
                else if(b.getText().toString().equalsIgnoreCase("Film Sub ITA")){
                    Toast.makeText(FilmActivity.this,b.getText().toString(),Toast.LENGTH_LONG).show();
                    new prepareMovieData(Constant.MOVIEURL1, Constant.MOVIEURL1_SUB).execute();
                    movieList.clear();
                    mAdapter.notifyDataSetChanged();
                    Category = Constant.MOVIEURL1_SUB;
                    isNext = false;
                    i = 0;
                }
                else if(b.getText().toString().equalsIgnoreCase("Attori Consigliati")){
                    Toast.makeText(FilmActivity.this,b.getText().toString(),Toast.LENGTH_LONG).show();
                }
                else if(b.getText().toString().equalsIgnoreCase("Lista Film A/Z")){
                    Toast.makeText(FilmActivity.this,b.getText().toString(),Toast.LENGTH_LONG).show();
                    new prepareMovieData(Constant.MOVIEURL1, Constant.MOVIEURL1_ATOZ).execute();
                    movieList.clear();
                    mAdapter.notifyDataSetChanged();
                    Category = Constant.MOVIEURL1_ATOZ;
                    isNext = false;
                    i = 0;
                }
                else if(b.getText().toString().equalsIgnoreCase("Consigli")){
                    Toast.makeText(FilmActivity.this,b.getText().toString(),Toast.LENGTH_LONG).show();
                }
                else if(b.getText().toString().equalsIgnoreCase("Richiedi film +2100")){
                    Toast.makeText(FilmActivity.this,b.getText().toString(),Toast.LENGTH_LONG).show();
                }
                else if(b.getText().toString().equalsIgnoreCase("IL Film si Blocca?/Download Film?")){
                    Toast.makeText(FilmActivity.this,b.getText().toString(),Toast.LENGTH_LONG).show();
                }
            }
        };
    }


    public void bindData(String strUrl)
{
    try {
        Document doc = Jsoup.connect(Constant.MOVIEURL1+"/"+strUrl).timeout(10000).get();
        Elements title = doc.select("#dle-content p[class=h4]");
        int titleSize = title.size();
        Elements images = doc.select("#dle-content img[src~=(?i)\\.(png|jpe?g|gif)]");
        elementsize = images.size();
        Elements rating = doc.select("#dle-content span[class=ml-imdb]");
        int ratingSize = rating.size();
        Elements mElementYear = doc.select("#dle-content span[class=ml-label]");
        int yearSize = mElementYear.size();
        Elements mElementurl = doc.select("#dle-content a[href]");
        for(int j =0 ;j<yearSize;j++)
        { if(mElementYear.get(j).text().contains("min"))
        {
            durationArrayList.add(mElementYear.get(j).text());
        }
        else
        {
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
            Elements mElementUrl =  mElementH2.get(i).getElementsByTag("a");
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
            mProgressDialog = new ProgressDialog(FilmActivity.this);
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
                for (Element urls : doc.getElementsByClass("page_nav")) {
                    //perform your data extractions here.
                    for (Element urlss : urls.getElementsByTag("i")) {
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
            new prepareMovieData(NextPageUrl, "").execute();
        }

        ;
    }


    private class PreviousPagedata extends AsyncTask<String, Void, Void> {
        String PrevPageUrl = null;
        String newurl = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(FilmActivity.this);
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
            new prepareMovieData(PrevPageUrl, "").execute();
        }

        ;
    }

    private class searchdata extends AsyncTask<String, Void, Void> {
        String PrevPageUrl = null;
        String newurl = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(FilmActivity.this);
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
                        Jsoup.connect(Constant.MOVIEURL1)
                                .referrer(Constant.MOVIEURL1)
                                .userAgent("Mozilla/5.0")
                                .timeout(10 * 1000)
                                .followRedirects(true)
                                .execute();

                System.out.println("Fetched login page");

                //get the cookies from the response, which we will post to the action URL
                Map<String, String> mapLoginPageCookies = loginPageResponse.cookies();

                //lets make data map containing all the parameters and its values found in the form
                Map<String, String> mapParams = new HashMap<String, String>();
                mapParams.put("do", "search");
                mapParams.put("subaction", "search");
                mapParams.put("titleonly", "3");
                mapParams.put("story", etMoviename.getText().toString().trim());

                //URL found in form's action attribute
                String strActionURL = Constant.MOVIEURL1;

                Connection.Response responsePostLogin = Jsoup.connect(strActionURL)
                        //referrer will be the login page's URL
                        .referrer(Constant.MOVIEURL1)
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

                System.out.println("HTTP Status Code: " + responsePostLogin.statusCode());

                //parse the document from response
                Document document = responsePostLogin.parse();

                System.out.print(document);

                /* System.out.println(document);*/
               movieList.clear();
                Elements clasess=document.getElementsByClass("boxgrid caption");

                for(int i=0;i<clasess.size();i++){

                    Elements images=clasess.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                    String image = images.get(i).attr("src");
                    MoviesModel movie = new MoviesModel();
                    Log.d("image", image);
                    movie.setImage(image);
                    Elements mElementTitle = clasess.select("p[class=h4]");
                    String movieTitle = mElementTitle.get(i).text();
                    Log.d("movieTitle", movieTitle);
                    movie.setTitle(movieTitle);
                    Elements mElementRating = clasess.select("span[class=ml-imdb]");
                    String movieRating = mElementRating.get(i).text();
                    Log.d("rating",movieRating);
                    movie.setRating(movieRating);
                    Elements mElementYear = clasess.select("span[class=ml-label]");
                    int yearSize = mElementYear.size();
                    for (int j = 0; j < yearSize; j++) {
                        if (mElementYear.get(j).text().contains("min")) {
                            durationArrayList.add(mElementYear.get(j).text());
                        } else {
                            yearArrayList.add(mElementYear.get(j).text());
                        }

                    }
                    movie.setYear(yearArrayList.get(i));

                    //For duration
                    movie.setDuration(durationArrayList.get(i));

                    Elements mElementH2 = clasess.select("h2");
                    Elements mElementUrl =  mElementH2.get(i).getElementsByTag("a");
                    String url = mElementUrl.attr("href");
                    Log.d("href", url);
                    movie.setUrl(url);
                    movieList.add(movie);
                }
                /* System.out.println(clasess);*/

                //get the cookies
                Map<String, String> mapLoggedInCookies = responsePostLogin.cookies();


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
            mAdapter = new BindListAdapter(movieList, FilmActivity.this);
            // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilmActivity.this, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.invalidate();
            recyclerView.setAdapter(mAdapter);
        }

        ;
    }


}

