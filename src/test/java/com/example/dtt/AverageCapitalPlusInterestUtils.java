package com.example.dtt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * �ȶϢ���㹤����
 *
 * <p>
 * �ȶϢ���Ҳ�ƶ��ڸ�Ϣ���������ÿ�°���ȵĽ������Ϣ������ÿ�´�����Ϣ���³�ʣ��������㲢���½��塣�Ѱ��Ҵ���ı����ܶ�����Ϣ�ܶ���ӣ�
 * Ȼ��ƽ����̯���������޵�ÿ�����С���Ϊ�����ˣ�ÿ���»������й̶�����ÿ�»�����еı���������µ�������Ϣ�������µݼ���
 */
public class AverageCapitalPlusInterestUtils {

    /**
     * ÿ�³����������Ϣ
     * <p>
     * ��ʽ��ÿ�³�����Ϣ=�������������ʡ�(1��������)�޻����������¡�(1��������)�޻�������-1��
     *
     * @param invest     �ܽ�������,��λ�֣�
     * @param yearRate   ������
     * @param totalMonth ����������
     * @return ÿ�³����������Ϣ(��1 ��λ��)
     */
    public static long getPerMonthPrincipalInterest(long invest, double yearRate, int totalMonth) {
        double monthRate = yearRate / 12;
        double perMonthPrincipalInterest = invest * (monthRate * Math.pow(1 + monthRate, totalMonth))/(Math.pow(1 + monthRate, totalMonth) - 1);
        return new BigDecimal(perMonthPrincipalInterest).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
    }

    /**
     * �ȶϢ��ÿ�³�����Ϣ
     * <p>
     * ��ʽ��ÿ�³�����Ϣ=�����������ʡ���(1+������)^��������-(1+������)^(���������-1)���¡�(1+������)^��������-1��
     *
     * @param invest     �ܽ�������,�֣�
     * @param yearRate   ������
     * @param totalMonth ����������
     * @return ÿ�³�����Ϣ(��1 ��λ��)
     */
    public static Map<Integer, Long> getPerMonthInterest(long invest, double yearRate, int totalMonth) {
        Map<Integer, Long> map = new HashMap<>();
        double monthRate = yearRate / 12;
        double monthInterest;
        for (int i = 1; i < totalMonth + 1; i++) {
            double multiply = invest * monthRate;
            double sub = Math.pow(1 + monthRate, totalMonth) - Math.pow(1 + monthRate, i - 1);
            monthInterest = multiply * sub/(Math.pow(1 + monthRate, totalMonth) - 1);
            map.put(i, new BigDecimal(monthInterest).setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
        }
        return map;
    }

    /**
     * �ȶϢ��ÿ�³��������»���Ϣ-�»���Ϣ��
     *
     * @param invest     �ܽ�������,�֣�
     * @param yearRate   ������
     * @param totalMonth ����������
     * @return ÿ�³�������(ȡ���� ��λ��)
     */
    public static Map<Integer, Long> getPerMonthPrincipal(long invest, double yearRate, int totalMonth) {
        double monthRate = yearRate / 12;
        double monthIncome = invest * monthRate * Math.pow(1 + monthRate, totalMonth)
                /(Math.pow(1 + monthRate, totalMonth) - 1);
        long perMonthPrincipalInterest = new BigDecimal(monthIncome).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();

        Map<Integer, Long> mapPrincipal = new HashMap<>();
        double monthInterest;
        for (int i = 1; i < totalMonth + 1; i++) {
            Double multiply = invest * monthRate;
            double sub = (Math.pow(1 + monthRate, totalMonth)) - (Math.pow(1 + monthRate, i - 1));
            monthInterest = multiply* sub/(Math.pow(1 + monthRate, totalMonth) - 1);
            long monthInterestL = new BigDecimal(monthInterest).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
            mapPrincipal.put(i, perMonthPrincipalInterest-monthInterestL);
        }
        return mapPrincipal;
    }

    /**
     * �ȶϢ������Ϣ
     *
     * @param invest     �ܽ�������
     * @param yearRate   ������
     * @param totalMonth ����������
     * @return ����Ϣ (��λ��)
     */
    public static long getInterestCount(long invest, double yearRate, int totalMonth) {
        long count = 0;
        Map<Integer, Long> mapInterest = getPerMonthInterest(invest, yearRate, totalMonth);

        for (Map.Entry<Integer, Long> entry : mapInterest.entrySet()) {
            count = count + entry.getValue();
        }
        return count;
    }

}
