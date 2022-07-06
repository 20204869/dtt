package com.example.dtt;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author reid
 * @date 2022/5/26 17:20
 * @describe
 *
 * BigDecimal 类型用于货币类型数据计算，防止数据精度丢失
 *
 * BigDecimal 类型数据比较
 * BigDecimal a = new BigDecimal(金额1);
 * BigDecimal b = new BigDecimal(金额2);
 *
 * //前提为a、b均不能为null的情况下
  if(a.compareTo(b) == -1){
      System.out.println("a小于b");
  }
  if(a.compareTo(b) == 0){
      System.out.println("a等于b");
  }
  if(a.compareTo(b) == 1){
      System.out.println("a大于b");
  }
  if(a.compareTo(b) > -1){
      System.out.println("a大于等于b");
  }
  if(a.compareTo(b) < 1){
      System.out.println("a小于等于b");
  }
 */
public class GSDK2 {
    public static void main(String[] args) {

       // List<Integer> days = Arrays.asList(new Integer[]{29, 31, 30, 31, 30, 31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 29, 31, 30, 31, 30});

        //每期距离上一期天数，无顺序要求
        List<Integer> days = Arrays.asList(new Integer[]{31,30,31,31,30,31,30,31,31,28,31,30,31,30,31,31,30,31,30,31,31,29,31,30,31,30,31,31,30,31,30,31,31,28,31,30});

        //每单利率
        double lv = 0.056;
        //贷款金额（元）
        double Loan = 26500.0;
        double yearRate = rateCalculator(Loan,days,lv);
        System.out.println("每月应还金额：" + yearRate);
    }

    /**
     * 等额本息：已知月利率.
     *
     * @param totalLoan 贷款总额(单位：元)
     * @return 每月还款额(元)
     */
    public static double rateCalculator(double totalLoan,List<Integer> days,double lv) {

        double[] monthPayLoan = new double[days.size()]; //每期还的本金

        /*本算法采用二分法穷举第一月的还款本金来计算每月还款本金，计算出还的本金就可以得到月利率
         */
        double low = 0;
        double up = totalLoan;
        boolean isFinish = false;
        double yhje = 0;
        do {
            yhje = (low+up)/2;
            double havePayLoan = 0;
            double calculateLoan = 0;
            for(int i=0; i<days.size(); i++){
                monthPayLoan[i] = yhje - (totalLoan-havePayLoan)*lv*days.get(i)/360;
                havePayLoan += monthPayLoan[i];
                calculateLoan += monthPayLoan[i];
            }
            BigDecimal calculate = new BigDecimal(calculateLoan);
            BigDecimal total = new BigDecimal(totalLoan);
           // long calculate = Math.round(calculateLoan);
           // long total = Math.round(totalLoan);
            if(calculate.compareTo(total) == 0) {
                isFinish = true;
            } else if ( calculate.compareTo(total)  == 1 ) {
                up = yhje;
            } else {
                low = yhje;
            }

        } while(!isFinish);
        return yhje;
    }
}
