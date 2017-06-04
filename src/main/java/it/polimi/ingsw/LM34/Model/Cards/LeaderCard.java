package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.LeaderNames;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.LeaderRequirements;

/**
 * Created by Julius on 03/05/2017.
 */
    public class LeaderCard {
    private LeaderNames leaderName;
    private LeaderRequirements requirements;
    private AbstractEffect bonus;
    private Boolean isActivatedByPlayer; //For CopyOtherLeader

    public LeaderCard(LeaderNames leaderName, LeaderRequirements requirements, AbstractEffect bonus) {
        this.leaderName= leaderName;
        this.requirements= requirements;
        this.bonus = bonus;
    }

    public LeaderNames getName() {
        return this.leaderName;
    }

    public LeaderRequirements getRequirements() {
        return this.requirements;
    }

    public AbstractEffect getBonus() {
        return this.bonus;
    }



    public void setIsActivatedByPlayer() {
        this.isActivatedByPlayer = true;
    }

    public Boolean isActivatedByPlayer() {
        return this.isActivatedByPlayer;
    }

}
