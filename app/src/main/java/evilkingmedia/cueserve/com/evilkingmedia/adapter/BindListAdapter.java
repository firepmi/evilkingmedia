package evilkingmedia.cueserve.com.evilkingmedia.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class BindListAdapter extends RecyclerView.Adapter<BindListAdapter.myview> {

    private List<MoviesModel> moviesList;
    Context context;

    public class myview extends RecyclerView.ViewHolder {

        private ImageView imgcontent;
        private TextView txtMovieTitle, txtMovieRating, txtMovieYear, txtMovieDuration;

        public myview(View view) {
            super(view);
            imgcontent = view.findViewById(R.id.imgcontent);
            txtMovieTitle = view.findViewById(R.id.txtMovieTitle);
           txtMovieRating = view.findViewById(R.id.txtMovieRating);
            txtMovieYear = view.findViewById(R.id.txtMovieYear);
            txtMovieDuration = view.findViewById(R.id.txtMovieDuration);
        }
    }

    public BindListAdapter(List<MoviesModel> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @NonNull
    @Override
    public BindListAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_list, parent, false);

        return new myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BindListAdapter.myview holder, int position) {

        MoviesModel movie = moviesList.get(position);

        Picasso.with(context).load(Constant.MOVIEURL+movie.getImage()).into(holder.imgcontent);
        holder.txtMovieTitle.setText(movie.getTitle());
        holder.txtMovieRating.setText(movie.getRating());
        holder.txtMovieYear.setText(movie.getYear());
        holder.txtMovieDuration.setText(movie.getDuration());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
