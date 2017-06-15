package it.polimi.ingsw.LM34.Enums.Model;

/**
 * Created by vladc on 5/13/2017.
 */
public enum WorkingAreaType {
    PRODUCTION("PRODUCTION"),
    HARVEST("HARVEST");


    private String areaType;

    WorkingAreaType(String type) {
        this.areaType = type;
    }

    public String getWorkingAreaType() {
        return areaType;
    }
}
