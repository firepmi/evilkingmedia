
package evilkingmedia.cueserve.com.evilkingmedia.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.Meteo.MeteoActivity;
import evilkingmedia.cueserve.com.evilkingmedia.Meteo.VideoPlayer;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.model.MeteoModel;
import evilkingmedia.cueserve.com.evilkingmedia.model.MoviesModel;
import evilkingmedia.cueserve.com.evilkingmedia.series.SeriesActivityServer2;


public class MeteoAdapter extends RecyclerView.Adapter<MeteoAdapter.myview> {

    private List<String> moviesList;
    Context context;
    private ProgressDialog mProgressDialog;
    String videoPath;
    private int itemposition;
    BindListAdapter adapter;
    private List<MeteoModel> meteoList;

    public MeteoAdapter(List<MeteoModel> meteolist, MeteoActivity meteoActivity) {
        this.meteoList=meteolist;
        this.context=meteoActivity;
    }


    public class myview extends RecyclerView.ViewHolder {

        private CardView card_view;
        private ImageView imgcontent;
        private TextView txtMovieTitle, txtMovieRating, txtMovieYear, txtMovieDuration;
        private View view1, view2;

        public myview(View view) {
            super(view);
            card_view = view.findViewById(R.id.card_view);
            imgcontent = view.findViewById(R.id.imgcontent);
            txtMovieTitle = view.findViewById(R.id.txtMovieTitle);
            txtMovieRating = view.findViewById(R.id.txtMovieRating);
            txtMovieYear = view.findViewById(R.id.txtMovieYear);
            txtMovieDuration = view.findViewById(R.id.txtMovieDuration);
            view1 = view.findViewById(R.id.view1);
            view2 = view.findViewById(R.id.view2);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            txtMovieDuration.setVisibility(View.GONE);
            txtMovieRating.setVisibility(View.GONE);
            txtMovieYear.setVisibility(View.GONE);
        }
    }


    @NonNull
    @Override
    public MeteoAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_list, parent, false);

        return new myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeteoAdapter.myview holder, final int position) {

        final MeteoModel meteo = meteoList.get(position);


        //Picasso.with(context).load(movie.getImage()).into(holder.imgcontent);
        holder.txtMovieTitle.setText(meteoList.get(position).getTitle());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemposition = position;

                if(!appInstalledOrNot("co.wuffy.player")){
                    Toast.makeText(context, "Installed Wuffy Player To Play This Video",
                            Toast.LENGTH_LONG).show();
                }else{
                    Bundle bnd = new Bundle();
                    bnd.putString("path",meteoList.get(position).getUrl() );
                    bnd.putString("name", meteoList.get(position).getTitle());
                    Intent intent = new Intent();
                    intent.setClassName("co.wuffy.player", "org.wuffy.videoplayer.WuffyPlayer");
                    intent.putExtras(bnd);
                    context.startActivity(intent);
                }


              /*  Intent i = new Intent(context, VideoPlayer.class);
                i.putExtra("urldata", meteoList.get(position).getUrl());
                context.startActivity(i);*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return meteoList.size();
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }


}
