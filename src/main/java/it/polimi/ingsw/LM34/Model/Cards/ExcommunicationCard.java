package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.io.Serializable;

public class ExcommunicationCard implements Serializable {
    private static final long serialVersionUID = 1085193678618724890L;

    private Integer number;
    private Integer period;
    private AbstractEffect penalty;

    public ExcommunicationCard(Integer number, Integer period, AbstractEffect penalty) {
        this.number = number;
        this.period = period;
        this.penalty = penalty;
    }

    public Integer getPeriod() { return this.period; }

    public AbstractEffect getPenalty() {
        return this.penalty;
    }

    public Integer getNumber() {
        return number;
    }
}
