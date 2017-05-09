package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.PermanentBonus;

/**
 * Created by Julius on 03/05/2017.
 */
    public class LeaderCard {
    private String leaderName;
    private PermanentBonus requirements;
    private PermanentBonus benefits;

    public LeaderCard(String leaderName, PermanentBonus requirements, PermanentBonus benefits) {
        this.leaderName= leaderName;
        this.requirements= requirements;
        this.benefits= benefits;
    }


    public PermanentBonus getRequirements() {
        return this.requirements;
    }


    public String getName() {
        return this.leaderName;
    }

    //called once a turn in case of a Activable leader benefit
    public PermanentBonus getActivableBenefitBonus() {
        return this.benefits;
    }
}
