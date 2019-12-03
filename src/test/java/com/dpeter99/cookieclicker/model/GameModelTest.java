package com.dpeter99.cookieclicker.model;

import com.dpeter99.cookieclicker.Resources;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    GameModel model;

    @BeforeAll
    static void setUp_All(){
        Resources r = new Resources();

    }

    @org.junit.jupiter.api.BeforeEach()
    void setUp() {
        model=new GameModel();

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void clickCookie() {
        BigDecimal before = new BigDecimal(model.getCookieCount());
        model.clickCookie();
        BigDecimal after = new BigDecimal(model.getCookieCount());
        assertEquals(1,after.subtract(before).intValue());

    }

    @Test
    void addCookies() {
        BigDecimal before = new BigDecimal(model.getCookieCount());
        model.addCookies(BigDecimal.valueOf(1000));
        BigDecimal after = new BigDecimal(model.getCookieCount());
        assertEquals(1000,after.subtract(before).intValue());
    }

    @Test
    void removeCookies() {
        BigDecimal before = new BigDecimal(model.getCookieCount());
        model.removeCookies(BigDecimal.valueOf(1000));
        BigDecimal after = new BigDecimal(model.getCookieCount());
        assertEquals(-1000,after.subtract(before).intValue());
    }


    @org.junit.jupiter.api.Test
    void getCookieCount() {
        model.addCookies(BigDecimal.valueOf(10));
        assertEquals("10", model.getCookieCount());
    }

    @org.junit.jupiter.api.Test
    void getCookiesDisplayCount() {
        model.addCookies(BigDecimal.valueOf(10));
        assertEquals("10", model.getCookiesDisplayCount());
        assertEquals(false,model.getCookiesDisplayCount().contains("."));
    }


    @Test
    void getCpS() {
    }

    @Test
    void registerBuilding() {
        TestBuilding building = new TestBuilding(model);
        model.registerBuilding(building);
        assertEquals(true, model.getBuildings().containsKey(building.getRegistryName()));
    }

    @Test
    void registerUpgrade() {
        TestUpgrade upgrade = new TestUpgrade(model,BigDecimal.valueOf(10));
        model.registerUpgrade(upgrade);
        assertEquals(true, model.getUpgrades().containsKey(upgrade.getRegistryName()));
    }

    @Test
    void buyBuilding() {
        TestBuilding building = new TestBuilding(model);
        model.registerBuilding(building);

        model.addCookies(BigDecimal.valueOf(10));
        assertEquals(false,model.buyBuilding("building.test"));
        assertEquals("10",model.getCookieCount());
        assertEquals(0,building.count);
        model.removeCookies(BigDecimal.valueOf(10));

        model.addCookies(BigDecimal.valueOf(100));
        assertEquals(true,model.buyBuilding("building.test"));
        assertEquals("0",model.getCookieCount());
        assertEquals(1,building.count);

        model.addCookies(BigDecimal.valueOf(110));
        assertEquals(true,model.buyBuilding("building.test"));
        assertEquals("10",model.getCookieCount());
        assertEquals(2,building.count);

    }

    @Test
    void buyUpgrade() {
        TestUpgrade upgrade = new TestUpgrade(model,BigDecimal.valueOf(10));
        model.registerUpgrade(upgrade);

        model.addCookies(BigDecimal.valueOf(1));
        assertEquals(false, model.buyUpgrade("upgrades.test"));
        assertEquals("1",model.getCookieCount());
        assertEquals(false,upgrade.bought);
        model.removeCookies(BigDecimal.valueOf(1));

        model.addCookies(BigDecimal.valueOf(10));
        assertEquals(true,model.buyUpgrade("upgrades.test"));
        assertEquals("0",model.getCookieCount());
        assertEquals(true,upgrade.bought);

        model.addCookies(BigDecimal.valueOf(20));
        assertEquals(true,model.buyUpgrade("upgrades.test"));
        assertEquals("10",model.getCookieCount());
        assertEquals(true,upgrade.bought);
    }



    class TestBuilding extends Building {

        public TestBuilding(GameModel game) {
            super(game);
        }

        @Override
        public String getRegistryName() {
            return "building.test";
        }

        @Override
        public void registerUpgrades() {

        }

        @Override
        public BigDecimal getCost() {
            return BigDecimal.valueOf(100);
        }

        @Override
        public BigDecimal getCpS() {
            return BigDecimal.valueOf(10);
        }

        @Override
        public String getDisplayName() {
            return "ASD";
        }

        @Override
        public boolean isAvailable() {
            return true;
        }

        @Override
        public JsonObject writeObject() {
            return null;
        }

        @Override
        public void readObject(JsonObject a) {

        }
    }

    class TestUpgrade extends Upgrade{

        public TestUpgrade(GameModel game, BigDecimal cost) {
            super(game, cost);
        }

        @Override
        public String getRegistryName() {
            return "upgrades.test";
        }

        @Override
        public void onBuy() {

        }

        @Override
        public boolean isAwailable() {
            return false;
        }

        @Override
        public String getDisplayName() {
            return "Test";
        }

        @Override
        public String getDisplayDescription() {
            return "Test test";
        }
    }
}