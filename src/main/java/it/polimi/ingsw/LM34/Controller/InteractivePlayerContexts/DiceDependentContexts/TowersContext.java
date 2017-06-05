package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.TowerSlot;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Configurator;

import java.util.ArrayList;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class TowersContext extends AbstractGameContext implements  DiceDependentContextsInterface {
    //TODO: use a temporary dice value instead of modifying the real dice value stored in the game manager
    //TODO: handle Filippo Brunelleschi, Cesare Borgia
    private Boolean hasPenalty; //"predicatore"
    private ArrayList<FamilyMember> familyMembers;
    private Integer tempValue;
    private ArrayList<Tower> towers;


    public TowersContext() {
        contextType = ContextType.TOWERS_CONTEXT;
        towers = Configurator.getTowers();
    }


    @Override
    public void interactWithPlayer(Player player) {
        familyMembers = player.getFamilyMembers();

        FamilyMember familyMemberChoosed;
        getContextByType(INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT).interactWithPlayer(player);
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
        towerSelected.getTowerSlotResources().applyEffect(this, player);

    }

    public void setHasPenalty(Boolean hasPenalty) {
        this.hasPenalty = hasPenalty; //set by "predicatore"
    }



    @Override
    public void sweep() {
        towers.forEach(tower -> tower.sweep());
    }


    //TODO: evaluate if the buy should stay in this class
    public void buyCard(Player player, TowerSlot slot) throws InvalidCardType {
        //TODO: check many things here
        player.getPersonalBoard().addCard(slot.getCardStored());
    }

  //TODO
  @Override
    public ArrayList<ActionSlot> getActionSlots() {
        return null;
    }
}