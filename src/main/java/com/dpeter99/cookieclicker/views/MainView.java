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

public class MainView{

    private JPanel content;
    private CookieButton cookieButton;
    private BuildingsList buildingsList;
    private UpgradesPanel upgradesPanel;

    public JPanel getContent() {
        return content;
    }

    GameModel model;

    public MainView(GameModel model) {
        this.model = model;
        //model.registerObserver(this);
        //this.a.setSize(100,-1);
        //this.a.setPreferredSize(new Dimension(100,-1));
        content.updateUI();
    }

    void createUIComponents(){
        cookieButton = new CookieButton(model);
        buildingsList = new BuildingsList(model);
        upgradesPanel = new UpgradesPanel(model);
        //cookieButton.setSize(200,200);
    }
}
