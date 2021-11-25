package edu.neu.coe.info6205.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileLoad {

    /**
     * load names from resource
     * @return
     */
    public static List<String> loadNames() {
        List<String> names = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/resources/shuffledChinese.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String name = null;
        while (true) {
            try {
                if ((name = Objects.requireNonNull(bufferedReader).readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            names.add(name);
        }
        return names;
    }

}
