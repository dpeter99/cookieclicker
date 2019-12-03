package com.dpeter99.cookieclicker.views;

import com.dpeter99.cookieclicker.Resources;
import com.dpeter99.cookieclicker.components.BuildingsList;
import com.dpeter99.cookieclicker.components.CookieButton;
import com.dpeter99.cookieclicker.components.UpgradesPanel;
import com.dpeter99.cookieclicker.model.Building;
import com.dpeter99.cookieclicker.model.GameModel;
import com.dpeter99.cookieclicker.util.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * The main view of the app.
 * It contains 3 sub parts in a table.
 * Those parts are the:
 *  - Cookie button
 *  - Upgrade list
 *  - Building list
 *
 *  And it contains the menu bar as well
 */
public class MainView{

    /**
     * The root of this view
     */
    private JPanel content;
    private CookieButton cookieButton;
    private BuildingsList buildingsList;
    private UpgradesPanel upgradesPanel;

    private JMenuBar menubar;

    public JPanel getContent() {
        return content;
    }

    GameModel model;

    /**
     * Construct the view, creates all non generated parts of the UI (MenuBars)
     * @param model The game we are displaying
     */
    public MainView(GameModel model) {
        this.model = model;

        menubar = new JMenuBar();
        JMenu game = new JMenu("Game");
        menubar.add(game);

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (model) {
                    model.SaveToFile();
                }
            }
        });

        game.add(save);



        content.add(menubar, BorderLayout.NORTH);

        content.updateUI();
    }

    void createUIComponents(){
        cookieButton = new CookieButton(model);
        buildingsList = new BuildingsList(model);
        upgradesPanel = new UpgradesPanel(model);
        //cookieButton.setSize(200,200);
    }
}
