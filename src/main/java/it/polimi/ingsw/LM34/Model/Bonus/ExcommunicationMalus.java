package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 08/05/2017.
 */
public class ExcommunicationMalus extends PermanentBonus {
    private Integer onMilitaryPointsGainMalus = -1;
    private Integer onCoinGainMalus = -1;
    private Integer onServantGainMalus = -1;

    //this two all related to the same excommunication tile
    private Integer onWoodGainMalus = -1;
    private Integer onStoneGainMalus = -1;

    private Integer toHarvestValueMalus = -3;
    private Integer toProductionValueMalus = -3;
    private Integer diceValueMalus = -1;

    private Integer territoryCardBuyMalus = -4;
    private Integer buildingCardBuyMalus = -4;
    private Integer characterCardBuyMalus = -4;
    private Integer ventureCardBuyMalus = -4;

    private boolean marketBan;
    private boolean halveServantsValue;
    private boolean skipFirstFamilyMemberPlacement;

    private boolean denyFinalVictoryPointsPerCharacter;
    private boolean denyFinalVictoryPointsPerTerritory;
    private boolean denyFinalVictoryPointsByVentures;
    private boolean reduceVictoryPoints; // -1/5
    private boolean FinalsubtractionMilitaryToVictoryPoints;
    private boolean FinalStonesAndWoodsMalus;

}
