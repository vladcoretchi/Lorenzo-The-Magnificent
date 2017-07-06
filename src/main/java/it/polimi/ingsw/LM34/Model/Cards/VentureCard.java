package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.List;

public class VentureCard extends AbstractDevelopmentCard {
    private Integer endingVictoryPointsReward;
    private Integer militaryPointsRequired;
    private Integer militaryPointsSubtraction;
    private Boolean militaryPointsPaymentSubstitute;

    public VentureCard(String ventureName, Integer period, Integer militaryPointsRequired, Integer militaryPointsSubtraction, Resources resourcesReq, List<AbstractEffect> instantBonus, Integer endingVictoryPointsReward) {
        this.name = ventureName;
        this.color = DevelopmentCardColor.PURPLE;
        this.resourceRequired = resourcesReq;
        this.period = period;
        this.permanentBonus = null;
        this.instantBonus = instantBonus;
        this.endingVictoryPointsReward = endingVictoryPointsReward;
        this.militaryPointsRequired = militaryPointsRequired;
        this.militaryPointsSubtraction = militaryPointsSubtraction;
        this.militaryPointsPaymentSubstitute =
                (this.militaryPointsRequired != null && this.militaryPointsSubtraction != null &&
                        this.militaryPointsRequired > 0 && this.militaryPointsSubtraction > 0);
    }

    public boolean isThereAlternativeToMilitaryPointsPayment() {
        return militaryPointsPaymentSubstitute;
    }

    public Integer getMilitaryPointsRequired() {
        return this.militaryPointsRequired;
    }

    public Integer getMilitaryPointsSubstraction() {
        return this.militaryPointsSubtraction;
    }

    public Integer getEndingVictoryPointsReward() {
        return this.endingVictoryPointsReward;
    }
}
