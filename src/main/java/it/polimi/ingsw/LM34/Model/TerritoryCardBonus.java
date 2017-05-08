package it.polimi.ingsw.LM34.Model;

/**
 * Created by GiulioComi on 05/05/2017.
 */
public class TerritoryCardBonus extends Bonus {
    private Resources resources;
    private Integer victoryPoints;
    private Integer militaryPoints;
    private Integer faithPoints;
    private Integer councilPrivilege;
    private boolean isPermanent;


    public TerritoryCardBonus(Resources res,Integer valueToHarvest, boolean isPermanent, Integer vp, Integer mp, Integer fp, Integer councilPrivilege) {
        this.resources= res;
        this.victoryPoints= vp;
        this.militaryPoints= mp;
        this.faithPoints= fp;
        //only for "Provincia" card
        this.councilPrivilege= councilPrivilege;

        //disguish permanent effects from instant ones
        //TODO: evaluate if a boolean is really a smart solution
        this.isPermanent= isPermanent;
    }

    public Resources getResources () {
        return resources;
    }

    public Integer getVictoryPoints() {
        return victoryPoints;
    }

    public Integer getFaithPoints() {
        return faithPoints;
    }

    public Integer getMilitaryPoints() {
        return militaryPoints;
    }

    public boolean isPermanent() {
        return isPermanent;
    }
}
