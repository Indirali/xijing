package com.showtime.xijing.common.entity;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by liudajiang on 2017/6/19.
 */
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public abstract class VisibleBaseEntity<ID extends Serializable> extends BaseEntity<ID> {

    /**
     * 可见性
     */
    protected Visible visible = Visible.ALL;

    /**
     * 是否被删除
     *
     * @return
     */
    public boolean deleted() {
        return visible == Visible.INVISIBLE;
    }

    public enum Visible {
        ALL, AUTH_ONLY, INVISIBLE
    }

}


