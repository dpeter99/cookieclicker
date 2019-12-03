package com.dpeter99.cookieclicker.model.buildings;

import com.dpeter99.cookieclicker.Resources;
import com.dpeter99.cookieclicker.model.GameModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.json.JsonObject;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CursorTest {

    GameModel model;

    Cursor cursor;

    @BeforeAll
    static void setUp_All(){
        Resources r = new Resources();

    }


    @BeforeEach
    void setUp() {
        model = new GameModel();
        cursor = (Cursor) model.getBuildings().get("buildings.cursor");
    }

    @Test
    void getRegistryName() {
        assertEquals("buildings.cursor",cursor.getRegistryName());
    }

    @Test
    void registerUpgrades() {

    }

    @Test
    void getCost() {
        assertEquals(15,cursor.getCost().intValue());
        model.addCookies(BigDecimal.valueOf(15));
        model.buyBuilding("buildings.cursor");
        assertEquals(BigDecimal.valueOf(15f*1.15f),cursor.getCost());
    }

    @Test
    void getDisplayName() {
        assertEquals("Cursor",cursor.getDisplayName());
    }

    @Test
    void isAvailable() {
        assertEquals(true, cursor.isAvailable());
    }

    @Test
    void writeObject() {
        JsonObject out = cursor.writeObject();
        assertEquals(cursor.getRegistryName(),out.getString("ID"));
        assertEquals(cursor.getCount(),out.getInt("count"));
        assertEquals(cursor.getTotalProduction(),new BigDecimal(out.getString("total_prod")));
    }

    @Test
    void readObject() {
        JsonObject in = cursor.writeObject();
        Cursor out = new Cursor(model);
        out.readObject(in);
        assertEquals(cursor.getTotalProduction(),out.getTotalProduction());
        assertEquals(cursor.getCount(),out.getCount());
    }

    @Test
    void getCpS() {
        model.addCookies(BigDecimal.valueOf(15));
        model.buyBuilding(cursor.getRegistryName());
        assertEquals(BigDecimal.valueOf(0.5f),cursor.getCpS());

        model.addCookies(BigDecimal.valueOf(20));
        model.buyBuilding(cursor.getRegistryName());
        assertEquals(BigDecimal.valueOf(0.5f*2),cursor.getCpS());
    }
}