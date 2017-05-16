package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by vladc on 5/14/2017.
 */

//TODO: rename (at least)
public class OtherLeaderCardsBonuses implements EffectInterface {
    private Boolean noMilitaryPointsRequirementForTerritoryCards;
    private Boolean actionSpaceInvasion;
    private Boolean noOccupiedTowerTax;
    private Resources resourcesForChurchSupport;
    private Boolean copyOtherLeaderCardBonus;

    public OtherLeaderCardsBonuses(Boolean noMilitaryPointsRequirementForTerritoryCards, Boolean actionSpaceInvasion, Boolean noOccupiedTowerTax) {
        this.noMilitaryPointsRequirementForTerritoryCards = noMilitaryPointsRequirementForTerritoryCards;
        this.actionSpaceInvasion = actionSpaceInvasion;
        this.noOccupiedTowerTax = noOccupiedTowerTax;
        this.resourcesForChurchSupport = null;
        this.copyOtherLeaderCardBonus = false;
    }

    public OtherLeaderCardsBonuses(Resources resourcesForChurchSupport) {
        this.noMilitaryPointsRequirementForTerritoryCards = false;
        this.actionSpaceInvasion = false;
        this.noOccupiedTowerTax = false;
        this.resourcesForChurchSupport = resourcesForChurchSupport;
        this.copyOtherLeaderCardBonus = false;
    }

    public OtherLeaderCardsBonuses(Boolean copyOtherLeaderCardBonus) {
        this.noMilitaryPointsRequirementForTerritoryCards = false;
        this.actionSpaceInvasion = false;
        this.noOccupiedTowerTax = false;
        this.resourcesForChurchSupport = null;
        this.copyOtherLeaderCardBonus = copyOtherLeaderCardBonus;
    }

    public Boolean hasMilitaryPointsRequirementForTerritoryCardsExemption() {
        return this.noMilitaryPointsRequirementForTerritoryCards;
    }

    public Boolean canInvadeOccupiedActionSpaces() {
        return this.actionSpaceInvasion;
    }

    public Boolean hasOccupoiedTowerTaxExemption() {
        return this.noOccupiedTowerTax;
    }

    public Resources getResourcesForChurchSupport() {
        return this.resourcesForChurchSupport;
    }

    public Boolean canCopyOtherLeaderCardBonus() {
        return this.copyOtherLeaderCardBonus;
    }
}
