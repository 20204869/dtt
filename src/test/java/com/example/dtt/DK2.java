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
        // ��������, û�з���ֵ, ȫ������̨��ӡ���, ���з�װ���ظ�ʽ
        calculate(new BigDecimal(87000.0), new BigDecimal("0.065"), 36);
    }

    /**
     * @param principal ����
     * @param rateForYear ������
     * @param month ��������
     */
    public static void calculate(BigDecimal principal, BigDecimal rateForYear, Integer month) {
        DecimalFormat format = new DecimalFormat("#0.00");

        // ����������
        BigDecimal rate = rateForYear.divide(BigDecimal.valueOf(12), 6, RoundingMode.HALF_UP);

        // ��1 + �����ʣ�
        BigDecimal oneAddRate = rate.add(BigDecimal.ONE);

        // ������ ����1 + �����ʣ�^ ��������
        BigDecimal factor0 = oneAddRate.pow(month).multiply(rate);

        // (1 + ������) ^ ��������
        BigDecimal factor1 = oneAddRate.pow(month);

        // (1 + ������) ^ �������� - 1
        BigDecimal factor2 = factor1.subtract(BigDecimal.ONE);


        for(int i = 1; i <= month; i++) {

            // ( 1 + ������) ^ (��������� - 1)
            BigDecimal factor3 = oneAddRate.pow(i - 1);

            // ����ÿ�»�����Ϣ
            BigDecimal monthlyInterest = principal.multiply(rate).multiply(factor1.subtract(factor3)).divide(factor2, 2, RoundingMode.HALF_UP);
            System.out.print("��" + i + "����Ϣ: " + monthlyInterest + ",\t\t");

            // ����ÿ�»����
            BigDecimal monthlyPrinciple = principal.multiply(rate).multiply(factor3).divide(factor2, 2, RoundingMode.HALF_UP);
            System.out.print("��" + i + "�±���: " + monthlyPrinciple + "\t\t");

            System.out.println("�ϼ�: " + monthlyInterest + " + " + monthlyPrinciple + "=" + (monthlyInterest.add(monthlyPrinciple)));

        }

        // ����ÿ�»����, ������ monthlyInterest + monthlyPrinciple ���
        // BigDecimal monthly = monthlyInterest + monthlyPrinciple

        // ÿ�»����, ���ҵ����Ҫ���� monthlyInterest �� monthlyPrinciple, Ҳ����ͨ�������ʽֱ�Ӽ���
        BigDecimal monthly = principal.multiply(factor0).divide(factor2, 2, RoundingMode.HALF_UP);

        // ��Ϣ�ܺ� TODO ������, ÿ�»���������ϵļ��������һ��, ���Ǳ�Ϣ�ܺ������ (����0.5)
        BigDecimal all = monthly.multiply(BigDecimal.valueOf(month));

        // ����Ϣ
        BigDecimal sumInterest = all.subtract(principal);
        System.out.println("����Ϣ: " + format.format(sumInterest));

        System.out.println("�ܻ����: " + all);

    }
}
