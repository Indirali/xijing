package com.showtime.xijing.web.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.service.UserService;
import com.showtime.xijing.utils.GaoDeMapUtil;
import com.showtime.xijing.utils.RequestAll;
import com.showtime.xijing.utils.WXRequestUtil;
import com.showtime.xijing.web.utils.AesCbcUtil;
import com.showtime.xijing.web.utils.EmojiFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by li on 2017/12/9.
 */
@Slf4j
@RestController
public class IdentityController {

    private UserService userService;

    @Autowired
    public IdentityController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 需要将前端code 发送到该处，使用code 换取openid 和 session_key
     *
     * @param code 前台通过wx.login函数success微信服务器回传的code（有效期5min）
     * @return json格式数据 如果成功返回值为user对象
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Result login(String encryptedData, String iv, String code, String longitude, String latitude) {
        JsonElement json = getWXContent(code);
        Assert.isTrue(json != null, "微信响应错误!");
        String openid = json.getAsJsonObject().get("openid").getAsString();
        User user = userService.findByOpenId(openid);
        String session_key = json.getAsJsonObject().get("session_key").getAsString();
        boolean newUser = false;
        try {
            if (user == null) {
                String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
                JsonElement userInfo = new JsonParser().parse(result);
                User base = new User();
                base.setNickname(EmojiFilter.filterEmoji(userInfo.getAsJsonObject().get("nickName").getAsString()));
                base.setSex(userInfo.getAsJsonObject().get("gender").getAsInt());
                base.setHeadPortrait(userInfo.getAsJsonObject().get("avatarUrl").getAsString());
                base.setOpenId(openid);
                base.setLongitude(longitude);
                base.setLatitude(latitude);
                base.setLastLoginTime(new Date());
                base.setPlace(GaoDeMapUtil.getLocation(longitude, latitude));
                user = userService.save(base);
                newUser = true;
            } else {
                user.setLongitude(longitude);
                user.setLatitude(latitude);
                user.setLastLoginTime(new Date());
                user.setPlace(GaoDeMapUtil.getLocation(longitude, latitude));
                userService.save(user);
            }

        } catch (Exception e) {
            log.debug(ExceptionUtils.getStackTrace(e));
            return Result.fail("用户信息解析失败");
        }
        return Result.success().data("User", user).data("newUser", newUser);
    }

    /**
     * 获取用户绑定手机号
     *
     * @param encryptedData
     * @param iv
     * @param openId
     * @return
     */
    @RequestMapping(value = "/getPhoneNumber", method = RequestMethod.POST)
    public Result getPhoneNumber(String encryptedData, String iv, String openId, String code) {
        User user = userService.findByOpenId(openId);
        JsonElement json = getWXContent(code);
        Assert.isTrue(json != null, "微信响应错误!");
        String session_key = json.getAsJsonObject().get("session_key").getAsString();
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            JsonElement userInfo = new JsonParser().parse(result);
            user.setPhoneNumber(userInfo.getAsJsonObject().get("purePhoneNumber").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        userService.save(user);
        return Result.success();
    }

    private JsonElement getWXContent(String code) {
        log.info("用户进入");
        Assert.notNull(code, "code 不能为空");
        log.info("微信小程序登录，请求数据为[ code:" + code + "]");
        String token = WXRequestUtil.getWebAccess(code);  // 获取用户openid的实际网址
        String rec = RequestAll.httpGet(token);  // 通过HttpGet方法将token发送至微信服务器并获得其回执
        log.info("微信回执为:" + rec);
        JsonElement json = new JsonParser().parse(rec);  // 使用json类解析结果
        return json;
    }

}
