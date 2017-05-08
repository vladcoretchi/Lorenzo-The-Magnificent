package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Model.Board.GameBoard.ActionSlot;
import java.io.FileInputStream;
import java.util.ArrayList;
import it.polimi.ingsw.LM34.Model.Enum.ResourceType;
import it.polimi.ingsw.LM34.Model.Resources;

import org.json.*;
import org.apache.commons.io.IOUtils;

/**
 * Created by GiulioComi on 07/05/2017.
 * config parameters loaded from file as static variables
 */
public final class Configurator {

    private static ArrayList<ActionSlot> marketSlots;

    public static ArrayList<ActionSlot> getMarketSlots() {return marketSlots;}


    public static void loadConfigs() {
        JSONObject jsonObject=null;
        try {
            String jsonString = IOUtils.toString(new FileInputStream("C:\\Users\\Julius\\Desktop\\ProvaFinale\\configurations.json"));
            jsonObject = new JSONObject(jsonString).getJSONObject("configuration");
        } catch (Exception e) {e.printStackTrace();}

        setupMarket(jsonObject);

    }

    private static void setupMarket(JSONObject jsonObject) {
        marketSlots = new ArrayList<>();
        JSONArray market = jsonObject.getJSONObject("actionSlots").getJSONArray("market");
        for (int i = 0; i < market.length(); i++) {
            marketSlots.add(getActionSlotFromJson(market.getJSONObject(i).getJSONObject("resources")));

        }
    }

    private static ActionSlot getActionSlotFromJson(JSONObject jsonObject) {
        Integer[] resourcesArray = new Integer[jsonObject.length()];
        resourcesArray[0]= jsonObject.getInt("coins");
        resourcesArray[1]= jsonObject.getInt("woods");
        resourcesArray[2]= jsonObject.getInt("stones");
        resourcesArray[3]= jsonObject.getInt("faithPoints");
        resourcesArray[4]= jsonObject.getInt("servants");
        resourcesArray[5]= jsonObject.getInt("militaryPoints");
        resourcesArray[6]= jsonObject.getInt("victoryPoints");
        Integer numberCouncilPrivilege = jsonObject.getInt("councilPrivilege");

        Resources resources= new Resources(resourcesArray[0], resourcesArray[1], resourcesArray[2], resourcesArray[3], resourcesArray[4], resourcesArray[5], resourcesArray[6]);
        return new ActionSlot(resources, numberCouncilPrivilege);
    }
    private static void printMarket() {
        Resources res = null;
        for (Integer i = 0; i < marketSlots.size(); i++) {
            res = marketSlots.get(i).getResourcesReward();
            try {

                System.out.println((res.getResourceByType(ResourceType.STONES)));
                System.out.println((res.getResourceByType(ResourceType.SERVANTS)));
                System.out.println((res.getResourceByType(ResourceType.WOODS)));
                System.out.println((res.getResourceByType(ResourceType.COINS)));
                System.out.println((res.getResourceByType(ResourceType.FAITH_POINTS)));
                System.out.println((res.getResourceByType(ResourceType.MILITARY_POINTS)));
                System.out.println((res.getResourceByType(ResourceType.VICTORY_POINTS)));
                System.out.println(marketSlots.get(i).getCouncilPrivilege());
                System.out.println("-----------------------------------------");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        Configurator.loadConfigs();
        Configurator.printMarket();



    }
}
