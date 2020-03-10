package com.doudoubird.weather.view;

import android.widget.ImageView;

public class LooperRunnable implements Runnable {

    private ImageView ad;
    private int s1;
    private int s2;

    private int postion = 0;

    public LooperRunnable(ImageView ad, int s1, int s2) {
        this.ad = ad;
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public void run() {

        while (true) {

            if (postion == 0) {
                ad.post(new Runnable() {
                    @Override
                    public void run() {
                        ad.setImageResource(s2);
                        postion = 1;
                    }
                });
            } else if (postion == 1) {
                ad.post(new Runnable() {
                    @Override
                    public void run() {
                        ad.setImageResource(s1);
                        postion = 0;
                    }
                });
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}
