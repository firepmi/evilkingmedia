package evilkingmedia.cueserve.com.evilkingmedia.film;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListAdapter;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class FilmActivity extends AppCompatActivity {
    private LinearLayout linearCategory;
    private RecyclerView recyclerView;
    private BindListAdapter mAdapter;
    private List<MoviesModel> movieList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private int elementsize;
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    private ArrayList<String> yearArrayList = new ArrayList<>();
    private ArrayList<String> durationArrayList = new ArrayList<>();
    private Button btn[];
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

        new prepareMovieData("").execute();
    }


    private class prepareMovieData extends AsyncTask<String, Void, Void> {
        String movieUrl;

        public prepareMovieData(String movieurl1Cinema) {
        this.movieUrl = movieurl1Cinema;
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
                Document doc = Jsoup.connect(Constant.MOVIEURL1+"/"+movieUrl).timeout(10000).get();

                //For Categories
                Elements categoryUl =   doc.select("#main_container div.menu-menu-1-container > ul");
                Log.e("categoryUl", ""+categoryUl.size());
                Elements category = categoryUl.select("li");
                Log.e("category", ""+category.size());
                final Button button[] = new Button[category.size()];
                btn  = new Button[category.size()];
                for(int k=0; k < category.size();k++) {
                    Elements mElementUrl = category.get(k).getElementsByTag("a");
                    String url = mElementUrl.text();
                    Log.e("categoty_list",url);

                    button[k] = new Button(FilmActivity.this);
                    button[k].setText(url);
                    button[k].setTextColor(getResources().getColor(R.color.colorWhite));
                    button[k].setPadding(5,5,5,5);
                    button[k].setBackgroundColor(getResources().getColor(R.color.colorBlack));
                    button[k].setTextSize(12);
                    button[k].setOnClickListener(onClickButton);

                    final int finalK = k;
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            linearCategory.addView(button[finalK]);


                        }
                    });

                }
                // Using Elements to get the Meta data
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

           //Movie2
          /*  try {
                // Connect to the web site
                Document doc = Jsoup.connect(Constant.MOVIEURL2+"/").timeout(10000).get();
                // Using Elements to get the Meta data
                Elements title = doc.select("div.row wracontindexpost");
                int titleSize = title.size();
                for (int i = 0; i < titleSize; i++) {
                    Elements mElementImage = doc.select("div.row wracontindexpost");
                    String image = mElementImage.get(i).getElementsByClass("div.container-index-post col-xs-4 col-sm-3 col-md-2-5 col-lg-2").text();
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
            }*/
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
            }
            else {
                mAdapter = new BindListAdapter(movieList, FilmActivity.this);
                // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilmActivity.this, 3);
                recyclerView.setLayoutManager(mLayoutManager);
                //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
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
                    new prepareMovieData(Constant.MOVIEURL1_CINEMA).execute();
                    movieList.clear();
                    mAdapter.notifyDataSetChanged();
                    //bindData(Constant.MOVIEURL1_SUB);
                }
                else if(b.getText().toString().equalsIgnoreCase("Film Sub ITA")){
                    Toast.makeText(FilmActivity.this,b.getText().toString(),Toast.LENGTH_LONG).show();
                    new prepareMovieData(Constant.MOVIEURL1_SUB).execute();
                    movieList.clear();
                    mAdapter.notifyDataSetChanged();
                }
                else if(b.getText().toString().equalsIgnoreCase("Attori Consigliati")){
                    Toast.makeText(FilmActivity.this,b.getText().toString(),Toast.LENGTH_LONG).show();
                }
                else if(b.getText().toString().equalsIgnoreCase("Lista Film A/Z")){
                    Toast.makeText(FilmActivity.this,b.getText().toString(),Toast.LENGTH_LONG).show();
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


}

