package com.dpeter99.cookieclicker.model.buildings;

import com.dpeter99.cookieclicker.Resources;
import com.dpeter99.cookieclicker.model.Building;
import com.dpeter99.cookieclicker.model.GameModel;
import com.dpeter99.cookieclicker.model.Upgrade;

import java.awt.*;
import java.math.BigDecimal;

public class Grandma extends Building {

    public Grandma(GameModel game) {
        super(game);
    }


    final BigDecimal baseCost = new BigDecimal("100");
    final String REGISTRY_NAME = "buildings.grandma";

    BigDecimal multiplyer = new BigDecimal("0");

    @Override
    public String getRegistryName() {
        return REGISTRY_NAME;
    }

    @Override
    public void registerUpgrades() {
        game.registerUpgrade(new GrandmaUpgrade(game,"basic" ,BigDecimal.valueOf(100),1,1));
        game.registerUpgrade(new GrandmaUpgrade(game,"basic2",BigDecimal.valueOf(500),34,5));
        game.registerUpgrade(new GrandmaUpgrade(game,"basic3",BigDecimal.valueOf(10000),(33*2)+1,25));
    }


    @Override
    public BigDecimal getCost() {
        return  baseCost.multiply(BigDecimal.valueOf(1.15).pow(count));
    }

    @Override
    public String getDisplayName() {
        return "Grandma";
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public BigDecimal getCpS() {
        BigDecimal a = BigDecimal.valueOf(1).multiply(BigDecimal.valueOf(count));
        if(!multiplyer.equals(BigDecimal.ZERO))
            a = a.multiply(multiplyer);
        return a;
    }


    public class GrandmaUpgrade extends Upgrade {

        final String REGISTRY_NAME;

        int treshold;

        String name;
        String desc;

        public GrandmaUpgrade(GameModel g, String id, BigDecimal cost, int iconID, int treshold) {
            super(g,cost);
            this.iconID = iconID;
            this.REGISTRY_NAME = MakeID(Grandma.this,id);
            this.treshold = treshold;
            this.name = Resources.Instance.getString(REGISTRY_NAME.concat(".name"));
            this.desc = Resources.Instance.getString(REGISTRY_NAME.concat(".description"));
        }

        @Override
        public void onBuy() {
            multiplyer = multiplyer.add(BigDecimal.valueOf(2));
        }

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
    }
}
