package com.dpeter99.cookieclicker;

import com.dpeter99.cookieclicker.model.GameModel;

/**
 * The thread that updates the game in a continuous loop.
 */
public class GameThread extends Thread {

    GameModel model;

    boolean running;

    public GameThread(GameModel model) {
        this.model = model;
        this.running = false;
    }

    void Stop(){
        running = false;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        running = true;

        long  lastTime = System.currentTimeMillis();
        float currentDeltaTime = 0;

        while(running){
            long currentTime = System.currentTimeMillis();
            currentDeltaTime = (currentTime - lastTime)/1000.0f;
            lastTime = currentTime;

            synchronized (model) {
                model.Update(currentDeltaTime);
                //System.out.println(model.GetCookieCount() + "\t CpS: " + model.getCpS());
            }

            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
