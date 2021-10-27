package cn.etouch.ecalendar.tools.life;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.fragment.app.Fragment;
import cn.etouch.ecalendar.tools.life.bean.g;

public class o extends Fragment {


    public List<g> m = new ArrayList();



    @Override
    public void onResume() {
        super.onResume();
        settt();
    }




    void settt(){

        Log.e("lyn","删除");


        Iterator<g> it = m.iterator();

        while (it.hasNext()) {

            g x = it.next();

            if (x.b.equals("体育")
                    || x.b.equals("军事")
                    || x.b.equals("国际")
                    || x.b.equals("社会")) {
                it.remove();
            }

        }


    }



}
