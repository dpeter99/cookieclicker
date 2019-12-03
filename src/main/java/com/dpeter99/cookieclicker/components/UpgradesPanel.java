package com.dpeter99.cookieclicker.components;

import com.dpeter99.cookieclicker.model.Building;
import com.dpeter99.cookieclicker.model.GameModel;
import com.dpeter99.cookieclicker.model.Upgrade;
import com.dpeter99.cookieclicker.util.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The panel holding all the upgrades in a flow panel
 *
 * This is responsible for displaying the currently available upgrades.
 */
public class UpgradesPanel implements Runnable , Observer {
    public JPanel getContent() {
        return content;
    }

    private JPanel content;
    private JTabbedPane tabbedPane1;

    private JPanel buyPanel;
    private Map<Upgrade,UpgradeButton> mappedList = new HashMap<>();


    GameModel game;


    /**
     * Constructor for this class
     * It makes a list of the currently available upgrades.
     *
     * It also registers itself as a Observer for the <code>building</code> so it can update the display
     *
     * @param game The game we are representing
     */
    public UpgradesPanel(GameModel game) {
        this.game = game;
        game.registerObserver(this);


        buyPanel.setMaximumSize(buyPanel.getPreferredSize());

        for (Upgrade b : game.getUpgrades().values()) {
            if(b.isAwailable() && !b.isBought()) {
                UpgradeButton icon = new UpgradeButton(this, b);
                mappedList.put(b,icon);
                buyPanel.add(icon.getContent());
            }
        }

        content.updateUI();
        //tabbedPane1.updateUI();
    }

    private void createUIComponents() {
        buyPanel = new JPanel();
        FlowLayout asd = new FlowLayout(FlowLayout.LEFT);
        buyPanel.setLayout(asd);


        buyPanel.setPreferredSize(new Dimension(400,-1));
    }

    /**
     * Removes the UpgradeButton form the list
     * @param i The button to remove
     */
    public void buyItem(UpgradeButton i){
        buyPanel.remove(i.getContent());
        mappedList.remove(i);
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

    @Override
    public synchronized void onObservableChanged() {
        for (Upgrade b : game.getUpgrades().values()) {
            if(b.isAwailable() && !b.isBought() && !mappedList.containsKey(b)) {
                UpgradeButton icon = new UpgradeButton(this, b);
                mappedList.put(b,icon);
                buyPanel.add(icon.getContent());
            }
        }
    }
}
