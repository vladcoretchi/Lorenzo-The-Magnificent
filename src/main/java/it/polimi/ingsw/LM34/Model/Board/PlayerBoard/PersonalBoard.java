package it.polimi.ingsw.LM34.Model.Board.PlayerBoard;

import it.polimi.ingsw.LM34.Exception.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import java.util.ArrayList;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
/*this class has the aim to aggregate what resources and cards the player have collected*/
public class PersonalBoard {
    //cards
    private ArrayList<AbstractDevelopmentCard> territories = new ArrayList<AbstractDevelopmentCard>();
    private ArrayList<AbstractDevelopmentCard> characters = new ArrayList<AbstractDevelopmentCard>();
    private ArrayList<AbstractDevelopmentCard> ventures = new ArrayList<AbstractDevelopmentCard>();
    private ArrayList<AbstractDevelopmentCard> buildings = new ArrayList<AbstractDevelopmentCard>();
    //bonus tile
    BonusTile personalBonusTile;


    public void addCard(AbstractDevelopmentCard card) throws InvalidCardType {
        switch (card.getColor()) {
            case GREEN:
                territories.add(card);
                break;
            case BLUE:
                characters.add(card);
                break;
            case YELLOW:
                buildings.add(card);
                break;
            case PURPLE:
                ventures.add(card);
                break;
            default:
                throw new InvalidCardType("This card is not a DevelopmentCard");
        }
    }


    public ArrayList<AbstractDevelopmentCard> getDevelopmentCardsByType(DevelopmentCardColor color) throws InvalidCardType {
        switch (color) {
            case PURPLE:
                return ventures;
            case BLUE:
                return characters;
            case GREEN:
                return territories;
            case YELLOW:
                return buildings;
            default:
                throw new InvalidCardType("This card is not a DevelopmentCard");

        }
    }
}
