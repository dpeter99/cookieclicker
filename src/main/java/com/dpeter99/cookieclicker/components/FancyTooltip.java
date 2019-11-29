package com.dpeter99.cookieclicker.components;

import com.dpeter99.cookieclicker.model.Upgrade;

import javax.swing.*;
import java.awt.*;

public class FancyTooltip extends JToolTip {

    JComponent content;

    UpgradeTooltip panel;

    public FancyTooltip(JComponent component,  Upgrade upgrade) {

        //this.setPreferredSize(new Dimension(200,100));


        setComponent(component);


        setLayout(new BorderLayout());

        panel =  new UpgradeTooltip(upgrade);
        content = panel.getContent();

        add(content);



        this.updateUI();


    }

    /**
     * Sets the text to show when the tool tip is displayed.
     * The string <code>tipText</code> may be <code>null</code>.
     *
     * @param tipText the <code>String</code> to display
     */
    @Override
    public void setTipText(String tipText) {

    }

    @Override
    public Dimension getPreferredSize() {
        return content.getPreferredSize();
    }
}
