package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Controller.MarketBanException;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughResourcesException;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Utils.Validator;
import java.util.List;
import java.util.logging.Level;
import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.SERVANTS;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class FamilyMemberSelectionContext extends AbstractGameContext {
    private Integer familyMemberValue;
    private ContextType currentActionContext;

    public FamilyMemberSelectionContext() {
        this.contextType = FAMILY_MEMBER_SELECTION_CONTEXT;
    }

    @Override
    public FamilyMember interactWithPlayer(Object... args) throws IncorrectInputException, MarketBanException, OccupiedSlotException, NotEnoughResourcesException {
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

        List<FamilyMember> familyMembers = this.gameManager.getCurrentPlayer().getFamilyMembers();
        Integer selectedFamilyMember = this.gameManager.getActivePlayerNetworkController().familyMemberSelection(familyMembers);
        Validator.checkValidity(selectedFamilyMember, familyMembers);

        FamilyMember familyMember = familyMembers.get(selectedFamilyMember);
        this.familyMemberValue = familyMember.getValue();

        setChanged();
        notifyObservers(this);

        if(this.gameManager.getCurrentPlayer().getResources().getResourceByType(SERVANTS) + this.familyMemberValue < minValueRequested)
            throw new IncorrectInputException();

        if(servantsRequestAnyway || this.familyMemberValue < minValueRequested)
            familyMember.setValue((Integer) getContextByType(INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT).
                    interactWithPlayer(minValueRequested - this.familyMemberValue) + this.familyMemberValue);

        return familyMember;
    }

    public ContextType getCurrentActionContext() {
        return this.currentActionContext;
    }

    public void changeFamilyMemberValue(Integer value, Boolean relative) {
        this.familyMemberValue = relative ? this.familyMemberValue + value : value;
    }
}
