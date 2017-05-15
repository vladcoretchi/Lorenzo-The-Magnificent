package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.LeaderRequirements;

/**
 * Created by Julius on 03/05/2017.
 */
    public class LeaderCard {
    private String leaderName;
    private LeaderRequirements requirements;
    private LeaderBonus bonus;
    private boolean oncePerTurn;
    //private PermanentBonus benefits;

    public LeaderCard(String leaderName, LeaderRequirements requirements, LeaderBonus bonus) {
        this.leaderName= leaderName;
        this.requirements= requirements;
        this.bonus = bonus;
    }


    public LeaderRequirements getRequirements() {
        return this.requirements;
    }

    public String getName() {
        return this.leaderName;
    }
}
