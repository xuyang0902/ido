package com.ido;

import java.math.BigDecimal;

/**
 *
 * 25950
 * @author xu.qiang
 * @date 20/1/7
 */
public class SalarayCalculate {

    public static final BigDecimal base社保2019max = new BigDecimal("16608");
    public static final BigDecimal base社保2019min = new BigDecimal("3321.6");
    public static final BigDecimal leve_3 = new BigDecimal("36000");
    public static final BigDecimal leve_10 = new BigDecimal("144000");
    public static final BigDecimal leve_20 = new BigDecimal("300000");
    public static final BigDecimal leve_25 = new BigDecimal("420000");
    public static final BigDecimal leve_30 = new BigDecimal("660000");
    public static final BigDecimal leve_35 = new BigDecimal("960000");


    private BigDecimal 累计计税额 = BigDecimal.ZERO;

    private BigDecimal 累计现金收入 = BigDecimal.ZERO;
    private BigDecimal 累计收入加公积金 = BigDecimal.ZERO;

    /**
     * 计算薪水
     *
     * @param month      月份
     * @param baseAmount 工资
     * @param roomAmount 公积金
     * @param dectAmount 抵扣金额
     * @return
     */
    public void calculate(int month, BigDecimal baseAmount, BigDecimal roomAmount, BigDecimal dectAmount) {
        BigDecimal 基本工资 = baseAmount;
        BigDecimal 公积金 = roomAmount;
        BigDecimal base社保;
        if (基本工资.compareTo(base社保2019max) > 0) {
            base社保 = base社保2019max;
        } else if (基本工资.compareTo(base社保2019min) < 0) {
            base社保 = base社保2019min;
        } else {
            base社保 = 基本工资;
        }

        BigDecimal 社保代扣 = base社保.multiply(new BigDecimal("0.08")
                .add(new BigDecimal("0.02"))
                .add(new BigDecimal("0.005"))
        );//养老保险 8% 医疗保险 2% 失业险


        BigDecimal 个人所得税 = null;

        BigDecimal 累计计税before = 累计计税额;
        BigDecimal 计税工资 = 基本工资.subtract(公积金).subtract(社保代扣).subtract(dectAmount);
        BigDecimal 累计计税after = 累计计税额.add(计税工资);

        if (累计计税after.compareTo(leve_3) < 0) {
            //3%
            个人所得税 = 计税工资.multiply(new BigDecimal("0.03"));
        } else if (累计计税after.compareTo(leve_10) < 0) {
            if (累计计税before.compareTo(leve_3) < 0) {
                BigDecimal 超过部分百分之10 = 累计计税after.subtract(leve_3);
                BigDecimal 没有超过部分还是百分之3 = 计税工资.subtract(超过部分百分之10);
                个人所得税 = 超过部分百分之10.multiply(new BigDecimal("0.1")).add(没有超过部分还是百分之3.multiply(new BigDecimal("0.03")));
            } else {
                个人所得税 = 计税工资.multiply(new BigDecimal("0.1"));
            }
        } else if (累计计税after.compareTo(leve_20) < 0) {
            //20%
            if (累计计税before.compareTo(leve_3) < 0) {

                throw new RuntimeException("暂时不考虑跨度过大的 3% --> 20%");

            } else if (累计计税before.compareTo(leve_10) < 0) {
                BigDecimal 超过部分百分之20 = 累计计税after.subtract(leve_10);
                BigDecimal 没有超过部分还是百分之10 = 计税工资.subtract(超过部分百分之20);
                个人所得税 = 超过部分百分之20.multiply(new BigDecimal("0.2")).add(没有超过部分还是百分之10.multiply(new BigDecimal("0.1")));
            } else {
                个人所得税 = 计税工资.multiply(new BigDecimal("0.2"));
            }

        } else if (累计计税after.compareTo(leve_25) < 0) {
            //25%
            if (累计计税before.compareTo(leve_3) < 0) {

                throw new RuntimeException("暂时不考虑跨度过大的 3% --> 25%");

            } else if (累计计税before.compareTo(leve_10) < 0) {

                throw new RuntimeException("暂时不考虑跨度过大的 10% --> 25%");

            } else if (累计计税before.compareTo(leve_20) < 0) {
                BigDecimal 超过部分百分之25 = 累计计税after.subtract(leve_20);
                BigDecimal 没有超过部分还是百分之20 = 计税工资.subtract(超过部分百分之25);
                个人所得税 = 超过部分百分之25.multiply(new BigDecimal("0.25")).add(没有超过部分还是百分之20.multiply(new BigDecimal("0.2")));

            } else {
                个人所得税 = 计税工资.multiply(new BigDecimal("0.25"));
            }

        } else if (累计计税after.compareTo(leve_30) < 0) {
            //30%

            if (累计计税before.compareTo(leve_3) < 0) {

                throw new RuntimeException("暂时不考虑跨度过大的 3% --> 30%");

            } else if (累计计税before.compareTo(leve_10) < 0) {

                throw new RuntimeException("暂时不考虑跨度过大的 10% --> 30%");

            } else if (累计计税before.compareTo(leve_20) < 0) {

                throw new RuntimeException("暂时不考虑跨度过大的 20% --> 30%");

            } else if (累计计税before.compareTo(leve_25) < 0) {

                BigDecimal 超过部分百分之25 = 累计计税after.subtract(leve_25);
                BigDecimal 没有超过部分还是百分之20 = 计税工资.subtract(超过部分百分之25);
                个人所得税 = 超过部分百分之25.multiply(new BigDecimal("0.25")).add(没有超过部分还是百分之20.multiply(new BigDecimal("0.2")));


            } else {
                个人所得税 = 计税工资.multiply(new BigDecimal("0.30"));
            }

        } else if (累计计税after.compareTo(leve_35) < 0) {
            //35%

            //这个代码等以后有机会再写吧 哈哈哈哈 todo
            throw new RuntimeException("not support yet");

        } else {
            //45%
            throw new RuntimeException("not support yet");
        }

        累计计税额 = 累计计税额.add(计税工资);

        BigDecimal 现金收入 = baseAmount.subtract(公积金).subtract(社保代扣).subtract(个人所得税);
        累计现金收入 = 现金收入.add(累计现金收入);
        累计收入加公积金 = 累计收入加公积金.add(现金收入).add(公积金).add(公积金);

        System.out.println(String.format("月份：%s，薪水：%s，公积金（加公司部分）：%s，社保：%s，个税：%s，到手现金:%s，累计现金收入：%s,累计收入含公积金:%s ,累计计税金额:%s",
                month,
                baseAmount,
                公积金.multiply(new BigDecimal("2")),
                社保代扣,
                个人所得税,
                现金收入,
                累计现金收入,
                累计收入加公积金,
                累计计税额));

    }


    public static void main(String[] args) {
        SalarayCalculate salarayCalculate = new SalarayCalculate();

        salarayCalculate.calculate(1,new BigDecimal("10000"),new BigDecimal("1200"),new BigDecimal("6000"));
        salarayCalculate.calculate(2,new BigDecimal("10000"),new BigDecimal("1200"),new BigDecimal("6000"));
        salarayCalculate.calculate(3,new BigDecimal("10000"),new BigDecimal("1200"),new BigDecimal("6000"));
        salarayCalculate.calculate(4,new BigDecimal("10000"),new BigDecimal("1200"),new BigDecimal("6000"));
        salarayCalculate.calculate(5,new BigDecimal("10000"),new BigDecimal("1200"),new BigDecimal("6000"));
        salarayCalculate.calculate(6,new BigDecimal("10000"),new BigDecimal("1200"),new BigDecimal("6000"));
        salarayCalculate.calculate(7,new BigDecimal("10000"),new BigDecimal("1200"),new BigDecimal("6000"));
        salarayCalculate.calculate(8,new BigDecimal("10000"),new BigDecimal("1200"),new BigDecimal("6000"));
        salarayCalculate.calculate(9,new BigDecimal("10000"),new BigDecimal("1200"),new BigDecimal("6000"));
        salarayCalculate.calculate(10,new BigDecimal("10000"),new BigDecimal("1200"),new BigDecimal("6000"));
        salarayCalculate.calculate(11,new BigDecimal("10000"),new BigDecimal("1200"),new BigDecimal("6000"));
        salarayCalculate.calculate(12,new BigDecimal("10000"),new BigDecimal("1200"),new BigDecimal("6000"));
        salarayCalculate.calculate(13,new BigDecimal("10000"),new BigDecimal("0"),new BigDecimal("0"));


    }
}
