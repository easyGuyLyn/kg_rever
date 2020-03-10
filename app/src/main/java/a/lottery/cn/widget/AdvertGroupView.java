package a.lottery.cn.widget;

import a.lottery.cn.bean.Advert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;


import java.util.List;

public class AdvertGroupView extends LinearLayout {

    public static class AdvertGroup {
        private List<Advert> adverts;

        public AdvertGroup() {
        }

        public AdvertGroup(List<Advert> list) {
            this.adverts = list;
        }

        public List<Advert> getAdverts() {
            return this.adverts;
        }

        public void setAdverts(List<Advert> list) {
            this.adverts = list;
        }
    }

    public AdvertGroupView(Context context) {
        super(context);
        init();
    }

    public AdvertGroupView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public AdvertGroupView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    @RequiresApi(api = 21)
    public AdvertGroupView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    private void init() {
//        setOrientation(1);
//        setBackgroundResource(R.drawable.bg_s1_gold);
//        setDividerDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_h1_gold));
//        setShowDividers(2);
    }



    @SuppressLint("WrongConstant")
    public void setAdvertGroup(AdvertGroup advertGroup) {
        removeAllViews();
        if (advertGroup != null) {
            List adverts = advertGroup.getAdverts();
            if (adverts != null && adverts.size() > 0) {
                int i = adverts.size() - 1;
                while (i < adverts.size()) {
                    Advert advert = (Advert) adverts.get(i);
                    if (advert.getIsDisplay() == 1) {
                        LinearLayout linearLayout = new LinearLayout(getContext());
                        linearLayout.setOrientation(0);
                        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(), 2130837769));
                        linearLayout.setShowDividers(2);
                        AdvertView advertView = new AdvertView(getContext());
                        advertView.setAd(advert, 5);
                        LayoutParams layoutParams = new LayoutParams(0, -2);
                        layoutParams.weight = 1.0f;
                        linearLayout.addView(advertView, layoutParams);
                        if (i + 1 < adverts.size()) {
                            Advert advert2 = (Advert) adverts.get(i + 1);
                            if (advert2.getIsDisplay() == 1) {
                                AdvertView advertView2 = new AdvertView(getContext());
                                advertView2.setAd(advert2, 6);
                                LayoutParams layoutParams2 = new LayoutParams(0, -2);
                                layoutParams2.weight = 1.0f;
                                linearLayout.addView(advertView2, layoutParams2);
                                i++;
                            }
                        }
                        addView(linearLayout);
                    } else {
                        AdvertView advertView3 = new AdvertView(getContext());
                        advertView3.setAd(advert, 5);
                        addView(advertView3);
                    }
                    i++;
                }
            }
        }
    }


}