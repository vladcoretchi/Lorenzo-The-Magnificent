package it.polimi.ingsw.LM34.Model.Bonus;

/**
 * Created by vladc on 5/14/2017.
 */
public class ActionSpacePenalty implements EffectInterface {
    private Integer noResourcesFromTowerLevels[];
    private Boolean marketBan;

    public ActionSpacePenalty(Integer noResourcesFromTowerActionSpaces[]) {
        this.noResourcesFromTowerLevels = noResourcesFromTowerActionSpaces;
        this.marketBan = false;
    }

    public ActionSpacePenalty(Boolean marketBan) {
        this.noResourcesFromTowerLevels = null;
        this.marketBan = marketBan;
    }

    public Integer[] getNoResourcesFromTowerLevels() {
        return this.noResourcesFromTowerLevels;
    }

    public Boolean hasMarketBan() {
        return this.marketBan;
    }
}
