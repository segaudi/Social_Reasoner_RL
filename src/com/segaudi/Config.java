package com.segaudi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import com.segaudi.util.*;

/**
 * Created by xuchenyou on 12/29/16.
 */
public class Config {
    public static String cache_path = "./cache/";
    public static String data_path = "./data_rearranged/";
    public static List<String> verbalNames = new ArrayList<String>(Arrays.asList("BC", "SE", "PR", "RE", "VSN", "SD" +
            ""));
    public static List<String> nonverbalNames = new ArrayList<>();
    public String myName;
    public double lr = 0.05; // Learning rate

    Config(){
        myName = new String("Simon");
    }
}
