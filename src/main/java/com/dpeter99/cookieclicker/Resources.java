package com.dpeter99.cookieclicker;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Resources {

    public static Resources Instance;

    private BufferedImage image;

    public Resources() {
        Instance = this;

        try {
            image = ImageIO.read(Resources.Instance.getFileFromResources("images/icons.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // get file from classpath, resources folder
    public File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }

    public String getString(String key){
        return ResourceBundle.getBundle("lang/lang", Locale.getDefault()).getString(key);
    }

    public String getFormatedString(String key, Object... args){
        return String.format(getString(key), args);
    }

    public BufferedImage getIconByID(int id){

        BufferedImage off_Image =
                new BufferedImage(48, 48,
                        BufferedImage.TYPE_INT_ARGB);
        int row = 33;

        int pos_row = id / row;
        int pos_coll = id - (pos_row*row);

        int icon_h = 48;
        int icon_w = 48;



        off_Image.createGraphics().drawImage(image,0,0,48,48,pos_coll*icon_w,pos_row*icon_h,(pos_coll+1)*icon_w,(pos_row+1)*icon_h,null);

        return off_Image;
    }

}
