package com.dpeter99.cookieclicker.components;

import com.dpeter99.cookieclicker.model.Building;
import com.dpeter99.cookieclicker.model.GameModel;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * The list of all the buildings
 * This is a custom list implementation that handles the displaying of <code>BuildingListElement</code>
 * instances for each building that is in the game
 */
public class BuildingsList {

    List<BuildingListElement> list = new LinkedList<>();

    GameModel model;

    JPanel panel;
    public JPanel content;
    private JScrollPane scroll;

    /**
     * Constructor for the building list
     *
     *
     * @param model The game we are getting the buildings form
     */
    public BuildingsList(GameModel model) {
        this.model = model;

        for (Building b : model.getBuildings().values()) {
            BuildingListElement e = new BuildingListElement(b, model);
            list.add(e);
            panel.add(e.getContent());
        }

        panel.setMaximumSize(panel.getPreferredSize());

        content.updateUI();
    }


    /**
     * Creates the custom components
     */
    private void createUIComponents() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        scroll =  new JScrollPane(panel);


    }
}
