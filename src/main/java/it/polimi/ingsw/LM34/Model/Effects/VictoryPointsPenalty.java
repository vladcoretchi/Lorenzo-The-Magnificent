package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.END_GAME_CONTEXT;

/**
 * Created by vladc on 5/13/2017.
 */
// handles third period excommunication tiles
public class VictoryPointsPenalty extends AbstractEffect implements Observer {
    private Integer victoryPoints;
    private Resources resources;
    private Resources playerGoods;
    private DevelopmentCardColor cardColor;
    private Resources buildingCardsResources;

    public VictoryPointsPenalty(Integer victoryPoints, Resources playerGoods) {
        this.victoryPoints = victoryPoints;
        this.resources = new Resources();
        this.playerGoods = playerGoods;
        this.buildingCardsResources = new Resources();
        this.cardColor = null;
    }

    public VictoryPointsPenalty(Integer victoryPoints, Resources resources, Resources buildingCardsResources) {
        this.victoryPoints = victoryPoints;
        this.resources = resources;
        this.playerGoods = new Resources();
        this.buildingCardsResources = buildingCardsResources;
        this.cardColor = null;
    }

    public VictoryPointsPenalty(DevelopmentCardColor cardColor) {
        this.victoryPoints = 0;
        this.resources = new Resources();
        this.playerGoods = new Resources();
        this.buildingCardsResources = new Resources();
        this.cardColor = cardColor;
    }

    public Integer getVictoryPoints() {
        return this.victoryPoints;
    }

    public Resources getResources() {
        return this.resources;
    }

    public Resources getPlayerGoods() {
        return this.playerGoods;
    }

    public Resources getBuildingCardsWoods() {
        return this.buildingCardsResources;
    }

    public Optional<DevelopmentCardColor> getCardColor() {
        return Optional.ofNullable(this.cardColor);
    }

    @Override
    public void update(Observable o, Object arg) {
        //TODO
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(END_GAME_CONTEXT).addObserver(this);
    }

//end game




}
