package com.showtime.xijing.web.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.service.UserService;
import com.showtime.xijing.web.utils.AesCbcUtil;
import com.showtime.xijing.web.utils.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 16:05
 **/
@Slf4j
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    private UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Result findOnlyUser(@Validated User user) {
        userService.findByOpenId(user.getOpenId());
        return Result.success();
    }

    @RequestMapping(value = "/allUser", method = RequestMethod.GET)
    public Result findAll(Pageable pageable) {
        return Result.success(userService.findAll(pageable));
    }

    /**
     * 需要将前端code 发送到该处，使用code 换取openid 和 session_key
     *
     * @param code 前台通过wx.login函数success微信服务器回传的code（有效期5min）
     *             本例中前台格式为 code: res.code
     * @return json格式数据 如果成功返回值为user对象
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Result login(@RequestParam(name = "encryptedData") String encryptedData,
                        @RequestParam(name = "iv") String iv,
                        @RequestParam(name = "code") String code) {
        log.info("用户进入");
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            return Result.fail("code 不能为空");
        }
        log.info("微信小程序登录，请求数据为[ code:" + code + "]");
        //获取用户openid的实际网址
        String token = UserInfoUtil.getWebAccess(code);
        //通过HttpGet方法将token发送至微信服务器并获得其回执
        String rec = UserInfoUtil.httpGet(token);
        log.info("微信回执为:" + rec);
        //使用json类解析结果
        JsonElement json = new JsonParser().parse(rec);
        //获取回执的openid
        User user = new User();
        if (json != null) {
            String openid = json.getAsJsonObject().get("openid").getAsString();
            user = userService.findByOpenId(openid);
            String session_key = json.getAsJsonObject().get("session_key").getAsString();
            try {
                String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
                JsonElement userInfo = new JsonParser().parse(result);
                if (null != result && result.length() > 0) {
                    if (user == null) {
                        User base = new User();
                        base.setNickname(userInfo.getAsJsonObject().get("nickName").getAsString());
                        base.setSex(userInfo.getAsJsonObject().get("gender").getAsInt());
                        String place = null;
                        if (userInfo.getAsJsonObject().get("country") != null)
                            place += userInfo.getAsJsonObject().get("country").getAsString() + "-";
                        if (userInfo.getAsJsonObject().get("province") != null)
                            place += userInfo.getAsJsonObject().get("province").getAsString() + "-";
                        if (userInfo.getAsJsonObject().get("city") != null)
                            place += userInfo.getAsJsonObject().get("city").getAsString();
                        base.setPlace(place);
                        base.setHeadPortrait(userInfo.getAsJsonObject().get("avatarUrl").getAsString());
                        base.setOpenId(openid);
                        user = userService.save(base);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Result.fail("用户信息解析失败");
            }
        }
        return Result.success(user);
    }


}


