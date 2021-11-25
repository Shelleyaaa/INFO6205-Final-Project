package edu.neu.coe.info6205.sort.chinese;

import com.ibm.icu.text.Collator;

import java.util.Locale;

public class LSDChineseSort {

    private final int UNICODE_RANGE = 65281;
    private static Collator collator = Collator.getInstance(Locale.CHINA);

    /**
     * findMaxLength method returns maximum length of all available strings in an array
     *
     * @param strArr It contains an array of String from which maximum length needs to be found
     * @return int Returns maximum length value
     */
    private int findMaxLength(String[] strArr) {
        int maxLength = strArr[0].length();
        for (String str : strArr)
            maxLength = Math.max(maxLength, str.length());
        return maxLength;
    }

    private int charChinese(String str, int charPosition) {
        if (charPosition >= str.length()) {
            return 0;
        } else {
            byte[] bytes = collator.getCollationKey(String.valueOf(str.charAt(charPosition))).toByteArray();
            if (bytes.length < 7) {
                return (bytes[0] & 0xFF) * 255;
            } else {
                return (bytes[0] & 0xFF) * 255 + (bytes[1] & 0xFF);
            }
        }
    }

    /**
     * charSort method is implementation of LSD sort algorithm at particular character.
     *
     * @param strArr       It contains an array of String on which LSD char sort needs to be performed
     * @param charPosition This is the character position on which sort would be performed
     * @param from         This is the starting index from which sorting operation will begin
     * @param to           This is the ending index up until which sorting operation will be continued
     */
    private void charSort(String[] strArr, int charPosition, int from, int to) {
        int[] count = new int[UNICODE_RANGE + 2];
        String[] result = new String[strArr.length];

        for (int i = from; i <= to; i++) {
            int c = charChinese(strArr[i], charPosition);
            count[c + 2]++;
        }

        // transform counts to indices
        for (int r = 1; r < UNICODE_RANGE + 2; r++)
            count[r] += count[r - 1];

        // distribute
        for (int i = from; i <= to; i++) {
            int c = charChinese(strArr[i], charPosition);
            result[count[c + 1]++] = strArr[i];
        }

        // copy back
        if (to + 1 - from >= 0) System.arraycopy(result, 0, strArr, from, to + 1 - from);
    }

    /**
     * sort method is implementation of LSD String sort algorithm.
     *
     * @param strArr It contains an array of String on which LSD sort needs to be performed
     * @param from   This is the starting index from which sorting operation will begin
     * @param to     This is the ending index up until which sorting operation will be continued
     */
    public void sort(String[] strArr, int from, int to) {
        int maxLength = findMaxLength(strArr);
        for (int i = maxLength - 1; i >= 0; i--)
            charSort(strArr, i, from, to);
    }

    /**
     * sort method is implementation of LSD String sort algorithm.
     *
     * @param strArr It contains an array of String on which LSD sort needs to be performed
     */
    public void sort(String[] strArr) {
        sort(strArr, 0, strArr.length - 1);
    }

}
