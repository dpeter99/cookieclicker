package com.dpeter99.cookieclicker.model;

import com.dpeter99.cookieclicker.model.buildings.Cursor;
import com.dpeter99.cookieclicker.model.buildings.Grandma;
import com.dpeter99.cookieclicker.util.Observable;

import javax.json.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * The central object holding all the data together.
 * It represents the satae of the game and all oprerations available on it like (buyBuilding, clickCookie)
 *
 * It stores a list of all buildings in the <code>buildings</code> list that is built in the constructor
 * It also stores a similar list of all the available upgrades in <code>upgrades</code>. Both of these are <code>Map string,...</code> where the string is a ID that is unique to the object
 * This ID is given back by the <code>getRegistryName()</code> function in each base class.
 *
 * The number of cookies currently in the bank is stored by the <code>BigDecimal cookies</code> field,
 * witch is returned rounded down for displaying by the  <code>getCookiesDisplayCount</code> function.
 */
public class GameModel extends Observable implements Serializable {

    /**
     * The number off cookies currently available
     * Use: <code>addCookies</code>, <code>removeCookies</code>, and <code>getCookieCount</code>
     */
    BigDecimal cookies = new BigDecimal("0");


    /**
     * This is the amount of cookies a single click produces
     */
    BigDecimal cookiesPerClick =  new BigDecimal("1");


    /**
     * This is the amount we are producing passively each second
     */
    BigDecimal CpS = BigDecimal.ZERO;


    /**
     * Returns the current CpS that we are making
     *
     * @return The current CpS
     */
    public BigDecimal getCpS() {
        return CpS;
    }

    /**
     * This is the registry of the buildings in the game
     *
     * each building is mapped to it's registry name witch is defined by each class and returned by it's <code>Building.getRegistryName()</code>
     */
    Map<String,Building> buildings = new HashMap<>();
    public Map<String, Building> getBuildings() {
        return buildings;
    }

    /**
     * This is the registry of the upgrades in the game
     *
     * each building is mapped to it's registry name witch is defined by each class and returned by it's <code>Upgrade.getRegistryName()</code>
     */
    Map<String,Upgrade> upgrades = new HashMap<>();
    public Map<String, Upgrade> getUpgrades() {
        return upgrades;
    }

    /**
     * The constructor for the game model
     * It registers all the content (Buildings, Upgrades).
     */
    public GameModel() {

        registerBuilding(new Cursor(this));
        registerBuilding(new Grandma(this));
    }

    /**
     * Registers a given building, shortcut to make registering easier.
     * And asks the <code>Building</code> to register it's upgrades by calling the <code>Building.registerUpgrades()</code>
     *
     * @param building The building to register
     */
    void registerBuilding(Building building){
        buildings.put(building.getRegistryName(),building);
        building.registerUpgrades();
    }

    /**
     * Registers an upgrade
     *
     * @param upgrade The upgrade to register
     */
    public void registerUpgrade(Upgrade upgrade) {
        upgrades.put(upgrade.getRegistryName(),upgrade);

    }

    /**
     * This function is called to update the game word continously.
     *
     * This is called form a separate thread to make sure the UI can run freely.
     * The caller thread of this is <code>GameThread</code>
     *
     * @param deltaTime the time since this was last called.
     *
     * @see com.dpeter99.cookieclicker.GameThread
     */
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


    /**
     * Adds a specified amount of cookies
     * Useful for debugging or later for rewards
     *
     * @param count The amount to add
     */
    public  void addCookies(BigDecimal count){
        cookies = cookies.add(count);
        notifyObservers();
    }

    /**
     * Removes a specified amount of cookies
     * Useful for debugging or later for unlucky rewards
     *
     * @param count The amount to remove
     */
    public void removeCookies(BigDecimal count){
        cookies = cookies.subtract(count);
        notifyObservers();
    }

    /**
     * Clicks the cookie to produce more if it.
     *
     * Adds the <code>cookiesPerClick</code> to the total cookies
     */
    public void clickCookie(){
        cookies = cookies.add(cookiesPerClick);
    }

    /**
     * Gives back the number of cookies
     *
     * @return The number of cookies
     */
    public String getCookieCount(){
        return cookies.toString();
    }

    /**
     * Returns the number of cookies in the display format.
     *
     * @return The string to display
     */
    public String getCookiesDisplayCount(){
        return cookies.toBigInteger().toString();
    }

    /**
     * Trys to buy a building
     *
     * @param id The id of the building to buy
     * @return <code>true</code> if the building could be bought <code>fasle</code> if not
     */
    public boolean buyBuilding(String id){
        Building b = buildings.get(id);
        if(b.getCanBuy()){
            b.buy(1);
            return true;
        }
        return false;
    }


    /**
     * Trys to buy an upgrade
     *
     * @param id The id of the upgrade to buy
     * @return <code>true</code> if the upgrade could be bought <code>fasle</code> if not
     */
    public boolean buyUpgrade(String id) {
        Upgrade b = upgrades.get(id);
        if(b.getCanBuy()){
            b.buy();
            return true;
        }
        return false;
    }


    /**
     * Saves the game data to the save file
     */
    public void SaveToFile(){

        FileWriter writer = null;
        try {
            writer = new FileWriter("save.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonWriter w = Json.createWriter(writer);
        JsonObject o = this.writeObject();
        w.writeObject(o);
        w.close();

    }

    /**
     * Actually does the saving of the game data
     * The fields that get saved are:
     *  - cookies
     *  - buildings
     *  - upgrades
     *
     * @return The JsonObject that holds the data
     */
    private JsonObject writeObject() {

        JsonArrayBuilder buildingList = Json.createArrayBuilder();
        for (Building b : buildings.values()) {
            buildingList.add(b.writeObject());
        }

        JsonArrayBuilder upgradesList = Json.createArrayBuilder();
        for (Upgrade u : upgrades.values()) {
            upgradesList.add(u.writeObject());
        }

        JsonObject value =
                Json.createObjectBuilder()
                        .add("cookies", cookies.toString())
                        .add("buildings", buildingList)
                        .add("upgrades", upgradesList)
                        .build();

        return value;
    }

    /**
     * Used to try and load in form a file. If it can't be done it returns a fresh new game.
     *
     * @param path The path of the file to load in form
     * @return returns a new instance of GameModel that contains the loaded data if it was successful.
     */
    public static GameModel openFromFile(String path){
        InputStream reader = null;
        GameModel g = new GameModel();

        try {
             reader = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            System.out.println("Can't load save file. Starting new game");
            return g;
        }



        JsonReader jsonReader = Json.createReader(reader);

        JsonObject obj = null;

        try {
            obj = jsonReader.readObject();
        }
        catch (JsonException e){
            System.out.println("Can't load save file. Starting new game");
            return g;
        }


        g.readObject(obj);

        return g;
    }

    /**
     * reads in the actual data form the JsonObject given
     *
     * @param o The object to readFrom
     */
    private void readObject(JsonObject o){

        cookies = new BigDecimal(o.getString("cookies"));

        JsonArray buildingsArray = o.getJsonArray("buildings");

        for (int i = 0; i < buildingsArray.size(); i++) {
            JsonObject a = buildingsArray.getJsonObject(i);
            String id = a.getString("ID");
            if(buildings.containsKey(id)) {
                Building b = buildings.get(id);
                b.readObject(a);
            }
            else{
                //Unknown building possible version mismatch
            }
        }

        JsonArray upgradesArray = o.getJsonArray("upgrades");

        for (int i = 0; i < upgradesArray.size(); i++) {
            JsonObject a = upgradesArray.getJsonObject(i);
            String id = a.getString("ID");
            if(upgrades.containsKey(id)) {
                Upgrade b = upgrades.get(id);
                b.readObject(a);
            }
            else{
                //Unknown building possible version mismatch
            }
        }
    }



}
