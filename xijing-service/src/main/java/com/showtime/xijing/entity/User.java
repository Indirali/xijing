package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import com.showtime.xijing.convert.EmojiConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.util.Date;
import java.util.List;

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

    private Point place;

    @NotNull(message = "昵称不能为空.")
    @Convert(converter = EmojiConverter.class)
    private String nickname;

    @NotNull(message = "不能为空.")
    private String headPortrait;

    // 0 未知  1 男   2 女
    @NotNull(message = "性别不能为空.")
    private int sex;

    private int age;

    /*private long moka;

    private long video;*/

    private String introduction;

    @OneToOne
    private UserInfo userInfo;

    @NotNull(message = "角色不能为空.")
    private int role;

    private String idCard;

    private int authStatus;

    private long wxImage;

    private String wxNumber;

    private String phoneNumber;

    private int creditDegree;

    private int status;

    private Date lastLoginTime;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<UserFile> userFiles;

}
