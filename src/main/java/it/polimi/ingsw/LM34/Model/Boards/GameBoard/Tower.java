package it.polimi.ingsw.LM34.Model.Boards.GameBoard;


import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//LET US USE A MATRIX FOR THE TOWER (ROWS (LEVELS)=4, COLOUMNS (TYPE OF CARD STORED)= 4)
//TODO: HASHMAP?
//the tower has been designed as a kind of matrix in which are stored card in the slots based on the card type and slot level
public class Tower extends GameSpace implements Serializable {

    //TODO: adjust this in order to know what kind of card are stored in this tower
    //TODO: add a CardColorEnum in towers?
    private DevelopmentCardColor cardColor;
    private Integer level;
    private Integer coloumn;
    private ArrayList<TowerSlot>  slots;

    public Tower (DevelopmentCardColor cardColor) {
        this.cardColor = cardColor;
        slots = new ArrayList<>();
    }

    public boolean hasNextLevel() {
        return level < slots.size();
    }

    /**
     * @return the type of development cards that are stored in this tower
     */
    public DevelopmentCardColor getCardColor() {return cardColor;}


    public void addCard (AbstractDevelopmentCard card) {
        boolean inserted= false;
        //found the right tower based on the card type
        level = 0;
        //now add the card at the first free tower slot found
        while(this.hasNextLevel() && !inserted) {
            AbstractDevelopmentCard temp;
            if (!this.slots.get(level).isEmpty())
                level++;
            else {
                slots.get(level).setCardStored(card);
                inserted=true;
            }
        }
    }

    public AbstractDevelopmentCard retrieveCard (TowerSlot slot) throws Exception {
        AbstractDevelopmentCard temp;
        level = slot.getLevel();
        if (!this.slots.get(level).isEmpty()) {
            //temporary store the card
            temp= this.slots.get(level).getCardStored();
            //free the slot, il setCardStored(null) è brutto a vedersi
            this.slots.get(level).sweepTowerSlot();
            return temp;

        } else throw new Exception("space empty");
    }

    //call to check is a coloumn has already a familyMember inside so that a 3 coins penalty is activated
    public boolean isTowerEmpty() {
        while(this.hasNextLevel()) {
            if (!this.slots.get(level).isEmpty())
                return false;
        }
        return true;
    }

    //called at the end of each turn
   @Override
    public void sweep() {
            while(this.hasNextLevel())
                slots.get(level).sweepTowerSlot();
    }
    public DevelopmentCardColor getDevelopmentTypeStored() {
        return this.cardColor;
    }

    //TODO
    public Tower() {}

    public List<AbstractDevelopmentCard> getCardsStored() {
        List<AbstractDevelopmentCard> cardsStoredInTower = new ArrayList<>();
        this.slots.forEach(slot -> cardsStoredInTower.add(slot.getCardStored()));
        return cardsStoredInTower;
    }

    //TODO: load slots bonuses from configurator
    public void setSlots(List<TowerSlot> slotsToLoad) {
        slots = (ArrayList) slotsToLoad;
    }

    public List<TowerSlot> getSlotsStored() {
       return slots;
    }

    public List<TowerSlot> getTowerSlots() {
        return slots;
    }
}