package it.polimi.ingsw.LM34.Enums.Controller;

/**
 * List of the context a player can choose to interact directly with
 */
public enum PlayerSelectableContexts {
    MARKET_AREA_CONTEXT("Market"),
    PRODUCTION_AREA_CONTEXT("Production Area"),
    LEADER_CARDS_CONTEXT("Leader Activate or Discard"),
    COUNCIL_PALACE_CONTEXT("Council Privilege"),
    HARVEST_AREA_CONTEXT("Harvest Area"),
    TOWERS_CONTEXT("Towers");

    private String contextName;

    PlayerSelectableContexts(String contextName) {
        this.contextName = contextName;
    }

    @Override
    public String toString() {
        return contextName;
    }
}
