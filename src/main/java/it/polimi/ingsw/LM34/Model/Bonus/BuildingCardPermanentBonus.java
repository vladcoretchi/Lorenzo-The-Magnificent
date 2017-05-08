package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 05/05/2017.
 */
public class BuildingCardPermanentBonus extends PermanentBonus {
    private Resources resources;
    private Integer victoryPoints;
    private Integer militaryPoints;
    private Integer faithPoints;
    private boolean isPermanent;

    private Integer councilPrivilege; //cards "residenza", "castelletto"

    private boolean mutualExclusion;
}
