package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.InstantBonus;
import it.polimi.ingsw.LM34.Model.Bonus.PermanentBonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public abstract class AbstractDevelopmentCard {
    private DevelopmentCardColor color;
    private String name;
    private Integer period;
    private PermanentBonus permanentBonus;
    private InstantBonus instantBonus;
    private Resources resourcesRequired;

    //remember that all cards only resources except VentureCard type
    public Resources getResourcesRequired() {return this.resourcesRequired;}
    public Integer getPeriod() {return this.period;}
    public String getName() {return this.name;}
    public PermanentBonus getPermanentBonus() {return this.permanentBonus;}
    public InstantBonus getInstantBonus() {return this.instantBonus;}
    public DevelopmentCardColor getColor() {return this.color;}
}
