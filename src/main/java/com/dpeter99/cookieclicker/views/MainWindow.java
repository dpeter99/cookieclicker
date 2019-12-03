package com.dpeter99.cookieclicker.views;

import com.dpeter99.cookieclicker.model.GameModel;
import com.dpeter99.cookieclicker.util.Observer;

import javax.swing.*;
import java.awt.*;

/**
 * The main window of the application
 * This is what hosts the MainView Component that contains all sub-parts
 */
public class MainWindow extends JFrame  {

    MainView main;

    GameModel model;


    public MainWindow(GameModel m) throws HeadlessException {
        this.model = m;

        ToolTipManager.sharedInstance().setInitialDelay(100);

        main = new MainView(model);
        this.add(main.getContent());


        this.pack();
        //this.setSize(new Dimension(0,500));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }



}
