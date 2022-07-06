package com.example.dtt;

public class RateCalculator{
    public static void main(String[] args) {
        /*if (args.length < 3) {
            System.out.println("至少输入3个参数(贷款金额 还款期数 每月还款)!");
            return;
        }*/
        double Loan = 87000.0;//Double.parseDouble(args[0]);  //贷款金额（元）
        int months = 36;//Integer.parseInt(args[1]);     //还款期数（月）
        double monthPay = 2669.483891;//Double.parseDouble(args[2]); //每月还款（元）
        double yearRate = rateCalculator(Loan,months,monthPay); //年化利率（%）

        System.out.println("贷款金额：" + Loan);
        System.out.println("还款期数：" + months);
        System.out.println("每月还款：" + monthPay);
        System.out.println("年化利率：" + String.format("%.2f", yearRate) + "%");
    }

    /**
     * 等额本息：已知每月还款额，计算年利率.
     *
     * @param totalLoan 贷款总额(单位：元)
     * @param months 贷款期数(月数)
     * @param monthPay 每月还款额(元)
     * @return 年利率(%)
     */
    public static double rateCalculator(double totalLoan, int months, double monthPay) {

        double monthRate; //月利率
        double[] monthPayLoan = new double[months]; //每期还的本金

        /*本算法采用二分法穷举第一月的还款本金来计算每月还款本金，计算出还的本金就可以得到月利率
         */
        double low = 0;
        double up = totalLoan/months;
        boolean isFinish = false;
        int j=0;
        do {
            monthPayLoan[0] = (low+up)/2;
            monthRate = (monthPay - monthPayLoan[0]) / totalLoan;
            double havePayLoan = 0;
            double calculateLoan = monthPayLoan[0];
            for(int i=1; i<months; i++){
                havePayLoan += monthPayLoan[i-1];
                monthPayLoan[i] = monthPay-(totalLoan-havePayLoan)*monthRate;
                calculateLoan += monthPayLoan[i];
            }
            long calculate = Math.round(calculateLoan);
            long total = Math.round(totalLoan);
            if(calculate == total) {
                isFinish = true;
            } else if (calculate > total) {
                up = monthPayLoan[0];
            } else {
                low = monthPayLoan[0];
            }
            if(++j>30){
                isFinish = true;
                monthRate = -1;
            }
        } while(!isFinish);
        return monthRate*12*100;
    }
}
