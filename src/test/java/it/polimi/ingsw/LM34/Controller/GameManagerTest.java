package it.polimi.ingsw.LM34.Controller;

import com.sun.org.apache.regexp.internal.RE;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Cards.DevelopmentCardDeck;
import it.polimi.ingsw.LM34.Model.Cards.TerritoryCard;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Configurator;
import org.junit.Test;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.LM34.Utils.Configurator.getTerritoryCards;
import static org.junit.Assert.*;

public class GameManagerTest {

    private Market market;
    private List<Tower> towers;
    private WorkingArea harvestArea;
    private WorkingArea productionArea;
    private Resources resources = new Resources(3,3,3,3,3,3,3);
    private ResourcesBonus resourcesBonus = new ResourcesBonus(3);
    private ActionSlot actionSlot = new ActionSlot(true, 3, resourcesBonus);
    private ArrayList<ActionSlot> actionSlots = new ArrayList<>();

    @Test
    public void setUpGameSpaces() throws Exception {
        //Market market = Configurator.getMarket();
        actionSlots.add(actionSlot);
        Market market = new Market(actionSlots);
        assertTrue(market.getMarketSlots().get(0).isSinglePawnSlot());
        assertTrue(market.getMarketSlots().get(0).getDiceValue()==3);

    }

    @Test
    public void setUpDecks() throws Exception {
        DevelopmentCardDeck<TerritoryCard> territoryCardDeck = Configurator.getTerritoryCards();

        assertNull(territoryCardDeck.getCardColor());
    }

    @Test
    public void nextRound() throws Exception {
        Dice dice1 = new Dice(DiceColor.BLACK);
        List<Dice> dices = new ArrayList<>();

        dices.add(dice1);

        dices.forEach(Dice::rollDice);

        assertTrue(dices.get(0).getValue()<=6 && dices.get(0).getValue()>=1);

        assertTrue(dices.get(0).getColor()==DiceColor.BLACK);
    }

    @Test
    public void nextTurn() throws Exception {
    }

    @Test
    public void nextPeriod() throws Exception {
    }

    @Test
    public void nextPhase() throws Exception {
    }

    @Test
    public void replaceCards() throws Exception {
    }

    @Test
    public void sweepActionSlots() throws Exception {
    }

    @Test
    public void rollDices() throws Exception {
    }

    @Test
    public void setupPlayersResources() throws Exception {
    }

    @Test
    public void setupGameContexts() throws Exception {
    }

    @Test
    public void changeCards() throws Exception {
    }

    @Test
    public void bonusTileSelectionPhase() throws Exception {
    }

    @Test
    public void leaderSelectionPhase() throws Exception {
    }

}