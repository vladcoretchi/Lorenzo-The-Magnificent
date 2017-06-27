package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.List;

public class VentureCard extends AbstractDevelopmentCard {
    private Integer endingVictoryPointsReward;
    private Integer militaryPointsRequired;
    private Integer militaryPointsSubtraction;
    //TODO: verified by the controller if this variable is set to true and then two type of payments are allowed
    private boolean isThereAlternativeToMilitaryPointsPayment; //carte "sostegno al cardinale e sostegno al papa"

    //Only a peculiarity of VentureCards

    public VentureCard(String ventureName, Integer period, Integer militaryPointsRequired, Integer militaryPointsSubtraction, Resources resourcesReq, List<AbstractEffect> instantBonus, Integer endingVictoryPointsReward) {
        this.name = ventureName;
        color= DevelopmentCardColor.PURPLE;
        resourceRequired = resourcesReq;
        this.period = period;
        this.permanentBonus = permanentBonus;
        this.instantBonus = instantBonus;
        this.endingVictoryPointsReward = endingVictoryPointsReward;
        this.militaryPointsRequired = militaryPointsRequired;
        this.militaryPointsSubtraction = militaryPointsSubtraction;
    }

    //TODO: handle in the controller the payment alternative?
    public boolean isThereAlternativeToMilitaryPointsPayment() {
        return isThereAlternativeToMilitaryPointsPayment;
    }

    public Integer getMilitaryPointsRequired(Integer militaryPointsRequired) {
        return this.militaryPointsRequired;
    }

    public Integer getEndingVictoryPointsReward() {
        return this.endingVictoryPointsReward;
    }
}
