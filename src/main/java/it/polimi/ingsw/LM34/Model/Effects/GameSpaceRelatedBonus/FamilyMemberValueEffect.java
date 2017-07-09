package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.FamilyMemberSelectionContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.FAMILY_MEMBER_SELECTION_CONTEXT;

/**
 * This class aggregates instant and permanent effects from different development card types, leader cards and
 * excomunication tiles, all of which are related to the context where a player places a family member in a game space so that
 * the dice values associated to his pawns has to be incremented or decreased by the effects of the cards mentioned above
 */
public class FamilyMemberValueEffect extends AbstractEffect implements Observer {
    private static final long serialVersionUID = -2410726346554385940L;
    
    /**
     * Keeps track of what dice the effect is applied to
     */
    private List<DiceColor> diceColors;
    private ContextType contextType;

    private Integer value;

    /**
     * indicates if the value is:
     *      absolute (substitutes the 'old' value)
     *      relative (adds/subs to 'old' value)
     */
    private Boolean relative;

    public FamilyMemberValueEffect(List<DiceColor> colors, Integer value, Boolean relative) {
        this.diceColors = colors;
        this.value = value;
        this.relative = relative;
    }

    public List<DiceColor> getDiceColor() {
        return this.diceColors;
    }

    public Integer getValue() {
        return this.value;
    }

    public Boolean isRelative() {
        return this.relative;
    }

    @Override
    public void update(Observable o, Object arg) {
        FamilyMemberSelectionContext callerContext = (FamilyMemberSelectionContext) arg;

        if((this.diceColors.size() == 1 && this.diceColors.get(0).name().equals(DiceColor.DEFAULT.name())) ||
                this.diceColors.stream().anyMatch(dc -> dc.name().equals(callerContext.getAssociatedDiceColor().name())))
            callerContext.changeFamilyMemberValue(this.value, this.relative, FAMILY_MEMBER_SELECTION_CONTEXT);
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT).addObserver(this);
    }
}
