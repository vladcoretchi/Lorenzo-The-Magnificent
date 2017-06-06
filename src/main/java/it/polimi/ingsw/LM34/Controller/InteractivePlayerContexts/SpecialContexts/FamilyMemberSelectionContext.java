package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Utils.Validator;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 04/06/2017.
 */

/*Called by DiceDependentContext classes*/
public class FamilyMemberSelectionContext extends AbstractGameContext {
    private Integer tempValue;

    @Override
    public void interactWithPlayer() {
        /*VOID*/
    }


    public FamilyMember familyMemberSelection(ArrayList<FamilyMember> pawnsCloned) {
        FamilyMember selectedPawn = null;
        ArrayList<FamilyMember> playerPawns = new ArrayList();
        Integer selected = this.gameManager.getActivePlayerNetworkController().familyMemberSelection(pawnsCloned);

        try {
            Validator.checkValidity(selected.toString(),playerPawns);
            selectedPawn = playerPawns.get(selected);
            tempValue = selectedPawn.getValue();

                //gameManager.getContextByType(INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT).interactWithPlayer(player);

        }
        /*If input mismatches expected informations... the player is able to try again*/
        catch(IncorrectInputException ide){
            familyMemberSelection(pawnsCloned);
        }
      //TODO: handle if player has not enough dice value...
        return selectedPawn;
    }


    public void increaseTempValue(Integer servantsConsumed) {
        tempValue += servantsConsumed;
    }

}
