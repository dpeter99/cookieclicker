package com.dpeter99.cookieclicker.components;

import com.dpeter99.cookieclicker.Resources;
import com.dpeter99.cookieclicker.model.Upgrade;
import com.dpeter99.cookieclicker.util.Observer;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The upgrade button that is displayed for each upgrade in the list.
 * It consist of a beveled center that is inside the root of the component
 * Inside that is a custom Icon component that can display the icon and uses the correct tooltip
 */
public class UpgradeButton implements Observer, Runnable {
    //private final GameModel game;

    public JPanel getContent() {
        return content;
    }

    private JPanel content;
    private JPanel center;
    private UpgradeIcon icon;

    Color bkg;

    UpgradesPanel parent;

    Upgrade upgrade;

    /**
     * The constructor for the upgrade button it cheats and sets up all the connections.
     *
     * @param upgradesPanel The panel this belongs to
     * @param b The upgrade this represents
     */
    public UpgradeButton(UpgradesPanel upgradesPanel, Upgrade b) {
        this.upgrade = b;
        this.parent = upgradesPanel;
        upgrade.registerObserver(this);

        content.setBackground(Color.blue);



        content.setPreferredSize(new Dimension(50,50));
        content.setMaximumSize(content.getPreferredSize());

        updateContent();;

        icon.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                if( upgradesPanel.game.buyUpgrade(upgrade.getRegistryName())) {
                    parent.buyItem(UpgradeButton.this);
                }
            }
        });
        icon.setToolTipText("asd");



        content.updateUI();
    }

    private void createUIComponents() {
        icon = new UpgradeIcon(upgrade.getIconID());
    }

    @Override
    public void onObservableChanged() {
        updateContent();

        SwingUtilities.invokeLater(this);
    }

    private void updateContent() {
        if(upgrade.getCanBuy()){
            bkg = Color.green;
        }
        else{
            bkg = Color.GRAY;
        }
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
        icon.repaint();
    }

    /**
     * This is the image that gets displayed
     * It also uses the FancyTooltip as it's tooltip
     */
    protected class UpgradeIcon extends Icon{

        public UpgradeIcon(int id) {
            super(id);
        }

        /**
         * Calls the UI delegate's paint method, if the UI delegate
         * is non-<code>null</code>.  We pass the delegate a copy of the
         * <code>Graphics</code> object to protect the rest of the
         * paint code from irrevocable changes
         * (for example, <code>Graphics.translate</code>).
         * <p>
         * If you override this in a subclass you should not make permanent
         * changes to the passed in <code>Graphics</code>. For example, you
         * should not alter the clip <code>Rectangle</code> or modify the
         * transform. If you need to do these operations you may find it
         * easier to create a new <code>Graphics</code> from the passed in
         * <code>Graphics</code> and manipulate it. Further, if you do not
         * invoke super's implementation you must honor the opaque property, that is
         * if this component is opaque, you must completely fill in the background
         * in an opaque color. If you do not honor the opaque property you
         * will likely see visual artifacts.
         * <p>
         * The passed in <code>Graphics</code> object might
         * have a transform other than the identify transform
         * installed on it.  In this case, you might get
         * unexpected results if you cumulatively apply
         * another transform.
         *
         * @param g the <code>Graphics</code> object to protect
         * @see #paint
         * @see ComponentUI
         */

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),0,0,image.getWidth(),image.getHeight(),bkg,null);
        }


        /**
         * Returns the instance of <code>JToolTip</code> that should be used
         * to display the tooltip.
         * Components typically would not override this method,
         * but it can be used to
         * cause different tooltips to be displayed differently.
         *
         * @return the <code>JToolTip</code> used to display this toolTip
         */
        @Override
        public JToolTip createToolTip() {
            FancyTooltip fancyTooltip = new FancyTooltip(this, upgrade);
            //fancyTooltip.setComponent(this);
            //JToolTip fancyTooltip = new JToolTip();
            fancyTooltip.setComponent(this);
            return fancyTooltip;
        }
    }
}
