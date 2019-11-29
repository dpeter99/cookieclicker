package com.dpeter99.cookieclicker.components;

import com.dpeter99.cookieclicker.Resources;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.image.BufferedImage;

class Icon extends JPanel {

    private BufferedImage image;

    public Icon(int id) {
        super();
        image = Resources.Instance.getIconByID(id);

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
        Graphics2D g2 = (Graphics2D)g;

        // int rule = AlphaComposite.CLEAR;
        //int rule = AlphaComposite.SRC_OVER;
        //Composite comp = AlphaComposite.getInstance(rule , 1 );
        // g2.setComposite(comp );
        g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),0,0,image.getWidth(),image.getHeight(),null,null);
    }

}
