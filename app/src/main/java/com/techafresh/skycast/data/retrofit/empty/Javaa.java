package com.techafresh.skycast.data.retrofit.empty;

import java.util.Random;

public class Javaa {
    Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    });

    Random rand = new Random();
    int ff = rand.nextInt();

}
