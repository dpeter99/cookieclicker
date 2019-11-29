package com.dpeter99.cookieclicker.components;

import com.dpeter99.cookieclicker.model.Building;
import com.dpeter99.cookieclicker.model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class BuildingsList {

    List<BuildingListElement> list = new LinkedList<>();

    GameModel model;

    JPanel panel;
    public JPanel content;
    private JScrollPane scroll;

    public BuildingsList(GameModel model) {
        this.model = model;

        for (Building b : model.getBuildings().values()) {
            BuildingListElement e = new BuildingListElement(this, b, model);
            list.add(e);
            panel.add(e.getContent());
        }

        panel.setMaximumSize(panel.getPreferredSize());

        content.updateUI();
    }


    private void createUIComponents() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        scroll =  new JScrollPane(panel);


    }
}
