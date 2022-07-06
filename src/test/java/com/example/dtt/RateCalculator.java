package com.example.dtt;

public class RateCalculator{
    public static void main(String[] args) {
        /*if (args.length < 3) {
            System.out.println("��������3������(������ �������� ÿ�»���)!");
            return;
        }*/
        double Loan = 87000.0;//Double.parseDouble(args[0]);  //�����Ԫ��
        int months = 36;//Integer.parseInt(args[1]);     //�����������£�
        double monthPay = 2669.483891;//Double.parseDouble(args[2]); //ÿ�»��Ԫ��
        double yearRate = rateCalculator(Loan,months,monthPay); //�껯���ʣ�%��

        System.out.println("�����" + Loan);
        System.out.println("����������" + months);
        System.out.println("ÿ�»��" + monthPay);
        System.out.println("�껯���ʣ�" + String.format("%.2f", yearRate) + "%");
    }

    /**
     * �ȶϢ����֪ÿ�»�������������.
     *
     * @param totalLoan �����ܶ�(��λ��Ԫ)
     * @param months ��������(����)
     * @param monthPay ÿ�»����(Ԫ)
     * @return ������(%)
     */
    public static double rateCalculator(double totalLoan, int months, double monthPay) {

        double monthRate; //������
        double[] monthPayLoan = new double[months]; //ÿ�ڻ��ı���

        /*���㷨���ö��ַ���ٵ�һ�µĻ����������ÿ�»���𣬼�������ı���Ϳ��Եõ�������
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
