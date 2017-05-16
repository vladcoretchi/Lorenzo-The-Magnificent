package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.EffectInterface;
import it.polimi.ingsw.LM34.Model.Bonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class VentureCard extends AbstractDevelopmentCard {
    private DevelopmentCardColor color= DevelopmentCardColor.PURPLE;
    private String name;
    private Integer period;
    private EffectInterface permanentBonus;
    private ResourcesBonus instantBonus;

    //verified by the controller if this variable is set to true and then two type of payments are allowed
    private boolean isThereAlternativeToMilitaryPointsPayment; //carte "sostegno al cardinale e sostegno al papa"

    //Only a peculiarity of VentureCards
    private Integer endingVictoryPointsReward;

    Resources resourcesRequired;
    private Integer militaryPointsRequired;

    public VentureCard( String ventureName, Integer period, Resources resourcesRequired, ResourcesBonus instantBonus, Integer endingVictoryPointsReward, EffectInterface permanentBonus) {
        this.name = ventureName;
        this.resourcesRequired = resourcesRequired;
        this.period = period;
        this.permanentBonus = permanentBonus;
        this.instantBonus = instantBonus;
        this.endingVictoryPointsReward = endingVictoryPointsReward;
    }

    public String getName() {
        return name;
    }

    public ResourcesBonus getInstantBonus() {
        return instantBonus;
    }


    public EffectInterface getPermanentBonus() {
        return permanentBonus;
    }

    @Override
    public Integer getPeriod() {
        return this.period;
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

    @Override
    public DevelopmentCardColor getColor() {return this.color;}
}
