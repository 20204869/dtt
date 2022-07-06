package com.example.dtt;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author reid
 * @date 2022/5/26 17:20
 * @describe
 *
 * BigDecimal �������ڻ����������ݼ��㣬��ֹ���ݾ��ȶ�ʧ
 *
 * BigDecimal �������ݱȽ�
 * BigDecimal a = new BigDecimal(���1);
 * BigDecimal b = new BigDecimal(���2);
 *
 * //ǰ��Ϊa��b������Ϊnull�������
  if(a.compareTo(b) == -1){
      System.out.println("aС��b");
  }
  if(a.compareTo(b) == 0){
      System.out.println("a����b");
  }
  if(a.compareTo(b) == 1){
      System.out.println("a����b");
  }
  if(a.compareTo(b) > -1){
      System.out.println("a���ڵ���b");
  }
  if(a.compareTo(b) < 1){
      System.out.println("aС�ڵ���b");
  }
 */
public class GSDK2 {
    public static void main(String[] args) {

       // List<Integer> days = Arrays.asList(new Integer[]{29, 31, 30, 31, 30, 31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 29, 31, 30, 31, 30});

        //ÿ�ھ�����һ����������˳��Ҫ��
        List<Integer> days = Arrays.asList(new Integer[]{31,30,31,31,30,31,30,31,31,28,31,30,31,30,31,31,30,31,30,31,31,29,31,30,31,30,31,31,30,31,30,31,31,28,31,30});

        //ÿ������
        double lv = 0.056;
        //�����Ԫ��
        double Loan = 26500.0;
        double yearRate = rateCalculator(Loan,days,lv);
        System.out.println("ÿ��Ӧ����" + yearRate);
    }

    /**
     * �ȶϢ����֪������.
     *
     * @param totalLoan �����ܶ�(��λ��Ԫ)
     * @return ÿ�»����(Ԫ)
     */
    public static double rateCalculator(double totalLoan,List<Integer> days,double lv) {

        double[] monthPayLoan = new double[days.size()]; //ÿ�ڻ��ı���

        /*���㷨���ö��ַ���ٵ�һ�µĻ����������ÿ�»���𣬼�������ı���Ϳ��Եõ�������
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
