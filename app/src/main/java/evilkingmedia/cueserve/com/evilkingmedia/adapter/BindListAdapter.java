package evilkingmedia.cueserve.com.evilkingmedia.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;

public class BindListAdapter extends RecyclerView.Adapter<BindListAdapter.myview> {

    private List<MoviesModel> moviesList;
    Context context;

    public class myview extends RecyclerView.ViewHolder {

        private ImageView imgcontent;

        public myview(View view) {
            super(view);
            imgcontent = view.findViewById(R.id.imgcontent);

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
                .inflate(R.layout.displaycontent, parent, false);

        return new myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BindListAdapter.myview holder, int position) {

        MoviesModel movie = moviesList.get(position);
        Picasso.with(context).load(movie.getImage()).into(holder.imgcontent);

    }

    @Override
    public int getItemCount() {
        Log.d("d", moviesList.size() + "");
        return moviesList.size();
    }
}
