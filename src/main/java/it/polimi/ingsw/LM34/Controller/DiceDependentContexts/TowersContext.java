package it.polimi.ingsw.LM34.Controller.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.TowerSlot;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class TowersContext extends AbstractGameContext implements  DiceDependentContextsInterface {
    //TODO: use a temporary dice value instead of modifying the real dice value stored in the game manager
    //TODO: handle Filippo Brunelleschi, Cesare Borgia
    private Player currentPlayer;
    private Boolean hasPenalty; //"predicatore"
    private ArrayList<FamilyMember> familyMembers;
    private Integer tempValue;


    public TowersContext() {}


    public void initContext(Player player) {
        currentPlayer = player;
        familyMembers = currentPlayer.getFamilyMembers();
    }

    @Override
    public void interactWithPlayer(Player player) {

        FamilyMember familyMemberChoosed;
        //TODO: implement what player can do here and modify the model in this controller class
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
;
    }



    @Override
    public ContextType getType() {
        return ContextType.TOWERS_CONTEXT;
    }



    //TODO: evaluate if the buy should stay in this class
    public void buyCard(TowerSlot slot) throws InvalidCardType {
       currentPlayer.getPersonalBoard().addCard(slot.getCardStored());
    }

    public void setHasPenalty(Boolean hasPenalty) {
        this.hasPenalty = hasPenalty; //set by "predicatore"
    }

    @Override
    public void increaseTempValue(Integer servantsConsumed) {
        tempValue += servantsConsumed;
    }
}
