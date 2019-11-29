package com.dpeter99.cookieclicker.views;

import com.dpeter99.cookieclicker.model.GameModel;
import com.dpeter99.cookieclicker.util.Observer;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame  {

    MainView main;

    GameModel model;

    /**
     * Constructs a new frame that is initially invisible.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     *                           returns true.
     * @see GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @see JComponent#getDefaultLocale
     */
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
