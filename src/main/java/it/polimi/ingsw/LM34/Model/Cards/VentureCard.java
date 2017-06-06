package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class VentureCard extends AbstractDevelopmentCard {
    private DevelopmentCardColor color= DevelopmentCardColor.PURPLE;
    private String name;
    private Integer period;
    Resources resourcesRequired;

    //TODO: verified by the controller if this variable is set to true and then two type of payments are allowed
    private boolean isThereAlternativeToMilitaryPointsPayment; //carte "sostegno al cardinale e sostegno al papa"

    //Only a peculiarity of VentureCards
    private Integer endingVictoryPointsReward;
    private Integer militaryPointsRequired;

    public VentureCard( String ventureName, Integer period, Resources resourcesRequired, ArrayList<AbstractEffect> instantBonus, Integer endingVictoryPointsReward, AbstractEffect permanentBonus) {
        this.name = ventureName;
        this.resourcesRequired = resourcesRequired;
        this.period = period;
        this.permanentBonus = permanentBonus;
        this.instantBonus = instantBonus;
        this.endingVictoryPointsReward = endingVictoryPointsReward;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public AbstractEffect getPermanentBonus() {
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
