package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/13/2017.
 */
// handles third period excomunication tiles
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

    }

//end game

    @Override
    public void applyEffect(Player player) {

    }



}
