package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.PermanentBonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class VentureCard implements DevelopmentCardInterface {
    private DevelopmentCardColor color= DevelopmentCardColor.PURPLE;
    private String ventureName;
    private Integer period;
    private PermanentBonus permanentBonus;

    //TODO: evaluate to wrap resources and points in a single Bonus object
    Resources resourcesRequired;
    private Integer militaryPointsRequired;


    public VentureCard( String ventureName, Integer period, Resources resourcesRequire, PermanentBonus permanentBonus) {
        this.ventureName= ventureName;
        this.resourcesRequired= resourcesRequired;
        this.period= period;
        this.permanentBonus= permanentBonus;
    }


    public Resources getResourcesRequired() {
        return resourcesRequired;
    }

    @Override
    public Integer getPeriod() {
        return period;
    }

    @Override
    public String getName() {
        return ventureName;
    }



    @Override
    public PermanentBonus getPermanentBonus() {
        return permanentBonus;
    }


    public String toString() {
        return "ventureCard";
    }

    public Integer getMilitaryPointsRequired(Integer militaryPointsRequired) {
        return this.militaryPointsRequired;
    }

}
