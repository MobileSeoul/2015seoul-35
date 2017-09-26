package seoul.com.art;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gold24park on 15. 9. 20..
 */
public class Painting {
    String img;
    String title;
    String title_en;
    String name;
    String name_en;
    String category;
    String date;
    String size;
    String material;
    String desc;

    static Bitmap bm;

    Painting(String img, String title, String title_en, String name, String name_en, String category, String date, String width, String height, String material, String desc){
        this.img = img;
        this.title = title;
        this.title_en = title_en;
        this.name = name;
        this.name_en = name_en;
        this.category = category;
        this.date = "(" + date + ")";
        this.size = width + " x " + height;
        this.material = material;
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getName_en() {
        return name_en;
    }

    public String getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }

    public String getMaterial() {
        return material;
    }

    public String getSize() {
        return size;
    }

    public String getTitle_en() {
        return title_en;
    }

    public String getCategory() {
        return category;
    }

    public String getBm() {
        return img;
    }


}
