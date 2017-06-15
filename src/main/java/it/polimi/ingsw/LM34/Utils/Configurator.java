package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.*;
import it.polimi.ingsw.LM34.Model.Cards.*;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.FamilyMemberValueEffect;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.HalveServantsValue;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.MarketBan;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus.DevelopmentCardAcquireEffect;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.WorkingAreaValueEffect;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesPerItemBonus;
import it.polimi.ingsw.LM34.Model.Effects.SkipFirstTurn;
import it.polimi.ingsw.LM34.Model.Effects.VictoryPointsPenalty;
import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
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
    /*CONVENTION GAME_CODES*/
    private static final Integer BACK_OR_PASS = -1;


    public static final Integer WAITING_ROOM_TIMEOUT = 10000;
    public static final Integer WAITING_ROOM_PLAYERS_THRESHOLD = 2;
    public static final Integer PLAYER_MOVE_TIMEOUT = 2000;

    public static final Integer MAX_TOWER_LEVELS = 4;
    public static final Integer MAX_PLAYERS = 4;
    public static final Integer TOTAL_PERIODS = 3; //#total periods
    public static final Integer CARD_PER_ROUND = 4; //#development cards stored in a tower per round
    public static final Integer[] MIN_FAITHS_POINTS = {3,4,5};
    public static final Integer MAX_LEADER_PER_PLAYER = 4;
    public static final Integer BASE_COINS = 5; //#coins given to first player at game start
    public static final Integer COINS_INCREMENT_PLAYER_ORDER = 1;
    public static final Integer BASE_WOODS = 2; //#woods given to first player at game start
    public static final Integer WOODS_INCREMENT_PLAYER_ORDER = 0;
    public static final Integer BASE_STONES = 2; //#stones given to first player at game start
    public static final Integer STONES_INCREMENT_PLAYER_ORDER = 0;
    public static final Integer BASE_SERVANTS = 2; //#servants given to first player at game start
    public static final Integer SERVANTS_INCREMENT_PLAYER_ORDER = 0;

    private static Market market;
    private static CouncilPalace palace;
    private static ArrayList<Tower> towers;
    private static WorkingArea harvestArea;
    private static WorkingArea productionArea;
    //private static DevelopmentCardDeck<TerritoryCard> territoryDeck;
    private static ArrayList<TerritoryCard> territoryCards;
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

        /*try {
            setupMarket(jsonObject.getJSONObject("actionSlots").getJSONArray("market"));
            setupDevelopmentCards(jsonObject.getJSONObject("developmentCards"));
        } catch (Exception e) {e.printStackTrace();}*/
        setupDevelopmentCards(jsonObject.getJSONObject("developmentCards"));
    }

    private static void setupMarket(JSONArray market_array) {
        market = new Market(new ArrayList<>());
        for (int i = 0; i < market_array.length(); i++) {
            market.getMarketSlots().add(getActionSlotFromJson(market_array.getJSONObject(i)));
        }
    }

    private static void setupDevelopmentCards(JSONObject decks) {
        //System.out.println(decks.toString());
        //territoryCards = getTerritoryCardsFromJson(decks.getJSONArray("territories"));
        //buildingCards = getBuildingCardsFromJson(decks.getJSONArray("buildings"));
        //ventureCards = getVentureCardsFromJson(decks.getJSONArray("ventures"));
    }

    private static ActionSlot getActionSlotFromJson(JSONObject jsonObject) {
        Boolean singlePawnSlot = jsonObject.optBoolean("singlePawnSlot", true);
        Integer diceValue = jsonObject.optInt("diceValue", 0);
        //Integer councilPrivilege = jsonObject.getInt("councilPrivilege");
        ResourcesBonus resourcesBonus = getResourcesBonusFromJson(jsonObject); //TODO: test if this is correct
        //wrapper
        //ResourcesBonus resourcesBonus = new ResourcesBonus(resources, councilPrivilege);
        return new ActionSlot(singlePawnSlot, diceValue, resourcesBonus);
    }

    private static ResourcesBonus getResourcesBonusFromJson(JSONObject jsonResourcesBonus) {
        Map<ResourceType, Integer> resourcesMap = new HashMap<>();
        Integer councilPrivileges = 0;
        JSONObject jsonResources = jsonResourcesBonus.optJSONObject("resources");
            Integer value;
            if (jsonResources != null) {
                for (ResourceType type : ResourceType.values()) {
                    value = jsonResources.optInt(type.toString(), 0);
                    if (value != 0)
                        resourcesMap.put(type, value);
                }
            }
                councilPrivileges = jsonResourcesBonus.optInt("councilPrivilege", 0);
                System.out.println("privileges: " + councilPrivileges);

        Resources resources = new Resources(resourcesMap);
        return new ResourcesBonus(resources, councilPrivileges);
    }

    public static ArrayList<TerritoryCard> getTerritoryCardsFromJson(JSONArray jsonArray) {
        ArrayList<TerritoryCard> cards = new ArrayList<>();
        //System.out.println(jsonArray.toString());
        for(int i = 0; i < jsonArray.length(); i++) {
            cards.add(getTerritoryCardFromJson(jsonArray.getJSONObject(i)));
        }

        return cards;
    }

    private static TerritoryCard getTerritoryCardFromJson(JSONObject jsonObject) {
        System.out.println(jsonObject.toString());
        Integer period = jsonObject.optInt("period");
        String name = jsonObject.optString("name");

        Integer diceValueToHarvest = jsonObject.optInt("diceValueToHarvest");

        ResourcesBonus permanentResources = getResourcesBonusFromJson(jsonObject.getJSONObject("permanentBonus").getJSONObject("resourcesBonus"));
        //Integer councilPrivilege = jsonObject.getJSONObject("permanentBonus").getJSONObject("resourcesBonus").optInt("councliPrivilege");
        List<AbstractEffect> instantResources = new ArrayList<>();
        instantResources.add(getResourcesBonusFromJson(jsonObject.getJSONObject("instantBonus").getJSONObject("resourcesBonus").optJSONObject("resources")));


        return new TerritoryCard(name, diceValueToHarvest, period, instantResources, permanentResources);
    }



    public static ArrayList<BuildingCard> getBuildingCardsFromJson(JSONArray jsonArray) {
        ArrayList<BuildingCard> cards = new ArrayList<>();
        System.out.println(jsonArray.toString());
        for(int i = 0; i < jsonArray.length(); i++) {
            cards.add(getBuildingCardFromJson(jsonArray.getJSONObject(i)));
        }

        return cards;
    }

    private static BuildingCard getBuildingCardFromJson(JSONObject jsonObject) {
        System.out.println(jsonObject.toString());
        String name = jsonObject.optString("name");
        System.out.println(name);
        Integer period = jsonObject.optInt("period");
        Integer diceValueToProduct = jsonObject.optInt("diceValueToProduct");
        Resources resourcesRequired = getResourcesBonusFromJson(jsonObject.getJSONObject("requirements")).getResources();
        AbstractEffect permanentBonus = null;

        try{
            permanentBonus = getResourcesBonusFromJson(jsonObject.optJSONObject("permanentBonus").optJSONObject("resourcesBonus"));
        } catch (Exception e) {
            System.out.println("Campo resourcesBonus assente nella carta");
        }
        //Integer councilPrivilege = jsonObject.getJSONObject("permanentBonus").getJSONObject("resourcesBonus").optInt("councliPrivilege");
        List<AbstractEffect> instantResources = new ArrayList<>();
        instantResources.add(getResourcesBonusFromJson(jsonObject.getJSONObject("instantBonus").optJSONObject("resourcesBonus")));

        /*----ResourcePerItem----*/
       try {
           String color = jsonObject.optJSONObject("permanentBonus").optJSONObject("resourcesPerItemBonus").optString("developmentCardColor");
           Resources bonusResources;
           DevelopmentCardColor cardType = null;

           if(!color.isEmpty()) {
               bonusResources = getResourcesBonusFromJson(jsonObject.getJSONObject("permanentBonus")
                       .getJSONObject("resourcesPerItemBonus")).getResources();
               for (DevelopmentCardColor dc : DevelopmentCardColor.values())
                   if (color.equalsIgnoreCase(dc.toString()))
                       cardType = dc;
               instantResources.add(new ResourcesPerItemBonus(bonusResources, cardType));
           }

        } catch(Exception e) {
           System.out.println("Nessun effetto resourcesPerItemBonus in questa carta");
       }


        /*----ResourceExchangeBonus----*/
        JSONObject exchange = jsonObject.optJSONObject("permanentBonus");
        JSONArray resourcesExchangeBonus = null;
        Resources resToDiscard;
        ResourcesBonus resToGain;
        List<Pair<Resources, ResourcesBonus>> resExchanges = null;
        if(!exchange.isNull("resourcesExchangeBonus")) {
            resourcesExchangeBonus = exchange.getJSONArray("resourcesExchangeBonus");
            try {

                for (int i = 0; i < resourcesExchangeBonus.length(); i++) {
                    resToDiscard = getResourcesBonusFromJson(resourcesExchangeBonus.getJSONObject(i).getJSONObject("resourcesDiscard")).getResources();
                    resToGain = getResourcesBonusFromJson(resourcesExchangeBonus.getJSONObject(i).getJSONObject("resourcesGain"));
                }
            } catch (Exception e) {
                System.out.println("nessun campo resources exchange");
            }
            //TODO: resExchanges.add(new Pair(resToDiscard, resToGain));
        }
        //permanentBonus = new ResourcesExchangeBonus(resExchangesList);

        return new BuildingCard(name, diceValueToProduct, period, resourcesRequired, instantResources, permanentBonus);
    }


    public static ArrayList<VentureCard> getVentureCardsFromJson(JSONArray jsonArray) {
        System.out.println(jsonArray.length());
        ArrayList<VentureCard> cards = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            cards.add(getVentureCardFromJson(jsonArray.getJSONObject(i)));
        }

        return cards;
    }

    private static VentureCard getVentureCardFromJson(JSONObject jsonObject) {
        //System.out.println(jsonObject.toString());
        Integer period = jsonObject.optInt("period");
        String name = jsonObject.optString("name");
        List<AbstractEffect> instantBonus = new ArrayList<>(); //TODO
        Integer endingVictoryPointsReward = jsonObject.getJSONObject("permanentBonus").optInt("endingVictoryPointsReward");
        Integer militaryPointsRequired = 0;
        Integer militaryPointsSubtraction = 0;
        Resources resourcesRequired = new Resources();

        /***********RESOURCESBONUS********/
        try {
            instantBonus.add(getResourcesBonusFromJson(jsonObject.getJSONObject("instantBonus").getJSONObject("resourcesBonus")));
            System.out.println("pergamene: " + getResourcesBonusFromJson(jsonObject.getJSONObject("instantBonus").getJSONObject("resourcesBonus")).getCouncilPrivilege());
            militaryPointsRequired = jsonObject.getJSONObject("requirements").getJSONObject("MILITARY_POINTS").optInt("militaryPointsRequired");
            militaryPointsSubtraction = jsonObject.getJSONObject("requirements").getJSONObject("MILITARY_POINTS").optInt("militaryPointsSubtraction");
            resourcesRequired = getResourcesBonusFromJson(jsonObject.getJSONObject("requirements")).getResources();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***********WORKING AREA VALUE EFFECT********/
        Integer diceValue = 0;
        String areaType;
        ContextType workingAreaType;

        try {
            diceValue = jsonObject.getJSONObject("instantBonus").getJSONObject("workingAreaValueEffect").getInt("diceValue");
            areaType = jsonObject.getJSONObject("instantBonus").getJSONObject("workingAreaValueEffect").optString("workingAreaType");

            if(areaType.equalsIgnoreCase("PRODUCTION"))
                workingAreaType = ContextType.PRODUCTION_AREA_CONTEXT;
            else if (areaType.equalsIgnoreCase("HARVEST"))
                workingAreaType = ContextType.HARVEST_AREA_CONTEXT;
            else
                throw new Exception("Errore nella working area caricata da file per carta " + name.toString());
            instantBonus.add(new WorkingAreaValueEffect(workingAreaType, diceValue, false));
        } catch (Exception e) {
        }
        /***********DEVELOPMENT CARD ACQUIRE EFFECT********/
        String cardColor;
        DevelopmentCardColor color = null;
        diceValue = 0; //reset dice value if it has been incremented by working area value effect
        try {
            diceValue = jsonObject.getJSONObject("instantBonus").getJSONObject("developmentCardAcquireEffect").getInt("diceValue");
            cardColor = jsonObject.getJSONObject("instantBonus").getJSONObject("developmentCardAcquireEffect").getString("developmentCardColor");
            for(DevelopmentCardColor c : DevelopmentCardColor.values())
                if(c.getDevType().equalsIgnoreCase(cardColor))
                    color = c;

            instantBonus.add(new DevelopmentCardAcquireEffect(color, diceValue, false));
        } catch(Exception e) {
            //System.out.println(e.getCause());
        }

        return new VentureCard(name, period, militaryPointsRequired, militaryPointsSubtraction, resourcesRequired,  instantBonus, endingVictoryPointsReward);
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
    public static Market getMarket(Integer numPlayers) {
        return market;
    }

    public static ArrayList<Tower> getTowers(Integer numPlayers) {
        return towers;
    }
    public static WorkingArea getHarvestArea(Integer numPlayers) {
        return harvestArea;
    }
    public static WorkingArea getProductionArea(Integer numPlayers) {
        return productionArea;
    }

    public static DevelopmentCardDeck<TerritoryCard> getTerritoryCards() {
        //System.out.println(territoryCards.toArray().length);
        return new DevelopmentCardDeck<TerritoryCard>(territoryCards);
    }
    public static DevelopmentCardDeck<BuildingCard> getBuildingCards() {
        return new DevelopmentCardDeck<>(buildingCards);
    }
    public static DevelopmentCardDeck<CharacterCard> getCharactersCards() {
        return new DevelopmentCardDeck<CharacterCard>(characterCards);
    }
    public static DevelopmentCardDeck<VentureCard> getVentureCards() {
        return new DevelopmentCardDeck<>(ventureCards);
    }
    public static List<LeaderCard> getLeaderCards(Integer numPlayers) {
        return leaderCards;
    }
    public static List<ExcommunicationCard> getExcommunicationTiles() {
        return excommunicationTiles;
    }

    public static DevelopmentCardDeck<? extends AbstractDevelopmentCard> prepareDevelopmentDeck() {

        DevelopmentCardDeck<?> deck = new DevelopmentCardDeck<>();

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


    private ExcommunicationCard getExcommunicationCardFromJson(JSONObject tile) {
        Integer period = tile.getInt("period");
        JSONObject jsonPenalty = tile.getJSONObject("penalty");
        AbstractEffect penalty = null;

        /******Resource Income Penalty******/
        if(tile.getJSONObject("resourcesIncomePenalty") != null) {
            penalty = getResourcesBonusFromJson(jsonPenalty.getJSONObject("resourcesIncomePenalty"));

            return new ExcommunicationCard(period, penalty);
        }


        /******Working Area Value Negative Effect*****/
        if(jsonPenalty.getJSONObject("workingAreaValueEffect") != null) {
            /***********WORKING AREA VALUE EFFECT********/
            Integer diceValue = 0;
            String areaType;
            ContextType workingAreaType = null;

                diceValue = jsonPenalty.getJSONObject("workingAreaValueEffect").getInt("diceValue");
                areaType = jsonPenalty.getJSONObject("workingAreaValueEffect").optString("workingAreaType");

                if(areaType.equalsIgnoreCase("PRODUCTION"))
                    workingAreaType = ContextType.PRODUCTION_AREA_CONTEXT;
                else if (areaType.equalsIgnoreCase("HARVEST"))
                    workingAreaType = ContextType.HARVEST_AREA_CONTEXT;

              penalty = new WorkingAreaValueEffect(workingAreaType, diceValue, false);

              return new ExcommunicationCard(period, penalty);
        }


        /********Family Member Value Effect*******/
        JSONArray familyMemberValueEffect = jsonPenalty.getJSONArray("FamilyMemberValueEffect");
        JSONObject tempJson;
        String tempStringColor;
        List<DiceColor> diceColors = new ArrayList<>();
        Boolean isRelative;
        Integer diceValue = 0;
        if(familyMemberValueEffect != null) {
            for (Integer index = 0; index < familyMemberValueEffect.length(); index++) {
                tempJson = familyMemberValueEffect.getJSONObject(index);
                tempStringColor = tempJson.getString("diceColor");
                for (DiceColor c : DiceColor.values())
                    if (c.toString().equalsIgnoreCase(tempStringColor))
                        diceColors.add(c);
                diceValue = tempJson.optInt("diceValue", 0);
                isRelative = tempJson.optBoolean("relative", false);

                penalty = new FamilyMemberValueEffect(diceColors, diceValue, isRelative);
            }
        }

        /**********Development Acquire Value Effect*******/     String cardColor;
        DevelopmentCardColor color = null;
        diceValue = jsonPenalty.getJSONObject("developmentCardAcquireEffect").getInt("diceValue");
        tempStringColor = jsonPenalty.getJSONObject("developmentCardAcquireEffect").optString("developmentCardColor");
        if(jsonPenalty.getJSONObject("developmentCardAcquireEffect") !=null) {
            diceValue = 0; //reset dice value if it has been incremented by working area value effect
            for (DevelopmentCardColor c : DevelopmentCardColor.values())
                if (c.toString().equalsIgnoreCase(tempStringColor))
                    color = c;

            penalty = new DevelopmentCardAcquireEffect(color, diceValue, true);

        }

        /****MarketBan****/
        if(jsonPenalty.getJSONObject("marketBan") != null)
            penalty = new MarketBan();

        /****SkipFirstTurn****/
        if(jsonPenalty.getJSONObject("skipFirstTurn") != null)
            penalty = new SkipFirstTurn();

        /****HalveServantsValue****/
        if(jsonPenalty.getJSONObject("halveServantsValue") != null)
            penalty = new HalveServantsValue();

        /****VictoryPointsPenalty****/
        JSONObject jsonVictoryPenalty;
        Integer victoryPoints = 0;
        Resources resources;
        if(jsonPenalty.getJSONObject("victoryPointsPenalty") != null) {
            jsonVictoryPenalty = jsonPenalty.getJSONObject("victoryPointsPenalty");

            victoryPoints = jsonVictoryPenalty.optInt("VICTORY_POINTS", 0);

            if(jsonVictoryPenalty.getJSONObject("resources") != null) {
                resources = getResourcesBonusFromJson(jsonVictoryPenalty.getJSONObject("resources")).getResources();

                penalty = new VictoryPointsPenalty(victoryPoints, resources);
            }

            //TODO:
            if(jsonVictoryPenalty.getString("developmentCardColor") != null) {
                tempStringColor = jsonVictoryPenalty.getString("developmentCardColor");
                for (DevelopmentCardColor c : DevelopmentCardColor.values())
                    if (c.toString().equalsIgnoreCase(tempStringColor))
                        color = c;

                penalty = new VictoryPointsPenalty(color);
            }

            if(jsonVictoryPenalty.getString("playerGoods") != null) {
                resources = new Resources(victoryPoints, victoryPoints, victoryPoints, victoryPoints);

                penalty = new VictoryPointsPenalty(victoryPoints, resources);
            }

            //TODO
            if(jsonVictoryPenalty.getString("buildingCardsResources") != null) {
                resources = new Resources(0,1,1,0);

                penalty = new VictoryPointsPenalty(victoryPoints, resources);



             }
        }

        return new ExcommunicationCard(period, penalty);
    }

    //MAIN WITH THE PURPOSE TO VERIFY THE CORRECT LOADING OF MODEL OBJECTS FROM FILE
    public static void main(String[] args) {
        Configurator.loadConfigs();
    }
}
