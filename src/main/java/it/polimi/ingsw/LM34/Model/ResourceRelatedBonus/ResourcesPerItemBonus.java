package it.polimi.ingsw.LM34.Model.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Utilities;

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

    public ResourcesPerItemBonus(Resources bonusResources, DevelopmentCardColor cardColor, Integer diceValue) {
        this.bonusResources = bonusResources;
        this.cardColor = cardColor; //"nobile, araldo, cortigiana,governatore, zecca, teatro, esattoria,arco di triongo"
        this.militaryPointsRequired = militaryPointsRequired; //"generale" card
        this.diceValue = diceValue;
    }

    /*Constructor for "generale" card*/
    public ResourcesPerItemBonus(Player player, Resources bonusResources, Integer militaryPointsRequired) {
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
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts) {
        Utilities.getContextByType(ContextType.PRODUCTION_AREA_CONTEXT).addObserver(this);
    }

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
        Integer numberOfThatCardTypeOwned = 0;
        FamilyMember familyMemberUsed = (FamilyMember) arg;
        if( familyMemberUsed.getValue() >= diceValue) {
            try {
                /*Count the #cards of the specified development type belonging to the player*/
                numberOfThatCardTypeOwned = player.getPersonalBoard().getDevelopmentCardsByType(cardColor).size();
            } catch (InvalidCardType e) {
                e.printStackTrace();
            }
            for (Integer timesApplied = 0; timesApplied < numberOfThatCardTypeOwned; timesApplied++)
                player.addResources(bonusResources);
        }
    }


 }

