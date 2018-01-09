package com.mvp.lt.flyset.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * $name
 *
 * @author ${LiuTao}
 * @date 2017/12/22/022
 */

public class SortUtils {

    public static List<String> sortByStatus() {
        List<String> list = new ArrayList<String>();
        list.add("2017-12-24 14.52.33");
        list.add("2017-12-22 14.52.33");
        list.add("2017-12-25 14.52.33");
        list.add("2017-12-26 14.52.33");
        list.add("2017-12-26 14.52.33");
        list.add("2017-12-23 14.52.33");
        list.add("2017-12-28 14.52.33");
        list.add("2017-12-29 14.52.33");
        list.add("2017-12-21 11.52.33");

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s2.compareTo(s1);
            }
        });
        for (String string : list) {
            System.out.println(string);
        }
        return list;
    }
}
