package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.FamilyMemberSelectionContext;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Configurator;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;

/**
 * Created by GiulioComi on 24/05/2017.
 */
public class CouncilPalaceContext extends AbstractGameContext implements DiceDependentContextsInterface {
    private CouncilPalace councilPalace;
    private ResourcesBonus reward; //The council privilege
    private Integer councilPrivileges;
    private Integer tempValue;

    public CouncilPalaceContext() {
        contextType = COUNCIL_PALACE_CONTEXT;
        councilPalace = Configurator.getPalace();
    }


    @Override
    public void interactWithPlayer(Player player) {
        tempValue = 0;
            //TODO: interact: let the player choice the family member to use

            FamilyMember selectedPawn = ((FamilyMemberSelectionContext) getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT))
                    .familyMemberSelection(player);


            if(selectedPawn.getValue() >= councilPalace.getDiceValue()) {
                councilPalace.insertFamilyMember(selectedPawn);
                reward = councilPalace.getReward();
                player.addCouncilPrivileges(reward.getCouncilPrivilege());
                //Since the player got a council privilege, we call the context in which he can spend it for resources
                gameManager.getContextByType(USE_COUNCIL_PRIVILEGE_CONTEXT).interactWithPlayer(player);
        }
        else //TODO: REFACTOR THIS
            interactWithPlayer(player); //The player reselect the pawn if the choosed one has not enough dice value.

    }

    @Override
    public void increaseTempValue(Integer servantsConsumed) {
        tempValue += servantsConsumed;
    }


    @Override
    public void sweep() {
        councilPalace.sweepPalace();
    }



    public CouncilPalace getCouncilPalace() {
        return councilPalace;
    }
}