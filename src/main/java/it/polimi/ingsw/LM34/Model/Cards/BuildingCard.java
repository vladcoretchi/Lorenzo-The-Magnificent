package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class BuildingCard extends AbstractDevelopmentCard {
    private DevelopmentCardColor color= DevelopmentCardColor.YELLOW;



    private String name;
    private Integer diceValueToProduct;
    private Resources resourcesRequired; //we do not store single type of resources as Integer but we wrap them in a Resources class
    private Integer period;
    //this two variables together represents the instant Effects
    private ResourcesBonus instantBonus;
    private ResourcesBonus permanentBonus;



    public BuildingCard(String buildingName, Integer diceValueToProduct, Integer period, Resources resourcesRequired, ResourcesBonus instantBonus, ResourcesBonus  permanentBonus) {
        this.resourcesRequired= resourcesRequired;
        this.name= buildingName;
        this.period = period;
        this.permanentBonus= permanentBonus;
        this.diceValueToProduct= diceValueToProduct;
        this.instantBonus = instantBonus;
    }


    @Override
    public Resources getResourcesRequired() {
        return resourcesRequired;
    }

    @Override
    public AbstractEffect getPermanentBonus() {
        return this.permanentBonus;
    }

    @Override
    public ResourcesBonus getInstantBonus() {
        return instantBonus;
    }

    public Integer getDiceValueToProduct() {
        return diceValueToProduct;
    }

    @Override
    public DevelopmentCardColor getColor() { return this.color; }

    @Override
    public Integer getPeriod() {
        return this.period;
    }

    @Override
    public String getName() {
        return name;
    }

    public void applyPermanentEffect(ArrayList<AbstractGameContext> contexts, Player player) {
        this.getPermanentBonus().applyEffect(player);
    }
}
