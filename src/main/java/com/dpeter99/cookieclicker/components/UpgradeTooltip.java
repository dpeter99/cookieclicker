package com.dpeter99.cookieclicker.components;

import com.dpeter99.cookieclicker.Resources;
import com.dpeter99.cookieclicker.model.Upgrade;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UpgradeTooltip {
    private JPanel icon;
    private JLabel cost;
    private JLabel name;

    public JPanel getContent() {
        return content;
    }

    private JPanel content;
    private JLabel description;

    public UpgradeTooltip(Upgrade upgrade) {
        cost.setText(upgrade.getCost().toString());
        name.setText(upgrade.getDisplayName());
        description.setText(upgrade.getDisplayDescription());
    }


    private void createUIComponents() {
       icon = new Icon(0);
    }
}
