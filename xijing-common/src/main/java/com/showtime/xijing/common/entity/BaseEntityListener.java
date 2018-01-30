package com.showtime.xijing.common.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class BaseEntityListener {
    @PrePersist
    public void setCreateTime(BaseEntity baseEntity) {
        Long current=System.currentTimeMillis();
        baseEntity.createTime = current;
        baseEntity.updateTime = current;
    }

    @PreUpdate
    public void setUpdateTime(BaseEntity baseEntity) {
        baseEntity.updateTime = System.currentTimeMillis();
    }
}
