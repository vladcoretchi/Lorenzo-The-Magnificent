package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;
import java.util.List;

public class CharacterCard extends AbstractDevelopmentCard {

    public CharacterCard(String name, Integer period, Integer coinsRequired, List<AbstractEffect> instantBonus, AbstractEffect permanentBonus)  {
        this.color = DevelopmentCardColor.BLUE;
        this.name = name;
        this.period = period;
        this.instantBonus = instantBonus;
        this.permanentBonus = permanentBonus;
        this.resourceRequired = new Resources(coinsRequired, 0, 0, 0);
    }

    @Override
    public String toString() {
        return "characterCard";
    }
}
