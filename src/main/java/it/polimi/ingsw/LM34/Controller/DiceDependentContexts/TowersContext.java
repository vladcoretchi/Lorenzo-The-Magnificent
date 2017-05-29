package it.polimi.ingsw.LM34.Controller.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.TowerSlot;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;

import java.util.ArrayList;

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
        towers = Configurator.getTowers();
    }


    public void initContext(Player player) {

    }

    @Override
    public void interactWithPlayer(Player player) {
        familyMembers = player.getFamilyMembers();

        FamilyMember familyMemberChoosed;
        //TODO: player chooses tower...
        //TODO: TOWER OF TERRITORY CARDS
        Tower towerSelected = towers.get(DevelopmentCardColor.GREEN.ordinal());

        //if player choose a territory card... let's calculate if he has enough military points, or
        //skip this step if cesaare borgia is activated
        //TODO: card choosed
        notifyObservers("cesare borgia, activate yourself");
        setChanged();
        //notifyObservers(familyMemberChoosed);
        //TODO
        /*
        buyCard(); tower slot selected*/
        //card.getInstantBonus().applyInstantEffect();
        //card.getPermanentBonus().applyPermanentEffect();
        towerSelected.getTowerSlotResources().applyEffect(player);

    }

    public void setHasPenalty(Boolean hasPenalty) {
        this.hasPenalty = hasPenalty; //set by "predicatore"
    }

    @Override
    public void increaseTempValue(Integer servantsConsumed) {
        tempValue += servantsConsumed;
    }

    @Override
    public void sweep() {
        towers.forEach(tower -> tower.sweep());
    }

    @Override
    public ContextType getType() {
        return ContextType.TOWERS_CONTEXT;
    }

    //TODO: evaluate if the buy should stay in this class
    public void buyCard(Player player, TowerSlot slot) throws InvalidCardType {
        //TODO: check many things here
        player.getPersonalBoard().addCard(slot.getCardStored());
    }

}
