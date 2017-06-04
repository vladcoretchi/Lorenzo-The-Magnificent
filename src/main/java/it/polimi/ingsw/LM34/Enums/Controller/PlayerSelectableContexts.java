package it.polimi.ingsw.LM34.Enums.Controller;

/**
 * Created by GiulioComi on 30/05/2017.
 */
public enum PlayerSelectableContexts {
    MARKET_AREA_CONTEXT("Market"),
    PRODUCTION_AREA_CONTEXT("Production Area"),
    LEADER_ACTIVATE_OR_DISCARD_CONTEXT("Leader Activate or Discard"),
    COUNCIL_PALACE_CONTEXT("Council Privilege"),
    HARVEST_AREA_CONTEXT("Harvest Area"),
    TOWERS_CONTEXT("Towers");

    /*The contextName is used in CLI and GUI for showing a beautified and human friendly name of the contexts*/
    private String contextName;

    PlayerSelectableContexts(String contextName) {
        this.contextName = contextName;
    }

    public String getContextName() {
        return contextName;
    }


}
