package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.InstantBonus;
import it.polimi.ingsw.LM34.Model.Bonus.PermanentBonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class VentureCard extends DevelopmentCardInterface {
    private DevelopmentCardColor color= DevelopmentCardColor.PURPLE;
    private String ventureName;
    private Integer phase;
    private PermanentBonus permanentBonus;
    private InstantBonus instantBonus;

    private boolean isThereAlternativeToMilitaryPointsPayment; //carte "sostegno al cardinale e sostegno al papa"

    //Only a peculiarity of VentureCards
    private Integer endingVictoryPointsReward;

    //TODO: evaluate to wrap resources and points in a single Bonus object
    Resources resourcesRequired;
    private Integer militaryPointsRequired;


    public VentureCard( String ventureName, Integer phase, Resources resourcesRequire,Integer endingVictoryPointsReward, PermanentBonus permanentBonus) {
        this.ventureName= ventureName;
        this.resourcesRequired= resourcesRequired;
        this.phase = phase;
        this.permanentBonus= permanentBonus;
        this.endingVictoryPointsReward = endingVictoryPointsReward;
    }


    public Resources getResourcesRequired() {
        return resourcesRequired;
    }

    @Override
    public Integer getPhase() {
        return phase;
    }

    @Override
    public String getName() {
        return ventureName;
    }

    @Override
    public InstantBonus getInstantBonus() {
        return instantBonus;
    }

    @Override
    public PermanentBonus getPermanentBonus() {
        return permanentBonus;
    }


    public String toString() {
        return "ventureCard";
    }


    //TODO: handle in the controller the payment alternative
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
