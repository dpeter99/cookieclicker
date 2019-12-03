package com.dpeter99.cookieclicker.components;

import com.dpeter99.cookieclicker.Resources;
import com.dpeter99.cookieclicker.model.Building;
import com.dpeter99.cookieclicker.model.GameModel;
import com.dpeter99.cookieclicker.util.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class representing each row in the buildings list.
 * It manages the text displays based on the public function of buildings (like: getDisplayName())
 * It also calls the games buy method to buy a new building when i's button is pressed.
 */
public class BuildingListElement implements Observer, Runnable {

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


    /**
     * Constructor for this class
     * It gets the Building it represents and th GAme model in it's constructor
     *
     * In the constructor it assigns the values to the correct amounts and adds the button listeners
     *
     * It also registers itself as a Observer for the <code>building</code> so it can update the display
     *
     * @param building The Building this represents
     * @param game The game we are displaying
     */
    public BuildingListElement(Building building, GameModel game) {

        this.building = building;
        this.game = game;

        building.registerObserver(this);

        buy.addActionListener(new BuyBtnClicked());
        count.setText("0");
        name.setText(building.getDisplayName());
        cost.setText(building.getCostDisplay());
        CpSLabel.setText(Resources.Instance.getFormattedString("building.production", building.getCpS().toString()));
        total.setText(Resources.Instance.getFormattedString("building.total", building.getTotalProduction().toBigInteger().toString()));
        buy.setEnabled(building.getCanBuy());

        content.setPreferredSize(new Dimension(400,80));
        content.setMaximumSize(content.getPreferredSize());
        content.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Called when the building's properties changed.
     * Updates the values of the GUI and dispatches a task to update the actual gui by using
     * <code>SwingUtilities.invokeLater(this);</code>
     */
    @Override
    public void onObservableChanged() {
        count.setText(Integer.toString(building.getCount()));
        cost.setText(building.getCostDisplay());
        CpSLabel.setText(Resources.Instance.getFormattedString("building.production", building.getCpS().toString()));
        total.setText(Resources.Instance.getFormattedString("building.total", building.getTotalProduction().toBigInteger().toString()));
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

    private class BuyBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            synchronized (game) {
                game.buyBuilding(building.getRegistryName());
            }

        }
    }
}
