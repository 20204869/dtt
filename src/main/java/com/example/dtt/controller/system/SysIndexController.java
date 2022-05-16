package com.example.dtt.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.example.dtt.common.config.DttConfig;
import com.example.dtt.constant.SsoHttpConstants;
import com.example.dtt.domain.entity.system.SysUser;
import com.example.dtt.exception.GlobalException;
import com.example.dtt.service.system.ISysConfigService;
import com.example.dtt.service.system.ISysUserService;
import com.example.dtt.utils.SecurityUtils;
import com.example.dtt.utils.http.HttpReqUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 首页
 */
@RestController
public class SysIndexController {
    /**
     * 系统基础配置
     */
    @Autowired
    private DttConfig dttConfig;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    /**
     * 访问首页，提示语

     @RequestMapping("/") public String index()
     {
     return StringUtils.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", dttConfig.getName(), dttConfig.getVersion());
     }
     */

    /**
     * 访问首页，并进行登录页跳转
     */
    @RequestMapping("/")
    public void index(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        JSONObject tokenResult = null;
        JSONObject userResult = null;
        String userName = "";
        String password = "";
        //获取统一登录token
        String tokenUrl = SsoHttpConstants.PRO_TOKEN_URL;
        //token 参数
        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("appKey", SsoHttpConstants.PRO_APP_KEY);
        tokenParams.put("appSecret", SsoHttpConstants.PRO_APP_SECRET);
        try {
            tokenResult = HttpReqUtils.getPostToken(tokenUrl, tokenParams);
            if (tokenResult == null) {
                new GlobalException("获取token失败！");
            }
            String resulsCode = tokenResult.getString("result");
            if (!SsoHttpConstants.SUCCESS.equals(resulsCode)) {
                new GlobalException("获取token失败！");
            }
            String token = JSONObject.parseObject(tokenResult.getString("obj")).getString("token");
            //获取st
            String st = request.getQueryString().substring(3);
            System.out.println("ST==========" + st);
            //携带token及st获取用户信息
            Map<String, Object> userParams = new HashMap<>();
            userParams.put("appId", SsoHttpConstants.PRO_APP_KEY);
            userParams.put("st", st);
            userResult = HttpReqUtils.getPostUser(SsoHttpConstants.PRO_USER_URL, userParams, token);
            if (userResult == null || !SsoHttpConstants.SUCCESS.equals(userResult.getString("result"))) {
                new GlobalException("携带token与st获取用户信息失败！");
            }
            String account = userResult.getString("obj");
            JSONObject accountJson = JSONObject.parseObject(JSONObject.parseObject(account).getString("account"));
            //获取用户详细信息
            String phone = accountJson.getString("mobile");
            String nickName = accountJson.getString("name");
            userName = accountJson.getString("userName");
            String email = accountJson.getString("email");
            String userSn = accountJson.getString("userSn");
            SysUser user = userService.selectUserByUserSn(userSn);
            password = configService.selectConfigByKey("sys.user.initPassword");
            //数据库内无改账户则新增改用户并跳转至首页
            if (user == null) {
                SysUser saveUser = new SysUser();
                saveUser.setNickName(nickName);
                saveUser.setUserName(userName);
                saveUser.setPhoneNumber(phone);
                saveUser.setEmail(email);
                saveUser.setPassword(SecurityUtils.encryptPassword(password));
                saveUser.setUserSn(userSn);
                userService.insertUser(saveUser);
            }
            if (SsoHttpConstants.SYS_ADMINISTRATOR_1.equals(userName)) {
                userName = SsoHttpConstants.SYS_ADMINISTRATOR_U;
                password = SsoHttpConstants.SYS_ADMINISTRATOR_PW;
            }
        } catch (Exception e) {
            e.printStackTrace();
            new GlobalException("请校验是否获取token或解析返回JSON是否异常！");
        }
        resp.sendRedirect(SsoHttpConstants.PRO_PATH + "userName=" + userName + "&password=" + password);
        //return ;
    }
}
