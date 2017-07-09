package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import org.junit.Test;

public class FamilyMemberSelectionContextTest {

    /**
     * this test will check if context will properly selected
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {

        FamilyMemberSelectionContext familyMemberSelectionContext = new FamilyMemberSelectionContext();
        familyMemberSelectionContext.interactWithPlayer(new Integer(1), new Boolean(true), ContextType.ACTION_SLOT_CONTEXT);
    }

    /**
     * this test will check if family member value will effectively changed
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void changeFamilyMemberValue() throws Exception {
        FamilyMemberSelectionContext familyMemberSelectionContext = new FamilyMemberSelectionContext();
        familyMemberSelectionContext.changeFamilyMemberValue(1, true, null);

    }

}