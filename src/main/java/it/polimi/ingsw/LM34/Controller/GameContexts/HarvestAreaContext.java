package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.TerritoryCard;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class HarvestAreaContext extends AbstractGameContext {
    private ArrayList<FamilyMember> familyMember;
    private Integer tempValue;

    public void initContext(Player player) {
        familyMember = player.getFamilyMembers();
        setChanged();
        notifyObservers(player.getFamilyMembers());
    }

    @Override
    public void interactWithPlayer(Player player) {
        //TODO: now values of dices are increased
        //TODO: implement what player can do here and modify the model in this controller class
        //Utilities.getContextByType(contexts, ContextType.ACTION_SLOT_CONTEXT).initContext();
        //TODO: player chooses the familymember
        setChanged();
        //TODO: now values of dices are increased
        FamilyMember memberChoosed = player.getFamilyMembers().get(1);
        //TODO: here we pass the family member chosed (only one)
        notifyObservers(memberChoosed);
        tempValue = memberChoosed.getValue(); //TODO: change this harcoded position

        TerritoryCard territoryCard;
        for(AbstractDevelopmentCard c : player.getPersonalBoard().getBuildingCardOwned()) {
            territoryCard = (TerritoryCard) c;
            if (territoryCard.getDiceValueToHarvest() <= tempValue) {
                //ask player if he wants to activate this card
                territoryCard.applyPermanentEffect(player);
            }
        }

        //turnContext.interactWithPlayer();
    }


    @Override
    public ContextType getType() {
        return ContextType.HARVEST_AREA_CONTEXT;
    }

}
