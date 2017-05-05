package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by Julius on 03/05/2017.
 */
    public class LeaderCard {
    private String leaderName;
    private Bonus requirements;
    private Bonus benefits;

    public LeaderCard(String leaderName, Bonus requirements, Bonus benefits) {
        this.leaderName= leaderName;
        this.requirements= requirements;
        this.benefits= benefits;
    }


    public Bonus getRequirements() {
        return this.requirements;
    }


    public String getName() {
        return this.leaderName;
    }

    //called once a turn in case of a Activable leader benefit
    public Bonus getActivableBenefitBonus() {
        return this.benefits;
    }
}
