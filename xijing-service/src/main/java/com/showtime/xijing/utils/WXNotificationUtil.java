package com.showtime.xijing.utils;

import com.showtime.xijing.common.utils.DateUtil;
import com.showtime.xijing.entity.NotificationInfo;
import com.showtime.xijing.entity.NotificationInfoData;
import com.showtime.xijing.entity.Reports;

import java.util.HashMap;
import java.util.Map;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/27
 * Time: 16:17
 **/
public class WXNotificationUtil {

    private static String Recruit_Pass = "SZNADHJCRviz6yNvj9k8XDi2M_h-3nXPH1mCIcRoT3U";

    private static String Notification_Recruit_Pass = "快快点击进行二次确认,这样才会正式报名成功哦！";
    private static String Notification_Recruit_Fail = "这次的可能不太适合你，快进入戏鲸再报名一个吧！";


    public static NotificationInfo recruitPassNotificationParam(Reports reports, String openId, String formId) {
        NotificationInfo notificationInfo = new NotificationInfo();
        notificationInfo.setTouser(openId);
        notificationInfo.setTemplate_id(Recruit_Pass);
        // TODO 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
        notificationInfo.setPage("index");
        notificationInfo.setForm_id(formId);
        Map<String, NotificationInfoData> notificationInfoData = new HashMap<>();
        NotificationInfoData first = new NotificationInfoData(reports.getReportRecruit().getTitle(), "#000000");
        NotificationInfoData second = new NotificationInfoData(DateUtil.toString(reports.getReportRecruit().getParticipationTime()), "#000000");
        NotificationInfoData thirdly = new NotificationInfoData(reports.getReportRecruit().getUser().getPhoneNumber(), "#000000");
        NotificationInfoData fourth = new NotificationInfoData(reports.getReportRecruit().getPoint(), "#000000");
        String salary = String.valueOf(reports.getReportRecruitInfo().getSalary());
        if (reports.getReportRecruitInfo().getSalary() == 0)
            salary = "无薪酬";
        if (reports.getReportRecruitInfo().getSalary() == -1)
            salary = "薪酬面议";
        NotificationInfoData fifth = new NotificationInfoData(salary, "#000000");
        NotificationInfoData sixth = new NotificationInfoData(reports.getUser().getNickname(), "#000000");
        NotificationInfoData seventh = new NotificationInfoData(reports.getStatus() == 1 ? "未通过" : "已通过", "#000000");
        NotificationInfoData eighth = new NotificationInfoData(reports.getStatus() == 1 ? Notification_Recruit_Fail : Notification_Recruit_Pass, "#000000");
        notificationInfoData.put("first", first);
        notificationInfoData.put("second", second);
        notificationInfoData.put("thirdly", thirdly);
        notificationInfoData.put("fourth", fourth);
        notificationInfoData.put("fifth", fifth);
        notificationInfoData.put("sixth", sixth);
        notificationInfoData.put("seventh", seventh);
        notificationInfoData.put("eighth", eighth);
        notificationInfo.setData(notificationInfoData);
        return notificationInfo;
    }

}
