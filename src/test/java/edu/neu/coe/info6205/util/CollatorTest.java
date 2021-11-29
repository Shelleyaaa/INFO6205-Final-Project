package edu.neu.coe.info6205.util;

import com.ibm.icu.text.CollationKey;
import com.ibm.icu.text.Collator;

import java.util.Arrays;
import java.util.Locale;

public class CollatorTest {

    public static void main(String[] args) {
        Collator collator = Collator.getInstance(Locale.CHINA);

        CollationKey key1 = collator.getCollationKey("阿");
        CollationKey key2 = collator.getCollationKey("苏");
        CollationKey key3 = collator.getCollationKey("何");
        CollationKey key4 = collator.getCollationKey("中");
        CollationKey key5 = collator.getCollationKey("辰");

        createIntArray(key1);
        createIntArray(key2);
        createIntArray(key3);
        createIntArray(key4);
        createIntArray(key5);
    }

    private static void createIntArray(CollationKey key){
        byte[] bytes = key.toByteArray();
        System.out.print(key.getSourceString() + " ");
        int[] byteArray = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            int res = bytes[i] & 0xFF;
            byteArray[i] = res;
        }
        System.out.println(Arrays.toString(byteArray));
    }
}
