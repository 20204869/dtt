package com.example.dtt.controller.system;

import com.example.dtt.controller.base.BaseController;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.model.RegisterBody;
import com.example.dtt.service.SysRegisterService;
import com.example.dtt.service.system.ISysConfigService;
import com.example.dtt.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
