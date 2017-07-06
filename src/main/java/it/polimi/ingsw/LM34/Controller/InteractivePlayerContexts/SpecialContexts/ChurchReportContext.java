package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Configurator;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.CHURCH_REPORT_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.FAITH_POINTS;

public class ChurchReportContext extends AbstractGameContext {
    private Integer[] minFaithPoints;
    public ChurchReportContext() {
        this.contextType = CHURCH_REPORT_CONTEXT;
    }

    @Override
    public Void interactWithPlayer(Object... args) {
        minFaithPoints = this.gameManager.getMinFaithPoints();
        Boolean applyPenalty = false;

        Player player = (Player) args[0];
        player.getResources().sumResources(new Resources(0,5,5));

        if(player.getResources().getResourceByType(FAITH_POINTS) < minFaithPoints[this.gameManager.getPeriod()])
            applyPenalty = true;
        else
            if(this.gameManager.getPlayerNetworkController(player).churchSupport())
                player.addResources(new Resources(0, 0, Configurator.getFaithPath().get(player.getResources().getResourceByType(FAITH_POINTS))));
            else
                applyPenalty = true;

        if(applyPenalty) {
            this.gameManager.getExcommunicationCards().get(gameManager.getPeriod()).getPenalty().applyEffect(this);
            player.addExcommunicationCards(this.gameManager.getExcommunicationCards().get(gameManager.getPeriod()));
        }

        return null;
    }
}


