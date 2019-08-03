package com.lee.mydemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.lee.mydemo.model.EntRespson;
import com.lee.mydemo.config.UserConfig;
import com.lee.mydemo.model.User;
import com.lee.mydemo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CtrlUser {
    @Autowired
    private UserConfig config;

    @RequestMapping("test1")
    public String test() {
        return "ok test" + config.getName();
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public EntRespson login(@RequestBody User user) {
        EntRespson entRespson = new EntRespson();
        try {
            String usename = config.getName();
            String pwds = config.getPassword();
            if (!(user.getName().equals(usename) &&
                    user.getPassword().equals(pwds)))throw new Exception("账户密码错误");
            //登录成功获得token
            String token = JwtUtils.encode(config.getId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token",token);
            entRespson.setData(jsonObject);
        } catch (Exception e) {
            entRespson.setCode(-1);
            entRespson.setResultMsg(e.getMessage());
        }
        return entRespson;
    }
}
