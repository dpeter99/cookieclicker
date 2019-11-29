package com.dpeter99.cookieclicker.model;

import com.dpeter99.cookieclicker.util.Observable;
import com.dpeter99.cookieclicker.util.Observer;

import java.math.BigDecimal;

public abstract class Building extends Observable implements Observer {

    protected int count;



    protected BigDecimal tatalProduction = new BigDecimal("0");
    public BigDecimal getTatalProduction() {
        return tatalProduction;
    }

    protected GameModel game;


    public Building(GameModel game) {
        this.game = game;
        game.registerObserver(this);
    }


    public abstract String getRegistryName();

    public abstract void registerUpgrades();

    public abstract BigDecimal getCost();


    public int getCount() {
        return count;
    }

    private void setCount(int count) {
        this.count = count;
        notifyObservers();
    }

    private void addCount(int count){
        this.count += count;
        notifyObservers();
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


    public void buy(int count){
        game.removeCookies(getCost());
        addCount(count);
    }


    @Override
    public void onObservableChanged() {
        calcCanBuy();
    }


    public abstract BigDecimal getCpS();

    public void RegisterProduction(BigDecimal prod){
        tatalProduction = tatalProduction.add(prod);
        notifyObservers();
    }

    public String getCostDisplay(){
        return this.getCost().toBigInteger().toString();
    }

    public abstract String getDisplayName();

    public abstract boolean isAvailable();
}
