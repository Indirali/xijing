package com.showtime.xijing.common.entity;

import com.showtime.xijing.common.observerableEntity.ObservableEntity;
import com.showtime.xijing.common.utils.PrettyTimeUtils;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
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
    protected Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time", updatable = false)
    protected Date updateTime;

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

}


