package cn.etouch.ecalendar.tools.tag;

import android.app.Activity;
import android.os.Bundle;

import java.util.Iterator;
import java.util.List;

import cn.etouch.ecalendar.bean.aq;

public class ChannelManageActivity extends Activity {


    public List<aq> t;


    String a = "中华万年历";
    String b = "中华老黄历";


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settt();
    }


    void settt() {

        Iterator<aq> it = t.iterator();

        while (it.hasNext()) {

            aq x = it.next();

            if (x.b.equals("体育")
                    || x.b.equals("军事")
                    || x.b.equals("国际")
                    || x.b.equals("社会")) {
                it.remove();
            }

        }
    }


}
