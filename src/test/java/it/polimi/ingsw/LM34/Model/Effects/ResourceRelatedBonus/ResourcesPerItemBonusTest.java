package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.GameRoom;
import javafx.application.Platform;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.MILITARY_POINTS;
import static org.junit.Assert.*;

public class ResourcesPerItemBonusTest {

    @Test
    public void applyEffect() throws Exception {
        Player player = new Player("aldo", PawnColor.GREEN, new PersonalBoard());
        Resources bonusResources = new Resources(1,1,1,1,1,1,1);
        DevelopmentCardColor cardColor;
        Integer militaryPointsRequired = 10;
        ResourceIncomeContext incomeContext;

    }

}