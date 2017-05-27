package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.ArrayList;
import java.util.function.BiFunction;

/**
 * Utilities class
 * Contains generic methods that can be used everywhere
 *
 * @author vladc
 */
public final class Utilities {
    private Utilities() {}

    /**
     * Sums element by element 2 Integer arrays
     * @param array1 - first array of Integers
     * @param array2 - second array of Integers
     * @return array of Integers with a length equal to the minimum of 2 parameters
     */
    public static Integer[] sumElementByElement(Integer array1[], Integer array2[]) {
        if (array1 == null || array2 == null)
            throw new NullPointerException();

        Integer arrayLength = Math.min(array1.length, array2.length);
        Integer sum[] = new Integer[arrayLength];
        for (int i = 0; i < arrayLength; i++)
            sum[i] = array1[i] + array2[i];

        return sum;
    }

    public static BiFunction<Integer, Integer, Integer> sumInteger = (val1, val2) -> val1 + val2;


    /**
     This static method is called often by observers to register themselves to the right context
     */
    //Utilized by all games observer that passes the contexts of that particular game
    //and receives back the instance of the context it needs
    public static AbstractGameContext getContextByType(ArrayList<AbstractGameContext> contexts, ContextType contextType) {
        //TODO: refactor this loop
        for(AbstractGameContext context : contexts)
            if(context.getType() == ContextType.CURCH_REPORT_CONTEXT)
                    return context;

        return null;
    }

    public static Integer getTotalAmount(Resources resources) {

         Integer totalUnit = 0;
         totalUnit = resources.getResourceByType(ResourceType.WOODS)+
                     resources.getResourceByType(ResourceType.STONES)+
                     resources.getResourceByType(ResourceType.COINS)+
                     resources.getResourceByType(ResourceType.SERVANTS);

         return totalUnit;
    }


}