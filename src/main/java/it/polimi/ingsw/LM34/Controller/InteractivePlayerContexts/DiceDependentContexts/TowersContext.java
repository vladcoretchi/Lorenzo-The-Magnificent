package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.NoResourcesException;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.TowerSlot;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Configurator;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class TowersContext extends AbstractGameContext implements  DiceDependentContextsInterface {
    //TODO: use a temporary dice value instead of modifying the real dice value stored in the game manager
    //TODO: handle Filippo Brunelleschi, Cesare Borgia
    private Boolean hasPenalty; //"predicatore"
    private List<FamilyMember> familyMembers;
    private Integer tempValue;
    private List<Tower> towers;


    public TowersContext() {
        contextType = ContextType.TOWERS_CONTEXT;
        towers = Configurator.getTowers();
    }


    @Override
    public void interactWithPlayer() {
        familyMembers = gameManager.getCurrentPlayer().getFamilyMembers();

        FamilyMember familyMemberChoosed;
        getContextByType(INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT).interactWithPlayer();
        //TODO: player chooses tower...
        //TODO: TOWER OF TERRITORY CARDS
        Tower towerSelected = towers.get(DevelopmentCardColor.GREEN.ordinal());

        //if player choose a territory card... let's calculate if he has enough military points, or
        //skip this step if cesare borgia is activated
        //TODO: card choosed
        notifyObservers("cesare borgia, activate yourself");
        setChanged();
        //notifyObservers(familyMemberChoosed);
        //TODO
        /*
        buyCard(); tower slot selected*/
        //card.getInstantBonus().applyEffect();
        //card.getPermanentBonus().applyEffect();
        Integer levelSelected = 0; //TODO
        towerSelected.getTowerSlots().get(levelSelected).getResourcesReward().applyEffect(this);

        finalizeRewardAttribution();

    }

    public void setHasPenalty(Boolean hasPenalty) {
        this.hasPenalty = hasPenalty; //set by "predicatore"
    }



    @Override
    public void sweep() {
        towers.forEach(tower -> tower.sweep());
    }

    //TODO: complete this, Vlad ;D
    public void buyCard(Player player, TowerSlot slot) throws InvalidCardType, NoResourcesException {
        player.subResources(slot.getCardStored().getResourcesRequired());
        /*setChanged();
        notifyObservers();*/
        player.getPersonalBoard().addCard(slot.getCardStored());
    }


  @Override
    public ArrayList<ActionSlot> getActionSlots() {
        return null;
    }

    @Override
    public void finalizeRewardAttribution(Player player) {
        //TODO
    }


    public void finalizeRewardAttribution() {
        ((ResourceIncomeContext)gameManager.getContextByType(RESOURCE_INCOME_CONTEXT)).finalizeIncome();
    }


}