package com.dpeter99.cookieclicker.model;

import com.dpeter99.cookieclicker.util.Observable;
import com.dpeter99.cookieclicker.util.Observer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.math.BigDecimal;

/**
 * This is the base class for all the buildings (Cursor, Grandma etc.) in the game
 * It provides base function to help with some aspects, while giving space for customization.
 */
public abstract class Building extends Observable implements Observer {

    protected GameModel game;

    /**
     * Returns a registry name (ID) of this type. The inheriting member implements it
     * and assigns a custom value that should be unique
     * This name is used to refer to this building in code.
     *
     * @return The ID
     */
    public abstract String getRegistryName();

    /**
     * The number of currently owned buildings of this type;
     * Usefully for calcuatng the CpS this building adds to the total.
     */
    protected int count;


    /**
     * The total number of cookies produced by this type of building during the game
     */
    protected BigDecimal totalProduction = new BigDecimal("0");

    /**
     * @return The total number of cookies produced by this building
     */
    public BigDecimal getTotalProduction() {
        return totalProduction;
    }




    /**
     * Simple constructor to set the game reference
     *
     * @param game The game instance this belongs to
     */
    public Building(GameModel game) {
        this.game = game;
        game.registerObserver(this);
    }




    /**
     * This method is called to make the building register all the upgrades that it provides.
     */
    public abstract void registerUpgrades();

    /**
     * Gives back the cost of buying a new one of these buildings.
     *
     * @return The cost of a new one
     */
    public abstract BigDecimal getCost();


    /**
     * @return The current count of this building
     */
    public int getCount() {
        return count;
    }

    /**
     * ONLY FOR DEBUGGING
     * Sets the owned building count to a given value.
     *
     * @param count The value to set to
     */
    private void setCount(int count) {
        this.count = count;
        notifyObservers();
    }

    /**
     * Adds to the count of this building
     * @param count The count to increment with
     */
    private void addCount(int count){
        this.count += count;
        notifyObservers();
    }


    /**
     * This function is called to calculate if a new one of this building can be bought
     * It sets the canBuy variable as well as returning the fresh value.
     *
     * Impl:
     * Currently it checks if the player has enough cookies.
     * But a child class can overwrite it to check for other parameters as well.
     *
     * @return Weather a new one can be bought
     */
    public boolean calcCanBuy(){
        if(getCost().compareTo(game.cookies) <= 0){
            setCanBuy(true);
            return true;
        }
        setCanBuy(false);
        return false;
    }

    /**
     * Whether this building can be bought currently.
     */
    protected boolean canBuy;

    public boolean getCanBuy() {
        return canBuy;
    }

    /**
     * sets whether this building can be bought currently.
     * Notifies the observers about the change
     * @param canBuy whether this building can be bought
     */
    public void setCanBuy(boolean canBuy){
        if(this.canBuy != canBuy){
            this.canBuy = canBuy;
            notifyObservers();
        }
    }


    /**
     * Buys a new one of this building.
     *
     * Removes the cost from the game and increases the count.
     *
     * @param count The amount to buy
     */
    public void buy(int count){
        game.removeCookies(getCost());
        addCount(count);
    }


    @Override
    public void onObservableChanged() {
        calcCanBuy();
    }


    public abstract BigDecimal getCpS();

    /**
     * called by the GameModel.Update() to notify the building that it produced a certain amount of cookies.
     * Currently it adds the amount to the totalProduction count. But can be overwritten by child classes to extend the default
     * like: A building that produces more the more it produced up to +100% (1% of each 100000 cookies it produced)
     * @param prod The amount this building type produced
     */
    public void RegisterProduction(BigDecimal prod){
        totalProduction = totalProduction.add(prod);
        notifyObservers();
    }

    /**
     * Returns the cost in the display format.
     * @return The cost to display
     */
    public String getCostDisplay(){
        return this.getCost().toBigInteger().toString();
    }

    /**
     * Returns the name to display.
     * The child class implements it
     * @return The display name
     */
    public abstract String getDisplayName();

    /**
     * [Not implemented]
     * Returns weather this building should be displayed
     * @return weather this building should be displayed
     */
    public abstract boolean isAvailable();

    /**
     * Writes the save data of the object to a JsonObject
     * @return The JsonObject containing the data
     */
    public abstract JsonObject writeObject();

    /**
     * Saves that default data:
     *  - count
     *  - total_prod
     * @return The <code>JsonObjectBuilder</code> containing the base data
     */
    public JsonObjectBuilder baseWriteObject(){
        JsonObjectBuilder value =
                Json.createObjectBuilder()
                        .add("ID", getRegistryName())
                        .add("count", this.count)
                        .add("total_prod", this.totalProduction.toString());

        return value;
    }

    /**
     * Reads in the saved data form a JsonObject
     *
     * @param a The object containing the data
     */
    public abstract void readObject(JsonObject a);

    /**
     * Default data reader
     * Reads in the basic data:
     *  - count
     *  - total_prod
     *
     * @param a The JsonObject the base data should be red from
     */
    public void baseReadObject(JsonObject a){
        this.count = a.getInt("count");
        this.totalProduction = new BigDecimal(a.getString("total_prod"));
    }
}
