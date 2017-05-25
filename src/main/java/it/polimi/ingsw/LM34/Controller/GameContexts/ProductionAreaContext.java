package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.BuildingCard;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class ProductionAreaContext extends AbstractGameContext {
    private ArrayList<FamilyMember> familyMembers;
    private Integer tempValue;

    public void initContext(Player player) {
        familyMembers = player.getFamilyMembers();
        setChanged();
        notifyObservers(player.getFamilyMembers());
    }


    @Override
    public void interactWithPlayer(Player player) {

        //TODO: implement what player can do here and modify the model in this controller class
        //Utilities.getContextByType(contexts, ContextType.ACTION_SLOT_CONTEXT).initContext();
        //TODO: player chooses the familymember
        setChanged();
        //TODO: now values of dices are increased
        FamilyMember memberChoosed = player.getFamilyMembers().get(1);
        //TODO: here we pass the family member chosed (only one)
        notifyObservers(memberChoosed);
        tempValue = memberChoosed.getValue(); //TODO: change this harcoded position

        BuildingCard buildingCard;
        for(AbstractDevelopmentCard c : player.getPersonalBoard().getBuildingCardOwned()) {
            buildingCard = (BuildingCard) c;
            if (buildingCard.getDiceValueToProduct() <= tempValue) {
                //ask player if he wants to activate this card
                buildingCard.applyPermanentEffect(player);
            }
        }

        //turnContext.interactWithPlayer();
    }


    @Override
    public ContextType getType() {
        return ContextType.PRODUCTION_AREA_CONTEXT;
    }


}
