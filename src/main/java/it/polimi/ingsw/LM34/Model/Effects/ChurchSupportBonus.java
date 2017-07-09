package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.UseCouncilPrivilegeContext;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.CHURCH_REPORT_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

/**
 * This class represents Sisto IV peculiar effect and registers itself to ChurchReportContext
 */
public class ChurchSupportBonus extends AbstractEffect implements Observer {
    private static final long serialVersionUID = 5712732830307229555L;
    
    private ResourcesBonus resources;

    public ChurchSupportBonus(ResourcesBonus resources) {
        this.resources = resources;
    }

    public ResourcesBonus getResources() {
        return this.resources;
    }

    @Override
    public void update(Observable o, Object arg) {
        AbstractGameContext callerContext = (AbstractGameContext) arg;
        callerContext.getCurrentPlayer().getResources().sumResources(this.resources.getResources());
        if(this.resources.getCouncilPrivilege() > 0)
            try {
                ((UseCouncilPrivilegeContext) callerContext.getContextByType(USE_COUNCIL_PRIVILEGE_CONTEXT)).interactWithPlayer(this.resources.getCouncilPrivilege());
            } catch(IncorrectInputException ex) {
                LOGGER.log(Level.FINEST, ex.getMessage(), ex);
            }
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(CHURCH_REPORT_CONTEXT).addObserver(this);
    }
}
