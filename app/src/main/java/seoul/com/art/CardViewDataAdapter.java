package seoul.com.art;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gold24park on 15. 9. 20..
 */
public class CardViewDataAdapter extends RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {

    static List<Painting> list_painting;
    Context mContext;

    public CardViewDataAdapter(List<Painting> paintings) {
        this.list_painting = paintings;
    }

    // Create new views
    @Override
    public CardViewDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item, null);

        // create ViewHolder
        mContext = parent.getContext();
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Painting list = list_painting.get(position);
        viewHolder.txt_title.setText(list.getTitle());
        viewHolder.txt_name.setText(list.getName());

        Glide.with(mContext).load(list.getBm()).centerCrop().into(viewHolder.iv_img);
        viewHolder.txt_name.setTypeface(CardViewActivity.typeface);
        viewHolder.txt_title.setTypeface(CardViewActivity.typeface);
    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return list_painting.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_title;
        public TextView txt_name;
        public ImageView iv_img;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);

            txt_title = (TextView) itemLayoutView.findViewById(R.id.txt_title);
            txt_name = (TextView) itemLayoutView.findViewById(R.id.txt_name);
            iv_img = (ImageView) itemLayoutView.findViewById(R.id.iv_img);

            // Onclick event for the row to show the data in toast
            itemLayoutView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent i = new Intent(itemLayoutView.getContext(), AppreciateActivity.class);
                    i.putExtra("img", list_painting.get(getPosition()).getImg());
                    i.putExtra("title", list_painting.get(getPosition()).getTitle());
                    i.putExtra("title_en", list_painting.get(getPosition()).getTitle_en());
                    i.putExtra("name", list_painting.get(getPosition()).getName());
                    i.putExtra("name_en", list_painting.get(getPosition()).getName_en());
                    i.putExtra("category", list_painting.get(getPosition()).getCategory());
                    i.putExtra("size", list_painting.get(getPosition()).getSize());
                    i.putExtra("date", list_painting.get(getPosition()).getDate());
                    i.putExtra("desc", list_painting.get(getPosition()).getDesc());
                    i.putExtra("material", list_painting.get(getPosition()).getMaterial());
                    itemLayoutView.getContext().startActivity(i);

                }
            });

        }

    }
}
