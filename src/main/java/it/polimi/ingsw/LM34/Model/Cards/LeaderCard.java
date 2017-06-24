package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.LeaderRequirements;

import java.io.Serializable;

/**
 * Created by Julius on 03/05/2017.
 */
    public class LeaderCard implements Serializable {
    private String leaderName;
    private LeaderRequirements requirements;
    private AbstractEffect bonus;
    private Boolean isActivatedByPlayer; //For CopyOtherLeader
    private Boolean oncePerRound;

    public LeaderCard(String leaderName, LeaderRequirements requirements, AbstractEffect bonus, Boolean oncePerRound) {
        this.leaderName= leaderName;
        this.requirements= requirements;
        this.bonus = bonus;
        this.oncePerRound = oncePerRound;
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
    
    public void setIsActivatedByPlayer() {
        this.isActivatedByPlayer = true;
    }

    public Boolean isActivatedByPlayer() {
        return this.isActivatedByPlayer;
    }

}
