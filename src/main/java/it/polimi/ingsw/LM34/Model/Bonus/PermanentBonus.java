package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 08/05/2017.
 */
public class PermanentBonus {
    //resources&councilPrivilege represents the instant bonuses
    private Resources resources;
    private Integer councilPrivilege;

    private Integer perdevelopmentCardRewardCoins;
    private Integer perTerritoryCardRewardVictoryPoints;
    private Integer perBuildingCardRewardVictoryPoints;
    private Integer perCharacterCardReward;

    private Integer toHarvestPermanentBonus;
    private Integer toProductionPermanentBonus;

    private Integer resourcesPerDevelopmentCard;
    private Resources resourcesInChange;
    private boolean mutualExclusion;
}
