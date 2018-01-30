package com.showtime.xijing.common.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.showtime.xijing.common.observerableEntity.ObservableEntity;
import com.showtime.xijing.common.utils.PrettyTimeUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by liudajiang on 2017/6/19.
 */
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public abstract class BaseEntity<ID extends Serializable> extends ObservableEntity<ID> {
    static {
        /**
         * 格式化输出属性
         *
         *	 Person@182f0db[
         name=John Doe
         age=33
         smoker=false
         ]

         */
        ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected ID id;

    /**
     * 创建时间
     */
    @Column(name = "create_time", updatable = false)
    @JsonSerialize
    protected Long createTime;

    /**
     * 更新时间
     */
    @JsonSerialize
    protected Long updateTime;


    /**
     * 格式化显示创建时间
     *
     * @return
     * @create_date 2016年1月4日
     */
    public String displayCreateTime() {
        return Instant.ofEpochMilli(createTime).atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 显示据现在的时间段
     *
     * @return
     * @create_date 2016年1月4日
     */
    public String displayFromNow() {
        return PrettyTimeUtils.prettyTime(createTime);
    }

    /**
     * 当前实体是否在数据库中存在
     *
     * @return
     */
    @Transient
    public boolean isNew() {

        return null == getId();
    }


    @Override
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#equals(java.lang.Object)
         */
    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        BaseEntity<?> that = (BaseEntity<?>) obj;

        return null != this.getId() && this.getId().equals(that.getId());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public enum Visible {
        ALL, AUTH_ONLY, INVISIBLE
    }

    /**
     * 通用验证Group
     *
     * @author liudajiang
     * @create_date 2015年12月2日
     */
    public static class Valid {
        public static interface Create {
        }


        public static interface Update {
        }
    }

}


