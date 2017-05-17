package it.polimi.ingsw.LM34.Utils;

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
}
