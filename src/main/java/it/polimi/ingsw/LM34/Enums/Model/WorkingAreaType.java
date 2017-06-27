package it.polimi.ingsw.LM34.Enums.Model;

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
