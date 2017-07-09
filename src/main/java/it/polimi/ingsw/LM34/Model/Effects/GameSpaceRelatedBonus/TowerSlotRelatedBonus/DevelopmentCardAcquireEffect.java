package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.ProductionAreaContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.TowersContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.FamilyMemberSelectionContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.CardTypeNumLimitReachedException;
import it.polimi.ingsw.LM34.Exceptions.Controller.NetworkConnectionException;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughMilitaryPoints;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughResourcesException;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.Utils.Validator;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.FAMILY_MEMBER_SELECTION_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.PRODUCTION_AREA_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.TOWERS_CONTEXT;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class DevelopmentCardAcquireEffect extends AbstractEffect implements Observer {
    private static final long serialVersionUID = 9154894195925064951L;

    private DevelopmentCardColor color;
    private Integer diceValue;
    private ResourcesBonus requirementsDiscount;

    /**
     * applied only if value is not null
     * if true - the value is applied when the user goes on a tower action space.
     * else - the user can go to a tower action space without using the family member
     */
    private Boolean isRelative;

    public DevelopmentCardAcquireEffect(DevelopmentCardColor color, Integer value, Boolean relative) {
        this(color, value, relative, null);
    }

    public DevelopmentCardAcquireEffect(DevelopmentCardColor color, Integer value, Boolean relative, ResourcesBonus requirementsDiscount) {
        this.color = color;
        this.diceValue = value;
        this.requirementsDiscount = requirementsDiscount;
        this.isRelative = relative;
    }

    public DevelopmentCardColor getDevelopmentCardColor() {
        return this.color;
    }

    public Integer getValue() {
        return this.diceValue;
    }

    public ResourcesBonus getRequirementsDiscount() {
        return this.requirementsDiscount;
    }

    public Boolean isRelative() {
        return this.isRelative;
    }

    @Override
    public void update(Observable o, Object arg) {
        AbstractGameContext callerContext = (AbstractGameContext) arg;

        if(callerContext instanceof FamilyMemberSelectionContext) {
            FamilyMemberSelectionContext familyMemberContext = (FamilyMemberSelectionContext) callerContext;
            if(familyMemberContext.getCurrentActionContext().name().equals(TOWERS_CONTEXT.name())) {
                TowersContext towerContext = (TowersContext) familyMemberContext.getContextByType(familyMemberContext.getCurrentActionContext());
                if(this.color.name().equals(DevelopmentCardColor.MULTICOLOR.name()) || this.color.name().equals(towerContext.getTowerColor().name()))
                    familyMemberContext.changeFamilyMemberValue(this.diceValue, this.isRelative);
            }
        }
        else if(callerContext instanceof TowersContext) {
            TowersContext towerContext = (TowersContext) callerContext;
            if(this.isRelative) {
                if(this.color.name().equals(DevelopmentCardColor.MULTICOLOR.name()) || this.color.name().equals(towerContext.getTowerColor().name()))
                    towerContext.setRequirementsDiscount(this.requirementsDiscount.getResources());
            }
            else {
                towerContext.noFamilyMemberRequired();
                towerContext.setRequirementsDiscount(requirementsDiscount.getResources());
            }
        }
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        if(this.isRelative) {
            callerContext.getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT).addObserver(this);
            callerContext.getContextByType(TOWERS_CONTEXT).addObserver(this);
        }
        else {
            TowersContext towerContext = (TowersContext) callerContext.getContextByType(ContextType.TOWERS_CONTEXT);
            towerContext.addObserver(this);
            playerInteraction(towerContext, Optional.empty());
            towerContext.deleteObserver(this);
        }
    }

    private void playerInteraction(TowersContext towerContext, Optional<Exception> error) {
        if(towerContext.getCurrentPlayer().isConnected())
            try {
                PlayerAction action = towerContext.getGameManager().getActivePlayerNetworkController().freeAction(
                        new PlayerAction(PlayerSelectableContexts.TOWERS_CONTEXT, new ImmutablePair<>(this.color, this.diceValue)), error);

                Validator.checkPlayerActionValidity(action);

                if(action.getContext() == null)
                    return;

                if(!action.getContext().name().equals(PlayerSelectableContexts.TOWERS_CONTEXT.name()))
                    throw new IncorrectInputException();

                towerContext.interactWithPlayer(action.getAction(), this.color, this.diceValue);
            } catch (IncorrectInputException |
                    NotEnoughMilitaryPoints |
                    OccupiedSlotException |
                    NotEnoughResourcesException |
                    CardTypeNumLimitReachedException ex) {
                LOGGER.log(Level.WARNING, ex.getMessage(), ex);
                playerInteraction(towerContext, Optional.of(ex));
            } catch (NetworkConnectionException ex) {
                LOGGER.log(Level.INFO, ex.getMessage(), ex);
                towerContext.getCurrentPlayer().setDisconnected();
            }
    }
}
