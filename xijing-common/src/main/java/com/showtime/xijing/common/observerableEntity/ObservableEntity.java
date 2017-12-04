package com.showtime.xijing.common.observerableEntity;

import org.springframework.data.domain.Persistable;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@EntityListeners(ObservableEntityListener.class)
public abstract class ObservableEntity<ID extends Serializable> implements Persistable<ID>,Cloneable {

    @Override
    public ObservableEntity clone() {
        try {
            return (ObservableEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            throw  new RuntimeException(e);
        }
    }
}
