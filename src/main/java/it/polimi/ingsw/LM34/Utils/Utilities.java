package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities class
 * Contains generic methods that can be used everywhere
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

    /**
     * Sums 2 Integer values
     */
    public static BiFunction<Integer, Integer, Integer> sumInteger = (val1, val2) -> val1 + val2;
    public static BiFunction<Integer, Integer, Integer> subInteger = (val1, val2) -> val1 - val2;

    /**
     * Split a string in substring with a maximum length
     * https://stackoverflow.com/questions/25853393/split-a-string-in-java-into-equal-length-substrings-while-maintaining-word-bound
     * @param data string to split
     * @param maxLength maximum length of the substrings
     * @return
     */
    public static String[] splitStringByLength(String data, Integer maxLength) {
        List<String> result = new ArrayList<>();

        Pattern p = Pattern.compile("\\G(\\s*|_*)(.{1," + maxLength + "})(?=\\s|_|$)", Pattern.DOTALL);
        Matcher m = p.matcher(data);
        while (m.find())
            result.add(StringUtils.strip(m.group(2).trim(), "_"));

        return result.toArray(new String[0]);
    }

    /*Useful method helpful in counting end game total amount of resources*/
    public static Integer getTotalAmount(Resources resources) {
        Integer totalUnit = resources.getResourceByType(ResourceType.WOODS) +
                resources.getResourceByType(ResourceType.STONES) +
                resources.getResourceByType(ResourceType.COINS) +
                resources.getResourceByType(ResourceType.SERVANTS);
        return totalUnit;
    }
}