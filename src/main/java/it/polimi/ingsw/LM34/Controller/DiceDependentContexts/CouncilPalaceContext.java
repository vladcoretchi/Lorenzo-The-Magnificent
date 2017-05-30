package it.polimi.ingsw.LM34.Controller.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;

/**
 * Created by GiulioComi on 24/05/2017.
 */
public class CouncilPalaceContext extends AbstractGameContext implements DiceDependentContextsInterface {
    private CouncilPalace councilPalace;
    private ResourcesBonus reward; //The council privilege
    private Integer councilPrivileges;
    private Integer tempValue;

    public CouncilPalaceContext() {
       councilPalace = Configurator.getPalace();
    }

    @Override
    public ContextType getType() {
        return ContextType.COUNCIL_PALACE_CONTEXT;
    }


    @Override
    public void interactWithPlayer(Player player) {
        //TODO: interact: let the player choice the family member to use
        //if(fm.getValue() >= councilPalace.getDiceValue();
        //councilPalace.insertFamilyMember(fm);
        reward = councilPalace.getReward();
        player.addCouncilPrivileges(reward.getCouncilPrivilege());
        //Since the player got a council privilege, we call the context in which he can spend it for resources
        gameManager.getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT).interactWithPlayer(player);

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
