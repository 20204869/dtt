package com.example.dtt.constant;

/**
 * 访问统一登录
 */
public class SsoHttpConstants
{
    /**
     * 获取统一登录token URL
     */
    public static final String TEST_TOKEN_URL = "http://10.42.1.51:9094/cango-mid-account/accountApp/token";
    public static final String PRO_TOKEN_URL = "http://midapi.cangoonline.net/cango-mid-account/accountApp/token";


    /**
     * 获取用户信息URL
     */
    public static final String TEST_USER_URL = "http://ssoapi-test.cangoonline.net/verify/checkSt";
    public static final String PRO_USER_URL = "https://ssoapi.cangoonline.net/verify/checkSt";


    /**
     * 获取token及用户信息的关键KEY
     *
     * 测试环境：key & secret :appManager
     *
     */
    public static final String TEST_APP_KEY = "appManager";
    public static final String PRO_APP_KEY = "dataManager";
    public static final String PRO_APP_SECRET = "d7771b77874eb7aeb60073fcaaa42dd5";

    /**
     * 获取第三方信息返回状态
     */

    public static final String SUCCESS = "0000";


    /**
     * 超级管理员
     */
    public static final String SYS_ADMINISTRATOR_1 = "zhangshanshan";
    public static final String SYS_ADMINISTRATOR_2 = "renpengyuan";

    public static final String SYS_ADMINISTRATOR_U = "admin";
    public static final String SYS_ADMINISTRATOR_PW = "bigdata@2022";

    public static final String LOCAL_PATH = "http://localhost:80/#/login?";
    public static final String PRO_PATH = "http://datamanager.cangoonline.net/#/login?";

}
