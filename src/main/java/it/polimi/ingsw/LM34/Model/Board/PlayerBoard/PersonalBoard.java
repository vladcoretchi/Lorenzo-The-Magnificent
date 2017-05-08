package it.polimi.ingsw.LM34.Model.Board.PlayerBoard;

import it.polimi.ingsw.LM34.Exception.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Bonus;
import it.polimi.ingsw.LM34.Model.Cards.DevelopmentCardInterface;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import java.util.ArrayList;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
/*this class has the aim to aggregate what resources and cards the player have collected*/
public class PersonalBoard {
    private ArrayList<Bonus> harvestBonuses;
    private ArrayList<Bonus> productionBonuses;

    //cards
    private ArrayList<DevelopmentCardInterface> territories= new ArrayList<DevelopmentCardInterface>();
    private ArrayList<DevelopmentCardInterface> characters= new ArrayList<DevelopmentCardInterface>();
    private ArrayList<DevelopmentCardInterface> ventures= new ArrayList<DevelopmentCardInterface>();
    private ArrayList<DevelopmentCardInterface> buildings= new ArrayList<DevelopmentCardInterface>();
    //bonus tile
    BonusTile personalBonusTile;



    public void addCard(DevelopmentCardInterface card) throws InvalidCardType {
        switch (card.getClass().toString()) {
            case "TerritoryCard":
                territories.add(card);
                break;
            case "characterCard":
                break;
            case "buildingCard":
                break;
            case "ventureCard":
                break;
            default:
                throw new InvalidCardType("This card is not a DevelopmentCard");
        }
    }
}
