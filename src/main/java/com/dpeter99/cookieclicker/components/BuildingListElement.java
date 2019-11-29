package com.dpeter99.cookieclicker.components;

import com.dpeter99.cookieclicker.Resources;
import com.dpeter99.cookieclicker.model.Building;
import com.dpeter99.cookieclicker.model.GameModel;
import com.dpeter99.cookieclicker.util.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

class BuildingListElement implements Observer, Runnable {
    private BuildingsList buildingsList;

    Building building;
    GameModel game;

    private JButton buy;
    private JLabel name;

    public JPanel getContent() {
        return content;
    }

    private JPanel content;
    private JLabel count;
    private JLabel cost;
    private JLabel CpSLabel;
    private JLabel total;


    public BuildingListElement(BuildingsList buildingsList, Building building, GameModel game) {
        this.buildingsList = buildingsList;

        this.building = building;
        this.game = game;

        building.registerObserver(this);

        buy.addActionListener(new BuyBtnClicked());
        count.setText("0");
        name.setText(building.getDisplayName());
        cost.setText(building.getCostDisplay());
        CpSLabel.setText(Resources.Instance.getFormatedString("building.production", building.getCpS().toString()));
        total.setText(Resources.Instance.getFormatedString("building.total", building.getTatalProduction().toBigInteger().toString()));
        buy.setEnabled(building.getCanBuy());

        content.setPreferredSize(new Dimension(400,80));
        content.setMaximumSize(content.getPreferredSize());
        content.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    public void onObservableChanged() {
        count.setText(Integer.toString(building.getCount()));
        cost.setText(building.getCostDisplay());
        CpSLabel.setText(Resources.Instance.getFormatedString("building.production", building.getCpS().toString()));
        total.setText(Resources.Instance.getFormatedString("building.total", building.getTatalProduction().toBigInteger().toString()));
        buy.setEnabled(building.getCanBuy());

        SwingUtilities.invokeLater(this);
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
        content.updateUI();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private class BuyBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            game.BuyBuilding(building.getRegistryName());

        }
    }
}
