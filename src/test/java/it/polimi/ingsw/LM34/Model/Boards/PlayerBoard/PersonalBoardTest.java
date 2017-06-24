package it.polimi.ingsw.LM34.Model.Boards.PlayerBoard;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class PersonalBoardTest {

     Resources cardResourcesRequired = new Resources();
     AbstractDevelopmentCard card = new FillAbstractDevelopmentCard();

    @Test
    public void addCard() {

        cardResourcesRequired.modifyResourceByType(ResourceType.COINS, 3);
        cardResourcesRequired.modifyResourceByType(ResourceType.WOODS, 1);
        cardResourcesRequired.modifyResourceByType(ResourceType.STONES, 3);

        PersonalBoard personalBoard = new PersonalBoard();
        try {
            personalBoard.addCard(card);
        } catch (InvalidCardType invalidCardType) {
            fail("invalid card type");
        }

    }

    private class FillAbstractDevelopmentCard extends AbstractDevelopmentCard {

        FillAbstractDevelopmentCard() {
            this.name = "Bank";
            this.period = 3;
            this.color = DevelopmentCardColor.YELLOW;
            this.permanentBonus = null; //see how to fill an AbstractEffect
            this.instantBonus = null;
            this.resourceRequired = cardResourcesRequired;

        }
    }

}