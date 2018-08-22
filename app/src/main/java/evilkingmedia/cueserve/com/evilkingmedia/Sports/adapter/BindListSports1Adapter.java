package evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.SportsActivityCatServer1;
import evilkingmedia.cueserve.com.evilkingmedia.film.WebViewActivity;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class BindListSports1Adapter extends RecyclerView.Adapter<BindListSports1Adapter.myview>  {
    private List<SportsModel> sportsModelUrlList;
    private List<SportsModel> sportsModelList;
    Context context;
    String videoPath;
    private int itemposition;
    private Map<String, String> integerStringMap = new HashMap<String, String>();


    public class myview extends RecyclerView.ViewHolder {

        private CardView card_view;
        private TextView txtDate, txtTime;
        private View view1,view2;
        private LinearLayout ll_bottom;

        public myview(View view) {
            super(view);
            card_view = view.findViewById(R.id.card_view);
            txtDate = view.findViewById(R.id.txtMovieTitle);
            txtTime = view.findViewById(R.id.txtMovieRating);
            view1 = view.findViewById(R.id.view1);
            view2 = view.findViewById(R.id.view2);
            ll_bottom = view.findViewById(R.id.ll_bottom);
        }
    }

    public BindListSports1Adapter(List<SportsModel> sportsModelList, Context context, List<SportsModel> urlList) {
        this.sportsModelList = sportsModelList;
        this.context = context;
        this.sportsModelUrlList = urlList;
    }

    @NonNull
    @Override
    public BindListSports1Adapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_list, parent, false);

        return new BindListSports1Adapter.myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BindListSports1Adapter.myview holder, final int position) {

        final SportsModel sportsModel = sportsModelList.get(position);

        holder.view1.setVisibility(View.GONE);
        holder.view2.setVisibility(View.GONE);
        if(sportsModelList.get(position).getTime() == null)
        {
            holder.txtDate.setText(sportsModel.getTeam1());
          //  holder.txtTime.setText(sportsModel.getDate());

        }
        else
        {
            holder.txtDate.setText(sportsModel.getDate());
            holder.txtTime.setText(sportsModel.getTime());
            holder.ll_bottom.setGravity(Gravity.CENTER);

        }

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemposition = position;

                if(sportsModelList.get(position).getTime() == null )
                {
                    new  prepareSportsUrl(sportsModelList.get(position).getUrl()).execute();



                }
                else {
                    Intent sportCategoryIntent = new Intent(context, SportsActivityCatServer1.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("urldata", (Serializable) sportsModelUrlList);
                    sportCategoryIntent.putExtras(bundle);
                    sportCategoryIntent.putExtra("position", position);
                    context.startActivity(sportCategoryIntent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return sportsModelList.size();
    }

    private class prepareSportsUrl extends AsyncTask<String, Void, Void> {
        String mainurl;
        String url;
        public prepareSportsUrl(String mainurl) {

            this.mainurl = mainurl;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... strings) {

            //Movie1
           try {
                Document doc  = Jsoup.connect(mainurl).timeout(10000).get();
                //For Categories
                Elements container = doc.select("center");
                Elements iframe = container.select("iframe");
                url = iframe.attr("src");

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
        @Override
        protected void onPostExecute (Void result){
            // Set description into TextView

            Intent webIntent = new Intent(context, WebViewActivity.class);
            webIntent.putExtra("url", url);
            context.startActivity(webIntent);





        }

    }
}



   /* @Override
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
*/
