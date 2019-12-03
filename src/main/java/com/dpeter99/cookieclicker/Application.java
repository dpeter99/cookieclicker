package com.dpeter99.cookieclicker;

import com.dpeter99.cookieclicker.GameThread;
import com.dpeter99.cookieclicker.model.GameModel;
import com.dpeter99.cookieclicker.views.MainWindow;

import javax.swing.*;

public class Application {



    public static void main(String[] args) {
        Resources r = new Resources();

        GameModel game = GameModel.openFromFile("save.json");
        //game.AddBuilding("Cursor",2);


        GameThread thread = new GameThread(game);

        thread.start();

        synchronized (game){
            MainWindow w = new MainWindow(game);
            w.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

    }

}