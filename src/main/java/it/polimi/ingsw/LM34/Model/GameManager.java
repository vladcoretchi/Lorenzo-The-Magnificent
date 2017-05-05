package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Model.Board.GameBoard.GameBoard;
import it.polimi.ingsw.LM34.Model.Cards.*;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 05/05/2017.
 */
public class GameManager {
    private Integer period;
    private Integer turn;
    private ArrayList<Dice> dices;
    private ArrayList<Player> players;

    //TODO: apply factory patterns to decks
    //decks cointaining all the 96 development cards of the game, that at the beginning of each period will be partially loaded in the towers
    private ArrayList<TerritoryCard> territoryCardDeck;
    private ArrayList<CharacterCard> characterCardDeck;
    private ArrayList<VentureCard> ventureCardsDeck;
    private ArrayList<BuildingCard> buildingCardsDeck;
    private ArrayList<LeaderCard> leaderCardsDeck;

    //TODO: remove GameBoard class and add all of his variable directly in GameManager?
    private GameBoard gameBoard;

    //TODO: add and implements all the methods of this game controller


}
