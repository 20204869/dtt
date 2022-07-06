package com.example.dtt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author reid
 * @date 2022/5/25 23:14
 * @describe
 */
public class DK2 {
    public static void main(String[] args) {
        // 测试用例, 没有返回值, 全部控制台打印输出, 自行封装返回格式
        calculate(new BigDecimal(87000.0), new BigDecimal("0.065"), 36);
    }

    /**
     * @param principal 本金
     * @param rateForYear 年利率
     * @param month 还款月数
     */
    public static void calculate(BigDecimal principal, BigDecimal rateForYear, Integer month) {
        DecimalFormat format = new DecimalFormat("#0.00");

        // 计算月利率
        BigDecimal rate = rateForYear.divide(BigDecimal.valueOf(12), 6, RoundingMode.HALF_UP);

        // （1 + 月利率）
        BigDecimal oneAddRate = rate.add(BigDecimal.ONE);

        // 月利率 ×（1 + 月利率）^ 还款月数
        BigDecimal factor0 = oneAddRate.pow(month).multiply(rate);

        // (1 + 月利率) ^ 还款月数
        BigDecimal factor1 = oneAddRate.pow(month);

        // (1 + 月利率) ^ 还款月数 - 1
        BigDecimal factor2 = factor1.subtract(BigDecimal.ONE);


        for(int i = 1; i <= month; i++) {

            // ( 1 + 月利率) ^ (还款月序号 - 1)
            BigDecimal factor3 = oneAddRate.pow(i - 1);

            // 计算每月还款利息
            BigDecimal monthlyInterest = principal.multiply(rate).multiply(factor1.subtract(factor3)).divide(factor2, 2, RoundingMode.HALF_UP);
            System.out.print("第" + i + "月利息: " + monthlyInterest + ",\t\t");

            // 计算每月还款本金
            BigDecimal monthlyPrinciple = principal.multiply(rate).multiply(factor3).divide(factor2, 2, RoundingMode.HALF_UP);
            System.out.print("第" + i + "月本金: " + monthlyPrinciple + "\t\t");

            System.out.println("合计: " + monthlyInterest + " + " + monthlyPrinciple + "=" + (monthlyInterest.add(monthlyPrinciple)));

        }

        // 计算每月还款额, 可以用 monthlyInterest + monthlyPrinciple 获得
        // BigDecimal monthly = monthlyInterest + monthlyPrinciple

        // 每月还款额, 如果业务不需要计算 monthlyInterest 和 monthlyPrinciple, 也可以通过这个公式直接计算
        BigDecimal monthly = principal.multiply(factor0).divide(factor2, 2, RoundingMode.HALF_UP);

        // 本息总和 TODO 经测试, 每月还款金额和网上的计算器结果一样, 但是本息总和有误差 (少了0.5)
        BigDecimal all = monthly.multiply(BigDecimal.valueOf(month));

        // 总利息
        BigDecimal sumInterest = all.subtract(principal);
        System.out.println("总利息: " + format.format(sumInterest));

        System.out.println("总还款额: " + all);

    }
}
