package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.END_GAME_CONTEXT;

/**
 * Created by vladc on 5/13/2017.
 */
// handles third period excommunication tiles
public class VictoryPointsPenalty extends AbstractEffect implements Observer {
    private Integer victoryPoints;
    private Resources resources;
    private Integer playerGoods;
    private DevelopmentCardColor cardColor;
    Resources buildingCardsResources;


    public VictoryPointsPenalty(Integer victoryPoints, Integer playerGoods) {
        this.victoryPoints = victoryPoints;
        this.resources = null;
        this.playerGoods = playerGoods;
        this.buildingCardsResources = null;
        this.cardColor = null;
    }

    public VictoryPointsPenalty(Integer victoryPoints, Resources resources, Resources buildingCardsResources) {
        this.victoryPoints = victoryPoints;
        this.resources = resources;
        this.playerGoods = 0;
        this.buildingCardsResources = buildingCardsResources;
        this.cardColor = null;
    }

    public VictoryPointsPenalty(DevelopmentCardColor cardColor) {
        this.victoryPoints = null;
        this.resources = null;
        this.playerGoods = 0;
        this.buildingCardsResources = null;
        this.cardColor = cardColor;
    }

    public Integer getVictoryPoints() {
        return this.victoryPoints;
    }

    public Resources getResources() {
        return this.resources;
    }

    public Integer getPlayerGoods() {
        return this.playerGoods;
    }

    public Resources getBuildingCardsWoods() {
        return this.buildingCardsResources;
    }

    public DevelopmentCardColor getCardColor() {
        return this.cardColor;
    }

    @Override
    public void update(Observable o, Object arg) {
        //TODO
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext, Player player) {
        callerContext.getContextByType(END_GAME_CONTEXT).addObserver(this);
    }

//end game




}
