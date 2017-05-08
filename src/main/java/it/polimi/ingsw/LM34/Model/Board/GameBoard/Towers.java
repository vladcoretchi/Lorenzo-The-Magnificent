package it.polimi.ingsw.LM34.Model.Board.GameBoard;


import it.polimi.ingsw.LM34.Exception.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Bonus;
import it.polimi.ingsw.LM34.Model.Cards.DevelopmentCardInterface;
import it.polimi.ingsw.LM34.Model.Resources;


//LET US USE A MATRIX FOR THE TOWER (ROWS (LEVELS)=4, COLOUMNS (TYPE OF CARD STORED)= 4)
//TODO: HASHMAP?
//the tower has been designed as a kind of matrix in which are stored card in the slots based on the card type and slot level
public class Towers  {
    private Integer MAX_LEVELS;
    private Integer MAX_COLOUMNS;
    private Integer level;
    private Integer coloumn;
    public TowerSlot [][]  slots;

    //TODO: add a CardColorEnum in towers?
    public Towers (Integer MAX_COLOUMNS, Integer MAX_LEVELS) {
        this.MAX_LEVELS = MAX_LEVELS;
        this.MAX_COLOUMNS = MAX_COLOUMNS;
        slots= new TowerSlot [MAX_LEVELS][MAX_COLOUMNS];
    }

    public boolean hasNextLevel() {
        return level<=MAX_LEVELS;
    }

    public boolean hasNextColoumn() {
        return level<=MAX_COLOUMNS;
    }

    public void addCard (TowerSlot slot, DevelopmentCardInterface card) throws InvalidCardType {
        boolean inserted= false;
        //found the right tower based on the card type
        switch (card.toString()) {
            case "territoryCard":
                coloumn= 1;
                break;
            case "characterCard":
                coloumn= 2;
                break;
            case "buildingCard":
                coloumn= 3;
                break;
            case "ventureCard":
                coloumn= 4;
                break;
            default:
                throw new InvalidCardType("This card is not a DevelopmentCard");
        }
        //ora aggiungi nello slot giusto al livello libero la carta...
        while(this.hasNextLevel() && inserted==false) {
            DevelopmentCardInterface temp;
            if (!this.slots[level][coloumn].isEmpty())
                level++;
            else {
                slots[level][coloumn].setCardStored(card);
                inserted=true;
            }

        }
    }


    public DevelopmentCardInterface retrieveCard (TowerSlot slot) throws Exception {
        DevelopmentCardInterface temp;
        if (!this.slots[level][coloumn].isEmpty()) {
            //temporary store the card
            temp= this.slots[level][coloumn].getCardStored();
            //free the slot, il setCardStored(null) è brutto a vedersi
            this.slots[level][coloumn].sweepTowerSlot();
            return temp;

        } else throw new Exception("space empty");

    }


    //call to check is a coloumn has already a familyMember inside so that a 3 coins penalty is activated
    public boolean isColoumnEmpty(Integer coloumn) {
        while(this.hasNextLevel()) {
            if (!this.slots[level][coloumn].isEmpty())
                return false;
        }
        return true;
    }



    //called at the end of each turn
    public void SweepTower() {
        for (Integer col=1; col<MAX_COLOUMNS; col++)
            while(this.hasNextLevel())
                slots[level][col].sweepTowerSlot();

    }


    public Resources getTowerSlotResources() {
        return slots[level][coloumn].getResourcesReward();
    }
}