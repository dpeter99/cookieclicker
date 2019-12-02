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

public class MainView{

    private JPanel content;
    private CookieButton cookieButton;
    private BuildingsList buildingsList;
    private UpgradesPanel upgradesPanel;

    private JMenuBar menubar;

    public JPanel getContent() {
        return content;
    }

    GameModel model;

    public MainView(GameModel model) {
        this.model = model;

        menubar = new JMenuBar();
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (model) {
                    model.SaveToFile();
                }
            }
        });

        menubar.add(save);

        content.add(save, BorderLayout.NORTH);

        content.updateUI();
    }

    void createUIComponents(){
        cookieButton = new CookieButton(model);
        buildingsList = new BuildingsList(model);
        upgradesPanel = new UpgradesPanel(model);
        //cookieButton.setSize(200,200);
    }
}
