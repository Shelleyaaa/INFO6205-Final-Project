package edu.neu.coe.info6205.util;

import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;

public class OriginalCollatorTest {

    private static Collator collator = Collator.getInstance(Locale.CHINA);

    public static void main(String[] args) {
        // "啊","这", "都", "能", "赢", "梵" => "a1", "zhe4", "dou1", "neng2", "ying2","fan2"
        String[] words = new String[]{"啊", "这", "都", "能", "赢", "梵"};
        Arrays.sort(words, collator);

        for (String word : words) {
            System.out.print(word + " ");
        }

        System.out.println();
    }
}
