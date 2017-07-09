package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Enums.Model.WorkingAreaType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.*;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.BonusTile;
import it.polimi.ingsw.LM34.Model.Cards.*;
import it.polimi.ingsw.LM34.Model.Effects.*;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.*;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus.DevelopmentCardAcquireEffect;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus.NoMilitaryRequirementsForTerritory;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus.NoOccupiedTowerTax;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus.TowerSlotPenalty;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesExchangeBonus;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesPerItemBonus;
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
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.MULTICOLOR;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.MILITARY_POINTS;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

/**
 * The {@link Configurator} loads all parameters from file as variables that will be used by each game
 */
public class Configurator {
    /*CONVENTION GAME_CODES*/
    private Integer waitingRoomTimeout;
    private Integer TURN_TIMEOUT;
    private final Integer WAITING_ROOM_PLAYERS_THRESHOLD = 2;

    public final Integer PLAYER_MOVE_TIMEOUT = 10000;
    public static final Integer MAX_TOWER_LEVELS = 4;
    public static final Integer MAX_LEADER_PER_PLAYER = 4;
    public static final Integer MAX_PLAYERS = 4;
    public static final Integer TOTAL_PERIODS = 3; //#total periods
    private static final Integer[] minFaithPoints = {3,4,5};
    public static final Integer CARD_PER_ROUND = 4;
    public static final Integer BASE_COINS = 5; //#coins given to first player at game start
    public static final Integer COINS_INCREMENT_PLAYER_ORDER = 1;
    public static final Integer BASE_WOODS = 2; //#woods given to first player at game start
    public static final Integer WOODS_INCREMENT_PLAYER_ORDER = 0;
    public static final Integer BASE_STONES = 2; //#stones given to first player at game start
    public static final Integer STONES_INCREMENT_PLAYER_ORDER = 0;
    public static final Integer BASE_SERVANTS = 3; //#servants given to first player at game start
    public static final Integer SERVANTS_INCREMENT_PLAYER_ORDER = 0;
    public static final Resources TOWER_OCCUPIED_COST = new Resources(3, 0, 0, 0);
    public static final Integer MAX_ACQUIRABLE_CARDS_PER_TYPE = 6;
    public static final Integer COUNCIL_PRIVILEGES_FOR_DISCARDED_LEADER_CARD = 1;

    private Market market;
    private CouncilPalace palace;
    private List<Tower> towers;
    private WorkingArea harvestArea;
    private WorkingArea productionArea;
    private List<TerritoryCard> territoryCards;
    private List<BuildingCard> buildingCards;
    private List<CharacterCard> characterCards;
    private List<VentureCard> ventureCards;
    private List<LeaderCard> leaderCards;
    private List<ExcommunicationCard> excommunicationTiles;
    private List<Resources> councilPrivilegeRewards;
    private Map<Integer, Integer> faithPath;
    private Map<Integer, Integer> endingGameCharactersVictoryPoints;
    private Map<Integer, Integer> endingGameTerritoriesVictoryPoints;
    private Map<Integer,Integer> militaryPointsForTerritories;
    private Integer resourcesForVictoryPoints;
    private List<BonusTile> advancedPersonalTiles;

    /**JSON frequently used constants strings**/
    private final String WORKING_AREA_VALUE_EFFECT_JSONSTRING = "workingAreaValueEffect";
    private final String ACTION_SLOTS_JSONSTRING = "actionSlots";
    private final String DEVELOPMENT_CARD_COLOR_JSONSTRING = "developmentCardColor";
    private final String FAMILY_MEMBER_VALUE_EFFECT_JSONSTRING = "familyMemberValueEffect";
    private final String PERMANENT_BONUS_JSONSTRING= "permanentBonus";
    private final String COUNCIL_PRIVILEGE_JSONSTRING = "councilPrivilege";
    private final String PERIOD_JSONSTRING = "period";
    private final String RESOURCES_JSONSTRING = "resources";
    private final String RESOURCES_BONUS_JSONSTRING = "resourcesBonus";
    private final String FINAL_VICTORY_POINTS_JSONSTRING = "finalVictoryPoints";
    private final String DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING = "developmentCardAcquireEffect";
    private final String CARDS_JSONSTRING = "cards";
    private final String LEADER_REQUIREMENTS_JSONSTRING = "leaderRequirements";
    private final String REQUIREMENTS_DISCOUNT_JSONSTRING = "requirementsDiscount";
    private final String NAME_JSONSTRING = "name";
    private final String DICE_VALUE_JSONSTRING = "diceValue";
    private final String RESOURCES_PER_ITEM_BONUS_JSONSTRING = "resourcesPerItemBonus";
    private final String RESOURCES_EXCHANGE_BONUS_JSONSTRING = "resourcesExchangeBonus";
    private final String MILITARY_POINTS_JSONSTRING = "MILITARY_POINTS";
    private final String REQUIREMENTS_JSONSTRING = "requirements";
    private final String INSTANT_BONUS_JSONSTRING = "instantBonus";
    private final String PRODUCTION_JSONSTRING = "production";
    private final String HARVEST_JSONSTRING = "harvest";
    private final String WORKING_AREA_TYPE_JSONSTRING = "workingAreaType";

    public Configurator() {
        this.loadConfigs();
    }

    /**
     * Loads all variable from the JSON data
     */
    private void loadConfigs() {
        JSONObject jsonObject = null;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            File file = new File(loader.getResource("configurations/config.json").getFile());
            InputStream inputStream = new FileInputStream(file);
            String jsonString  = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            jsonObject = new JSONObject(jsonString).optJSONObject("configuration");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot load file from configs.json", e);
        }

        /****GameRoom Timeout****/
        if(jsonObject != null) {
            waitingRoomTimeout = jsonObject.getJSONObject("server").getInt("timeout");
            TURN_TIMEOUT = jsonObject.getJSONObject("server").getInt("timeoutTurn");

            /**
             * Setups all game objects
             */
            setupLeaderCards(jsonObject.optJSONObject(CARDS_JSONSTRING).optJSONArray("leaderCards"));
            setupGame(jsonObject.getJSONObject("game"));
            setupPersonalTiles(jsonObject);
            setupMarket(jsonObject.optJSONObject(ACTION_SLOTS_JSONSTRING).optJSONArray("market"));
            setupProductionArea(jsonObject.optJSONObject(ACTION_SLOTS_JSONSTRING).optJSONArray("productionArea"));
            setupHarvestArea(jsonObject.optJSONObject(ACTION_SLOTS_JSONSTRING).optJSONArray("harvestArea"));
            setupCouncilPalace(jsonObject.optJSONObject(ACTION_SLOTS_JSONSTRING).optJSONObject("councilPalace"));
            setupTowers(jsonObject.optJSONObject(ACTION_SLOTS_JSONSTRING).optJSONArray("towers"));
            setupDevelopmentCards(jsonObject.optJSONObject(CARDS_JSONSTRING));
            setupExcommunicationTiles(jsonObject.optJSONObject(CARDS_JSONSTRING).optJSONArray("excommunicationTiles"));
        }
    }

    /**
     * @param jsonLeaderCards JSON from which to extract information about the {@link LeaderCard}s
     */
    private void setupLeaderCards(JSONArray jsonLeaderCards) {
        leaderCards = new ArrayList<>();
        String name;
        AbstractEffect bonus = null;
        Boolean oncePerRound;

        /**Prepare each LeaderCard**/
        for (Integer index = 0; index < jsonLeaderCards.length(); index++) {
            Resources resRequirements = new Resources();
            Map<DevelopmentCardColor, Integer> cardRequirements = new HashMap<DevelopmentCardColor, Integer>();
            name = jsonLeaderCards.getJSONObject(index).getString("leaderName");
            oncePerRound = jsonLeaderCards.getJSONObject(index).getBoolean("oncePerRound");
            JSONArray cardsReq = new JSONArray();

            /**
             * Load for each card its {@link LeaderRequirements}
             */
            if (jsonLeaderCards.getJSONObject(index).getJSONObject(LEADER_REQUIREMENTS_JSONSTRING).optJSONArray("cardRequirements") != null) {
                cardsReq = jsonLeaderCards.getJSONObject(index).getJSONObject(LEADER_REQUIREMENTS_JSONSTRING).getJSONArray("cardRequirements");


                /**Retrieve the quantity of specific card colors the player needs to have for activate the {@link LeaderCard}**/
                for (Integer k = 0; k < cardsReq.length(); k++) {
                    if (!cardsReq.getJSONObject(k).getString(DEVELOPMENT_CARD_COLOR_JSONSTRING).isEmpty()) {
                        cardRequirements.put(getCardTypeFromJson(cardsReq.getJSONObject(k).getString(DEVELOPMENT_CARD_COLOR_JSONSTRING)),
                                cardsReq.getJSONObject(k).getInt("number"));
                    }
                }
            }
            /**Extract the Requirements for activating the {@link LeaderCard}**/
            if (jsonLeaderCards.getJSONObject(index).getJSONObject(LEADER_REQUIREMENTS_JSONSTRING).optJSONObject("resourcesRequirements") != null)
                resRequirements = getResourcesBonusFromJson(jsonLeaderCards.getJSONObject(index).getJSONObject(LEADER_REQUIREMENTS_JSONSTRING).optJSONObject("resourcesRequirements")).getResources();


            /**Store the {@link AbstractEffect} bonus in the {@link LeaderCard}
             * Note: only one {@link AbstractEffect} is allowed for each {@link LeaderCard}
             * based on The Rules
             */
            JSONObject jsonBonus;
            if (jsonLeaderCards.optJSONObject(index).optJSONObject("bonus") != null) {
                jsonBonus = jsonLeaderCards.getJSONObject(index).getJSONObject("bonus");

                if (!jsonBonus.optString("actionSlotLimitBypass").isEmpty())
                    bonus = new ActionSlotLimitBypass();

                if (jsonBonus.optJSONObject("churchSupportBonus") != null)
                    bonus = new ChurchSupportBonus(getResourcesBonusFromJson(jsonBonus.getJSONObject("churchSupportBonus")));

                if (jsonBonus.optJSONObject(RESOURCES_BONUS_JSONSTRING) != null)
                    bonus = getResourcesBonusFromJson(jsonBonus.getJSONObject(RESOURCES_BONUS_JSONSTRING));

                if (!jsonBonus.optString("doubleGoodsIncomeFromDevelopmentCardsImmediateEffect").isEmpty())
                    bonus = new ResourcesBonus(jsonBonus.getInt("doubleGoodsIncomeFromDevelopmentCardsImmediateEffect"));

                if (!jsonBonus.optString("noMilitaryPointsRequirementForTerritory").isEmpty())
                    bonus = new NoMilitaryRequirementsForTerritory();

                if (!jsonBonus.optString("copyOtherLeader").isEmpty())
                    bonus = new CopyOtherLeader();

                if (!jsonBonus.optString("noOccupiedTowerTax").isEmpty())
                    bonus = new NoOccupiedTowerTax();

                Integer diceValue = 0;
                String areaType;
                ContextType workingAreaType;

                if (!jsonBonus.optString(WORKING_AREA_VALUE_EFFECT_JSONSTRING).isEmpty()) {
                    diceValue = jsonBonus.optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING).optInt(DICE_VALUE_JSONSTRING);
                    areaType = jsonBonus.optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING).optString(WORKING_AREA_TYPE_JSONSTRING);
                    if (areaType.equalsIgnoreCase(PRODUCTION_JSONSTRING))
                        workingAreaType = ContextType.PRODUCTION_AREA_CONTEXT;
                    else
                        workingAreaType = ContextType.HARVEST_AREA_CONTEXT;

                    bonus = new WorkingAreaValueEffect(workingAreaType, diceValue, false);
                }

                /**Family Member Value Effect**/
                JSONArray familyEffects;
                List<DiceColor> diceColors = new ArrayList<>();
                Integer diceTempValue = 0;
                Boolean relative = false;
                if (jsonBonus.optJSONArray(FAMILY_MEMBER_VALUE_EFFECT_JSONSTRING) != null) {
                    familyEffects = jsonBonus.getJSONArray(FAMILY_MEMBER_VALUE_EFFECT_JSONSTRING);
                    for (Integer j = 0; j < jsonBonus.getJSONArray(FAMILY_MEMBER_VALUE_EFFECT_JSONSTRING).length(); j++) {
                        diceColors.add(getDiceColorFromJson(familyEffects.getJSONObject(j).getString("diceColor")));
                        diceTempValue = familyEffects.getJSONObject(j).getInt(DICE_VALUE_JSONSTRING);
                        relative = familyEffects.getJSONObject(j).optBoolean("relative");
                    }

                    bonus = new FamilyMemberValueEffect(diceColors, diceTempValue, relative);
                }

                /**Development Card Acquire Effect**/
                JSONObject cardAcquire;
                if (jsonBonus.optJSONArray(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING) != null) {
                    cardAcquire = jsonBonus.getJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING);
                    DevelopmentCardColor cardColor = getCardTypeFromJson(cardAcquire.getString(DEVELOPMENT_CARD_COLOR_JSONSTRING));
                    Resources cardDiscount = getResourcesBonusFromJson(cardAcquire.getJSONObject(REQUIREMENTS_DISCOUNT_JSONSTRING)).getResources();
                    ResourcesBonus discount = new ResourcesBonus(cardDiscount, 0);
                    bonus = new DevelopmentCardAcquireEffect(cardColor, diceValue, false, discount);
                }
            }
            /**
             * And now create the {@link LeaderCard}
             */
            leaderCards.add(new LeaderCard(name, new LeaderRequirements(resRequirements, cardRequirements), bonus, oncePerRound));
        }
    }

    /**
     *
     * @param diceColor string from which to extract dice colors
     * @return
     */
    private DiceColor getDiceColorFromJson(String diceColor) {
        for(DiceColor color : DiceColor.values())
            if(color.toString().equalsIgnoreCase(diceColor))
                return color;

        return DiceColor.DEFAULT;
    }

    /**
     * @param developmentCardColor String from which to associate the corrisponding Enum type
     * @return the development card type
     */
    private DevelopmentCardColor getCardTypeFromJson(String developmentCardColor) {
        for(DevelopmentCardColor cardType : DevelopmentCardColor.values())
            if(cardType.toString().equalsIgnoreCase(developmentCardColor))
                return cardType;

        return MULTICOLOR;
    }

    /**
     * Sets up the standardTile and the advanced ones
     * @param jsonObject from which to extract information on personalTile to create
     */
    private void setupPersonalTiles(JSONObject jsonObject) {
        Integer  advancedProductionDiceValue;
        ResourcesBonus advProdReward;
        Integer advancedHarvestDiceValue;
        ResourcesBonus advHarvReward;
        JSONArray jsonAdvancedTiles = jsonObject.getJSONArray("personalTiles");
        advancedPersonalTiles = new ArrayList<>();

        for(Integer index = 0; index < jsonAdvancedTiles.length(); index++) {
            advancedProductionDiceValue = jsonAdvancedTiles.getJSONObject(index).getJSONObject(PRODUCTION_JSONSTRING).getInt(DICE_VALUE_JSONSTRING);
            advProdReward = getResourcesBonusFromJson(jsonAdvancedTiles.getJSONObject(index).getJSONObject(PRODUCTION_JSONSTRING));
            advancedHarvestDiceValue = jsonAdvancedTiles.getJSONObject(index).getJSONObject(HARVEST_JSONSTRING).getInt(DICE_VALUE_JSONSTRING);
            advHarvReward = getResourcesBonusFromJson(jsonAdvancedTiles.getJSONObject(index).getJSONObject(HARVEST_JSONSTRING));

            advancedPersonalTiles.add(new BonusTile(advancedHarvestDiceValue, advancedProductionDiceValue, advProdReward, advHarvReward));
        }
    }

    /**
     * @param jsonObject from which to load game path, council privileges rewards and other games related configs
     */
    private void setupGame(JSONObject jsonObject) {

        /****Rewards given by council Privileges*/
        JSONArray jsonPrivilegeArray = jsonObject.getJSONArray(COUNCIL_PRIVILEGE_JSONSTRING);
        councilPrivilegeRewards = new ArrayList<>();
        for(Integer index = 0; index < jsonPrivilegeArray.length(); index++)
            councilPrivilegeRewards.add(getResourcesBonusFromJson(jsonPrivilegeArray.getJSONObject(index)).getResources());

        /****Faith Path****/
        faithPath = new HashMap<>();
        JSONArray jsonPathArray = jsonObject.getJSONObject(FINAL_VICTORY_POINTS_JSONSTRING).getJSONArray("faithPath");
        for(Integer index = 0; index < jsonPathArray.length(); index++)
            faithPath.put(index, jsonPathArray.getJSONObject(index).getInt(index.toString()));

        /****Ending game victory points by number of Territories****/
        endingGameTerritoriesVictoryPoints = new HashMap<>();
        JSONArray jsonTerritoriesPointsArray = jsonObject.getJSONObject(FINAL_VICTORY_POINTS_JSONSTRING).getJSONArray("territories");
        for(Integer index = 0; index < jsonTerritoriesPointsArray.length(); index++)
            endingGameTerritoriesVictoryPoints.put(index, jsonTerritoriesPointsArray.getJSONObject(index).getInt(index.toString()));

        /****Ending game victory points by number of Characters****/
        endingGameCharactersVictoryPoints = new HashMap<>();
        JSONArray jsonCharactersPointsArray = jsonObject.getJSONObject(FINAL_VICTORY_POINTS_JSONSTRING).getJSONArray("characters");
        for(Integer index = 0; index < jsonCharactersPointsArray.length(); index++)
            endingGameCharactersVictoryPoints.put(index, jsonCharactersPointsArray.getJSONObject(index).getInt(index.toString()));

        /****Ending game victory points for the amount of resources specific****/
        resourcesForVictoryPoints = jsonObject.getJSONObject(FINAL_VICTORY_POINTS_JSONSTRING).getInt("resourcesForVictoryPoint");

        /****Military Points for Territories****/
        militaryPointsForTerritories = new HashMap<>();
        JSONArray jsonMilitaryPointsForTerritoriesArray = jsonObject.getJSONArray("militaryPointsForTerritories");
        for(Integer index = 0; index < jsonMilitaryPointsForTerritoriesArray.length(); index++)
            militaryPointsForTerritories.put(index, jsonMilitaryPointsForTerritoriesArray.getJSONObject(index).getInt(index.toString()));
    }

    /**
     * @param jsonArray from which to load into objects the {@link ExcommunicationCard}s
     */
    private void setupExcommunicationTiles(JSONArray jsonArray) {
        excommunicationTiles = new ArrayList<>();
        for(Integer index = 0; index < jsonArray.length(); index++)
            excommunicationTiles.add(getExcommunicationCardFromJson(jsonArray.optJSONObject(index)));
        /**
         * Now keep only the 3 excommunication cards that the {@link it.polimi.ingsw.LM34.Controller.GameManager} needs
         */
       orderExcommunicatioCardByPeriod();
    }

    /**
     * @param jsonTowersSlots from which to load the towerslot bonuses for the game
     */
    private void setupTowers(JSONArray jsonTowersSlots) {
        Integer index = 0;
        Integer iteration = 1;
        towers = new ArrayList<>();
        List<TowerSlot> tempSlotsOfTower;
        for(DevelopmentCardColor type : DevelopmentCardColor.values()) {
            if (type != MULTICOLOR) {
                tempSlotsOfTower = new ArrayList<>();
                for (; index < MAX_TOWER_LEVELS * iteration; index++) {
                    tempSlotsOfTower.add(getTowerSlotFromJson(jsonTowersSlots.optJSONObject(index)));
                }
                iteration++;
                towers.add(new Tower(type, tempSlotsOfTower));
            }
        }
    }

    /**
     * @param jsonObject from which to extra and fill the {@link TowerSlot}s
     */
    private TowerSlot getTowerSlotFromJson(JSONObject jsonObject) {
        Boolean singlePawnSlot = jsonObject.optBoolean("singlePawnSlot", true);
        Integer diceValue = jsonObject.optInt(DICE_VALUE_JSONSTRING, 0);
        Integer councilPrivilege = jsonObject.getInt(COUNCIL_PRIVILEGE_JSONSTRING);
        Resources resources = getResourcesBonusFromJson(jsonObject).getResources();
        //wrapper
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, councilPrivilege);
        return new TowerSlot(singlePawnSlot, diceValue, resourcesBonus);
    }

    /**
     * @param marketArray from which to extract the data about marketSlots
     */
    private void setupMarket(JSONArray marketArray) {
        market = new Market(new ArrayList<>());
        for (int i = 0; i < marketArray.length(); i++) {
            market.getActionSlots().add(getActionSlotFromJson(marketArray.optJSONObject(i)));
        }
    }

    /**
     * Load the cards from the JSON file {@see resources/configurations/config.json}, shuffle and order them
     * @param decks
     */
    private void setupDevelopmentCards(JSONObject decks) {
        characterCards = getCharacterCardsFromJson(decks.optJSONArray("characters"));
        buildingCards = getBuildingCardsFromJson(decks.optJSONArray("buildings"));
        territoryCards = getTerritoryCardsFromJson(decks.optJSONArray("territories"));
        ventureCards = getVentureCardsFromJson(decks.optJSONArray("ventures"));


        Collections.shuffle(territoryCards);
        Collections.shuffle(buildingCards);
        Collections.shuffle(ventureCards);
        Collections.shuffle(characterCards);
        territoryCards = orderDevelopmentCardByPeriod(territoryCards);
        buildingCards = orderDevelopmentCardByPeriod(buildingCards);
        characterCards = orderDevelopmentCardByPeriod(characterCards);
        ventureCards = orderDevelopmentCardByPeriod(ventureCards);
    }

    /**
     * Order the cards by period in the deck before the game starts
     * @param deck to order for period
     */
    public <T extends AbstractDevelopmentCard> List<T> orderDevelopmentCardByPeriod(List<T> deck) {
        List<T> orderedDeck = new ArrayList<>();

        for (Integer period = 1; period <= TOTAL_PERIODS; period++)
            for(T card : deck)
                if (card.getPeriod() == period)
                    orderedDeck.add(card);

        return orderedDeck;
    }

    /**
     * @param jsonObject from which to extra and fill the {@link ActionSlot}s
     */
    private ActionSlot getActionSlotFromJson(JSONObject jsonObject) {
        Boolean singlePawnSlot = jsonObject.optBoolean("singlePawnSlot", true);
        Integer diceValue = jsonObject.optInt(DICE_VALUE_JSONSTRING, 0);
        Integer councilPrivilege = jsonObject.getInt(COUNCIL_PRIVILEGE_JSONSTRING);
        Resources resources = getResourcesBonusFromJson(jsonObject).getResources();
        //wrapper
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, councilPrivilege);
        return new ActionSlot(singlePawnSlot, diceValue, resourcesBonus);
    }

    /**
     * @param jsonResourcesBonus from which to create and instantiate che {@link ResourcesBonus} effect
     */
    private ResourcesBonus getResourcesBonusFromJson(JSONObject jsonResourcesBonus) {
        Map<ResourceType, Integer> resourcesMap = new EnumMap<>(ResourceType.class);
        Integer councilPrivileges;
        JSONObject jsonResources;
        Integer value;
        if(jsonResourcesBonus.optJSONObject(RESOURCES_JSONSTRING) != null) {
             jsonResources = jsonResourcesBonus.optJSONObject(RESOURCES_JSONSTRING);
            for (ResourceType type : ResourceType.values()) {
                value = jsonResources.optInt(type.toString(), 0);
                if (value != 0)
                    resourcesMap.put(type, value);
            }
        }

        councilPrivileges = jsonResourcesBonus.optInt(COUNCIL_PRIVILEGE_JSONSTRING, 0);
        Resources resources = new Resources(resourcesMap);

        return new ResourcesBonus(resources, councilPrivileges);
    }

    /**
     * @param jsonArray from which to create and instantiate che {@link DevelopmentCardDeck} of {@link TerritoryCard}
     */
    private List<TerritoryCard> getTerritoryCardsFromJson(JSONArray jsonArray) {
        ArrayList<TerritoryCard> cards = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            cards.add(getTerritoryCardFromJson(jsonArray.optJSONObject(i)));
        }
        return cards;
    }

    private TerritoryCard getTerritoryCardFromJson(JSONObject jsonObject) {
        Integer period = jsonObject.optInt(PERIOD_JSONSTRING);
        String name = jsonObject.optString(NAME_JSONSTRING);

        Integer diceValueToHarvest = jsonObject.optInt("diceValueToHarvest");

        ResourcesBonus permanentResources = getResourcesBonusFromJson(jsonObject.optJSONObject(PERMANENT_BONUS_JSONSTRING).optJSONObject(RESOURCES_BONUS_JSONSTRING));
        List<AbstractEffect> instantResources = new ArrayList<>();
        instantResources.add(getResourcesBonusFromJson(jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING).optJSONObject(RESOURCES_BONUS_JSONSTRING)));

        return new TerritoryCard(name, diceValueToHarvest, period, instantResources, permanentResources);
    }

    /**
     * @param jsonArray from which to create and instantiate che {@link DevelopmentCardDeck} of {@link BuildingCard}
     */
    private List<BuildingCard> getBuildingCardsFromJson(JSONArray jsonArray) {
        List<BuildingCard> cards = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            cards.add(getBuildingCardFromJson(jsonArray.optJSONObject(i)));
        }
        return cards;
    }

    private BuildingCard getBuildingCardFromJson(JSONObject jsonObject) {
        String name = jsonObject.optString(NAME_JSONSTRING);
        Integer period = jsonObject.optInt(PERIOD_JSONSTRING);
        Integer diceValueToProduct = jsonObject.optInt("diceValueToProduct");
        Resources resourcesRequired = getResourcesBonusFromJson(jsonObject.optJSONObject(REQUIREMENTS_JSONSTRING)).getResources();
        AbstractEffect permanentBonus = new ResourcesBonus(new Resources(0,0,0,0), 0);
        List<AbstractEffect> instantResources = new ArrayList<>();
        String color = "";
        Resources bonusResources;

        if (jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING).optJSONObject(RESOURCES_BONUS_JSONSTRING) != null)
            instantResources.add(getResourcesBonusFromJson(jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING).optJSONObject(RESOURCES_BONUS_JSONSTRING)));

        if(jsonObject.optJSONObject(PERMANENT_BONUS_JSONSTRING) != null) {
            JSONObject jsonPermanentBonus = jsonObject.optJSONObject(PERMANENT_BONUS_JSONSTRING);

            if (jsonPermanentBonus.optJSONObject(RESOURCES_BONUS_JSONSTRING) != null)
                permanentBonus = getResourcesBonusFromJson(jsonObject.optJSONObject(PERMANENT_BONUS_JSONSTRING).optJSONObject(RESOURCES_BONUS_JSONSTRING));

           /*----ResourcePerItem----*/
            if (jsonPermanentBonus.optJSONObject(RESOURCES_PER_ITEM_BONUS_JSONSTRING) != null) {
                color = jsonPermanentBonus.optJSONObject(RESOURCES_PER_ITEM_BONUS_JSONSTRING).optString(DEVELOPMENT_CARD_COLOR_JSONSTRING);

                DevelopmentCardColor cardType = null;
                if (!color.isEmpty()) {
                    bonusResources = getResourcesBonusFromJson(jsonPermanentBonus
                            .optJSONObject(RESOURCES_PER_ITEM_BONUS_JSONSTRING)).getResources();
                    for (DevelopmentCardColor dc : DevelopmentCardColor.values())
                        if (color.equalsIgnoreCase(dc.toString()))
                            cardType = dc;

                    permanentBonus = new ResourcesPerItemBonus(bonusResources, cardType);
                }
            }
           /*----ResourceExchangeBonus----*/
            JSONArray resourcesExchangeBonus = null;
            Resources resToDiscard = new Resources(0,0,0,0);
            ResourcesBonus resToGain = new ResourcesBonus(new Resources(0,0,0,0), 0);
            List<Pair<Resources, ResourcesBonus>> resExchanges = new ArrayList<>();

            if (!jsonPermanentBonus.isNull(RESOURCES_EXCHANGE_BONUS_JSONSTRING)) {
                resourcesExchangeBonus = jsonPermanentBonus.optJSONArray(RESOURCES_EXCHANGE_BONUS_JSONSTRING);
                for (int i = 0; i < resourcesExchangeBonus.length(); i++) {
                    resToDiscard = getResourcesBonusFromJson(resourcesExchangeBonus.optJSONObject(i).optJSONObject("resourcesDiscard")).getResources();
                    resToGain = getResourcesBonusFromJson(resourcesExchangeBonus.optJSONObject(i).optJSONObject("resourcesGain"));

                resExchanges.add(new ImmutablePair<>(resToDiscard, resToGain));
                }
            permanentBonus = new ResourcesExchangeBonus(resExchanges);

            }
        }
        return new BuildingCard(name, diceValueToProduct, period, resourcesRequired, instantResources, permanentBonus);
    }

    /**
     * @param jsonArray from which to create and instantiate che {@link DevelopmentCardDeck} of {@link VentureCard}
     */
    private List<VentureCard> getVentureCardsFromJson(JSONArray jsonArray) {
        List<VentureCard> cards = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            cards.add(getVentureCardFromJson(jsonArray.optJSONObject(i)));
        }
        return cards;
    }

    private VentureCard getVentureCardFromJson(JSONObject jsonObject) {
        Integer period = jsonObject.optInt(PERIOD_JSONSTRING);
        String name = jsonObject.optString(NAME_JSONSTRING);
        List<AbstractEffect> instantBonus = new ArrayList<>();
        Integer endingVictoryPointsReward = jsonObject.optJSONObject(PERMANENT_BONUS_JSONSTRING).optInt("endingVictoryPointsReward");
        Integer militaryPointsRequired = 0;
        Integer militaryPointsSubtraction = 0;
        Resources resourcesRequired;
        resourcesRequired = getResourcesBonusFromJson(jsonObject.optJSONObject(REQUIREMENTS_JSONSTRING)).getResources();

        if(jsonObject.optJSONObject(REQUIREMENTS_JSONSTRING).optJSONObject(MILITARY_POINTS_JSONSTRING) != null) {
            militaryPointsRequired = jsonObject.optJSONObject(REQUIREMENTS_JSONSTRING).optJSONObject(MILITARY_POINTS_JSONSTRING).optInt("militaryPointsRequired");
            militaryPointsSubtraction = jsonObject.optJSONObject(REQUIREMENTS_JSONSTRING).optJSONObject(MILITARY_POINTS_JSONSTRING).optInt("militaryPointsSubtraction");
        }
        /***********RESOURCESBONUS********/
        if(jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING) != null) {
            JSONObject jsonInstantBonus = jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING);

            if (jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING) != null) {
                if (jsonInstantBonus.optJSONObject(RESOURCES_BONUS_JSONSTRING) != null) {
                    instantBonus.add(getResourcesBonusFromJson(jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING).optJSONObject(RESOURCES_BONUS_JSONSTRING)));
                }
            }


            /***********WORKING AREA VALUE EFFECT********/
            Integer diceValue;
            String areaType;
            ContextType workingAreaType;

            if (jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING).optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING) != null) {
                diceValue = jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING).optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING).getInt(DICE_VALUE_JSONSTRING);
                areaType = jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING).optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING).optString(WORKING_AREA_TYPE_JSONSTRING);

                if (areaType.equalsIgnoreCase(WorkingAreaType.PRODUCTION.getWorkingAreaType()))
                    workingAreaType = ContextType.PRODUCTION_AREA_CONTEXT;
                else
                    workingAreaType = ContextType.HARVEST_AREA_CONTEXT;

                instantBonus.add(new WorkingAreaValueEffect(workingAreaType, diceValue, false));
            }
            /***********DEVELOPMENT CARD ACQUIRE EFFECT********/
            String cardColor;
            DevelopmentCardColor color = null;
            if (jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING).optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING) != null) {
                diceValue = jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING).optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).getInt(DICE_VALUE_JSONSTRING);
                cardColor = jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING).optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).getString(DEVELOPMENT_CARD_COLOR_JSONSTRING);
                for (DevelopmentCardColor c : DevelopmentCardColor.values())
                    if ((c.toString()).equalsIgnoreCase(cardColor))
                        color = c;

                instantBonus.add(new DevelopmentCardAcquireEffect(color, diceValue, false));
            }
        }
        return new VentureCard(name, period, militaryPointsRequired, militaryPointsSubtraction, resourcesRequired,  instantBonus, endingVictoryPointsReward);
    }

    /**
     * @param numPlayers that will play the game
     * @return the exact number of leaders the game needs (4 per player)
     */
    public List<LeaderCard> getLeaderCards(Integer numPlayers) {
        Collections.shuffle(leaderCards);
        List<LeaderCard> leadersForPlaying = new ArrayList<>();
        for(Integer index = 0; index < MAX_LEADER_PER_PLAYER * numPlayers; index++)
            leadersForPlaying.add(leaderCards.get(index));

        return leadersForPlaying;
    }

    public List<ExcommunicationCard> getExcommunicationTiles() {
        return excommunicationTiles;
    }

    /**
     * Order the {@link ExcommunicationCard}s by period
     */
    public void orderExcommunicatioCardByPeriod() {
        List<ExcommunicationCard> temp = new ArrayList();
        Collections.shuffle(excommunicationTiles);
        for (Integer period = 0; period <= TOTAL_PERIODS - 1; period++) {
            excommlop:
            for (ExcommunicationCard e : excommunicationTiles)
                if (e.getPeriod() == period) {
                    temp.add(e);
                    break excommlop;
                }
        }
        excommunicationTiles = temp;
    }

    /**
     * @param productionSlotsJson from which to extra information about the Production Area ({@link WorkingArea})
     */
    private void setupProductionArea(JSONArray productionSlotsJson) {
        List<ActionSlot> productionSlots = new ArrayList<>();
        for( int i = 0; i < productionSlotsJson.length(); i++)
            productionSlots.add(getActionSlotFromJson(productionSlotsJson.optJSONObject(i)));

        productionArea = new WorkingArea(productionSlots);
    }

    /**
     * @param harvestSlotsJson from which to extra information about the Harvest Area ({@link WorkingArea})
     */
    private void setupHarvestArea(JSONArray harvestSlotsJson) {
        List<ActionSlot> harvestSlots = new ArrayList<>();
        for( int i = 0; i < harvestSlotsJson.length(); i++)
            harvestSlots.add(getActionSlotFromJson(harvestSlotsJson.optJSONObject(i)));

        harvestArea = new WorkingArea(harvestSlots);
    }

    private void setupCouncilPalace(JSONObject councilPalaceJson) {
        palace = new CouncilPalace(getActionSlotFromJson(councilPalaceJson));
    }

    private ExcommunicationCard getExcommunicationCardFromJson(JSONObject tile) {
        Integer number = tile.getInt("number");
        Integer period = tile.getInt(PERIOD_JSONSTRING);
        JSONObject jsonPenalty = tile.optJSONObject("penalty");
        AbstractEffect penalty = new ResourcesBonus(new Resources(), 0);

        /******RESOURCE INCOME PENALTY******/
        if(jsonPenalty.optJSONObject("resourcesIncomePenalty") != null) {
            penalty = getResourcesBonusFromJson(jsonPenalty.optJSONObject("resourcesIncomePenalty"));
        }

        if(jsonPenalty.optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING) != null) {
            /********WORKING AREA VALUE EFFECT********/
            Integer diceValue;
            String areaType;
            ContextType workingAreaType = null;

                diceValue = jsonPenalty.optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING).getInt(DICE_VALUE_JSONSTRING);
                areaType = jsonPenalty.optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING).optString(WORKING_AREA_TYPE_JSONSTRING);

                if(areaType.equalsIgnoreCase(PRODUCTION_JSONSTRING))
                    workingAreaType = ContextType.PRODUCTION_AREA_CONTEXT;
                else if (areaType.equalsIgnoreCase(HARVEST_JSONSTRING))
                    workingAreaType = ContextType.HARVEST_AREA_CONTEXT;

            penalty = new WorkingAreaValueEffect(workingAreaType, diceValue, true);
        }

        /********Family Member Value Effect*******/
        JSONArray familyMemberValueEffect;
        JSONObject tempJson;
        String tempStringColor;
        List<DiceColor> diceColors = new ArrayList<>();
        Boolean isRelative;
        Integer diceValue;
        if(jsonPenalty.optJSONArray(FAMILY_MEMBER_VALUE_EFFECT_JSONSTRING) != null) {
            familyMemberValueEffect = jsonPenalty.optJSONArray(FAMILY_MEMBER_VALUE_EFFECT_JSONSTRING);
            for (Integer index = 0; index < familyMemberValueEffect.length(); index++) {
                tempJson = familyMemberValueEffect.optJSONObject(index);
                tempStringColor = tempJson.getString("diceColor");
                for (DiceColor c : DiceColor.values())
                    if (c.toString().equalsIgnoreCase(tempStringColor))
                        diceColors.add(c);
            }
            diceValue = familyMemberValueEffect.getJSONObject(0).optInt(DICE_VALUE_JSONSTRING, 0);
            isRelative = familyMemberValueEffect.getJSONObject(0).optBoolean("relative", false);

            penalty = new FamilyMemberValueEffect(diceColors, diceValue, isRelative);
        }

        /**********Development Acquire Value Effect*******/
        DevelopmentCardColor color = null;

        if(jsonPenalty.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING) != null) {
            diceValue = jsonPenalty.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).getInt(DICE_VALUE_JSONSTRING);
            tempStringColor = jsonPenalty.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).optString(DEVELOPMENT_CARD_COLOR_JSONSTRING);
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
        Integer victoryPoints;
        Resources resourcesReward;
        if(jsonPenalty.optJSONObject("victoryPointsPenalty") != null) {

            jsonVictoryPenalty = jsonPenalty.optJSONObject("victoryPointsPenalty");

            victoryPoints = jsonVictoryPenalty.optInt("VICTORY_POINTS", 0);

            if(jsonVictoryPenalty.optJSONObject(RESOURCES_JSONSTRING) != null) {
                resourcesReward = getResourcesBonusFromJson(jsonVictoryPenalty).getResources();
                penalty = new VictoryPointsPenalty(victoryPoints, resourcesReward);
            }

            if(!jsonVictoryPenalty.optString(DEVELOPMENT_CARD_COLOR_JSONSTRING).isEmpty()) {
                tempStringColor = jsonVictoryPenalty.optString(DEVELOPMENT_CARD_COLOR_JSONSTRING);
                for (DevelopmentCardColor c : DevelopmentCardColor.values())
                    if (c.toString().equalsIgnoreCase(tempStringColor))
                        color = c;

                penalty = new VictoryPointsPenalty(color);
            }

            if(!jsonVictoryPenalty.optString("playerGoods").isEmpty()) {
                resourcesReward = new Resources(victoryPoints, victoryPoints, victoryPoints, victoryPoints);

                penalty = new VictoryPointsPenalty(victoryPoints, resourcesReward);
            }

            if(!jsonVictoryPenalty.optString("buildingCardsResources").isEmpty()) {
                resourcesReward = new Resources(0,1,1,0);

                penalty = new VictoryPointsPenalty(victoryPoints, resourcesReward);
             }
        }
        return new ExcommunicationCard(number, period, penalty);
    }

    /**
     * @param jsonArray from which to create and instantiate che {@link DevelopmentCardDeck} of {@link CharacterCard}
     */
    private List<CharacterCard> getCharacterCardsFromJson(JSONArray jsonArray) {
        List<CharacterCard> cards = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            cards.add(getCharacterCardFromJson(jsonArray.optJSONObject(i)));
        }
        return cards;
    }

    private CharacterCard getCharacterCardFromJson(JSONObject jsonObject) {
        Integer period = jsonObject.optInt(PERIOD_JSONSTRING);
        String name = jsonObject.optString(NAME_JSONSTRING);
        List<AbstractEffect> instantBonus = new ArrayList<>();
        Resources resourcesRequired;
        resourcesRequired = getResourcesBonusFromJson(jsonObject.optJSONObject(REQUIREMENTS_JSONSTRING)).getResources();
        AbstractEffect permanentBonus = new ResourcesBonus(new Resources(0,0,0,0), 0);
        JSONObject jsonInstantBonus;
        Integer diceValue;
        String areaType;
        Boolean relative;
        ContextType workingAreaType = null;
        String cardColor;
        ResourcesBonus requirementsDiscounts = new ResourcesBonus(new Resources(), 0);
        DevelopmentCardColor color = null;
        /***********RESOURCESBONUS********/
        if (jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING) != null) {
            jsonInstantBonus = jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING);
            if (jsonInstantBonus.optJSONObject(RESOURCES_BONUS_JSONSTRING) != null) {
                instantBonus.add(getResourcesBonusFromJson(jsonObject.optJSONObject(INSTANT_BONUS_JSONSTRING).optJSONObject(RESOURCES_BONUS_JSONSTRING)));
            }

            if (jsonInstantBonus.optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING) != null) {
                relative = jsonInstantBonus.optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING).getBoolean("relative");
                diceValue = jsonInstantBonus.optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING).getInt(DICE_VALUE_JSONSTRING);
                areaType = jsonInstantBonus.optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING).optString(WORKING_AREA_TYPE_JSONSTRING);

                if (areaType.equalsIgnoreCase(PRODUCTION_JSONSTRING))
                    workingAreaType = ContextType.PRODUCTION_AREA_CONTEXT;
                else if (areaType.equalsIgnoreCase(HARVEST_JSONSTRING))
                    workingAreaType = ContextType.HARVEST_AREA_CONTEXT;

                instantBonus.add(new WorkingAreaValueEffect(workingAreaType, diceValue, relative));
            }

            if (jsonInstantBonus.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING) != null) {
                relative = jsonInstantBonus.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).getBoolean("relative");
                diceValue = jsonInstantBonus.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).getInt(DICE_VALUE_JSONSTRING);
                cardColor = jsonInstantBonus.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).getString(DEVELOPMENT_CARD_COLOR_JSONSTRING);
                for (DevelopmentCardColor c : DevelopmentCardColor.values())
                    if ((c.toString()).equalsIgnoreCase(cardColor))
                        color = c;

                if (jsonInstantBonus.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).optJSONObject(REQUIREMENTS_DISCOUNT_JSONSTRING) != null)
                    requirementsDiscounts = getResourcesBonusFromJson(jsonInstantBonus.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).getJSONObject(REQUIREMENTS_DISCOUNT_JSONSTRING));

                instantBonus.add(new DevelopmentCardAcquireEffect(color, diceValue, relative, requirementsDiscounts));
            }

            Resources resBon = new Resources();
            /*----ResourcePerItem----*/
            if (jsonInstantBonus.optJSONObject(RESOURCES_PER_ITEM_BONUS_JSONSTRING) != null) {
                String colorString = jsonInstantBonus.optJSONObject(RESOURCES_PER_ITEM_BONUS_JSONSTRING).optString(DEVELOPMENT_CARD_COLOR_JSONSTRING);


                DevelopmentCardColor cardType = null;
                if (!colorString.isEmpty()) {
                    resBon = getResourcesBonusFromJson(jsonInstantBonus
                            .optJSONObject(RESOURCES_PER_ITEM_BONUS_JSONSTRING)).getResources();
                    for (DevelopmentCardColor dc : DevelopmentCardColor.values())
                        if (colorString.equalsIgnoreCase(dc.toString()))
                            cardType = dc;
                    instantBonus.add(new ResourcesPerItemBonus(resBon, cardType));
                }
            }
            /*----ResourcePerItemBonus----*/
            Resources resReqForItemBonus = new Resources();
            if (jsonInstantBonus.optJSONObject(RESOURCES_PER_ITEM_BONUS_JSONSTRING) != null
                    && jsonInstantBonus.optJSONObject(RESOURCES_PER_ITEM_BONUS_JSONSTRING).optJSONObject("requiredResources") != null) {
                resReqForItemBonus = getResourcesBonusFromJson(jsonInstantBonus.optJSONObject(RESOURCES_PER_ITEM_BONUS_JSONSTRING)
                        .optJSONObject("requiredResources")).getResources();
                resBon = getResourcesBonusFromJson(jsonInstantBonus
                        .optJSONObject(RESOURCES_PER_ITEM_BONUS_JSONSTRING)).getResources();

                instantBonus.add(new ResourcesPerItemBonus(resBon, resReqForItemBonus.getResourceByType(MILITARY_POINTS)));
            }
        }

            if (jsonObject.optJSONObject(PERMANENT_BONUS_JSONSTRING) != null) {
                JSONObject jsonPermanentBonus = jsonObject.optJSONObject(PERMANENT_BONUS_JSONSTRING);

                /***********WORKING AREA VALUE EFFECT********/
                if (jsonPermanentBonus.optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING) != null) {
                    diceValue = jsonPermanentBonus.optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING).getInt(DICE_VALUE_JSONSTRING);
                    areaType = jsonPermanentBonus.optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING).optString(WORKING_AREA_TYPE_JSONSTRING);
                    relative = jsonPermanentBonus.optJSONObject(WORKING_AREA_VALUE_EFFECT_JSONSTRING).getBoolean("relative");
                    if (areaType.equalsIgnoreCase(PRODUCTION_JSONSTRING))
                        workingAreaType = ContextType.PRODUCTION_AREA_CONTEXT;
                    else if (areaType.equalsIgnoreCase(HARVEST_JSONSTRING))
                        workingAreaType = ContextType.HARVEST_AREA_CONTEXT;

                    permanentBonus = new WorkingAreaValueEffect(workingAreaType, diceValue, relative);
                }
                /***********DEVELOPMENT CARD ACQUIRE EFFECT********/
                if (jsonPermanentBonus.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING) != null) {
                    relative = jsonPermanentBonus.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).getBoolean("relative");
                    diceValue = jsonPermanentBonus.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).getInt(DICE_VALUE_JSONSTRING);
                    cardColor = jsonPermanentBonus.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).getString(DEVELOPMENT_CARD_COLOR_JSONSTRING);
                    for (DevelopmentCardColor c : DevelopmentCardColor.values())
                        if ((c.toString()).equalsIgnoreCase(cardColor))
                            color = c;

                    if (jsonPermanentBonus.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).optJSONObject(REQUIREMENTS_DISCOUNT_JSONSTRING) != null)
                        requirementsDiscounts = getResourcesBonusFromJson(jsonPermanentBonus.optJSONObject(DEVELOPMENT_CARD_ACQUIRE_EFFECT_JSONSTRING).getJSONObject(REQUIREMENTS_DISCOUNT_JSONSTRING));

                    permanentBonus = new DevelopmentCardAcquireEffect(color, diceValue, relative, requirementsDiscounts);
                }

                /******Action Slot Penalty*****/
                List<Integer> towersLevels = new ArrayList<>();
                JSONArray noResourcesFromTowerLevels;
                if (jsonObject.optJSONObject(PERMANENT_BONUS_JSONSTRING).optJSONObject("actionSlotPenalty") != null) {
                    noResourcesFromTowerLevels = jsonObject.optJSONObject(PERMANENT_BONUS_JSONSTRING).optJSONObject("actionSlotPenalty")
                            .optJSONArray("noResourcesFromTowerLevels");
                    for (Integer index = 0; index < noResourcesFromTowerLevels.length(); index++)
                        towersLevels.add(Integer.parseInt(((JSONObject)(noResourcesFromTowerLevels.get(index))).get("level" + index).toString()));

                    permanentBonus = new TowerSlotPenalty(towersLevels);
                }
            }


        return new CharacterCard(name, period, resourcesRequired, instantBonus, permanentBonus);
    }

    public DevelopmentCardDeck<TerritoryCard> getTerritoryCards() {
        return new DevelopmentCardDeck<>(territoryCards);
    }
    public DevelopmentCardDeck<BuildingCard> getBuildingCards() {
        return new DevelopmentCardDeck<>(buildingCards);
    }

    public List<BuildingCard> getBuildingCardsList() {
        return buildingCards;
    }
    public DevelopmentCardDeck<CharacterCard> getCharactersCards() {
        return new DevelopmentCardDeck<>(characterCards);
    }
    public DevelopmentCardDeck<VentureCard> getVentureCards() {
        return new DevelopmentCardDeck<>(ventureCards);
    }
    public Market getMarket() {
        return market;
    }

    public CouncilPalace getPalace() {
        return palace;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public WorkingArea getHarvestArea() {
        return harvestArea;
    }

    public WorkingArea getProductionArea() {
        return productionArea;
    }

    public List<Resources> getCouncilPrivilegeRewards() {
        return councilPrivilegeRewards;
    }

    public List<BonusTile> getBonusTiles() {
        return advancedPersonalTiles;
    }

    public Map<Integer, Integer> getFaithPath() {
        return faithPath;
    }

    public Map<Integer, Integer> getMapCharactersToVictoryPoints() {
        return endingGameCharactersVictoryPoints;
    }

    public Map<Integer, Integer> getMapTerritoriesToVictoryPoints() {
        return endingGameTerritoriesVictoryPoints;
    }

    public Integer getResourcesForVictoryPoints() {
        return resourcesForVictoryPoints;
    }

    public Map<Integer, Integer> getMilitaryPointsForTerritories() {
        return militaryPointsForTerritories;
    }

    public Integer getWaitingRoomTimeout() {
        return waitingRoomTimeout;
    }

    public Integer[] getMinFaithPoints() {
        return minFaithPoints;
    }

    public Integer getWaitingRoomPlayersThreshold() {
        return WAITING_ROOM_PLAYERS_THRESHOLD;
    }
}
