package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.io.Serializable;

public class LeaderCard implements Serializable {
    private static final long serialVersionUID = -4128584530536090234L;

    private String leaderName;
    private LeaderRequirements requirements;
    private AbstractEffect bonus;
    private Boolean activated;
    private Boolean oncePerRound;
    private Boolean used;

    /**
     * {@link it.polimi.ingsw.LM34.Model.Effects.CopyOtherLeader}
     */
    private Boolean isActivatedByPlayer;

    public LeaderCard(String leaderName, LeaderRequirements requirements, AbstractEffect bonus, Boolean oncePerRound) {
        this.leaderName= leaderName;
        this.requirements= requirements;
        this.bonus = bonus;
        this.oncePerRound = oncePerRound;
        this.activated = false;
        this.used = false;
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

    public void activate() {
        this.activated = true;
    }

    public Boolean isActivatedByPlayer() {
        return this.activated;
    }

    public Boolean isUsed() {
        return used;
    }

    public void setUsed(Boolean usage) {
        this.used = usage;
    }

    public Boolean isOncePerRound() {
        return oncePerRound;
    }
}
