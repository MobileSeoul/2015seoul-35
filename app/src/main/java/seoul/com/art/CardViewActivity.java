package seoul.com.art;

import android.app.ActionBar;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by gold24park on 15. 9. 20..
 */
public class CardViewActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private CardViewDataAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private List<Painting> list_painting;

    private static final String TAG_TITLE_KR = "SUBJECT_K"; //한국어 제목
    private static final String TAG_TITLE_EN = "SUBJECT_E"; //영어 제목
    private static final String TAG_IMG = "MAIN_IMG"; //이미지 주소
    private static final String TAG_NAME_KR = "NAME_K"; //한국어 작가이름
    private static final String TAG_NAME_EN = "NAME_E"; //영어 작가이름
    private static final String TAG_DATE = "WORK_DATE"; //작품년도
    private static final String TAG_CATEGORY = "ART_CODE_NAME"; //분류
    private static final String TAG_WIDTH = "WIDTH"; //가로
    private static final String TAG_HEIGHT =  "DEPTH"; //높이
    private static final String TAG_MATERIAL = "MATERIAL_K"; //재료
    private static final String TAG_DESC = "DESC_K"; //설명
    JSONArray paintings = null;
    ArrayList<HashMap<String,String>> paintingsList;

    ProgressDialog pDialog;
    Context mContext;

    // on scroll

    private static int current_page = 1;

    private int ival = 0;
    private int loadLimit = 10;

    public final String TYPEFACE_NAME = "nanum.ttf";
    public static Typeface typeface;

    private void loadTypeface(){
        if(typeface==null)
            typeface = Typeface.createFromAsset(getAssets(), TYPEFACE_NAME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_activity);

        loadTypeface();

        /** 커스텀 액션바 **/
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.actionbar, null);
        actionBar.setCustomView(cView);


        list_painting = new ArrayList<Painting>();
        paintingsList = new ArrayList<HashMap<String, String>>();

        //AsyncTask 시작
        mContext = this;
        new LoadPaintingList().execute();

    }

    // By default, we add 10 objects for first time.
    private void loadData(int current_page) {

        for (int i = ival; i <= loadLimit; i++) {
            Painting pt = new Painting(
                    paintingsList.get(i).get(TAG_IMG),
                    paintingsList.get(i).get(TAG_TITLE_KR),
                    paintingsList.get(i).get(TAG_TITLE_EN),
                    paintingsList.get(i).get(TAG_NAME_KR),
                    paintingsList.get(i).get(TAG_NAME_EN),
                    paintingsList.get(i).get(TAG_CATEGORY),
                    paintingsList.get(i).get(TAG_DATE),
                    paintingsList.get(i).get(TAG_WIDTH),
                    paintingsList.get(i).get(TAG_HEIGHT),
                    paintingsList.get(i).get(TAG_MATERIAL),
                    paintingsList.get(i).get(TAG_DESC)
            );

            list_painting.add(pt);
            ival++;

        }

    }

    // adding 10 object creating dymically to arraylist and updating recyclerview when ever we reached last item
    private void loadMoreData(int current_page) {

        loadLimit = ival + 10;

        for (int i = ival; i <= loadLimit; i++) {
            Painting pt = new Painting(
                    paintingsList.get(i).get(TAG_IMG),
                    paintingsList.get(i).get(TAG_TITLE_KR),
                    paintingsList.get(i).get(TAG_TITLE_EN),
                    paintingsList.get(i).get(TAG_NAME_KR),
                    paintingsList.get(i).get(TAG_NAME_EN),
                    paintingsList.get(i).get(TAG_CATEGORY),
                    paintingsList.get(i).get(TAG_DATE),
                    paintingsList.get(i).get(TAG_WIDTH),
                    paintingsList.get(i).get(TAG_HEIGHT),
                    paintingsList.get(i).get(TAG_MATERIAL),
                    paintingsList.get(i).get(TAG_DESC)
            );

            list_painting.add(pt);
            ival++;
        }

        mAdapter.notifyDataSetChanged();

    }

/** ASYNC TASK : ArrayList 데이터를 생산합니다. **/

    class LoadPaintingList extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CardViewActivity.this);
            pDialog.setMessage(getString(R.string.info_loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
        protected String doInBackground(String... args) {
            try {
                JSONObject jsonObj = new JSONObject(loadJSON());

                // Getting JSON Array node
                paintings = jsonObj.getJSONArray("DATA");

                // looping through All Contacts
                for (int i = 0; i < paintings.length(); i++) {
                    JSONObject c = paintings.getJSONObject(i);

                    String title_kr = c.getString(TAG_TITLE_KR);
                    String title_en = c.getString(TAG_TITLE_EN);
                    String img = c.getString(TAG_IMG);
                    String name_kr = c.getString(TAG_NAME_KR);
                    String name_en = c.getString(TAG_NAME_EN);
                    String date = c.getString(TAG_DATE);
                    String category = c.getString(TAG_CATEGORY);
                    String width = c.getString(TAG_WIDTH);
                    String height = c.getString(TAG_HEIGHT);
                    String material = c.getString(TAG_MATERIAL);
                    String desc = c.getString(TAG_DESC);
                    desc = desc.replaceAll("&quot;","'");


                    // tmp hashmap for single contact
                    HashMap<String, String> painting = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    painting.put(TAG_TITLE_KR, title_kr);
                    painting.put(TAG_TITLE_EN, title_en);
                    painting.put(TAG_IMG, img);
                    painting.put(TAG_NAME_KR, name_kr);
                    painting.put(TAG_NAME_EN, name_en);
                    painting.put(TAG_DATE, date);
                    painting.put(TAG_CATEGORY, category);
                    painting.put(TAG_WIDTH, width);
                    painting.put(TAG_HEIGHT, height);
                    painting.put(TAG_MATERIAL, material);
                    painting.put(TAG_DESC, desc);


                    // adding contact to contact list
                    paintingsList.add(painting);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {

                    loadData(current_page);

                    mRecyclerView = (RecyclerView) findViewById(R.id.rv);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(CardViewActivity.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);

                    // create an Object for Adapter
                    mAdapter = new CardViewDataAdapter(list_painting);

                    // set the adapter object to the Recyclerview
                    mRecyclerView.setAdapter(mAdapter);

                    mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(
                            mLayoutManager) {
                        @Override
                        public void onLoadMore(int current_page) {
                            // do somthing...
                            loadMoreData(current_page);

                        }

                    });
                }
            });
        }
    }
    //Return JSON TEXT to String
    private String loadJSON() {

        String data = null;
        InputStream inputStream = getResources().openRawResource(R.raw.collection);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            data = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
            inputStream.close();

        }catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
