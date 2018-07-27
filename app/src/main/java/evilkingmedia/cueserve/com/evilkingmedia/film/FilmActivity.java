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
import android.view.Window;
import android.view.WindowManager;

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

        recyclerView = findViewById(R.id.recyclerview);

        new prepareMovieData().execute();
    }


    private class prepareMovieData extends AsyncTask<Void, Void, Void> {
        String desc;

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
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document doc = Jsoup.connect(Constant.MOVIEURL+"/").timeout(10000).get();
                // Using Elements to get the Meta data
                Elements title = doc.select("#dle-content p[class=h4]");
                int titleSize = title.size();
                Elements images = doc.select("#dle-content img[src~=(?i)\\.(png|jpe?g|gif)]");
                elementsize = images.size();
                Elements rating = doc.select("#dle-content span[class=ml-imdb]");
                int ratingSize = rating.size();
                Elements mElementYear = doc.select("#dle-content span[class=ml-label]");
                int yearSize = mElementYear.size();
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

                    movieList.add(movie);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView

            mAdapter = new BindListAdapter(movieList, FilmActivity.this);
           // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FilmActivity.this, 3);
             recyclerView.setLayoutManager(mLayoutManager);
          //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mProgressDialog.dismiss();
        }
    }



}

