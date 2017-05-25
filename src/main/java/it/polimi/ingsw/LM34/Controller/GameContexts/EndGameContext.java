package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
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

    //TODO: all excommunication III period penalties are applied here as observers

    @Override
    public ContextType getType() {
        return ContextType.END_GAME_CONTEXT;
    }


    public void endContext(ArrayList<Player> players) {
        interactWithPlayer(players);
    }

    public HashMap<Player, Integer> onEndGameCalculatePointsByDevelopmentCardsOwned(HashMap<Player, Integer> victoryPointsByPlayer) {
        //TODO
        return victoryPointsByPlayer;
    }

    /**
     * @return the hashmap with a correlation between players and their points earned by venture cards
     */
    public HashMap<Player, Integer> onEndCalculateVictoryPointsPerPlayerByVentureCards() {

        HashMap<Player, Integer> victoryPointsToPlayers = new HashMap<Player, Integer>();
        Integer totalVictoryPointsByVentureCardReward = 0;
        ArrayList<AbstractDevelopmentCard> tempPlayerVentureCards = new ArrayList<AbstractDevelopmentCard>();
        //for each player we calculate the sum of the victory points rewards provided by his venture cards stored in the personal board
        try {
            for (Player p : players) {
                //TODO: check if the player has the excommunication card that disables this step
                /*if(p.getMalus== noCalculateEndPoints)
                    victoryPointsToPlayers(p, 0);*/
                // else
                tempPlayerVentureCards = p.getPersonalBoard().getDevelopmentCardsByType(DevelopmentCardColor.PURPLE);
                totalVictoryPointsByVentureCardReward = 0;
                for (AbstractDevelopmentCard dci : tempPlayerVentureCards) {
                    VentureCard dciVenture = (VentureCard) dci;
                    totalVictoryPointsByVentureCardReward += dciVenture.getEndingVictoryPointsReward();
                }
                victoryPointsToPlayers.put(p, totalVictoryPointsByVentureCardReward);
            }
        } catch (InvalidCardType ict) {
            ict.printStackTrace();
        } //TODO:  adjust this exception handle

        return victoryPointsToPlayers;
    }



    public HashMap<Player, Integer> onEndCalculateVictoryPointsPerPlayerByResources() {

        HashMap<Player, Integer> victoryPointsToPlayers = new HashMap<Player, Integer>();
        Integer totalVictoryPointsByResources = 0;
        //for each player we calculate the sum of the victory points rewards provided by his resources

            for (Player p : players) {
                Resources resources = p.getResources();
                totalVictoryPointsByResources = Utilities.getTotalAmount(resources) /5; //TODO

                victoryPointsToPlayers.put(p, totalVictoryPointsByResources);
            }

        return victoryPointsToPlayers;
    }


    public void interactWithPlayer(ArrayList<Player> players) {
        setChanged();
        notifyObservers();

        onEndCalculateVictoryPointsPerPlayerByVentureCards();
        onEndCalculateVictoryPointsPerPlayerByResources();
        //TODO: show calculations and ranks
    }

}

