package com.dpeter99.cookieclicker.components;

import com.dpeter99.cookieclicker.Resources;
import com.dpeter99.cookieclicker.model.GameModel;
import com.dpeter99.cookieclicker.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CookieButton implements Runnable, Observer {

    private BufferedImage image;

    private GameModel game;

    public JPanel getContent() {
        return content;
    }

    private JPanel content;
    private BigCookie cookieButton;
    private JLabel cookies;
    private JLabel production;

    public CookieButton(GameModel game) {
        this.game = game;
        game.registerObserver(this);

        try {
            image = ImageIO.read(Resources.Instance.getFileFromResources("images/cookie.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        content.setPreferredSize(new Dimension(200,500));
        content.setBackground(Color.GRAY);


        cookies.setText("0");
        production.setText(Resources.Instance.getFormatedString("production.text", game.getCpS().toString()));

        cookieButton.setPreferredSize(new Dimension(200,200));
        cookieButton.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                game.ClickCookie();
            }
        });
        cookieButton.setToolTipText("asd");

    }

    void createUIComponents(){
        cookieButton = new BigCookie();
    }

    @Override
    public void onObservableChanged() {
        cookies.setText(game.GetCookiesDisplayCount());
        production.setText(Resources.Instance.getFormatedString("production.text", game.getCpS().toString()));
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


    class BigCookie extends JComponent{

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
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;

            //g.drawImage(image, 0, 0, null);
            g.drawImage(image, 0,0,200,200,0,0,image.getWidth(),image.getHeight(),Color.GRAY,null);
        }

    }
}
