package hi.zhangyang.annotation.util;

import hi.zhangyang.annotation.annotation.NeedSetValue;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//ApplicationContextAware 可以拿到上下文
@Component
public class BeanUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (this.applicationContext == null) {
            this.applicationContext = applicationContext;
        }
    }

    public void setFieldValueForCollection(Collection col) throws Exception {
        //最终目的： result = order list {id:1, customerId:2, customerName: Kane } customerName == @Annotation
        //反射拿注解，有了注解，取出来值

        Class<?> clazz = col.iterator().next().getClass(); //拿出来order对象
        Field[] fields = clazz.getDeclaredFields(); //拿出来order对象的属性
        Map<String, Object> cache = new HashMap<>();

        for (Field needField : fields) {
            NeedSetValue nv = needField.getAnnotation(NeedSetValue.class);//目的是拿到注解
            if (null == nv) {
                continue;
            } else {
                //needField就是customerName
                System.out.println();
                needField.setAccessible(true); //注解设为可见
                Object bean = this.applicationContext.getBean(nv.beanClass());
                Method method = nv.beanClass().getMethod(nv.method(), clazz.getDeclaredField(nv.param()).getType());
                Field paramField = clazz.getDeclaredField(nv.param()); //param入参是customerId，拿到了id之后来做循环获取名字

                paramField.setAccessible(true);

                Field targetField = null;
                String keyPrefix = nv.beanClass() + "-" + nv.method() + "-" + nv.targetField() + "-";

                for (Object obj : col) { //获取结果集
                    Object paramValue = paramField.get(obj);

                    if (null == paramValue) {
                        continue;
                    }
                    Object value = null;
                    String key = keyPrefix + paramValue;
                    if (cache.containsKey(key)) {
                        value = cache.get(key);
                    } else {
                        value = method.invoke(bean, paramValue);
                        if (value != null) {
                            if (targetField == null) {
                                targetField = value.getClass().getDeclaredField(nv.targetField());
                                targetField.setAccessible(true);
                            }
                            value = targetField.get(value);
                        }
                        cache.put(key, value);
                    }
                    needField.set(obj, value); //needField是customerName

                }

            }
        }
    }
}

