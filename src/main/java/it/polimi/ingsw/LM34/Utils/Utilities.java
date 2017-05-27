package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.DevelopmentCardDeck;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;

/**
 * Utilities class
 * Contains generic methods that can be used everywhere
 *
 * @author vladc
 */
public final class Utilities {
    private ArrayList<AbstractGameContext> contexts;
    private Utilities() {}

    /**
     * Sums element by element 2 Integer arrays
     * @param array1 - first array of Integers
     * @param array2 - second array of Integers
     * @return array of Integers with a length equal to the minimum of 2 parameters
     */
    public static Integer[] sumElementByElement(Integer array1[], Integer array2[]) {
        if (array1 == null || array2 == null)
            throw new NullPointerException();

        Integer arrayLength = Math.min(array1.length, array2.length);
        Integer sum[] = new Integer[arrayLength];
        for (int i = 0; i < arrayLength; i++)
            sum[i] = array1[i] + array2[i];

        return sum;
    }

    public static BiFunction<Integer, Integer, Integer> sumInteger = (val1, val2) -> val1 + val2;


    /**
     This static method is called often by observers to register themselves to the right context
     */
    public static AbstractGameContext getContextByType(ArrayList<AbstractGameContext> contexts, ContextType contextType) {
        //TODO: refactor this loop
        for(AbstractGameContext context : contexts)
            if(context.getType() == ContextType.CURCH_REPORT_CONTEXT)
                    return context;

        return null;
    }

    public static Integer getTotalAmount(Resources resources) {

         Integer totalUnit = 0;
         totalUnit = resources.getResourceByType(ResourceType.WOODS)+
                     resources.getResourceByType(ResourceType.STONES)+
                     resources.getResourceByType(ResourceType.COINS)+
                     resources.getResourceByType(ResourceType.SERVANTS);

         return totalUnit;
    }

    //TODO: refactor
    public static ArrayList<Player>  setNewTurnOrder(CouncilPalace councilPalace, ArrayList<Player> players) {

        ArrayList<Player> oldPlayersOrder = players;
        ArrayList<Player> newPlayersOrder = new ArrayList<>();
        ArrayList<FamilyMember> membersInOrder = councilPalace.getOccupyingPawns();

        /*First remove all multipe pawns associated to the same player*/
        /*These inner loops do not add temporal complexity because pawns' count is negligible*/
        for(FamilyMember fm1 : membersInOrder)
            for(FamilyMember fm2 : membersInOrder)
                if(fm1.getFamilyMemberColor() == fm2.getFamilyMemberColor())
                    membersInOrder.remove(fm2); //keep just the first pawn for every player

        /*now that there is one pawn per players order the player based on pawns' positions*/
        for(FamilyMember fm : membersInOrder) {
            PawnColor color = fm.getFamilyMemberColor();
            for (Player player : oldPlayersOrder)
                if (player.getPawnColor() == color) {
                    newPlayersOrder.add(player);
                    oldPlayersOrder.remove(player);
                }
        }
        /**
         *@param remainingPlayers that did not placed their familyMembers in councilPalace
         */
        newPlayersOrder.addAll(oldPlayersOrder);

        /**
         * @return this is the new players order for the next round
         */
        return newPlayersOrder;
    }

    /**
     *
     * @param towers from which choose the right tower by development card type
     * @param developmentDeck from which to extract and place in the tower the cards for the new round
     */
    public static void placeNewRoundCards(ArrayList<Tower> towers, DevelopmentCardDeck<?> developmentDeck) {

        Tower tower = new Tower();
        Iterator iterator  = developmentDeck.iterator();
        AbstractDevelopmentCard card;

        //select the right tower...
        for (Tower t : towers)
            if (t.getDevelopmentTypeStored() == developmentDeck.getCardColor())
                tower = t;

        //...and now place every card in the deck until the tower's slots are full
        Integer cardStored = 0;
        while (iterator.hasNext() && cardStored< Configurator.CARD_PER_ROUND) {
            card = (AbstractDevelopmentCard) iterator.next();
            tower.addCard(card);
        }

    }

    /**
     * @param player on which to control the amount of resources he has available
     * @param resourcesRequired to activate the exchange bonus
     * @see it.polimi.ingsw.LM34.Model.ResourceRelatedBonus.ResourcesExchangeBonus
     * @return if the effect is activable or not
     */
    public static Boolean hasEnoughResources (Player player, Resources resourcesRequired) {
        Resources resourcesAvailable = player.getResources();
        for(ResourceType resType : ResourceType.values())
            if(!(resourcesAvailable.getResourceByType(resType) >= resourcesRequired.getResourceByType(resType)))
                return false;

        return true;
    }

    /*Called by Game Manager only at the beginning of the game*/
    public void setContexts(ArrayList<AbstractGameContext> contexts) {
        this.contexts = contexts;
    }

    /**
     *
     * @param cards excommunication deck from which to extract one card by period
     * @return the 3 card choosed
     */
    public static ArrayList<ExcommunicationCard> getExcommunictionCards(ArrayList<ExcommunicationCard> cards) {
        ArrayList<ExcommunicationCard> exCardChoosed = new ArrayList<>();
        Integer period = 1;
        for(ExcommunicationCard card : cards)
            if(card.getPeriod() == period) {
                exCardChoosed.add(card);
                period++;
            }
        return exCardChoosed; //return the 3 cards, one by period
    }
}