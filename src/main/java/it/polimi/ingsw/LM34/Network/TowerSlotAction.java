package it.polimi.ingsw.LM34.Network;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;

import java.io.Serializable;

public class TowerSlotAction implements Serializable {
    private static final long serialVersionUID = 5484098198924201568L;

    private DevelopmentCardColor color;
    private Integer level;

    public TowerSlotAction(DevelopmentCardColor type, Integer level) {
        this.level = level;
        this.color = type;
    }

    public Integer getLevel() {
        return this.level;
    }

    public DevelopmentCardColor getColor() {
        return this.color;
    }
}
