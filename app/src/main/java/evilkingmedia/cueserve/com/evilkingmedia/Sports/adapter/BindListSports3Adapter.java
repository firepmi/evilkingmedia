package evilkingmedia.cueserve.com.evilkingmedia.Sports.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import evilkingmedia.cueserve.com.evilkingmedia.Constant;
import evilkingmedia.cueserve.com.evilkingmedia.R;
import evilkingmedia.cueserve.com.evilkingmedia.Sports.WebViewActivitySports3;
import evilkingmedia.cueserve.com.evilkingmedia.model.SportsModel;

public class BindListSports3Adapter extends RecyclerView.Adapter<BindListSports3Adapter.myview> {
    private List<SportsModel> sportsModelUrlList;
    private List<SportsModel> sportsModelList;
    Context context;
    String videoPath;
    private int itemposition;


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
            txtMovieYear.setTextSize(14);
            txtMovieDuration = view.findViewById(R.id.txtMovieDuration);
            view1 = view.findViewById(R.id.view1);
            view2 = view.findViewById(R.id.view2);
            txtMovieRating.setVisibility(View.GONE);
            txtMovieDuration.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
        }
    }

    public BindListSports3Adapter(List<SportsModel> sportsModelList, Context context, List<SportsModel> urlList) {
        this.sportsModelList = sportsModelList;
        this.context = context;
        this.sportsModelUrlList = urlList;
    }

    @NonNull
    @Override
    public BindListSports3Adapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_list, parent, false);

        return new BindListSports3Adapter.myview(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BindListSports3Adapter.myview holder, final int position) {

        final SportsModel sportsModel = sportsModelList.get(position);

        holder.txtMovieTitle.setText(sportsModel.getTime());
        holder.txtMovieYear.setText(sportsModel.getTitle());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemposition = position;
               /* Intent i = new Intent(context, SportsActivityServer3.class);
                i.putExtra("url", sportsModelList.get(position).getUrl());
                context.startActivity(i);
*/
                new prepareSportsData().execute();
            }
        });

    }

    private class prepareSportsData extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                String url = Constant.SPORTSURL3 + "/" + sportsModelList.get(itemposition).getUrl();

                Document doc = Jsoup.connect(url).timeout(10000).maxBodySize(0).get();

                Elements data = doc.select("tr[class=sectiontableentry2]").first().getElementsByTag("td").select("a");
                videoPath = data.attr("href");
                // System.out.print(data);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Intent webIntent = new Intent(context, WebViewActivitySports3.class);
            webIntent.putExtra("url", videoPath);
            context.startActivity(webIntent);
        }
    }

    @Override
    public int getItemCount() {
        return sportsModelList.size();
    }

}
