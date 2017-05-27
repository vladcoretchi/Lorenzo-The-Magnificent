package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.BuildingCard;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class ProductionAreaContext extends AbstractGameContext {
    private FamilyMember memberChoosed;
    private Integer tempValue;

    public void initContext(Player player) {
        setChanged();
        notifyObservers(player.getFamilyMembers());
    }


    @Override
    public void interactWithPlayer(Player player) {


        //TODO: the player chooses the slot to occupy
        //TODO: player chooses the familymember
        //Utilities.getContextByType(contexts, ContextType.ACTION_SLOT_CONTEXT).initContext();
        setChanged();
        //TODO: now values of dices have been increased
        memberChoosed = player.getFamilyMembers().get(1);
        //TODO: here we pass the family member chosed (only one)
        notifyObservers(memberChoosed);
        tempValue = memberChoosed.getValue(); //TODO: change this harcoded position
        //TODO: tempValue= increasepawnsvalue.interactwithplayer();

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
