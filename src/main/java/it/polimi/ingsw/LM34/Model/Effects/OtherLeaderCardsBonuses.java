package it.polimi.ingsw.LM34.Model.Effects;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/14/2017.
 */

//TODO: rename (at least)
public class OtherLeaderCardsBonuses extends AbstractEffect implements Observer {
    private Boolean noMilitaryPointsRequirementForTerritoryCards; //TODO: handle this in Tower related Bonuses
    private Boolean noOccupiedTowerTax; //TODO: handle this in Tower related Bonuses

    private Boolean copyOtherLeaderCardBonus;

    public OtherLeaderCardsBonuses(Boolean noMilitaryPointsRequirementForTerritoryCards, Boolean actionSpaceInvasion, Boolean noOccupiedTowerTax) {
        this.noMilitaryPointsRequirementForTerritoryCards = noMilitaryPointsRequirementForTerritoryCards;
        this.noOccupiedTowerTax = noOccupiedTowerTax;
        this.copyOtherLeaderCardBonus = false;
    }

    public OtherLeaderCardsBonuses(Boolean copyOtherLeaderCardBonus) {
        this.noMilitaryPointsRequirementForTerritoryCards = false;
        this.noOccupiedTowerTax = false;
        this.copyOtherLeaderCardBonus = copyOtherLeaderCardBonus;
    }

    public Boolean hasMilitaryPointsRequirementForTerritoryCardsExemption() {
        return this.noMilitaryPointsRequirementForTerritoryCards;
    }

    public Boolean hasOccupoiedTowerTaxExemption() {
        return this.noOccupiedTowerTax;
    }


    public Boolean canCopyOtherLeaderCardBonus() {
        return this.copyOtherLeaderCardBonus;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
