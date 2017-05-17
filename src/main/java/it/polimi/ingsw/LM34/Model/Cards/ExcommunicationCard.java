package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class ExcommunicationCard {
    private Integer period;
    private AbstractEffect penalty;

    public ExcommunicationCard(Integer period, AbstractEffect penalty) {
        this.period = period;
        this.penalty = penalty;
    }

    public Integer getPeriod() { return this.period; }

    public AbstractEffect getPenalty() {
        return this.penalty;
    }
}
