package it.polimi.ingsw.LM34.Network;

import java.io.Serializable;

public class SlotAction implements Serializable {
    private static final long serialVersionUID = 7749147067187330534L;

    private Integer slotId;

    public SlotAction(char slotId) {
        this.slotId = Character.getNumericValue(slotId);
    }

    public Integer getSlotId() {
        return this.slotId;
    }
}
