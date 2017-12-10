package com.showtime.xijing.web.vo;

import com.showtime.xijing.entity.Recruit;
import com.showtime.xijing.entity.RecruitInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by li on 2017/12/10.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RecruitVo {

    private Recruit recruit;

    private List<RecruitInfo> recruitInfos;

}
