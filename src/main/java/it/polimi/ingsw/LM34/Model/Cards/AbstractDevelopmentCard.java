package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;

import java.io.Serializable;
import java.util.List;

/**
 * Abstract class for all the development cards
 * {@link TerritoryCard}
 * {@link BuildingCard}
 * {@link CharacterCard}
 * {@link VentureCard}
 */
public abstract class AbstractDevelopmentCard implements Serializable {
    private static final long serialVersionUID = 474897345172229849L;

    protected String name;
    protected Integer period;
    protected DevelopmentCardColor color;
    protected AbstractEffect permanentBonus;
    protected List<AbstractEffect> instantBonus;
    protected Resources resourceRequired;

    public String getName() {
        return this.name;
    }

    public Integer getPeriod() {
        return this.period;
    }

    public DevelopmentCardColor getColor () { return this.color; }

    /**
     * @return {@link Resources} required to buy this card
     */
    public Resources getResourcesRequired() { return this.resourceRequired; }

    /**
     * @return {@link List<AbstractEffect>} of instant bonuses associated to the card
     */
    public List<AbstractEffect> getInstantBonus() { return this.instantBonus; }

    /**
     * @return {@link AbstractEffect} permanent bonus associated to the card
     */
    public AbstractEffect getPermanentBonus() { return this.permanentBonus; }

}


