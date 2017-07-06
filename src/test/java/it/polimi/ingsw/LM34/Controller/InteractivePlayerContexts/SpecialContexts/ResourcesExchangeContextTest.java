package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ResourcesExchangeContextTest {

    /**
     * this test will check if pair will correctly used inside interactWithPlayer
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {
        ResourcesExchangeContext resourcesExchangeContext = new ResourcesExchangeContext();
        Pair<Integer, Integer> argumentsPair = new Pair<Integer, Integer>() {
            @Override
            public Integer getLeft() {
                return null;
            }

            @Override
            public Integer getRight() {
                return null;
            }

            @Override
            public Integer setValue(Integer value) {
                return null;
            }
        };
        argumentsPair.setValue(1);
        List<Pair<Integer, Integer>> arguments = new ArrayList<>();
        arguments.add(argumentsPair);
        resourcesExchangeContext.interactWithPlayer(arguments);
    }

}