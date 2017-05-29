package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/13/2017.
 */
public class ResourcesPerItemBonus extends AbstractEffect implements Observer {
    private Player player;
    private Resources bonusResources;
    private DevelopmentCardColor cardColor; //"nobile, araldo, cortigiana,governatore, zecca, teatro, esattoria,arco di triongo"
    private Integer militaryPointsRequired; //for "generale" card
    private Integer diceValue;
    private ArrayList<ContextType> contextToBeSubscribedTo; //PRODUCTION_AREA_CONTEXT only for buildings, null for characters

    public ResourcesPerItemBonus(Resources bonusResources, DevelopmentCardColor cardColor, Integer diceValue, ArrayList<ContextType> contexts) {
        this.contextToBeSubscribedTo = contexts;
        this.bonusResources = bonusResources;
        this.cardColor = cardColor; //"nobile, araldo, cortigiana,governatore, zecca, teatro, esattoria,arco di triongo"
        this.militaryPointsRequired = militaryPointsRequired; //"generale" card
        this.diceValue = diceValue;
    }

    /*Constructor for "generale" card*/
    public ResourcesPerItemBonus(Player player, Resources bonusResources, Integer militaryPointsRequired) {
        contextToBeSubscribedTo = new ArrayList<>();
        contextToBeSubscribedTo.add(ContextType.TURN_CONTEXT);
        this.contextToBeSubscribedTo = null;
        this.player = player;
        this.bonusResources = bonusResources;
        this.cardColor = null; //"nobile, araldo, cortigiana,governatore, zecca, teatro, esattoria,arco di triongo"
        this.militaryPointsRequired = militaryPointsRequired; //"generale" card
        this.diceValue = 0;
    }


    /**
     * Only for building cards permanent bonuses
     * @param contexts
     */


    @Override
    public void applyEffect(Player player) {

    }

    /**
     * For instant bonuses
     */
    public void applyInstantEffect() {

        Integer numberOfThatCardTypeOwned = 0;


        for(Integer timesApplied = 0; timesApplied < militaryPointsRequired; timesApplied++)
            player.addResources(bonusResources);

        try {
            numberOfThatCardTypeOwned = player.getPersonalBoard().getDevelopmentCardsByType(cardColor).size();
        }
        catch (InvalidCardType e) {
            e.printStackTrace();
        }
        for(Integer timesApplied = 0; timesApplied < numberOfThatCardTypeOwned; timesApplied++)
            player.addResources(bonusResources);

    }

    @Override
    public void update(Observable o, Object arg) {
        //TODO: testing codes
        System.out.println("Sono stato notificato");
        //TODO: the following line has to be propagated to all bonuses that are instant special effects
        o.deleteObserver(this);
        //GameManager.getContextByType(ContextType.TURN_CONTEXT).deleteObserver(this);


       /* Integer numberOfThatCardTypeOwned = 0;
        ResourceIncomeContext incomeContext;
        FamilyMember familyMemberUsed = (FamilyMember) arg;
        if( familyMemberUsed.getValue() >= diceValue) {
            try {
                /*Count the #cards of the specified development type belonging to the player*/
             /*   numberOfThatCardTypeOwned = player.getPersonalBoard().getDevelopmentCardsByType(cardColor).size();
            } catch (InvalidCardType e) {
                e.printStackTrace();
            }
            for (Integer timesApplied = 0; timesApplied < numberOfThatCardTypeOwned; timesApplied++) {
                incomeContext = (ResourceIncomeContext) GameManager.getContextByType(ContextType.PRODUCTION_AREA_CONTEXT);
                incomeContext.handleResources(player, bonusResources);
            }
        }*/
    }


//production area
}

