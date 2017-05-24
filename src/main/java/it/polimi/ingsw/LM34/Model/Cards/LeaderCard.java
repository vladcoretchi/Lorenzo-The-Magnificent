package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.LeaderRequirements;

/**
 * Created by Julius on 03/05/2017.
 */
    public class LeaderCard {
    private String leaderName;
    private LeaderRequirements requirements;
    private AbstractEffect bonus;

    public LeaderCard(String leaderName, LeaderRequirements requirements, AbstractEffect bonus) {
        this.leaderName= leaderName;
        this.requirements= requirements;
        this.bonus = bonus;
    }

    public String getName() {
        return this.leaderName;
    }

    public LeaderRequirements getRequirements() {
        return this.requirements;
    }

    public AbstractEffect getBonus() {
        return this.bonus;
    }

}
