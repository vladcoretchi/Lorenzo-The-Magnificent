package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 05/05/2017.
 */
public class BuildingCardPermanentBonus extends PermanentBonus {

    private Integer coinsForDevCardType;
    private DevelopmentCardColor devCardThatProvideReward; //esattoria, zecca, teatro, arco di trionfo
    private boolean mutualExclusion;

    //tesoreria, gilda degli scultori, gilda dei pittori, falegname, tagliapietre
    private Resources resourcesInChangeInputPairTwo;
    private Resources resourcesInChangeOutputPairTwo;
    private Resources resourcesInChangeInputPairOne;
    private Resources resourcesInChangeOutputPairOne;



    private boolean resourcesInputExclusion; //basilica only


    public Resources getResourcesInChangeInputPairOne() {return this.resourcesInChangeInputPairOne;}
    public Resources getResourcesInChangeOutputPairOne() {return this.resourcesInChangeInputPairOne;}
    public Resources getResourcesInChangeInputPairTwo() {return this.resourcesInChangeInputPairTwo;}
    public Resources getResourcesInChangeOutputPairTwo() {return this.resourcesInChangeInputPairTwo;}

    public Resources getResourceExchangeOutputOne(Resources exchangeInput) {
        if(exchangeInput==resourcesInChangeInputPairOne) //TODO: evaluate if this is correct, maybe compareTo is more appropriate
            return resourcesInChangeOutputPairOne;
        else
            return null; //TODO: evaluate a throw
    }
    public Resources getResourceExchangeOutputTwo(Resources exchangeInput) {
        if(exchangeInput==resourcesInChangeInputPairTwo) //TODO: evaluate if this is correct, maybe compareTo is more appropriate
            return resourcesInChangeOutputPairTwo;
        else
            return null; //TODO: evaluate a throw

    }
}
