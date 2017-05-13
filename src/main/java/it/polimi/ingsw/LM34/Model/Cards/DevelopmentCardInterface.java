package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.InstantBonus;
import it.polimi.ingsw.LM34.Model.Bonus.PermanentBonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public abstract class DevelopmentCardInterface {
    private DevelopmentCardColor color;
    private String name;
    private Integer period;
    private PermanentBonus permanentBonus;
    private InstantBonus instantBonus;
    private Resources resourcesRequired;

    //remember that all cards only resources except VentureCard type
    Resources getResourcesRequired() {return this.resourcesRequired;}
    Integer getPeriod() {return this.period;}
    String getName() {return this.name;}
    PermanentBonus getPermanentBonus() {return this.permanentBonus;}
    InstantBonus getInstantBonus() {return this.instantBonus;}
}
