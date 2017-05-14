package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by vladc on 5/13/2017.
 */
public class ResourcesPerItemBonus implements EffectInterface {
    private Resources bonusResources;
    private DevelopmentCardColor cardColor;
    private Resources requiredResources;

    public ResourcesPerItemBonus(Resources bonusResources, DevelopmentCardColor cardColor, Resources requiredResources) {
        this.bonusResources = bonusResources;
        this.cardColor = cardColor;
        this.requiredResources = requiredResources;
    }

    public Resources getBonusResources() {
        return this.bonusResources;
    }

    public DevelopmentCardColor getCardColor() {
        return this.cardColor;
    }

    public Resources getRequiredResources() {
        return this.requiredResources;
    }
}
