package com.showtime.xijing.common.observerableEntity;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.PostLoad;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Slf4j
@Aspect
public class ObservableEntityListener implements ApplicationContextAware {

    static Map<Class, List<ObservableService>> entityServiceMap = new HashMap<>();
    static ThreadLocal<Map<String, ObservableEntity>> entityThreadLocal = ThreadLocal.withInitial(() -> new HashMap<>());
    static ThreadLocal<Set<String>> updatingEntityMap = ThreadLocal.withInitial(() -> new HashSet<String>());
    private ApplicationContext applicationContext;

    /***
     * 清除threadlocal中保存的实体
     */
    public void clearEntityMap() {
        entityThreadLocal.get().clear();
    }

    private String getEntityMapKey(ObservableEntity entity) {
        return entity.getClass().getCanonicalName() + entity.getId();
    }

    @PostLoad
    public void postLoad(ObservableEntity entity) {
        entityThreadLocal.get().put(getEntityMapKey(entity), entity.clone());
    }

    @PostConstruct
    public void init() {
        log.info("初始化实体观察器.");
        Map<String, ObservableService> map = applicationContext.getBeansOfType(ObservableService.class);
        Collection<ObservableService> observableServices = map.values();

        for (ObservableService service : observableServices) {
            Class entityClass = service.getClass();
            if (AopUtils.isCglibProxy(service)) {
                entityClass = entityClass.getSuperclass();
            }
            entityClass = (Class) ((ParameterizedType) entityClass.getGenericSuperclass()).getActualTypeArguments()[0];
            List<ObservableService> serviceList = entityServiceMap.computeIfAbsent(entityClass, k -> new ArrayList<>());
            serviceList.add(service);
        }
        log.info("初始化实体观察器完毕.");
    }

    @Around("target(org.springframework.data.repository.CrudRepository)")
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        String callMethod = pjp.getSignature().getName();
        if (!"save".equals(callMethod)) {
            return pjp.proceed();
        }
        Object arg = pjp.getArgs()[0];
        if (arg instanceof Iterable) {
            throw new RuntimeException("save 方法暂不支持批量保存");
        }
        if (!(arg instanceof ObservableEntity)) {
            return pjp.proceed();
        }
        ObservableEntity entity = (ObservableEntity) arg;
        String entityKey = getEntityMapKey(entity);
        List<ObservableService> serviceList = entityServiceMap.get(entity.getClass());
        Object result;
        if (entity.isNew()) {
            if (!CollectionUtils.isEmpty(serviceList)) {
                for (ObservableService service : serviceList) {
                    for (Object obj : service.getPreCreateObservers()) {
                        ObservableService.PreCreateObserver observer = (ObservableService.PreCreateObserver) obj;
                        boolean isContinue = observer.call(entity);
                        if (!isContinue) {
                            return null;
                        }
                    }
                }
            }
            result = pjp.proceed();
            entityThreadLocal.get().put(entityKey, entity.clone());
            if (!CollectionUtils.isEmpty(serviceList)) {
                for (ObservableService service : serviceList) {
                    for (Object obj : service.getPostCreateObservers()) {
                        ObservableService.PostCreateObserver observer = (ObservableService.PostCreateObserver) obj;
                        observer.call(entity);
                    }
                }
            }
        } else {
            ObservableEntity old = entityThreadLocal.get().get(entityKey);
            if (!CollectionUtils.isEmpty(serviceList)) {
                for (ObservableService service : serviceList) {
                    for (Object obj : service.getPreUpdateObservers()) {
                        ObservableService.PreUpdateObserver observer = (ObservableService.PreUpdateObserver) obj;
                        boolean isContinue = observer.call(old, entity);
                        if (!isContinue) {
                            return null;
                        }
                    }
                }
            }
            result = pjp.proceed();
            entityThreadLocal.get().put(entityKey, entity.clone());
            if (!CollectionUtils.isEmpty(serviceList)) {
                for (ObservableService service : serviceList) {
                    for (Object obj : service.getPostUpdateObservers()) {
                        ObservableService.PostUpdateObserver observer = (ObservableService.PostUpdateObserver) obj;
                        observer.call(old, entity);
                    }
                }
            }
        }
        return result;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
