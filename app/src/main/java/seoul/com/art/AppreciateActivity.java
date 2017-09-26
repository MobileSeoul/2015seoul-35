package seoul.com.art;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by gold24park on 15. 9. 20..
 */
public class AppreciateActivity extends ActionBarActivity {

    ProgressDialog pDialog;
    Context mContext;

    String img, title, title_en, name, name_en, category, size, date, desc, material;
    TextView txt_title, txt_title_en, txt_name, txt_name_en, txt_desc, txt_date, txt_category, txt_size, txt_material, ind_name, ind_category, ind_material, ind_size;
    ImageView iv_img;
    Bitmap bm;

    private static final String TYPEFACE_NAME = "nanum.ttf";
    private Typeface typeface = null;

    private void loadTypeface() {
        if (typeface == null)
            typeface = Typeface.createFromAsset(getAssets(), TYPEFACE_NAME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view);

        /** 커스텀 액션바 **/
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.actionbar, null);
        actionBar.setCustomView(cView);

        mContext = getApplicationContext();
        new LoadPainting().execute();

    }

    /**
     * ASYNC TASK : ArrayList 데이터를 생산합니다.
     **/

    class LoadPainting extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AppreciateActivity.this);
            pDialog.setMessage(getString(R.string.info_loading_info));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {
            Intent i = getIntent();
            img = i.getExtras().getString("img");
            title = i.getExtras().getString("title");
            title_en = i.getExtras().getString("title_en");
            name = i.getExtras().getString("name");
            name_en = i.getExtras().getString("name_en");
            category = i.getExtras().getString("category");
            date = i.getExtras().getString("date");
            size = i.getExtras().getString("size");
            desc = i.getExtras().getString("desc");
            material = i.getExtras().getString("material");

            txt_title = (TextView) findViewById(R.id.txt_title);
            txt_title_en = (TextView) findViewById(R.id.txt_title_en);
            txt_name = (TextView) findViewById(R.id.txt_name_kr);
            txt_name_en = (TextView) findViewById(R.id.txt_name_en);
            txt_category = (TextView) findViewById(R.id.txt_category);
            txt_size = (TextView) findViewById(R.id.txt_size);
            txt_date = (TextView) findViewById(R.id.txt_date);
            txt_material = (TextView) findViewById(R.id.txt_material);
            txt_desc = (TextView) findViewById(R.id.txt_desc);

            ind_name = (TextView) findViewById(R.id.ind_name);
            ind_category = (TextView) findViewById(R.id.ind_category);
            ind_material = (TextView) findViewById(R.id.ind_material);
            ind_size = (TextView) findViewById(R.id.ind_size);


            iv_img = (ImageView) findViewById(R.id.iv_img);
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {

                    loadTypeface();

                    txt_title.setText(title);
                    txt_title_en.setText(title_en);
                    txt_name.setText(name);
                    txt_name_en.setText(name_en);
                    txt_category.setText(category);
                    txt_size.setText(size);
                    txt_date.setText(date);
                    txt_material.setText(material);
                    txt_desc.setText(desc);
                    iv_img.setImageBitmap(bm);

                    Glide.with(mContext).load(img).into(iv_img);

                    txt_title.setTypeface(typeface);
                    txt_title_en.setTypeface(typeface);
                    txt_name.setTypeface(typeface);
                    txt_name_en.setTypeface(typeface);
                    txt_category.setTypeface(typeface);
                    txt_size.setTypeface(typeface);
                    txt_date.setTypeface(typeface);
                    txt_material.setTypeface(typeface);
                    txt_desc.setTypeface(typeface);
                    ind_name.setTypeface(typeface);
                    ind_size.setTypeface(typeface);
                    ind_material.setTypeface(typeface);
                    ind_category.setTypeface(typeface);

                }
            });
        }
    }


    }
