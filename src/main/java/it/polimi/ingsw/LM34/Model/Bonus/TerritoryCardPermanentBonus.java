package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 05/05/2017.
 */
public class TerritoryCardPermanentBonus extends PermanentBonus {
    private Resources resources;
    private Integer councilPrivilege;


    public TerritoryCardPermanentBonus(Resources res,Integer valueToHarvest, Integer councilPrivilege) {
        this.resources= res;
        //only for "Provincia" card
        this.councilPrivilege= councilPrivilege;

    }

    public Resources getResources () {
        return resources;
    }


}
