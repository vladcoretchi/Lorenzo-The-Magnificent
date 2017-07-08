package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.EndGameContext;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.END_GAME_CONTEXT;

// handles third period excommunication tiles
public class VictoryPointsPenalty extends AbstractEffect implements Observer {
    private static final long serialVersionUID = -7309570915017663730L;
    
    private Integer victoryPoints;
    private Optional<Resources> resources;
    private Resources playerGoods;
    private DevelopmentCardColor cardColor;
    private Resources buildingCardsResources;

    public VictoryPointsPenalty(Integer victoryPoints, Resources playerGoods) {
        this.victoryPoints = victoryPoints;
        this.resources = Optional.empty();
        this.playerGoods = playerGoods;
        this.buildingCardsResources = new Resources();
        this.cardColor = null;
    }

    public VictoryPointsPenalty(Integer victoryPoints, Resources resources, Resources buildingCardsResources) {
        this.victoryPoints = victoryPoints;
        this.resources = Optional.of(resources);
        this.playerGoods = new Resources();
        this.buildingCardsResources = buildingCardsResources;
        this.cardColor = null;
    }

    public VictoryPointsPenalty(DevelopmentCardColor cardColor) {
        this.victoryPoints = 0;
        this.resources = Optional.empty();
        this.playerGoods = new Resources();
        this.buildingCardsResources = new Resources();
        this.cardColor = cardColor;
    }

    public Optional<DevelopmentCardColor> getCardColor() {
        return Optional.ofNullable(this.cardColor);
    }

    /**
     * Enter in action and apply the penalties once invoked by the {@link EndGameContext}
     */
    @Override
    public void update(Observable o, Object arg) {
        AbstractGameContext callerContext = (((AbstractGameContext) o));
        EndGameContext endGameContext = (EndGameContext) callerContext.getContextByType(END_GAME_CONTEXT);

        if(cardColor != null)
            endGameContext.addDevelopmentCardPenalty(cardColor, victoryPoints);
        if(resources.isPresent())
            resources.get().getResources().forEach((resourceType, integer) -> {
                endGameContext.addResourcesPenalty(resourceType, victoryPoints);
            });
    }

    /**
     * Register to the right {@link Observable EndGameContext}
     */
    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(END_GAME_CONTEXT).addObserver(this);
    }
}
