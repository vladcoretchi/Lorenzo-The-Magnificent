package it.polimi.ingsw.LM34.Controller.NonInteractiveContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.VentureCard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by GiulioComi on 15/05/2017.
 */

public class EndGameContext  extends AbstractGameContext {
    ArrayList<Player> players;
    HashMap<Player, Integer> victoryPointsToPlayers;

    //TODO: all excommunication III period penalties are applied here as observers


   public EndGameContext() {

        contextType = ContextType.END_GAME_CONTEXT;
        victoryPointsToPlayers = new HashMap<>();
    }

    @Override
    public void interactWithPlayer() {
        players = gameManager.getPlayers();
    }

    /**
     *Entry point of the class. This method is called by the game manager at the end of the game
     * @param players
     */


    /**
     * @return the hashmap with a correlation between players and their points earned by cards
     */
    public HashMap<Player, Integer> onEndGameCalculatePointsByDevelopmentCardsOwned(HashMap<Player, Integer> victoryPointsByPlayer) {
        //TODO
        return victoryPointsByPlayer;
    }

    /**
     * @return the hashmap with a correlation between players and their points earned by venture cards
     */
    public HashMap<Player, Integer> onEndCalculateVictoryPointsPerPlayerByVentureCards(HashMap<Player, Integer> victoryPointsToPlayers) {

        Integer totalVictoryPointsByVentureCardReward = 0;
        ArrayList<AbstractDevelopmentCard> tempPlayerVentureCards = new ArrayList<AbstractDevelopmentCard>();
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
    public HashMap<Player, Integer> onEndCalculateVictoryPointsPerPlayerByResources(HashMap<Player, Integer> victoryPointsToPlayers) {

        Integer totalVictoryPointsByResources = 0;
        //for each player we calculate the sum of the victory points rewards provided by his resources

            for (Player p : players) {
                Resources resources = p.getResources();
                totalVictoryPointsByResources = Utilities.getTotalAmount(resources) /5;

                victoryPointsToPlayers.put(p, totalVictoryPointsByResources);
            }

        return victoryPointsToPlayers;
    }


    public void interactWithPlayer(ArrayList<Player> players) {
        setChanged();
        notifyObservers();

        onEndCalculateVictoryPointsPerPlayerByVentureCards(victoryPointsToPlayers);
        onEndCalculateVictoryPointsPerPlayerByResources(victoryPointsToPlayers);
        players.forEach(p -> gameManager.getPlayerNetworkController(p).endGameResults(victoryPointsToPlayers));
        //TODO: show calculations and ranks
    }

}

