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
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus.TowerSlotPenalty;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.WorkingAreaValueEffect;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesPerItemBonus;
import it.polimi.ingsw.LM34.Model.Effects.SkipFirstTurn;
import it.polimi.ingsw.LM34.Model.Effects.VictoryPointsPenalty;
import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.BLUE;
import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.MULTICOLOR;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.COINS;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.MILITARY_POINTS;

/**
 * Created by GiulioComi on 07/05/2017.
 * config parameters loaded from file as static variables
 */
public final class Configurator {
    /*CONVENTION GAME_CODES*/
    private static final Integer BACK_OR_PASS = -1;
    public static Integer WAITING_ROOM_TIMEOUT;
    public static final Integer WAITING_ROOM_PLAYERS_THRESHOLD = 2;

    public static final Integer PLAYER_MOVE_TIMEOUT = 2000;
    public static final Integer MAX_TOWER_LEVELS = 4;
    public static final Integer MAX_LEADER_PER_PLAYER = 4;
    public static final Integer MAX_PLAYERS = 4;
    public static final Integer TOTAL_PERIODS = 3; //#total periods
    public static final Integer[] MIN_FAITHS_POINTS = {3,4,5};
    public static final Integer CARD_PER_ROUND = 4;
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
    private static List<Tower> towers;
    private static WorkingArea harvestArea;
    private static WorkingArea productionArea;
    private static List<TerritoryCard> territoryCards;
    private static List<BuildingCard> buildingCards;
    private static List<CharacterCard> characterCards;
    private static List<VentureCard> ventureCards;
    private static List<LeaderCard> leaderCards;
    private static List<ExcommunicationCard> excommunicationTiles;
    private static JSONObject jsonObject;
    private static List<Resources> councilPrivilegeRewards;
    private static Map<Integer, Integer> faithPath;
    private static Map<Integer, Integer> endingGameCharactersVictoryPoints;
    private static Map<Integer, Integer> endingGameTerritoriesVictoryPoints;
    private static Integer resourcesForVictoryPoints;

    public static void loadConfigs() {
        JSONObject jsonObject = null;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            File file = new File(loader.getResource("configurations/config.json").getFile());
            InputStream inputStream = new FileInputStream(file);
            String jsonString  = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            jsonObject = new JSONObject(jsonString).optJSONObject("configuration");
        } catch (Exception e) {e.printStackTrace();}

        /****GameRoom Timeout****/
        WAITING_ROOM_TIMEOUT = jsonObject.getJSONObject("server").getInt("timeout");
        print(WAITING_ROOM_TIMEOUT.toString());

        setupGame(jsonObject.getJSONObject("game"));
        setupPersonalTiles(jsonObject);
        setupMarket(jsonObject.optJSONObject("actionSlots").optJSONArray("market"));
        setupProductionArea(jsonObject.optJSONObject("actionSlots").optJSONArray("productionArea"));
        setupHarvestArea(jsonObject.optJSONObject("actionSlots").optJSONArray("harvestArea"));
        setupCouncilPalace(jsonObject.optJSONObject("actionSlots").optJSONObject("councilPalace"));
        setupTowers(jsonObject.optJSONObject("actionSlots").optJSONArray("towers"));
        setupDevelopmentCards(jsonObject.optJSONObject("developmentCards"));
        setupExcommunicationTiles(jsonObject.optJSONObject("developmentCards").optJSONArray("excommunicationTiles"));
    }

    private static void setupPersonalTiles(JSONObject jsonObject) {

    }

    private static void setupGame(JSONObject jsonObject) {

        /****Rewards given by council Privileges*/
        JSONArray jsonPrivilegeArray = jsonObject.getJSONArray("councilPrivilege");
        councilPrivilegeRewards = new ArrayList<>();
        for(Integer index = 0; index < jsonPrivilegeArray.length(); index++)
            councilPrivilegeRewards.add(getResourcesBonusFromJson(jsonPrivilegeArray.getJSONObject(index)).getResources());

        /****Fait Path****/
        faithPath = new HashMap<>();
        JSONArray jsonPathArray = jsonObject.getJSONObject("finalVictoryPoints").getJSONArray("faithPath");
        for(Integer index = 0; index < jsonPathArray.length(); index++)
            faithPath.put(index, jsonPathArray.getJSONObject(index).getInt(index.toString()));

        /****Ending game victory points by number of Territories****/
        endingGameTerritoriesVictoryPoints = new HashMap<>();
        JSONArray jsonTerritoriesPointsArray = jsonObject.getJSONObject("finalVictoryPoints").getJSONArray("territories");
        for(Integer index = 0; index < jsonTerritoriesPointsArray.length(); index++)
            endingGameTerritoriesVictoryPoints.put(index, jsonTerritoriesPointsArray.getJSONObject(index).getInt(index.toString()));

        /****Ending game victory points by number of Characters****/
        endingGameCharactersVictoryPoints = new HashMap<>();
        JSONArray jsonCharactersPointsArray = jsonObject.getJSONObject("finalVictoryPoints").getJSONArray("characters");
        for(Integer index = 0; index < jsonCharactersPointsArray.length(); index++)
            endingGameCharactersVictoryPoints.put(index, jsonCharactersPointsArray.getJSONObject(index).getInt(index.toString()));

        /****Ending game victory points for the amount of resources specifiec****/
        resourcesForVictoryPoints = jsonObject.getJSONObject("finalVictoryPoints").getInt("resourcesForVictoryPoint");


    }

    private static void setupExcommunicationTiles(JSONArray jsonArray) {
        excommunicationTiles = new ArrayList<>();
        for(Integer index = 0; index < jsonArray.length(); index++)
            excommunicationTiles.add(getExcommunicationCardFromJson(jsonArray.optJSONObject(index)));
    }

    private static void setupTowers(JSONArray jsonTowersSlots) {
        Integer index = 0;
        Integer iteration = 1;
        towers = new ArrayList<>();
        Tower tower = new Tower(BLUE);
        for(DevelopmentCardColor type : DevelopmentCardColor.values()) {
            if (type != MULTICOLOR) {
                tower = new Tower(type);
                for (; index < MAX_TOWER_LEVELS * iteration; index++) {
                    tower.getTowerSlots().add(getTowerSlotFromJson(jsonTowersSlots.optJSONObject(index)));
                }
            }
            iteration++;
            towers.add(tower);
        }
    }

    private static TowerSlot getTowerSlotFromJson(JSONObject jsonObject) {
        Boolean singlePawnSlot = jsonObject.optBoolean("singlePawnSlot", true);
        Integer diceValue = jsonObject.optInt("diceValue", 0);
        Integer councilPrivilege = jsonObject.getInt("councilPrivilege");
        Resources resources = getResourcesBonusFromJson(jsonObject).getResources();
        //wrapper
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, councilPrivilege);
        return new TowerSlot(singlePawnSlot, diceValue, resourcesBonus);
    }

    private static void setupMarket(JSONArray market_array) {
        market = new Market(new ArrayList<>());
        for (int i = 0; i < market_array.length(); i++) {
            market.getMarketSlots().add(getActionSlotFromJson(market_array.optJSONObject(i)));
        }
    }

    private static void setupDevelopmentCards(JSONObject decks) {
        territoryCards = getTerritoryCardsFromJson(decks.optJSONArray("territories"));
        buildingCards = getBuildingCardsFromJson(decks.optJSONArray("buildings"));
        ventureCards = getVentureCardsFromJson(decks.optJSONArray("ventures"));
        characterCards = getCharacterCardsFromJson(decks.optJSONArray("characters"));
    }

    private static ActionSlot getActionSlotFromJson(JSONObject jsonObject) {
        Boolean singlePawnSlot = jsonObject.optBoolean("singlePawnSlot", true);
        Integer diceValue = jsonObject.optInt("diceValue", 0);
        Integer councilPrivilege = jsonObject.getInt("councilPrivilege");
        Resources resources = getResourcesBonusFromJson(jsonObject).getResources();
        //wrapper
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, councilPrivilege);
        return new ActionSlot(singlePawnSlot, diceValue, resourcesBonus);
    }

    private static ResourcesBonus getResourcesBonusFromJson(JSONObject jsonResourcesBonus) {
        Map<ResourceType, Integer> resourcesMap = new HashMap<>();
        Integer councilPrivileges = 0;
        JSONObject jsonResources;
        Integer value;
        if(jsonResourcesBonus.optJSONObject("resources") != null) {
             jsonResources = jsonResourcesBonus.optJSONObject("resources");
            for (ResourceType type : ResourceType.values()) {
                value = jsonResources.optInt(type.toString(), 0);
                if (value != 0)
                    resourcesMap.put(type, value);
            }
        }

        councilPrivileges = jsonResourcesBonus.optInt("councilPrivilege", 0);
        Resources resources = new Resources(resourcesMap);

        return new ResourcesBonus(resources, councilPrivileges);
    }

    private static List<TerritoryCard> getTerritoryCardsFromJson(JSONArray jsonArray) {
        ArrayList<TerritoryCard> cards = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            cards.add(getTerritoryCardFromJson(jsonArray.optJSONObject(i)));
        }
        return cards;
    }

    private static TerritoryCard getTerritoryCardFromJson(JSONObject jsonObject) {
        Integer period = jsonObject.optInt("period");
        String name = jsonObject.optString("name");

        Integer diceValueToHarvest = jsonObject.optInt("diceValueToHarvest");

        ResourcesBonus permanentResources = getResourcesBonusFromJson(jsonObject.optJSONObject("permanentBonus").optJSONObject("resourcesBonus"));
        //Integer councilPrivilege = jsonObject.optJSONObject("permanentBonus").optJSONObject("resourcesBonus").optInt("councliPrivilege");
        List<AbstractEffect> instantResources = new ArrayList<>();
        instantResources.add(getResourcesBonusFromJson(jsonObject.optJSONObject("instantBonus").optJSONObject("resourcesBonus").optJSONObject("resources")));

        return new TerritoryCard(name, diceValueToHarvest, period, instantResources, permanentResources);
    }

    private static List<BuildingCard> getBuildingCardsFromJson(JSONArray jsonArray) {
        List<BuildingCard> cards = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            cards.add(getBuildingCardFromJson(jsonArray.optJSONObject(i)));
        }

        return cards;
    }

    private static BuildingCard getBuildingCardFromJson(JSONObject jsonObject) {
        String name = jsonObject.optString("name");
        Integer period = jsonObject.optInt("period");
        Integer diceValueToProduct = jsonObject.optInt("diceValueToProduct");
        Resources resourcesRequired = getResourcesBonusFromJson(jsonObject.optJSONObject("requirements")).getResources();
        AbstractEffect permanentBonus = new ResourcesBonus(new Resources(0,0,0,0), 0);
        List<AbstractEffect> instantResources = new ArrayList<>();
        String color = new String();
        Resources bonusResources;

        if (jsonObject.optJSONObject("instantBonus").optJSONObject("resourcesBonus") != null)
            instantResources.add(getResourcesBonusFromJson(jsonObject.optJSONObject("instantBonus").optJSONObject("resourcesBonus")));

        if(jsonObject.optJSONObject("permanentBonus") != null) {
            JSONObject jsonPermanentBonus = jsonObject.optJSONObject("permanentBonus");

            if (jsonPermanentBonus.optJSONObject("resourcesBonus") != null)
                permanentBonus = getResourcesBonusFromJson(jsonObject.optJSONObject("permanentBonus").optJSONObject("resourcesBonus"));

           /*----ResourcePerItem----*/
            if (jsonPermanentBonus.optJSONObject("resourcesPerItemBonus") != null)
                color = jsonPermanentBonus.optJSONObject("resourcesPerItemBonus").optString("developmentCardColor");

            DevelopmentCardColor cardType = null;
            if (!color.isEmpty()) {
                bonusResources = getResourcesBonusFromJson(jsonPermanentBonus
                        .optJSONObject("resourcesPerItemBonus")).getResources();
                for (DevelopmentCardColor dc : DevelopmentCardColor.values())
                    if (color.equalsIgnoreCase(dc.toString()))
                        cardType = dc;
                instantResources.add(new ResourcesPerItemBonus(bonusResources, cardType));
            }

           /*----ResourceExchangeBonus----*/
            JSONArray resourcesExchangeBonus = null;
            Resources resToDiscard = new Resources(0,0,0,0);
            ResourcesBonus resToGain = new ResourcesBonus(new Resources(0,0,0,0), 0);
            List<Pair<Resources, ResourcesBonus>> resExchanges = new ArrayList<>();

            if (!jsonPermanentBonus.isNull("resourcesExchangeBonus")) {
                resourcesExchangeBonus = jsonPermanentBonus.optJSONArray("resourcesExchangeBonus");
                for (int i = 0; i < resourcesExchangeBonus.length(); i++) {
                    resToDiscard = getResourcesBonusFromJson(resourcesExchangeBonus.optJSONObject(i).optJSONObject("resourcesDiscard")).getResources();
                    resToGain = getResourcesBonusFromJson(resourcesExchangeBonus.optJSONObject(i).optJSONObject("resourcesGain"));
                }
            resExchanges.add(new ImmutablePair(resToDiscard, resToGain));
            }
        }
        return new BuildingCard(name, diceValueToProduct, period, resourcesRequired, instantResources, permanentBonus);
    }

    public static List<VentureCard> getVentureCardsFromJson(JSONArray jsonArray) {
        List<VentureCard> cards = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            cards.add(getVentureCardFromJson(jsonArray.optJSONObject(i)));
        }

        return cards;
    }

    private static VentureCard getVentureCardFromJson(JSONObject jsonObject) {
        Integer period = jsonObject.optInt("period");
        String name = jsonObject.optString("name");
        List<AbstractEffect> instantBonus = new ArrayList<>();
        Integer endingVictoryPointsReward = jsonObject.optJSONObject("permanentBonus").optInt("endingVictoryPointsReward");
        Integer militaryPointsRequired = 0;
        Integer militaryPointsSubtraction = 0;
        Resources resourcesRequired = new Resources();
        resourcesRequired = getResourcesBonusFromJson(jsonObject.optJSONObject("requirements")).getResources();
        /***********RESOURCESBONUS********/
        if(jsonObject.optJSONObject("instantBonus") != null) {
            if(jsonObject.optJSONObject("instantBonus").optJSONObject("resourcesBonus") != null)
                instantBonus.add(getResourcesBonusFromJson(jsonObject.optJSONObject("instantBonus").optJSONObject("resourcesBonus")));
            if(jsonObject.optJSONObject("requirements").optJSONObject("MILITARY_POINTS") != null) {
                militaryPointsRequired = jsonObject.optJSONObject("requirements").optJSONObject("MILITARY_POINTS").optInt("militaryPointsRequired");
                militaryPointsSubtraction = jsonObject.optJSONObject("requirements").optJSONObject("MILITARY_POINTS").optInt("militaryPointsSubtraction");

            }

            /***********WORKING AREA VALUE EFFECT********/
            Integer diceValue = 0;
            String areaType;
            ContextType workingAreaType;

            if (jsonObject.optJSONObject("instantBonus").optJSONObject("workingAreaValueEffect") != null) {
                diceValue = jsonObject.optJSONObject("instantBonus").optJSONObject("workingAreaValueEffect").getInt("diceValue");
                areaType = jsonObject.optJSONObject("instantBonus").optJSONObject("workingAreaValueEffect").optString("workingAreaType");

                if (areaType.equalsIgnoreCase("PRODUCTION"))
                    workingAreaType = ContextType.PRODUCTION_AREA_CONTEXT;
                else
                    workingAreaType = ContextType.HARVEST_AREA_CONTEXT;

                instantBonus.add(new WorkingAreaValueEffect(workingAreaType, diceValue, false));
            }
            /***********DEVELOPMENT CARD ACQUIRE EFFECT********/
            String cardColor;
            DevelopmentCardColor color = null;
            diceValue = 0; //reset dice value if it has been incremented by working area value effect
            if (jsonObject.optJSONObject("instantBonus").optJSONObject("developmentCardAcquireEffect") != null) {
                diceValue = jsonObject.optJSONObject("instantBonus").optJSONObject("developmentCardAcquireEffect").getInt("diceValue");
                cardColor = jsonObject.optJSONObject("instantBonus").optJSONObject("developmentCardAcquireEffect").getString("developmentCardColor");
                for (DevelopmentCardColor c : DevelopmentCardColor.values())
                    if (c.getDevType().equalsIgnoreCase(cardColor))
                        color = c;

                instantBonus.add(new DevelopmentCardAcquireEffect(color, diceValue, false));
            }
        }
        return new VentureCard(name, period, militaryPointsRequired, militaryPointsSubtraction, resourcesRequired,  instantBonus, endingVictoryPointsReward);
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

    public static void prepareLeaderAndExcommunicationDecks(List<LeaderCard> leaderCards, List<ExcommunicationCard> excommunicationCards) {
        Collections.shuffle(leaderCards);
        Collections.shuffle(excommunicationCards);
        orderExcommunicatioCardByPeriod(excommunicationCards);
    }

    public static void orderExcommunicatioCardByPeriod(List<ExcommunicationCard> exc) {
        List<ExcommunicationCard> temp = new ArrayList();

        for (Integer period = 1; period <= Configurator.TOTAL_PERIODS; period++)
            for (ExcommunicationCard e : exc)
                if (e.getPeriod() == period)
                    temp.add(e);
        exc = temp;
    }

    private static void setupProductionArea(JSONArray productionSlotsJson) {
        ActionSlot singleSlot = getActionSlotFromJson(productionSlotsJson.optJSONObject(0));
        List<ActionSlot> advancedSlots = new ArrayList<>();
        advancedSlots.add(getActionSlotFromJson(productionSlotsJson.optJSONObject(1)));

        productionArea = new WorkingArea(singleSlot, advancedSlots);
    }

    private static void setupHarvestArea(JSONArray harvestSlotsJson) {
        ActionSlot singleSlot = getActionSlotFromJson(harvestSlotsJson.optJSONObject(0));
        List<ActionSlot> advancedSlots = new ArrayList<>();
        for( int i = 1; i < harvestSlotsJson.length(); i++)
            advancedSlots.add(getActionSlotFromJson(harvestSlotsJson.optJSONObject(i)));

        harvestArea = new WorkingArea(singleSlot, advancedSlots);
    }

    private static void setupCouncilPalace(JSONObject councilPalaceJson) {
       palace = new CouncilPalace(getActionSlotFromJson(councilPalaceJson));
    }

    private static ExcommunicationCard getExcommunicationCardFromJson(JSONObject tile) {
        Integer number = tile.getInt("number");
        Integer period = tile.getInt("period");
        JSONObject jsonPenalty = tile.optJSONObject("penalty");
        AbstractEffect penalty = null;

        /******RESOURCE INCOME PENALTY******/
        if(jsonPenalty.optJSONObject("resourcesIncomePenalty") != null) {
            penalty = getResourcesBonusFromJson(jsonPenalty.optJSONObject("resourcesIncomePenalty"));
        }

        if(jsonPenalty.optJSONObject("workingAreaValueEffect") != null) {
            /********WORKING AREA VALUE EFFECT********/
            Integer diceValue = 0;
            String areaType;
            ContextType workingAreaType = null;

                diceValue = jsonPenalty.optJSONObject("workingAreaValueEffect").getInt("diceValue");
                areaType = jsonPenalty.optJSONObject("workingAreaValueEffect").optString("workingAreaType");

                if(areaType.equalsIgnoreCase("PRODUCTION"))
                    workingAreaType = ContextType.PRODUCTION_AREA_CONTEXT;
                else if (areaType.equalsIgnoreCase("HARVEST"))
                    workingAreaType = ContextType.HARVEST_AREA_CONTEXT;

            penalty = new WorkingAreaValueEffect(workingAreaType, diceValue, false);
        }

        /********Family Member Value Effect*******/
        JSONArray familyMemberValueEffect;
        JSONObject tempJson;
        String tempStringColor;
        List<DiceColor> diceColors = new ArrayList<>();
        Boolean isRelative;
        Integer diceValue = 0;
        if(jsonPenalty.optJSONArray("familyMemberValueEffect") != null) {
            familyMemberValueEffect = jsonPenalty.optJSONArray("familyMemberValueEffect");
            for (Integer index = 0; index < familyMemberValueEffect.length(); index++) {
                tempJson = familyMemberValueEffect.optJSONObject(index);
                tempStringColor = tempJson.getString("diceColor");
                for (DiceColor c : DiceColor.values())
                    if (c.toString().equalsIgnoreCase(tempStringColor))
                        diceColors.add(c);
            }
            diceValue = familyMemberValueEffect.getJSONObject(0).optInt("diceValue", 0);
            isRelative = familyMemberValueEffect.getJSONObject(0).optBoolean("relative", false);

            penalty = new FamilyMemberValueEffect(diceColors, diceValue, isRelative);
        }

        /**********Development Acquire Value Effect*******/
        String cardColor;
        DevelopmentCardColor color = null;

        if(jsonPenalty.optJSONObject("developmentCardAcquireEffect") != null) {
            diceValue = 0; //reset dice value if it has been incremented by working area value effect
            diceValue = jsonPenalty.optJSONObject("developmentCardAcquireEffect").getInt("diceValue");
            tempStringColor = jsonPenalty.optJSONObject("developmentCardAcquireEffect").optString("developmentCardColor");
            for (DevelopmentCardColor c : DevelopmentCardColor.values())
                if (c.toString().equalsIgnoreCase(tempStringColor))
                    color = c;

            penalty = new DevelopmentCardAcquireEffect(color, diceValue, true);
        }

        /****MarketBan****/
        if(!jsonPenalty.optString("marketBan").isEmpty())
            penalty = new MarketBan();

        /****SkipFirstTurn****/
        if(!jsonPenalty.optString("skipFirstTurn").isEmpty())
            penalty = new SkipFirstTurn();

        /****HalveServantsValue****/
        if(!jsonPenalty.optString("halveServantsValue").isEmpty())
            penalty = new HalveServantsValue();
        /****VictoryPointsPenalty****/
        JSONObject jsonVictoryPenalty;
        Integer victoryPoints = 0;
        Resources resourcesReward;
        if(jsonPenalty.optJSONObject("victoryPointsPenalty") != null) {

            jsonVictoryPenalty = jsonPenalty.optJSONObject("victoryPointsPenalty");

            victoryPoints = jsonVictoryPenalty.optInt("VICTORY_POINTS", 0);

            if(jsonVictoryPenalty.optJSONObject("resources") != null) {
                resourcesReward = getResourcesBonusFromJson(jsonVictoryPenalty.optJSONObject("resources")).getResources();
                penalty = new VictoryPointsPenalty(victoryPoints, resourcesReward);
            }

            if(!jsonVictoryPenalty.optString("developmentCardColor").isEmpty()) {
                tempStringColor = jsonVictoryPenalty.optString("developmentCardColor");
                for (DevelopmentCardColor c : DevelopmentCardColor.values())
                    if (c.toString().equalsIgnoreCase(tempStringColor))
                        color = c;

                penalty = new VictoryPointsPenalty(color);
            }

            if(!jsonVictoryPenalty.optString("playerGoods").isEmpty()) {
                resourcesReward = new Resources(victoryPoints, victoryPoints, victoryPoints, victoryPoints);

                penalty = new VictoryPointsPenalty(victoryPoints, resourcesReward);
            }

            if(jsonVictoryPenalty.optString("buildingCardsResources") != null) {
                resourcesReward = new Resources(0,1,1,0);

                penalty = new VictoryPointsPenalty(victoryPoints, resourcesReward);
             }
        }

        return new ExcommunicationCard(number, period, penalty);
    }

    private static List<CharacterCard> getCharacterCardsFromJson(JSONArray jsonArray) {
        List<CharacterCard> cards = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            cards.add(getCharacterCardFromJson(jsonArray.optJSONObject(i)));
        }

        return cards;
    }

    private static CharacterCard getCharacterCardFromJson(JSONObject jsonObject) {
        Integer period = jsonObject.optInt("period");
        String name = jsonObject.optString("name");
        List<AbstractEffect> instantBonus = new ArrayList<>();
        Resources resourcesRequired = new Resources();
        resourcesRequired = getResourcesBonusFromJson(jsonObject.optJSONObject("requirements")).getResources();
        AbstractEffect permanentBonus = new ResourcesBonus(null,0);
        JSONObject jsonInstantBonus = null;
        Integer diceValue = 0;
        String areaType;
        ContextType workingAreaType = null;
        String cardColor;
        ResourcesBonus requirementsDiscounts = new ResourcesBonus(new Resources(), 0);
        DevelopmentCardColor color = null;
        /***********RESOURCESBONUS********/
        if (jsonObject.optJSONObject("instantBonus") != null) {
            jsonInstantBonus = jsonObject.optJSONObject("instantBonus");
            if (jsonInstantBonus.optJSONObject("resourcesBonus") != null) {
                instantBonus.add(getResourcesBonusFromJson(jsonObject.optJSONObject("instantBonus").optJSONObject("resourcesBonus")));
            }

            if (jsonInstantBonus.optJSONObject("workingAreaValueEffect") != null) {
                diceValue = jsonInstantBonus.optJSONObject("workingAreaValueEffect").getInt("diceValue");
                areaType = jsonInstantBonus.optJSONObject("workingAreaValueEffect").optString("workingAreaType");

                if (areaType.equalsIgnoreCase("PRODUCTION"))
                    workingAreaType = ContextType.PRODUCTION_AREA_CONTEXT;
                else if (areaType.equalsIgnoreCase("HARVEST"))
                    workingAreaType = ContextType.HARVEST_AREA_CONTEXT;

                instantBonus.add(new WorkingAreaValueEffect(workingAreaType, diceValue, false));
            }

            if (jsonInstantBonus.optJSONObject("developmentCardAcquireEffect") != null) {
                diceValue = jsonInstantBonus.optJSONObject("developmentCardAcquireEffect").getInt("diceValue");
                cardColor = jsonInstantBonus.optJSONObject("developmentCardAcquireEffect").getString("developmentCardColor");
                for (DevelopmentCardColor c : DevelopmentCardColor.values())
                    if (c.getDevType().equalsIgnoreCase(cardColor))
                        color = c;

                if (jsonInstantBonus.optJSONObject("developmentCardAcquireEffect").optJSONObject("requirementsDiscount") != null)
                    requirementsDiscounts = getResourcesBonusFromJson(jsonInstantBonus.optJSONObject("developmentCardAcquireEffect"));

                instantBonus.add(new DevelopmentCardAcquireEffect(color, diceValue, false));
            }

            /*----ResourcePerItemBonus----*/
            Resources bonusResources = new Resources();
            DevelopmentCardColor cardType = null;
            String stringColor = new String();
            if (jsonInstantBonus != null)
                if (jsonInstantBonus.optJSONObject("resourcesPerItemBonus") != null) {
                    if (jsonInstantBonus.optJSONObject("resourcesPerItemBonus").optString("developmentCardColor") != null) {
                        Resources resReqForItemBonus = new Resources();

                        if (!stringColor.isEmpty()) {
                            bonusResources = getResourcesBonusFromJson((jsonInstantBonus).optJSONObject("resourcesPerItemBonus"))
                                    .getResources();

                            for (DevelopmentCardColor dc : DevelopmentCardColor.values())
                                if (stringColor.equalsIgnoreCase(dc.toString()))
                                    cardType = dc;

                            instantBonus.add(new ResourcesPerItemBonus(bonusResources, cardType));
                        }
                    }
                Resources resReqForItemBonus = new Resources();
                JSONObject jsonReqRes;
                if (jsonInstantBonus != null) {
                    if (jsonInstantBonus.optJSONObject("resourcesPerItemBonus") != null)
                        if (jsonInstantBonus.optJSONObject("resourcesPerItemBonus").optJSONObject("requiredResources") != null)
                            resReqForItemBonus = getResourcesBonusFromJson(jsonInstantBonus.optJSONObject("resourcesPerItemBonus")
                                    .optJSONObject("requiredResources")).getResources();
                }
                instantBonus.add(new ResourcesPerItemBonus(bonusResources, resReqForItemBonus.getResourceByType(MILITARY_POINTS)));
            }
        }

        diceValue = 0; //reset dice value if it has been incremented by working area value effect

            if (jsonObject.optJSONObject("permanentBonus") != null) {
                JSONObject jsonPermanentBonus = jsonObject.optJSONObject("permanentBonus");
                /***********DEVELOPMENT CARD ACQUIRE EFFECT********/
                if (jsonPermanentBonus.optJSONObject("developmentCardAcquireEffect") != null) {
                    diceValue = jsonPermanentBonus.optJSONObject("developmentCardAcquireEffect").getInt("diceValue");
                    cardColor = jsonPermanentBonus.optJSONObject("developmentCardAcquireEffect").getString("developmentCardColor");
                    for (DevelopmentCardColor c : DevelopmentCardColor.values())
                        if (c.getDevType().equalsIgnoreCase(cardColor))
                            color = c;

                    if (jsonPermanentBonus.optJSONObject("developmentCardAcquireEffect") != null)
                        if(jsonPermanentBonus.optJSONObject("developmentCardAcquireEffect").optJSONObject("requirementsDiscount") != null)
                            requirementsDiscounts = getResourcesBonusFromJson(jsonPermanentBonus.optJSONObject("developmentCardAcquireEffect").optJSONObject("requirementsDiscount"));

                    permanentBonus = new DevelopmentCardAcquireEffect(color, diceValue, true, requirementsDiscounts);
                }
                /***********WORKING AREA VALUE EFFECT********/
                if (jsonPermanentBonus.optJSONObject("workingAreaValueEffect") != null) {
                    diceValue = jsonObject.optJSONObject("permanentBonus").optJSONObject("workingAreaValueEffect").getInt("diceValue");
                    areaType = jsonObject.optJSONObject("permanentBonus").optJSONObject("workingAreaValueEffect").optString("workingAreaType");

                    if (areaType.equalsIgnoreCase("PRODUCTION"))
                        workingAreaType = ContextType.PRODUCTION_AREA_CONTEXT;
                    else if (areaType.equalsIgnoreCase("HARVEST"))
                        workingAreaType = ContextType.HARVEST_AREA_CONTEXT;

                    permanentBonus = new WorkingAreaValueEffect(workingAreaType, diceValue, false);
                }

                /******Action Slot Penalty*****/
                List<Integer> towersLevels = new ArrayList<>();
                JSONArray noResourcesFromTowerLevels = null;
                if (jsonObject.optJSONObject("permanentBonus") != null)
                    if (jsonObject.optJSONObject("permanentBonus").optJSONObject("actionSlotPenalty") != null) {
                        noResourcesFromTowerLevels = jsonObject.optJSONObject("permanentBonus").optJSONObject("actionSlotPenalty")
                                .optJSONArray("noResourcesFromTowerLevels");
                        for (Integer index = 0; index <= noResourcesFromTowerLevels.length(); index++)
                            towersLevels.add(((JSONObject) noResourcesFromTowerLevels.get(index)).getInt("level" + index));

                        permanentBonus = new TowerSlotPenalty(towersLevels);
                    }
            }
        return new CharacterCard(name, period, resourcesRequired.getResourceByType(COINS), instantBonus, permanentBonus);
    }

    public static DevelopmentCardDeck<TerritoryCard> getTerritoryCards() {
        return new DevelopmentCardDeck<>(territoryCards);
    }
    public static DevelopmentCardDeck<BuildingCard> getBuildingCards() {
        return new DevelopmentCardDeck<>(buildingCards);
    }
    public static DevelopmentCardDeck<CharacterCard> getCharactersCards() {
        return new DevelopmentCardDeck<>(characterCards);
    }
    public static DevelopmentCardDeck<VentureCard> getVentureCards() {
        return new DevelopmentCardDeck<>(ventureCards);
    }
    public static Market getMarket() {
        return market;
    }

    public static CouncilPalace getPalace() {
        return palace;
    }

    public static List<Tower> getTowers() {
        return towers;
    }

    public static WorkingArea getHarvestArea() {
        return harvestArea;
    }

    public static WorkingArea getProductionArea() {
        return productionArea;
    }

    //MAIN WITH THE PURPOSE TO VERIFY THE CORRECT LOADING OF MODEL OBJECTS FROM FILE
    public static void main(String[] args) {

        Configurator.loadConfigs();
        /*market.getMarketSlots().forEach(m -> {
                    System.out.println(m.getDiceValue());
                    System.out.println("coins: " + m.getResourcesReward().getResources().getResourceByType(COINS).toString());
                    System.out.println("woods: " + m.getResourcesReward().getResources().getResourceByType(WOODS).toString());
                    System.out.println("servants: " + m.getResourcesReward().getResources().getResourceByType(SERVANTS).toString());
                    System.out.println("stones: " + m.getResourcesReward().getResources().getResourceByType(STONES).toString());
                });

        try {
                System.out.println("PRODUCTION");
                productionArea.getAdvancedSlots().forEach(m -> {
                System.out.println(m.getDiceValue());
                System.out.println("coins: " + m.getResourcesReward().getResources().getResourceByType(COINS).toString());
                System.out.println("woods: " + m.getResourcesReward().getResources().getResourceByType(WOODS).toString());
                System.out.println("servants: " + m.getResourcesReward().getResources().getResourceByType(SERVANTS).toString());
                System.out.println("stones: " + m.getResourcesReward().getResources().getResourceByType(STONES).toString());
            });

                System.out.println(productionArea.getSingleSlot().getDiceValue());
                System.out.println("coins: " + productionArea.getSingleSlot().getResourcesReward().getResources().getResourceByType(COINS).toString());
                System.out.println("woods: " + productionArea.getSingleSlot().getResourcesReward().getResources().getResourceByType(WOODS).toString());
                System.out.println("servants: " + productionArea.getSingleSlot().getResourcesReward().getResources().getResourceByType(SERVANTS).toString());
                System.out.println("stones: " + productionArea.getSingleSlot().getResourcesReward().getResources().getResourceByType(STONES).toString());


                System.out.println("HARVEST");
                harvestArea.getAdvancedSlots().forEach(m -> {
                System.out.println(m.getDiceValue());
                System.out.println("coins: " + m.getResourcesReward().getResources().getResourceByType(COINS).toString());
                System.out.println("woods: " + m.getResourcesReward().getResources().getResourceByType(WOODS).toString());
                System.out.println("servants: " + m.getResourcesReward().getResources().getResourceByType(SERVANTS).toString());
                System.out.println("stones: " + m.getResourcesReward().getResources().getResourceByType(STONES).toString());
            });

                System.out.println( harvestArea.getSingleSlot().getDiceValue());
                System.out.println("coins: " +  harvestArea.getSingleSlot().getResourcesReward().getResources().getResourceByType(COINS).toString());
                System.out.println("woods: " +  harvestArea.getSingleSlot().getResourcesReward().getResources().getResourceByType(WOODS).toString());
                System.out.println("servants: " +  harvestArea.getSingleSlot().getResourcesReward().getResources().getResourceByType(SERVANTS).toString());
                System.out.println("stones: " +  harvestArea.getSingleSlot().getResourcesReward().getResources().getResourceByType(STONES).toString());

            System.out.println(palace.getReward().getResources().getResourceByType(COINS));
            System.out.println(palace.getReward().getCouncilPrivilege());

            excommunicationTiles.forEach(t -> {
                System.out.println(t.getNumber());
                System.out.println(t.getPenalty());
                System.out.println("Period: " + t.getPeriod());
                if(t.getPenalty() instanceof ResourcesPerItemBonus)
                    System.out.println(t.getPenalty().toString());
            });

                characterCards.forEach(t -> {
                System.out.println(t.getName());
                System.out.println(t.getColor());
                System.out.println("instant Bonus: " + t.getInstantBonus());
                System.out.println("Period: " + t.getPeriod());
                System.out.println("Resources required: " + t.getResourcesRequired().getResources().toString());
                System.out.println("Permanent bonus: " + t.getPermanentBonus());
                });
                territoryCards.forEach(t -> {
                System.out.println(t.getName());
                System.out.println(t.getColor());
                System.out.println("instant Bonus: " + t.getInstantBonus());
                System.out.println("Period: " + t.getPeriod());
                System.out.println("Resources required: " + t.getResourcesRequired().getResources().toString());
                System.out.println("Permanent bonus: " + t.getPermanentBonus());
                 });
                ventureCards.forEach(t -> {
                System.out.println(t.getName());
                System.out.println(t.getColor());
                System.out.println("instant Bonus: " + t.getInstantBonus());
                System.out.println("Period: " + t.getPeriod());
                System.out.println("Resources required: " + t.getResourcesRequired().getResources().toString());
                System.out.println("Permanent bonus: " + t.getPermanentBonus());
                });

                buildingCards.forEach(t -> {
                System.out.println(t.getName());
                System.out.println(t.getColor());
                System.out.println("instant Bonus: " + t.getInstantBonus());
                System.out.println("Period: " + t.getPeriod());
                System.out.println("Resources required: " + t.getResourcesRequired().getResources().toString());
                System.out.println("Permanent bonus: " + t.getPermanentBonus());
                });
        } catch (Exception e) { e.printStackTrace(); }
    }*/
    }
    private static void print(String s) {
        System.out.println();
    }
}
