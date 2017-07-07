package it.polimi.ingsw.LM34.Controller.NonInteractiveContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.VentureCard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.END_GAME_CONTEXT;

public class EndGameContext  extends AbstractGameContext {
    List<Player> players;
    Map<Player, Integer> victoryPointsToPlayers;

    //TODO: all excommunication III period penalties are applied here as observers


   public EndGameContext() {
        this.contextType = END_GAME_CONTEXT;
    }

    @Override
    public Void interactWithPlayer(Object... args) {
        players = gameManager.getPlayers();

        return null;
    }

    /**
     *Entry point of the class. This method is called by the game manager at the end of the game
     * @param players
     */


    /**
     * @return the hashmap with a correlation between players and their points earned by cards
     */
    public Map<Player, Integer> onEndGameCalculatePointsByDevelopmentCardsOwned(Map<Player, Integer> victoryPointsByPlayer) {
        //TODO
        return victoryPointsByPlayer;
    }

    /**
     * @return the hashmap with a correlation between players and their points earned by venture cards
     */
    public Map<Player, Integer> onEndCalculateVictoryPointsPerPlayerByVentureCards(Map<Player, Integer> victoryPointsToPlayers) {

        Integer totalVictoryPointsByVentureCardReward = 0;
        ArrayList<AbstractDevelopmentCard> tempPlayerVentureCards = new ArrayList<>();
        //for each player we calculate the sum of the victory points rewards provided by his venture cards stored in the personal board

            for (Player p : players) {
                //TODO: check if the player has the excommunication card that disables this step
                /*if(p.getMalus== noCalculateEndPoints)
                    victoryPointsToPlayers(p, 0);*/
                // else
                //TODO: now returned value is Optional, so we must fix the expected value
                // tempPlayerVentureCards = p.getPersonalBoard().getDevelopmentCardsByType(DevelopmentCardColor.PURPLE);

                totalVictoryPointsByVentureCardReward = 0;
                for (AbstractDevelopmentCard dci : tempPlayerVentureCards) {
                    VentureCard dciVenture = (VentureCard) dci;
                    totalVictoryPointsByVentureCardReward += dciVenture.getEndingVictoryPointsReward();
                }
                victoryPointsToPlayers.put(p, totalVictoryPointsByVentureCardReward);
            }


        return victoryPointsToPlayers;
    }


    /**
     * @return the hashmap with a correlation between players and their points earned by number of resources
     */
    public Map<Player, Integer> onEndCalculateVictoryPointsPerPlayerByResources(Map<Player, Integer> victoryPointsToPlayers) {

        Integer totalVictoryPointsByResources = 0;
        //for each player we calculate the sum of the victory points rewards provided by his resources

            for (Player p : players) {
                Resources resources = p.getResources();
                totalVictoryPointsByResources = Utilities.getTotalAmount(resources) /5;

                victoryPointsToPlayers.put(p, totalVictoryPointsByResources);
            }

        return victoryPointsToPlayers;
    }


    public void interactWithPlayer(List<Player> players) {
        setChanged();
        notifyObservers();

        onEndCalculateVictoryPointsPerPlayerByVentureCards(victoryPointsToPlayers);
        onEndCalculateVictoryPointsPerPlayerByResources(victoryPointsToPlayers);
        players.forEach(p -> gameManager.getPlayerNetworkController(p).endGame(players));
    }

}

