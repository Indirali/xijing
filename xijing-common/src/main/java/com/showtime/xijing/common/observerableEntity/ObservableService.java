package com.showtime.xijing.common.observerableEntity;

import org.springframework.data.domain.Persistable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObservableService<T extends Persistable> {
    private List<PreCreateObserver> preCreateObservers = new ArrayList<>();
    private List<PostCreateObserver> postCreateObservers = new ArrayList<>();
    private List<PreUpdateObserver> preUpdateObservers = new ArrayList<>();
    private List<PostUpdateObserver> postUpdateObservers = new ArrayList<>();

    protected List<PreCreateObserver> getPreCreateObservers() {
        return preCreateObservers;
    }

    protected List<PostCreateObserver> getPostCreateObservers() {
        return postCreateObservers;
    }

    protected List<PreUpdateObserver> getPreUpdateObservers() {
        return preUpdateObservers;
    }

    protected List<PostUpdateObserver> getPostUpdateObservers() {
        return postUpdateObservers;
    }

    protected void preCreate(PreCreateObserver<T> ... observerList) {
        preCreateObservers.addAll(Arrays.asList(observerList));
    }
    protected void postCreate(PostCreateObserver<T>... observerList) {
        postCreateObservers.addAll(Arrays.asList(observerList));
    }
    protected void preUpdate(PreUpdateObserver<T>... observerList) {
        preUpdateObservers.addAll(Arrays.asList(observerList));
    }
    protected void postUpdate(PostUpdateObserver<T>... observerList) {
        postUpdateObservers.addAll(Arrays.asList(observerList));
    }

    public interface PreCreateObserver<T extends Persistable> {
        boolean call(T entity);
    }
    public interface PostCreateObserver<T extends Persistable> {
        void call(T entity);
    }
    public interface PreUpdateObserver<T extends Persistable> {
        boolean call(T old, T news);
    }
    public interface PostUpdateObserver<T extends Persistable> {
        void call(T old, T news);
    }
}
