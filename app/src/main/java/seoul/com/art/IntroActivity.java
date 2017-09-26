package seoul.com.art;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gold24park on 15. 9. 20..
 * 이 액티비티는 시작 화면을 표시하고 JSON 문서를 읽어 데이터를 다음 액티비티(CardViewActivity)로 넘깁니다.
 */
public class IntroActivity extends Activity {
    Handler h;//핸들러 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //인트로화면이므로 타이틀바를 없앤다
        setContentView(R.layout.activity_intro);

        h= new Handler(); //딜래이를 주기 위해 핸들러 생성
        h.postDelayed(mrun, 2000); // 딜레이 ( 런어블 객체는 mrun, 시간 2초)
    }

    Runnable mrun = new Runnable(){
        @Override
        public void run(){


            //START NEXT ACTIVITY
            Intent i = new Intent(IntroActivity.this, CardViewActivity.class); //인텐트 생성(현 액티비티, 새로 실행할 액티비티)
            //i.putExtra("paintingsList", paintingsList); //만들어진 ArrayList를 다음 액티비티로 전송
            startActivity(i);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            //overridePendingTransition 이란 함수를 이용하여 fade in,out 효과를줌. 순서가 중요
        }
    };
    //인트로 중에 뒤로가기를 누를 경우 핸들러를 끊어버려 아무일 없게 만드는 부분
    //미 설정시 인트로 중 뒤로가기를 누르면 인트로 후에 홈화면이 나옴.
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        h.removeCallbacks(mrun);
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
