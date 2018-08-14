package evilkingmedia.cueserve.com.evilkingmedia.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.film.WebViewActivity;
import evilkingmedia.cueserve.com.evilkingmedia.film.WebViewActivityServer3;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class BindListAdapterServer4 extends RecyclerView.Adapter<BindListAdapterServer4.myview> implements Filterable {
    private List<MoviesModel> movielistFiltered;
    private List<MoviesModel> moviesList;
    Context context;
    private ProgressDialog mProgressDialog;
    String videoPath;
    private int itemposition;
    BindListAdapter adapter;


    public class myview extends RecyclerView.ViewHolder {

        private CardView card_view;
        private ImageView imgcontent;
        private TextView txtMovieTitle, txtMovieRating, txtMovieYear, txtMovieDuration;
        View view1, view2;

        public myview(View view) {
            super(view);
            card_view = view.findViewById(R.id.card_view);
            imgcontent = view.findViewById(R.id.imgcontent);
            txtMovieTitle = view.findViewById(R.id.txtMovieTitle);
            view1 = view.findViewById(R.id.view1);
            view2 = view.findViewById(R.id.view2);
            txtMovieRating = view.findViewById(R.id.txtMovieRating);
            txtMovieYear = view.findViewById(R.id.txtMovieYear);
            txtMovieDuration = view.findViewById(R.id.txtMovieDuration);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            txtMovieRating.setVisibility(View.GONE);
            txtMovieYear.setVisibility(View.GONE);
            txtMovieDuration.setVisibility(View.GONE);

        }
    }

    public BindListAdapterServer4(List<MoviesModel> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
        this.movielistFiltered = moviesList;
    }

    @NonNull
    @Override
    public BindListAdapterServer4.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_list, parent, false);

        return new myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BindListAdapterServer4.myview holder, final int position) {

        final MoviesModel movie = moviesList.get(position);

        if (movie.getImage() == "") {
            holder.imgcontent.setImageResource(R.color.colorWhite);
            //Picasso.with(context).load(R.drawable.ic_image).into(holder.imgcontent);
        } else {
            Picasso.with(context).load(movie.getImage()).into(holder.imgcontent);
        }

        holder.txtMovieTitle.setText(movie.getTitle());
       /* holder.txtMovieRating.setText(movie.getRating());
        holder.txtMovieYear.setText(movie.getYear());
        holder.txtMovieDuration.setText(movie.getDuration());*/

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent webIntent = new Intent(context, WebViewActivity.class);
                webIntent.putExtra("url", "https://speedvideo.net/embed-nalqx7svhsmq-607x360.html");
                context.startActivity(webIntent);*/
                itemposition = position;
                new prepareMovieData().execute();

            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private class prepareMovieData extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {


                Document doc = Jsoup.connect(moviesList.get(itemposition).getUrl()).timeout(10000).maxBodySize(0).get();

                Elements iframe = doc.getElementsByTag("iframe");
                String src = iframe.attr("src");

                Log.e("body", src);

                videoPath =  src;







                /*  Document ibody = Jsoup.parseBodyFragment(html);*/

             /*   Document docbody = Jsoup.connect("https://megadrive.co/embed/3gdselmarhke").timeout(6000000).maxBodySize(0)
                        .userAgent("Mozilla/5.0 (Windows NT 6.2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.69 Safari/537.36").ignoreContentType(true).ignoreHttpErrors(true).followRedirects(true).get();*/

               /* for( Element element : docbody.select("meta,script") )
                {
                    element.remove();
                    Log.e("body",element+"");
                }*/
                /* Element ibody=docbody.select("meta").first().remove();*/

                //Elements vurl = docbody.select("meta[property=og:video]");

               /* for (Element metaTag : vurl) {
                    String content = metaTag.attr("content");
                    Log.e("contents", content);
                }*/
                //Log.e("maindata", vurl + "");
                /*for(int i=0; i < iframe.size();i++){
                    Log.e("iframe",iframe+"");
                }*/


               /* videoPath=doc.select("iframe").attr("src");
                Log.e("video",videoPath);*/
                /*Elements elements = doc.select("#collapse1 ul.host > a");
                Elements videoUrl = elements.get(0).getElementsByTag("a");
                videoPath = videoUrl.attr("data-link");*/


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView


            //In app
           /*  Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(videoPath+".mp4"), "video/*");
            context.startActivity(intent);*/

            //Open video in browser
            /* Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://oload.site/embed/Ezw7lhzGxB4/"));
            context. startActivity(browserIntent);*/
            Intent webIntent = new Intent(context, WebViewActivity.class);
            webIntent.putExtra("url", videoPath);
            context.startActivity(webIntent);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.equals("")) {
                    moviesList = movielistFiltered;
                } else {
                    List<MoviesModel> filteredList = new ArrayList<>();
                    for (MoviesModel row : movielistFiltered) {
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    moviesList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = moviesList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                moviesList = (ArrayList<MoviesModel>) filterResults.values;
                notifyDataSetChanged();


            }
        };
    }


}
