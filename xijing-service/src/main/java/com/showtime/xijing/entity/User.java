package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import com.showtime.xijing.convert.EmojiConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 用户表
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 14:22
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity<Long> {

    @NotNull(message = "open_id 不能为空.")
    private String openId;

    private String place;

    @Convert(converter = EmojiConverter.class)
    private String nickname;

    private String headPortrait;

    // 0 未知  1 男   2 女
    @NotNull(message = "性别不能为空.")
    private int sex;

    private int age;

    private String introduction;

    @OneToOne
    @JoinColumn(name = "user_info_id", referencedColumnName = "id", unique = true)
    private UserInfo userInfo;

    @NotNull(message = "角色不能为空.")
    private int role;

    private String idCard;

    private int authStatus;

    private UserFile wxImage;

    private String wxNumber;

    private String phoneNumber;

    private int creditDegree;

    private int status;

    private Date lastLoginTime;

}
