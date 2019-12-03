package com.dpeter99.cookieclicker.model;

import com.dpeter99.cookieclicker.util.Observable;
import com.dpeter99.cookieclicker.util.Observer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.math.BigDecimal;

/**
 * This is the base class for all the upgrades (CursorUpgrade, GrandmaUpgrade etc.) in the game
 * It provides base function to help with some aspects, while giving space for customization.
 */
public abstract class Upgrade extends Observable implements Observer {

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
     * Helper function for building based upgrades to generate ID
     *
     * @param b The buildign this belongs to
     * @param ID The upgrade ID
     * @return The concatenated registry name
     */
    protected String MakeID(Building b, String ID){
        String res = "upgrade.";
        res = res.concat(b.getRegistryName());
        res = res.concat(".");
        res = res.concat(ID);
        return res;
    }


    /**
     * The current cost of this upgrade
     * This should not change during the game play
     */
    protected BigDecimal cost;

    /**
     * @return returns the cst of this upgrade
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * ONLY FOR DEBUGGING
     * Sets the cost to a number
     *
     * @param cost The number to set to cost to
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }


    /**
     * whether this upgrade has been bought or not
     */
    protected boolean bought;

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }


    /**
     * The ID of the icon to use for this upgrade from the icon map ("images/icons.png")
     */
    protected int iconID;

    /**
     * Returns the icon ID
     * @return The icon ID
     */
    public int getIconID(){
        return iconID;
    }


    /**
     * Simple constructor for setting the GameReference and the cost.
     * @param game The game this Building is in
     * @param cost The cost of the Upgrade
     */
    public Upgrade(GameModel game, BigDecimal cost) {
        this.cost = cost;
        this.game = game;
        game.registerObserver(this);
    }


    /**
     * Run when the upgrade is bought by the player or when the upgrade is loaded as bought
     */
    public abstract void onBuy();

    /**
     * Called to buy the upgrade.
     * Removes the cost from the bank and sets the bought flag.
     */
    public void buy(){
        game.removeCookies(getCost());
        setBought(true);
        onBuy();
    }


    /**
     * This function is called to calculate if this upgrade can be bought
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
     *
     */
    protected boolean canBuy;

    public boolean getCanBuy() {
        return canBuy;
    }

    /**
     * sets whether this upgrade can be bought currently.
     * Notifies the observers about the change
     * @param canBuy whether this upgrade can be bought
     */
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

    /**
     * Returns weather this upgrade should be displayed
     * @return weather this upgrade should be displayed
     */
    public abstract boolean isAwailable();

    /**
     * Returns the name to display.
     * The child class implements it
     * @return The display name (eg. "Cursor")
     */
    public abstract String getDisplayName();

    /**
     * Returns the description to display.
     * The child class implements it
     * @return The description (eg. "Nice granny")
     */
    public abstract String getDisplayDescription();

    /**
     * Writes the save data of the object to a JsonObject
     * @return The JsonObject containing the data
     */
    public JsonObjectBuilder writeObject(){
        JsonObjectBuilder value =
                Json.createObjectBuilder()
                        .add("ID", getRegistryName())
                        .add("bought", this.bought);

        return value;
    };

    /**
     * Reads in the saved data form a JsonObject
     *
     * @param a The object containing the data
     */
    public void readObject(JsonObject a) {
        this.bought = a.getBoolean("bought");
    }
}
