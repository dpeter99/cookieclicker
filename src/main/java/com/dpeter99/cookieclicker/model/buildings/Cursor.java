package com.dpeter99.cookieclicker.model.buildings;

import com.dpeter99.cookieclicker.Resources;
import com.dpeter99.cookieclicker.model.Building;
import com.dpeter99.cookieclicker.model.GameModel;
import com.dpeter99.cookieclicker.model.Upgrade;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Cursor  extends Building {

    final String REGISTRY_NAME = "buildings.cursor";

    public Cursor(GameModel game) {
        super(game);

    }


    final BigDecimal baseCost = new BigDecimal("15");

    BigDecimal multiplyer = new BigDecimal("0");

    List<CursorUpgrade> upgrades = new LinkedList<>();

    @Override
    public String getRegistryName() {
        return REGISTRY_NAME;
    }

    @Override
    public void registerUpgrades() {
        game.registerUpgrade(new CursorUpgrade(game,"basic" ,BigDecimal.valueOf(100),0,1));
        game.registerUpgrade(new CursorUpgrade(game,"basic2",BigDecimal.valueOf(500),33,1));
        game.registerUpgrade(new CursorUpgrade(game,"basic3",BigDecimal.valueOf(10000),33*2,10));
        game.registerUpgrade(new CursorUpgrade(game,"basic4",BigDecimal.valueOf(100000),33*13,25));
        game.registerUpgrade(new CursorUpgrade(game,"basic5",BigDecimal.valueOf(10000000),33*14,50));
    }


    @Override
    public BigDecimal getCost() {
        return  baseCost.multiply(BigDecimal.valueOf(1.15).pow(count));
    }

    @Override
    public String getDisplayName() {
        return "Cursor";
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public JsonObject writeObject() {

        JsonObjectBuilder value = baseWriteObject();


        return value.build();
    }

    @Override
    public void readObject(JsonObject a) {
        baseReadObject(a);
    }

    @Override
    public BigDecimal getCpS() {
        BigDecimal a = BigDecimal.valueOf(0.5).multiply(BigDecimal.valueOf(count));
        if(!multiplyer.equals(BigDecimal.ZERO))
                a = a.multiply(multiplyer);
        return a;
    }



    public class CursorUpgrade extends Upgrade{

        final String REGISTRY_NAME;

        int treshold;

        String name;
        String desc;

        public CursorUpgrade(GameModel g, String id, BigDecimal cost, int iconID, int treshold) {
            super(g,cost);

            upgrades.add(this);

            this.iconID = iconID;
            this.REGISTRY_NAME = MakeID(Cursor.this,id);
            this.treshold = treshold;
            this.name = Resources.Instance.getString(REGISTRY_NAME.concat(".name"));
            this.desc = Resources.Instance.getString(REGISTRY_NAME.concat(".description"));
        }

        @Override
        public void onBuy() {
            multiplyer = multiplyer.add(BigDecimal.valueOf(2));
        }

        public BigDecimal getMultiplyer() {return BigDecimal.valueOf(2);}

        @Override
        public boolean isAwailable() {
            return count >= treshold;
        }

        @Override
        public String getDisplayName() {
            return name;
        }

        public String getDisplayDescription() {
            return desc;
        }

        @Override
        public String getRegistryName() {
            return REGISTRY_NAME;
        }

        @Override
        public void readObject(JsonObject a) {
            super.readObject(a);
            if(bought)
            onBuy();
        }
    }
}
