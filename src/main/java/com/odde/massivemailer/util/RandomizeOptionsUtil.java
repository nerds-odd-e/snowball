package com.odde.massivemailer.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomizeOptionsUtil {
    public static List<String> randomize(List<String> options) {

        List<String> newOptions=new ArrayList<>();

        if(options==null || options.isEmpty())
            return Arrays.asList();

        boolean noneOfTheAboveExist=false;
        for(String option : options){
            if(option.equals("None of the above"))
            {
                noneOfTheAboveExist=true;
                continue;
            }
            newOptions.add(option);
        }

        Collections.shuffle(newOptions);
        if(noneOfTheAboveExist)
            newOptions.add("None of the above");
        return newOptions;
    }
}
