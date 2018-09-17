package evilkingmedia.cueserve.com.evilkingmedia.Cartoon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.SportsActivityServer2;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListAdapter;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListAdapterMovie2;
import evilkingmedia.cueserve.com.evilkingmedia.adapter.BindListAdapterServer2;
import evilkingmedia.cueserve.com.evilkingmedia.film.WebViewActivity;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class CartoonServer1Adapter extends RecyclerView.Adapter<CartoonServer1Adapter.myview> implements Filterable {
    private List<MoviesModel> movielistFiltered;
    private List<MoviesModel> moviesList;
    Context context;
    private ProgressDialog mProgressDialog;
    String videoPath;
    private int itemposition;
    BindListAdapter adapter;
    private ArrayList<String> urlArrayList = new ArrayList<>();
    private boolean isSubcategory = false;

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
            imgcontent.getLayoutParams().height = 400;

        }
    }

    public CartoonServer1Adapter(List<MoviesModel> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
        this.movielistFiltered = moviesList;
    }

    @NonNull
    @Override
    public CartoonServer1Adapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_list, parent, false);

        return new CartoonServer1Adapter.myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartoonServer1Adapter.myview holder, final int position) {

        final MoviesModel movie = moviesList.get(position);

        if (movie.getImage()!=null) {
            holder.imgcontent.setVisibility(View.VISIBLE);
            Picasso.with(context).load(movie.getImage()).into(holder.imgcontent);
            //Picasso.with(context).load(R.drawable.ic_image).into(holder.imgcontent);
        } else {
            holder.imgcontent.setVisibility(View.GONE);

        }
        holder.txtMovieTitle.setGravity(View.TEXT_ALIGNMENT_CENTER);
        holder.txtMovieTitle.setText(movie.getTitle());
       /* holder.txtMovieRating.setText(movie.getRating());
        holder.txtMovieYear.setText(movie.getYear());
        holder.txtMovieDuration.setText(movie.getDuration());*/

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    itemposition = position;
                    new  prepareMovieData().execute();

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
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {


                Document doc = Jsoup.connect(moviesList.get(itemposition).getUrl()).timeout(10000).maxBodySize(0).get();


                    moviesList.clear();
                    isSubcategory = true;
                    Elements data = doc.select("div[class=container]").select("table[class=table-ep]");
                    Elements td_title = data.select("td[class=td-numero]");
                    Elements td_url = data.select("td[class=td-link]");
                /*for (int j=0;j<td_url.size();j++)
                {

                }*/
                    for (int i = 0; i < td_url.size(); i++) {
                        String title = null;
                        if(td_title.size()>0) {
                            title = td_title.get(i).text();
                        }
                        String url = td_url.get(i).select("a").get(1).attr("href");
                        MoviesModel movie = new MoviesModel();

                        movie.setUrl(url);
                        movie.setTitle(title);
                        moviesList.add(movie);
                    }



            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(mProgressDialog!=null)
            {
                mProgressDialog.dismiss();
            }


                Intent i = new Intent(context, CartoonActivitySubServer1.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("urldata", (Serializable) moviesList);
                i.putExtras(bundle);
                context.startActivity(i);

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
