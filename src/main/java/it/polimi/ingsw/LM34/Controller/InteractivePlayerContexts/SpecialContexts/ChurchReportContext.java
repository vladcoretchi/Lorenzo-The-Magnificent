package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Model.Player;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.CHURCH_REPORT_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.FAITH_POINTS;
import static it.polimi.ingsw.LM34.Utils.Configurator.MIN_FAITHS_POINTS;

public class ChurchReportContext extends AbstractGameContext {

    public ChurchReportContext() {
        this.contextType = CHURCH_REPORT_CONTEXT;
    }

    @Override
    public Void interactWithPlayer(Object... args) {
        Boolean applyPenalty = false;

        Player player = (Player) args[0];

        if(player.getResources().getResourceByType(FAITH_POINTS) < MIN_FAITHS_POINTS[this.gameManager.getPeriod()])
            applyPenalty = true;
        else
            if(this.gameManager.getPlayerNetworkController(player).churchSupport()) {
                setChanged();
                notifyObservers(this);
            } else
                applyPenalty = true;

        if(applyPenalty) {
            this.gameManager.getExcommunicationCards().get(gameManager.getPeriod()).getPenalty().applyEffect(this);
            player.addExcommunicationCards(this.gameManager.getExcommunicationCards().get(gameManager.getPeriod()));
        }

        return null;
    }
}


