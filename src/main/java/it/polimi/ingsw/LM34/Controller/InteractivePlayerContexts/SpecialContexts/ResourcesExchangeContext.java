package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Validator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;

/**
 * Created by GiulioComi on 23/05/2017.
 */

/**
 *Building permanent effects make the player access
 *this contexts so that he can chooses what option he want to activate
 */
public class ResourcesExchangeContext extends AbstractGameContext {
    private List<Pair<Resources, ResourcesBonus>> resourceExchange;


    public ResourcesExchangeContext() {
        contextType = RESOURCE_INCOME_CONTEXT;
    }




    public void setBonuses(Player player, List<Pair<Resources, ResourcesBonus>> resourceExchange)  {
        this.resourceExchange = resourceExchange;

        interactWithPlayer(player);
    }
    @Override
    public void  interactWithPlayer(Player player) {
        //TODO: let the player choose the options...
        System.out.println("Scegli una delle due alternative ");
        //TODO: method for passing to client the options

    }


    public void resourceExchangeSelection(Player player) {

        Integer selected = this.gameManager.getActivePlayerNetworkController().resourceExchangeSelection(resourceExchange);
        //TODO
        try {
            Validator.checkValidity(selected.toString(),resourceExchange);
            Pair<Resources, ResourcesBonus> selectedBonus = resourceExchange.get(selected);
            gameManager.getContextByType(RESOURCE_INCOME_CONTEXT).interactWithPlayer(player);
        }
        /*If input mismatch expected informations... the player is able to try again*/
        catch(IncorrectInputException ide){
            resourceExchangeSelection(player);
        }
    }
}
