package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Cards.DevelopmentCardInterface;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 11/05/2017.
 */
public class InstantBonus {
    private Resources resourcesReward;
    private Integer councilPrivilege;

    //special actions
    private Integer harvestActionValue;
    private Integer productionActionValue;

    private DevelopmentCardColor additionalDevCardAcquisition;
    //TODO: handle discount application in the controller
    private Resources discountOnAdditionalDevCardAcquisition; //architetto, mecenate

    private Integer militaryToVictoryPoints = 2; //generale
    private Integer vitoryPointsPerDevelopmentCard = 2;  //TODO: remove this hardcoded value
    //governatore, cortigiana, araldo, nobile, generale



    public InstantBonus(Resources res, Integer councilPrivilege) {
        this.resourcesReward = res;
        this.councilPrivilege = councilPrivilege;
    }
    /*overloading*/
    public InstantBonus(Resources res, Integer councilPrivilege, Integer harvestActionValue, Integer productionActionValue, DevelopmentCardColor additionalDevCardAcquisition, Resources discount) {

        this(res, councilPrivilege);
        this.harvestActionValue = harvestActionValue;
        this.productionActionValue = productionActionValue;
        this.additionalDevCardAcquisition = additionalDevCardAcquisition;
        this.discountOnAdditionalDevCardAcquisition = discount;
    }

    /*overloading*/
    public InstantBonus(Resources res, Integer councilPrivilege, Integer harvestActionValue, Integer productionActionValue) {
        this(res,councilPrivilege);
        this.harvestActionValue = harvestActionValue;
        this. productionActionValue = productionActionValue;
    }

    public InstantBonus(Integer vitoryPointsPerDevelopmentCard, Integer militaryToVictoryPoints) {
        this();
        this.vitoryPointsPerDevelopmentCard = vitoryPointsPerDevelopmentCard;
        this.militaryToVictoryPoints = militaryToVictoryPoints;
    }
    public InstantBonus() {}


    public Integer getCouncilPrivilege() {return councilPrivilege;}

    public Resources getResources() {return this.resourcesReward;}

    public DevelopmentCardColor getAdditionalDevCardAcquisition() {return additionalDevCardAcquisition;}

    public Integer getProductionActionValue() {return productionActionValue;}

    public Integer getHarvestActionValue() {return harvestActionValue;}

    public Resources getDiscountOnAdditionalDevCardAcquisition() {return this.discountOnAdditionalDevCardAcquisition;}

}
