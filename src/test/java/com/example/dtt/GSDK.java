package com.example.dtt;

import java.util.Arrays;
import java.util.List;

/**
 * @author reid
 * @date 2022/5/26 15:59
 * @describe
 */
public class GSDK {
    public static void main(String[] args) {
        List<Integer> days = Arrays.asList(new Integer[]{29, 31, 30, 31, 30, 31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 29, 31, 30, 31, 30});
        System.out.println(getY(87000.0,days));
    }

    private static double getY(double dkje, List<Integer> days) {
        double lv = 0.065/10;
        double ts_tmp = 0.0;
        for (int k = 0; k < days.size(); k++) {
            ts_tmp = ts_tmp + days.get(k);
        }
        System.out.println(ts_tmp);

        return (dkje*2 + ts_tmp + 0.065/10)/36;
    }
}
