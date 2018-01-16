package com.showtime.xijing.common.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class BaseEntityListener {
    @PrePersist
    public void setCreateTime(BaseEntity baseEntity) {
        baseEntity.createTime = new Date();
        baseEntity.updateTime = new Date();
    }

    @PreUpdate
    public void setUpdateTime(BaseEntity baseEntity) {
        baseEntity.updateTime = new Date();
    }
}
