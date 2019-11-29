package com.dpeter99.cookieclicker.model;

import com.dpeter99.cookieclicker.model.buildings.Cursor;
import com.dpeter99.cookieclicker.model.buildings.Grandma;
import com.dpeter99.cookieclicker.util.Observable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

public class GameModel extends Observable {

    BigDecimal cookies = new BigDecimal("0");

    BigDecimal cookiesPerClick =  new BigDecimal("1");

    BigDecimal CpS = BigDecimal.ZERO;
    public BigDecimal getCpS() {
        return CpS;
    }

    Map<String,Building> buildings = new HashMap<>();
    public Map<String, Building> getBuildings() {
        return buildings;
    }

    Map<String,Upgrade> upgrades = new HashMap<>();
    public Map<String, Upgrade> getUpgrades() {
        return upgrades;
    }

    public GameModel() {

        RegisterBuilding(new Cursor(this));
        RegisterBuilding(new Grandma(this));
    }

    void RegisterBuilding(Building building){
        buildings.put(building.getRegistryName(),building);
        building.registerUpgrades();
    }

    public void registerUpgrade(Upgrade upgrade) {
        upgrades.put(upgrade.getRegistryName(),upgrade);

    }

    public void Update(float deltaTime){
        BigDecimal CpS_current = new BigDecimal("0");

        for (Building b : buildings.values()) {
            BigDecimal CpS_building =  b.getCpS();
            b.RegisterProduction(CpS_building.multiply(BigDecimal.valueOf(deltaTime)));

            CpS_current = CpS_current.add(CpS_building);

        }

        BigDecimal prod = CpS_current.multiply(BigDecimal.valueOf(deltaTime));
        CpS = CpS_current;

        cookies = cookies.add(prod);

        notifyObservers();
    }

    public void removeCookies(BigDecimal count){
        cookies = cookies.subtract(count);
    }

    public void ClickCookie(){
        cookies = cookies.add(cookiesPerClick);
    }

    public String GetCookieCount(){
        return cookies.toString();
    }

    public String GetCookiesDisplayCount(){
        return cookies.toBigInteger().toString();
    }

    public void BuyBuilding(String id){
        Building b = buildings.get(id);
        if(b.getCanBuy()){
            b.buy(1);
        }
    }


    public boolean BuyUpgrade(String id) {
        Upgrade b = upgrades.get(id);
        if(b.getCanBuy()){
            b.buy();
            return true;
        }
        return false;
    }
}
