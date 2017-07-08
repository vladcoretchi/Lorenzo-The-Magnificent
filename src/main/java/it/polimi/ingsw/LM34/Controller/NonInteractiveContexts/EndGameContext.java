package it.polimi.ingsw.LM34.Controller.NonInteractiveContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Exceptions.Controller.NetworkConnectionException;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.VentureCard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.*;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.END_GAME_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.VICTORY_POINTS;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

/**
 * End game calculation players' scores and presentation of the results of the match
 * are performed in this {@link AbstractGameContext}
 */
public class EndGameContext  extends AbstractGameContext {
    private Map<DevelopmentCardColor, Integer> devCardPenalty = new EnumMap<DevelopmentCardColor, Integer>(DevelopmentCardColor.class);
    private Map<ResourceType, Integer> resourceTypePenalty = new EnumMap<ResourceType, Integer>(ResourceType.class);
   public EndGameContext() {
        this.contextType = END_GAME_CONTEXT;
    }

    /**
     *Entry point of the class. This method is called by the game manager at the end of the game in order
     * to calculate final victory points of each player that has joined the game and declare the winner
     * based on the highest score
     */
    @Override
    public Void interactWithPlayer(Object... args) {
        List<Player> players;
        players = this.gameManager.getPlayers();

        /*Trigger excommunication tiles that are related to the endGame*/
        setChanged();
        notifyObservers(this);

        /*Calculate the victory points of each player based on the Rules*/
        players.forEach(this::onEndCalculateVictoryPointsPerPlayerByResources);
        players.forEach(this::onEndGameCalculatePointsByDevelopmentCardsOwned);

        /*And now tell each player how many victory points everyone has scored and declare the winner*/
        players.forEach(player -> {
            if(player.isConnected())
                try {
                    this.gameManager.getPlayerNetworkController(player).endGame(players);
                } catch(NetworkConnectionException ex) {
                    LOGGER.log(Level.INFO, ex.getMessage(), ex);
                    player.setDisconnected();
                }
        });
        return null;
    }

    /**
     * Based on the Rules, the following method covers the calculation of victory points of each player
     * at the end og the game, keeping in consideration the effects of the
     * {@link it.polimi.ingsw.LM34.Model.Effects.VictoryPointsPenalty} associated to them
     * @return the hashmap with a correlation between players and their points earned by cards
     */
    private void onEndGameCalculatePointsByDevelopmentCardsOwned(Player player) {
        Optional<List<AbstractDevelopmentCard>> cards;
        Integer pointsToAdd = 0;
        for(DevelopmentCardColor cardType: DevelopmentCardColor.values())
            if (!devCardPenalty.containsKey(cardType)) {
                cards = player.getPersonalBoard().getDevelopmentCardsByType(cardType);
                if(cards.isPresent())
                if(cardType == DevelopmentCardColor.BLUE)
                    pointsToAdd += this.gameManager.getMapCharactersToVictoryPoints().get(cards.get());
                else if(cardType == DevelopmentCardColor.GREEN)
                    pointsToAdd += this.gameManager.getMapTerritoriesToVictoryPoints().get(cards.get());
                else if(cardType == DevelopmentCardColor.PURPLE)
                    pointsToAdd += onEndCalculateVictoryPointsPerPlayerByVentureCards(player);

                /*And now add to the player the points he deserves*/
                player.getResources().sumResourceType(VICTORY_POINTS, pointsToAdd);
            }
    }

    /**
     * @return the {@link it.polimi.ingsw.LM34.Enums.Model.ResourceType} of VICTORY_POINTS
     * that are provided by venture cards the player has bought along the game
     */
    public Integer onEndCalculateVictoryPointsPerPlayerByVentureCards(Player player) {
        Integer totalVictoryPointsByVentureCardReward =0;
        List<AbstractDevelopmentCard> venturesOwned = player.getPersonalBoard()
                                                    .getDevelopmentCardsByType(DevelopmentCardColor.PURPLE).orElse(null);
        for(AbstractDevelopmentCard ventureCard: venturesOwned)
            totalVictoryPointsByVentureCardReward += ((VentureCard) ventureCard).getEndingVictoryPointsReward();

        return totalVictoryPointsByVentureCardReward;
    }

    /**
     * Calculate the VICTORY_POINTS of the player based on the Rules and the excommunication tiles applied during the game
     * @param player that will have his score influenced by this step
     */
    public void onEndCalculateVictoryPointsPerPlayerByResources(Player player) {
        Resources playerResources = player.getResources();
        //for each player we calculate the sum of the victory points rewards provided by his resources
        playerResources.sumResourceType(VICTORY_POINTS,
                                            Utilities.getTotalAmount(player.getResources())
                                                    / this.gameManager.getResourcesForVictoryPoints());

        /*And now subtract points based on the ResourceType penalty the excommunication tiles have*/
        resourceTypePenalty.forEach((resTypeMalus, pointsPenalty) -> {
            playerResources.sumResourceType(VICTORY_POINTS,
                    playerResources.getResourceByType(resTypeMalus) * pointsPenalty);
        });
    }

    /**
     * Excommunication card of the III period, that related to number of card a player has, add their penalty
     * to this map
     * @param cardType specified by the {@link it.polimi.ingsw.LM34.Model.Effects.VictoryPointsPenalty} card
     * @param points (VICTORY_POINTS) of penalty for that effect
     */
    public void addDevelopmentCardPenalty(DevelopmentCardColor cardType, Integer pointsPenalty) {
        devCardPenalty.put(cardType, pointsPenalty);
    }

    /**
     * Excommunication card of the III period, that related to number of resources a player has, add their penalty
     * to this map
     * @param resourceType specified by the {@link it.polimi.ingsw.LM34.Model.Effects.VictoryPointsPenalty} card
     * @param points (VICTORY_POINTS) of penalty for that effect
     */
    public void addResourcesPenalty(ResourceType resourceType, Integer pointsPenalty) {
        resourceTypePenalty.put(resourceType, pointsPenalty);
    }
}

