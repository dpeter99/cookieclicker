package com.dpeter99.cookieclicker.model;

import com.dpeter99.cookieclicker.util.Observable;
import com.dpeter99.cookieclicker.util.Observer;

import java.math.BigDecimal;

public abstract class Upgrade extends Observable implements Observer {

    protected GameModel game;

    public abstract String getRegistryName();

    protected String MakeID(Building b, String ID){
        String res = "upgrade.";
        res = res.concat(b.getRegistryName());
        res = res.concat(".");
        res = res.concat(ID);
        return res;
    }


    protected BigDecimal cost;

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }



    protected boolean bought;

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }


    protected int iconID;

    public int getIconID(){
        return iconID;
    }


    public Upgrade(GameModel game, BigDecimal cost) {
        this.cost = cost;
        this.game = game;
        game.registerObserver(this);
    }


    public abstract void onBuy();

    public void buy(){
        game.removeCookies(getCost());
        setBought(true);
        onBuy();
    }




    public boolean calcCanBuy(){
        if(getCost().compareTo(game.cookies) <= 0){
            setCanBuy(true);
            return true;
        }
        setCanBuy(false);
        return false;
    }

    protected boolean canBuy;

    public boolean getCanBuy() {
        return canBuy;
    }

    public void setCanBuy(boolean canBuy){
        if(this.canBuy != canBuy){
            this.canBuy = canBuy;
            notifyObservers();
        }
    }

    @Override
    public void onObservableChanged() {
        calcCanBuy();
    }


    public abstract boolean isAwailable();


    public abstract String getDisplayName();

    public abstract String getDisplayDescription();
}
