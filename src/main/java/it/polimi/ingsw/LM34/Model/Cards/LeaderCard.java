package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import java.io.Serializable;

public class LeaderCard implements Serializable {
    private String leaderName;
    private LeaderRequirements requirements;
    private AbstractEffect bonus;
    private Boolean activated;
    private Boolean oncePerRound;

    public LeaderCard(String leaderName, LeaderRequirements requirements, AbstractEffect bonus, Boolean oncePerRound) {
        this.leaderName= leaderName;
        this.requirements= requirements;
        this.bonus = bonus;
        this.oncePerRound = oncePerRound;
        this.activated = false;
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

    public Boolean isOncePerRound() { return this.oncePerRound; }
    
    public void activate() {
        this.activated = true;
    }

    public Boolean isActivatedByPlayer() {
        return this.activated;
    }
}
