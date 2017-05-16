package it.polimi.ingsw.LM34.Model.Boards.GameBoard;


import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Enums.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;


//LET US USE A MATRIX FOR THE TOWER (ROWS (LEVELS)=4, COLOUMNS (TYPE OF CARD STORED)= 4)
//TODO: HASHMAP?
//the tower has been designed as a kind of matrix in which are stored card in the slots based on the card type and slot level
public class Tower extends GameSpace {

    //TODO: adjust this in order to know what kind of card are stored in this tower
    //TODO: add a CardColorEnum in towers?
    DevelopmentCardColor cardColor;
    private Integer MAX_LEVELS;
    private Integer level;
    private Integer coloumn;
    public TowerSlot []  slots;

    public Tower (DevelopmentCardColor cardColor, Integer MAX_LEVELS) {
        this.MAX_LEVELS = MAX_LEVELS;
        this.cardColor = cardColor;
        slots= new TowerSlot [MAX_LEVELS];
    }

    public boolean hasNextLevel() {
        return level<=MAX_LEVELS;
    }

    /**
     * @return the type of development cards that are stored in this tower
     */
    public DevelopmentCardColor getCardColor() {return cardColor;}


    public void addCard (TowerSlot slot, AbstractDevelopmentCard card) throws InvalidCardType {
        boolean inserted= false;
        //found the right tower based on the card type

        //ora aggiungi nello slot giusto al livello libero la carta...
        while(this.hasNextLevel() && inserted==false) {
            AbstractDevelopmentCard temp;
            if (!this.slots[level].isEmpty())
                level++;
            else {
                slots[level].setCardStored(card);
                inserted=true;
            }

        }
    }

    public AbstractDevelopmentCard retrieveCard (TowerSlot slot) throws Exception {
        AbstractDevelopmentCard temp;
        if (!this.slots[level].isEmpty()) {
            //temporary store the card
            temp= this.slots[level].getCardStored();
            //free the slot, il setCardStored(null) è brutto a vedersi
            this.slots[level].sweepTowerSlot();
            return temp;

        } else throw new Exception("space empty");

    }


    //call to check is a coloumn has already a familyMember inside so that a 3 coins penalty is activated
    public boolean isTowerEmpty() {
        while(this.hasNextLevel()) {
            if (!this.slots[level].isEmpty())
                return false;
        }
        return true;
    }



    //called at the end of each turn
   
    public void sweep() {
            while(this.hasNextLevel())
                slots[level].sweepTowerSlot();

    }


    public Resources getTowerSlotResources() {
        return slots[level].getResourcesReward();
    }
}