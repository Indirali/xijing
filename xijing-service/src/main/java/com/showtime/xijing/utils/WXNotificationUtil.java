package com.showtime.xijing.utils;

import com.showtime.xijing.common.utils.DateUtil;
import com.showtime.xijing.entity.Notification;
import com.showtime.xijing.entity.NotificationDatas;
import com.showtime.xijing.entity.Reports;

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


    public static Notification recruitPassNotificationParam(Reports reports, String openId, String formId) {
        Notification notification = new Notification();
        notification.setTouser(openId);
        notification.setTemplate_id(Recruit_Pass);
        // TODO 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
        notification.setPage("index");
        notification.setForm_id(formId);
        NotificationDatas[] notificationDatas = new NotificationDatas[8];
        notificationDatas[0] = new NotificationDatas(reports.getReportRecruit().getTitle(), "#000000");
        notificationDatas[1] = new NotificationDatas(DateUtil.toString(reports.getReportRecruit().getParticipationTime()), "#000000");
        notificationDatas[2] = new NotificationDatas(reports.getReportRecruit().getUser().getPhoneNumber(), "#000000");
        notificationDatas[3] = new NotificationDatas(reports.getReportRecruit().getPoint(), "#000000");
        String salary = String.valueOf(reports.getReportRecruitInfo().getSalary());
        if (reports.getReportRecruitInfo().getSalary() == 0)
            salary = "无薪酬";
        if (reports.getReportRecruitInfo().getSalary() == -1)
            salary = "薪酬面议";
        notificationDatas[4] = new NotificationDatas(salary, "#000000");
        notificationDatas[5] = new NotificationDatas(reports.getUser().getNickname(), "#000000");
        notificationDatas[6] = new NotificationDatas(reports.getStatus() == 1 ? "未通过" : "已通过", "#000000");
        notificationDatas[7] = new NotificationDatas(reports.getStatus() == 1 ? Notification_Recruit_Fail : Notification_Recruit_Pass, "#000000");
        notification.setData(notificationDatas);
        return notification;
    }

}
