package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Exceptions.Controller.NetworkConnectionException;
import it.polimi.ingsw.LM34.Enums.UI.GameInformationType;
import it.polimi.ingsw.LM34.Model.Effects.ChurchSupportBonus;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Configurator;

import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.CHURCH_REPORT_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.FAITH_POINTS;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class ChurchReportContext extends AbstractGameContext {
    public ChurchReportContext() {
        this.contextType = CHURCH_REPORT_CONTEXT;
    }

    @Override
    public Void interactWithPlayer(Object... args) {
        Integer[] minFaithPoints = this.gameManager.getMinFaithPoints();
        Boolean applyPenalty = true;

        activateTemporarilyPlayerObservers();

        Player player = (Player) args[0];

        if(player.isConnected())
            try {
                if (player.getResources().getResourceByType(FAITH_POINTS) >= minFaithPoints[this.gameManager.getPeriod()] &&
                        !this.gameManager.getPlayerNetworkController(player).churchSupport())
                    applyPenalty = false;
            } catch (NetworkConnectionException ex) {
                LOGGER.log(Level.INFO, ex.getMessage(), ex);
                this.getCurrentPlayer().setDisconnected();
            }

        if(applyPenalty) {
            this.gameManager.getExcommunicationCards().get(this.gameManager.getPeriod()).getPenalty().applyEffect(this);
            player.addExcommunicationCards(this.gameManager.getExcommunicationCards().get(this.gameManager.getPeriod()));

            this.gameManager.getPlayers().forEach(p -> {
                if(player.isConnected())
                    try {
                        this.gameManager.getPlayerNetworkController(p)
                                .informInGamePlayers(GameInformationType.INFO_EXCOMMUNICATION, player.getPlayerName(), player.getPawnColor());
                    } catch (NetworkConnectionException ex) {
                        LOGGER.log(Level.INFO, ex.getMessage(), ex);
                        this.getCurrentPlayer().setDisconnected();
                    }
            });
        }
        else {
            player.addResources(new Resources(0, 0, this.gameManager.getConfigurator().getFaithPath()
                    .get(player.getResources().getResourceByType(FAITH_POINTS))));
            setChanged();
            notifyObservers(this);
        }

        return null;
    }

    /**
     * Activate all effects that are related to this special context
     */
    private void activateTemporarilyPlayerObservers() {
        this.getCurrentPlayer().getActivatedLeaderCards().forEach(card -> {
            if (card.getBonus() instanceof ChurchSupportBonus) {
                card.getBonus().applyEffect(this);
            }
        });
    }
}


