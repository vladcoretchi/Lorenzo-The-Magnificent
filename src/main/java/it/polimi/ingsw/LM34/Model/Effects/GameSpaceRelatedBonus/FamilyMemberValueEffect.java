package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;

/**
 * This class aggregates instant and permanent effects from different development card types, leader cards and
 * excomunication tiles, all of which are related to the context where a player places a family member in a game space so that
 * the dice values associated to his pawns has to be incremented or decreased by the effects of the cards mentioned above
 */

public class FamilyMemberValueEffect extends AbstractEffect implements Observer {

    /**
     * if null then the value is related to the neutral family member
     * if MULTICOLOR, the value is applied to all the dices
     */
    private List<DiceColor> diceColor; //keep track on what dice the effect is applied to
    private ContextType contextType;

    private Integer value;

    /**
     * indicates if the value is:
     *      absolute (substitutes the 'old' value)
     *      relative (adds/subs to 'old' value)
     */
    private Boolean relative;

    public FamilyMemberValueEffect(List<DiceColor> colors, Integer value, Boolean relative) {
        this.diceColor = colors;
        this.value = value;
        this.relative = relative;
    }

    public List<DiceColor> getDiceColor() {
        return this.diceColor;
    }

    public Integer getValue() {
        return this.value;
    }

    public Boolean isRelative() {
        return this.relative;
    }

    @Override
    public void update(Observable o, Object arg) {
        List<FamilyMember> familyMembers = (ArrayList<FamilyMember>) arg;
        //increase the values of the family members in this context
        familyMembers.forEach(member -> {
            for(DiceColor c : diceColor)
                if (member.getDiceColorAssociated() == c)
                    member.setValue(this.value + (this.relative ? member.getValue() : 0));
        });
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(MARKET_AREA_CONTEXT).addObserver(this);
        callerContext.getContextByType(TOWERS_CONTEXT).addObserver(this);
        callerContext.getContextByType(PRODUCTION_AREA_CONTEXT).addObserver(this);
        callerContext.getContextByType(HARVEST_AREA_CONTEXT).addObserver(this);
        callerContext.getContextByType(COUNCIL_PALACE_CONTEXT).addObserver(this);
    }
}
