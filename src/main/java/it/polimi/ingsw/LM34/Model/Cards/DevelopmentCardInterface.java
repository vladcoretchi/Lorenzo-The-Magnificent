package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public interface DevelopmentCardInterface {

    //remember that all cards only resources except VentureCard type
    Resources getResourcesRequired();
    Integer getPeriod();
    String getName();
    Bonus getInstantBonus();
    Bonus getPermanentBonus();
}
