package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TowerTest {

    private Tower tower;
    private AbstractDevelopmentCard abstractDevelopmentCard;

    @Before
    public void setUp() {
        tower = new Tower(DevelopmentCardColor.BLUE);
    }

    @Test
    public void addCardTest() {
        //TODO: solve abstractDevelopmentCard initialize problem
    }
}
