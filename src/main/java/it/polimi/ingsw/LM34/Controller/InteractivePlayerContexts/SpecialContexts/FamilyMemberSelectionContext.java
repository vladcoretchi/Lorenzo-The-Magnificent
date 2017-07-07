package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Validator;

import java.util.List;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.FAMILY_MEMBER_SELECTION_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.SERVANTS;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class FamilyMemberSelectionContext extends AbstractGameContext {
    private Integer familyMemberValue;
    private ContextType currentActionContext;

    public FamilyMemberSelectionContext() {
        this.contextType = FAMILY_MEMBER_SELECTION_CONTEXT;
    }

    @Override
    public FamilyMember interactWithPlayer(Object... args) throws IncorrectInputException {
        Integer minValueRequested;
        Boolean servantsRequestAnyway;
        try {
            minValueRequested = (Integer) args[0];
            servantsRequestAnyway = (Boolean) args[1];
            this.currentActionContext = (ContextType) args[2];
        } catch(Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new IncorrectInputException();
        }

        List<FamilyMember> familyMembers = this.gameManager.getCurrentPlayer().getAvailableFamilyMembers();
        Integer selectedFamilyMember = this.gameManager.getActivePlayerNetworkController().familyMemberSelection(familyMembers);
        Validator.checkValidity(selectedFamilyMember, familyMembers);

        FamilyMember familyMember = familyMembers.get(selectedFamilyMember);
        this.familyMemberValue = familyMember.getValue();

        setChanged();
        notifyObservers(this);

        if(this.gameManager.getCurrentPlayer().getResources().getResourceByType(SERVANTS) + this.familyMemberValue < minValueRequested)
            throw new IncorrectInputException();


        if(servantsRequestAnyway || this.familyMemberValue < minValueRequested) {
            Integer servantsUsed = ((IncreasePawnsValueByServantsContext) getContextByType(INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT)).
                    interactWithPlayer(minValueRequested - this.familyMemberValue);

            familyMember.setValue(servantsUsed + this.familyMemberValue);
            getCurrentPlayer().getResources().subResources(new Resources(0,0,0,servantsUsed));
        }

        return familyMember;
    }

    public ContextType getCurrentActionContext() {
        return this.currentActionContext;
    }

    public void changeFamilyMemberValue(Integer value, Boolean relative) {
        this.familyMemberValue = relative ? this.familyMemberValue + value : value;
    }
}
