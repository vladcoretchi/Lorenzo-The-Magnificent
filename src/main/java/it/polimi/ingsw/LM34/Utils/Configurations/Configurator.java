package it.polimi.ingsw.LM34.Utils.Configurations;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.*;
import it.polimi.ingsw.LM34.Model.Cards.*;
import it.polimi.ingsw.LM34.Model.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by GiulioComi on 07/05/2017.
 * config parameters loaded from file as static variables
 */
public final class Configurator {

    public static final Integer TOTAL_PERIODS = 3; //#total periods
    public static final Integer CARD_PER_ROUND = 4; //#development cards stored in a tower per round
    public static final Integer BASE_COINS = 5; //#coins given to first player at the starting of the game

    private static Market market;
    private static CouncilPalace palace;
    private static ArrayList<Tower> towers;
    private static WorkingArea harvestArea;
    private static WorkingArea productionArea;
    private static List<TerritoryCard> territoryCards;
    private static List<BuildingCard> buildingCards;
    private static List<CharacterCard> characterCards;
    private static List<VentureCard> ventureCards;
    private static List<LeaderCard> leaderCards;
    private static List<ExcommunicationCard> excommunicationTiles;


    public static void loadConfigs() {
        JSONObject jsonObject = null;

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            File file = new File(loader.getResource("configurations/config.json").getFile());
            InputStream inputStream = new FileInputStream(file);
            String jsonString  = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            jsonObject = new JSONObject(jsonString).getJSONObject("configuration");
        } catch (Exception e) {e.printStackTrace();}

        try {
            //setupMarket(jsonObject.getJSONObject("actionSlots").getJSONArray("market"));
            setupDevelopmentCards(jsonObject.getJSONObject("developmentCards"));
        } catch (Exception e) {e.printStackTrace();}
    }

    private static void setupMarket(JSONArray market_array) { //(JSONObject jsonObject) {
        market = new Market(new ArrayList<>());
        for (int i = 0; i < market_array.length(); i++) {
            market.addSlot(getActionSlotFromJson(market_array.getJSONObject(i)));
        }
    }

    private static void setupDevelopmentCards(JSONObject jsonObject) {
        territoryCards = getTerritoryCardsFromJson(jsonObject.getJSONArray("territories"));
    }

    private static ActionSlot getActionSlotFromJson(JSONObject jsonObject) {
        Boolean singlePawnSlot = jsonObject.optBoolean("singlePawnSlot", true);
        Integer diceValue = jsonObject.optInt("diceValue", 0);
        Integer councilPrivilege = jsonObject.getInt("councilPrivilege");
        Resources resources = getResourcesFromJson(jsonObject.getJSONObject("resources"));
        //wrapper
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, councilPrivilege);
        return new ActionSlot(singlePawnSlot, diceValue, resourcesBonus);
    }

    private static Resources getResourcesFromJson(JSONObject jsonResources) {
        Map<ResourceType, Integer> resourcesMap = new HashMap<>();

        if(jsonResources != null) {
            Integer value;
            for (ResourceType type : ResourceType.values()) {
                value = jsonResources.optInt(type.toString(), 0);
                if (value != 0)
                    resourcesMap.put(type, value);
            }
        }

        return new Resources(resourcesMap);
    }

    public static List<TerritoryCard> getTerritoryCardsFromJson(JSONArray jsonArray) {
        ArrayList<TerritoryCard> territoryCards = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            territoryCards.add(getTerritoryCardFromJson(jsonArray.getJSONObject(i)));
        }

        return territoryCards;
    }

    private static TerritoryCard getTerritoryCardFromJson(JSONObject jsonObject) {
        String name = jsonObject.optString("name");
        Integer period = jsonObject.optInt("period");
        Integer diceValueToHarvest = jsonObject.optInt("diceValueToHarvest");
        Resources resources = getResourcesFromJson(jsonObject.getJSONObject("permanentBonus").getJSONObject("resourcesBonus").optJSONObject("resources"));
        Integer councilPrivilege = jsonObject.getJSONObject("permanentBonus").getJSONObject("resourcesBonus").optInt("councliPrivilege");

        ResourcesBonus permanentBonus = new ResourcesBonus(resources, councilPrivilege);

        return new TerritoryCard(name, diceValueToHarvest, period, null, permanentBonus);
    }


    //MAIN WITH THE PURPOSE TO VERIFY THE CORRECT LOADING OF MODEL OBJECTS FROM FILE
    public static void main(String[] args) {
        Configurator.loadConfigs();


    }



    public static Market getMarket() {
        return market;
    }

    public static CouncilPalace getPalace() {
        return palace;
    }

    public static ArrayList<Tower> getTowers() {
        return towers;
    }

    public static WorkingArea getHarvestArea() {
        return harvestArea;
    }

    public static WorkingArea getProductionArea() {
        return productionArea;
    }

    public static DevelopmentCardDeck<? extends AbstractDevelopmentCard> prepareDevelopmentDeck() {

        DevelopmentCardDeck<?> deck = new DevelopmentCardDeck<>();
        //TODO: load from json all the cards

        deck.setupDevelopmentCardDeck();
        return deck;
    }



    public static void prepareLeaderAndExcommunicationDecks(ArrayList<LeaderCard> leaderCards, ArrayList<ExcommunicationCard> excommunicationCards) {
        //TODO: Load from json all cards
        Collections.shuffle(leaderCards);
        Collections.shuffle(excommunicationCards);
        orderExcommunicatioCardByPeriod(excommunicationCards);
    }


    public static void orderExcommunicatioCardByPeriod(ArrayList<ExcommunicationCard> exc) {
        ArrayList<ExcommunicationCard> temp = new ArrayList();

        for (Integer period = 1; period <= Configurator.TOTAL_PERIODS; period++)
            for (ExcommunicationCard e : exc)
                if (e.getPeriod() == period)
                    temp.add(e);
        exc = temp;
    }

    public static List<TerritoryCard> getTerritoryCards() {
        return territoryCards;
    }
}
