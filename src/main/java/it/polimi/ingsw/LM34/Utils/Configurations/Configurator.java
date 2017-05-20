package it.polimi.ingsw.LM34.Utils.Configurations;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by GiulioComi on 07/05/2017.
 * config parameters loaded from file as static variables
 */
public final class Configurator {

    private static Market market;
    private static CouncilPalace palace;
    private static ArrayList<Tower> towers;


    public static void loadConfigs() {
        JSONObject jsonObject = null;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            File file = new File(loader.getResource("configurations/config.json").getFile());
            InputStream inputStream = new FileInputStream(file);
            String jsonString  = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            jsonObject = new JSONObject(jsonString).getJSONObject("configuration");
        } catch (Exception e) {e.printStackTrace();}

        setupMarket(jsonObject);

    }

    private static void setupMarket(JSONObject jsonObject) {
        market = new Market(new ArrayList<ActionSlot>());
        JSONArray market_array = jsonObject.getJSONObject("actionSlots").getJSONArray("market");
        for (int i = 0; i < market_array.length(); i++) {
            market.addSlot(getActionSlotFromJson(market_array.getJSONObject(i).getInt("diceValue"),market_array.getJSONObject(i).getJSONObject("resources")));
        }

    }

    public static Market getMarket() {
        return market;
    }

    private static ActionSlot getActionSlotFromJson(Integer diceValue,JSONObject jsonObject) {
        Integer[] resourcesArray = new Integer[jsonObject.length()];
        resourcesArray[0] = jsonObject.getInt("coins");
        resourcesArray[1] = jsonObject.getInt("woods");
        resourcesArray[2] = jsonObject.getInt("stones");
        resourcesArray[3] = jsonObject.getInt("faithPoints");
        resourcesArray[4] = jsonObject.getInt("servants");
        resourcesArray[5] = jsonObject.getInt("militaryPoints");
        resourcesArray[6] = jsonObject.getInt("victoryPoints");
        Integer numberCouncilPrivilege = jsonObject.getInt("councilPrivilege");

        Resources resources = new Resources(resourcesArray[0], resourcesArray[1], resourcesArray[2], resourcesArray[3], resourcesArray[4], resourcesArray[5], resourcesArray[6]);
        return new ActionSlot(true, diceValue, resources, numberCouncilPrivilege);
    }
    //TODO: make this private for production! ;D
    public static void printMarket() {
        Resources res = null;
        for (Integer i = 0; i < market.getSize(); i++) {
            res = market.getActionSlots().get(i).getResourcesReward();
            try {
                System.out.println(market.getActionSlots().get(i).getDiceValue());
                System.out.println((market.getActionSlots().get(i).getDiceValue()));
                System.out.println((res.getResourceByType(ResourceType.STONES)));
                System.out.println((res.getResourceByType(ResourceType.SERVANTS)));
                System.out.println((res.getResourceByType(ResourceType.WOODS)));
                System.out.println((res.getResourceByType(ResourceType.COINS)));
                System.out.println((res.getResourceByType(ResourceType.FAITH_POINTS)));
                System.out.println((res.getResourceByType(ResourceType.MILITARY_POINTS)));
                System.out.println((res.getResourceByType(ResourceType.VICTORY_POINTS)));
                System.out.println(market.getActionSlots().get(i).getCouncilPrivilege());
                System.out.println("-----------------------------------------");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //MAIN WITH THE PURPOSE TO VERIFY THE CORRECT LOADING OF MODEL OBJECTS FROM FILE
    public static void main(String[] args) {
        Configurator.loadConfigs();
        Configurator.printMarket();



    }

    public static CouncilPalace getPalace() {
        return palace;
    }

    public static ArrayList<Tower> getTowers() {
        return towers;
    }
}
