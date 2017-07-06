package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import org.junit.Test;

import static org.junit.Assert.*;

public class UseCouncilPrivilegeContextTest {
    /**
     * this test will check if interactWithPlayer will be properly run
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {
        UseCouncilPrivilegeContext useCouncilPrivilegeContext = new UseCouncilPrivilegeContext();
        useCouncilPrivilegeContext.interactWithPlayer(new Integer(0));
    }

}