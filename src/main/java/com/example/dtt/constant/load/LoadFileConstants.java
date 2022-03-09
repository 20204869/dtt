package com.example.dtt.constant.load;

/**
 * @author reid
 * @date 2022/1/10 13:43
 * @describe 上传文件常量管理类
 */
public class LoadFileConstants {

    /**
     * hdfs 服务地址
     */
    public static final String HDFS_NAME_PROD = "xxx";
    public static final String HDFS_NAME_DEV = "xxx";
    /**
     * 业务离线数据表前缀
     */
    /**
     * 表前缀
     */
    //财务
    public static final String IM_PREFIX_FINANCIAL = "im_financial_";
    //4S渠道
    public static final String IM_PREFIX_4S = "im_4S_";

    /**
     * 业务离线数据路径前缀
     */
    public static final String FINANCIAL_PATH = "/load/data/finance/";
    /**
     * 车台险帐 离线数据上传路径   业务：财务PDCA   4
     */
    public static final String CAR_INSURANCE_BILL = FINANCIAL_PATH+"car_bill/";
    public static final String CAR_INSURANCE_BILL_TABLE = IM_PREFIX_FINANCIAL+"car_insurance_info_";
    /**
     * 车品牌分类 离线数据上传路径  业务：4S    6
     */
    public static final String CAR_BRAND = FINANCIAL_PATH+"car_brand/";
    public static final String CAR_BRAND_TABLE = IM_PREFIX_4S+"car_brand_class_";

    /**
     * 真实经销商分区 离线数据上传路径  业务：4S   3
     */
    public static final String DEALER_AREA = FINANCIAL_PATH+"dealer_area/";
    public static final String DEALER_AREA_TABLE = IM_PREFIX_4S+"dealer_area_detail_";

    /**
     * 冠销风险敞口 离线数据上传路径   业务：PDCA  5
     */
    public static final String CROWN_SELL_RISK_DATA = FINANCIAL_PATH+"crown_sell_risk_data/";
    public static final String CROWN_SELL_RISK_DATA_TABLE = IM_PREFIX_FINANCIAL+"risk_exposure_cp_";

    /**
     * PDCA线下 离线数据上传路径   业务：PDCA  1
     */
    public static final String PDCA_OFFLINE = FINANCIAL_PATH+"pdca_offline/";
    public static final String PDCA_OFFLINE_TABLE = IM_PREFIX_FINANCIAL+"pdca_";
    /**
     * 额外补充不到单 离线数据上传路径   业务：PDCA   2
     */
    public static final String OUT_ORDER_DATA = FINANCIAL_PATH+"out_order_data/";
    public static final String OUT_ORDER_DATA_TABLE = IM_PREFIX_FINANCIAL+"out_order_data_";

    /**
     * PDCA成本费用预算   8
     */
    public static final String INCOME_COSTS_BUDGET = FINANCIAL_PATH+"income_cost_budget/";
    public static final String INCOME_COSTS_BUDGET_TABLE = IM_PREFIX_FINANCIAL+"income_cost_budget_";

    /**
     * 保险费用预算   7
     */
    public static final String INSURANCE_BUDGET = FINANCIAL_PATH+"insurance_budget/";
    public static final String INSURANCE_BUDGET_TABLE = IM_PREFIX_FINANCIAL+"insurance_budget_";

    /**
     * 未知业务类型数据存储
     */
    public static final String UNKNOWN = "/load/data/unknown/";
}
